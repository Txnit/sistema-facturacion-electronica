package com.facturacion.electronica.service;

import com.facturacion.electronica.entity.*;
import com.facturacion.electronica.entity.Comprobante.EstadoComprobante;
import com.facturacion.electronica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la lógica de negocio de facturación
 */
@Service
@Transactional
public class FacturacionService {

    @Autowired
    private ComprobanteRepository comprobanteRepository;
    
    @Autowired
    private DetalleComprobanteRepository detalleRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ProductoRepository productoRepository;

    // Constantes para cálculos de IGV
    private static final BigDecimal IGV_RATE = new BigDecimal("0.18"); // 18%

    /**
     * Crear un nuevo comprobante con sus detalles
     */
    public Comprobante crearComprobante(CreateComprobanteRequest request) {
        // Validar que el cliente existe
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Crear el comprobante
        Comprobante comprobante = new Comprobante();
        comprobante.setCliente(cliente);
        comprobante.setTipoDocumento(request.getTipoDocumento());
        comprobante.setMoneda(request.getMoneda());
        comprobante.setFechaEmision(LocalDateTime.now());
        
        // Generar serie y número
        String serie = generarSerie(request.getTipoDocumento());
        Integer numero = generarNumero(serie, request.getTipoDocumento());
        comprobante.setSerie(serie);
        comprobante.setNumero(numero);
        
        comprobante.setEstado(EstadoComprobante.BORRADOR);

        // Guardar comprobante temporal
        comprobante = comprobanteRepository.save(comprobante);

        // Procesar detalles
        BigDecimal subtotalTotal = BigDecimal.ZERO;
        BigDecimal igvTotal = BigDecimal.ZERO;
        
        int item = 1;
        for (CreateDetalleRequest detalle : request.getDetalles()) {
            DetalleComprobante detalleComprobante = crearDetalle(comprobante, detalle, item++);
            subtotalTotal = subtotalTotal.add(detalleComprobante.getSubtotal());
            igvTotal = igvTotal.add(detalleComprobante.getIgv());
        }

        // Actualizar totales del comprobante
        comprobante.setSubtotal(subtotalTotal);
        comprobante.setIgv(igvTotal);
        comprobante.setMontoTotal(subtotalTotal.add(igvTotal));
        comprobante.setEstado(EstadoComprobante.GENERADO);

        return comprobanteRepository.save(comprobante);
    }

    /**
     * Crear un detalle de comprobante
     */
    private DetalleComprobante crearDetalle(Comprobante comprobante, CreateDetalleRequest request, int item) {
        // Validar que el producto existe
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetalleComprobante detalle = new DetalleComprobante();
        detalle.setComprobante(comprobante);
        detalle.setProducto(producto);
        detalle.setItem(item);
        detalle.setCodigo(producto.getCodigo());
        detalle.setDescripcion(producto.getDescripcion());
        detalle.setUnidadMedida(producto.getUnidadMedida());
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setTipoAfectacionIgv(producto.getTipoAfectacionIgv());

        // Calcular subtotal
        BigDecimal subtotal = producto.getPrecio().multiply(request.getCantidad())
                .setScale(2, RoundingMode.HALF_UP);
        
        // Calcular IGV según tipo de afectación
        BigDecimal igv = BigDecimal.ZERO;
        if ("10".equals(producto.getTipoAfectacionIgv())) { // Gravado
            igv = subtotal.multiply(IGV_RATE).setScale(2, RoundingMode.HALF_UP);
        }
        
        detalle.setSubtotal(subtotal);
        detalle.setIgv(igv);
        detalle.setTotal(subtotal.add(igv));

        // Actualizar stock del producto
        if (producto.getStock() != null) {
            producto.reducirStock(request.getCantidad());
            productoRepository.save(producto);
        }

        return detalleRepository.save(detalle);
    }

    /**
     * Generar serie para el tipo de documento
     */
    private String generarSerie(String tipoDocumento) {
        switch (tipoDocumento) {
            case "01": return "F001"; // Factura
            case "03": return "B001"; // Boleta
            case "07": return "FC01"; // Nota de Crédito
            case "08": return "FD01"; // Nota de Débito
            default: return "E001";
        }
    }

    /**
     * Generar número correlativo
     */
    private Integer generarNumero(String serie, String tipoDocumento) {
        Optional<Integer> maxNumero = comprobanteRepository
                .findMaxNumeroBySerieAndTipoDocumento(serie, tipoDocumento);
        return maxNumero.orElse(0) + 1;
    }

    /**
     * Firmar comprobante digitalmente
     */
    public Comprobante firmarComprobante(Long comprobanteId) {
        Comprobante comprobante = comprobanteRepository.findById(comprobanteId)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado"));

        if (comprobante.getEstado() != EstadoComprobante.GENERADO) {
            throw new RuntimeException("El comprobante debe estar en estado GENERADO para ser firmado");
        }

        // TODO: Implementar firma digital real
        // Por ahora simulamos la firma
        String hash = generarHash(comprobante);
        comprobante.setCodigoHash(hash);
        comprobante.setXmlFirmado("XML_FIRMADO_SIMULADO");
        comprobante.setEstado(EstadoComprobante.FIRMADO);

        return comprobanteRepository.save(comprobante);
    }

    /**
     * Enviar comprobante a SUNAT
     */
    public Comprobante enviarASunat(Long comprobanteId) {
        Comprobante comprobante = comprobanteRepository.findById(comprobanteId)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado"));

        if (comprobante.getEstado() != EstadoComprobante.FIRMADO) {
            throw new RuntimeException("El comprobante debe estar FIRMADO para ser enviado a SUNAT");
        }

        // TODO: Implementar envío real a SUNAT
        // Por ahora simulamos el envío
        comprobante.setFechaEnvioSunat(LocalDateTime.now());
        comprobante.setEstadoSunat("ACEPTADO");
        comprobante.setRespuestaSunat("CDR_SIMULADO");
        comprobante.setEstado(EstadoComprobante.ACEPTADO_SUNAT);

        return comprobanteRepository.save(comprobante);
    }

    /**
     * Listar comprobantes por cliente
     */
    public List<Comprobante> listarPorCliente(Long clienteId) {
        return comprobanteRepository.findByClienteId(clienteId);
    }

    /**
     * Buscar comprobantes pendientes de envío
     */
    public List<Comprobante> buscarPendientesEnvio() {
        return comprobanteRepository.findPendientesEnvioSunat();
    }

    /**
     * Generar hash simulado para el comprobante
     */
    private String generarHash(Comprobante comprobante) {
        String data = comprobante.getSerie() + comprobante.getNumero() + 
                     comprobante.getMontoTotal() + comprobante.getFechaEmision();
        return "HASH_" + Math.abs(data.hashCode());
    }

    // Clases DTO para requests
    public static class CreateComprobanteRequest {
        private Long clienteId;
        private String tipoDocumento;
        private String moneda = "PEN";
        private List<CreateDetalleRequest> detalles;

        // Getters y setters
        public Long getClienteId() { return clienteId; }
        public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
        
        public String getTipoDocumento() { return tipoDocumento; }
        public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
        
        public String getMoneda() { return moneda; }
        public void setMoneda(String moneda) { this.moneda = moneda; }
        
        public List<CreateDetalleRequest> getDetalles() { return detalles; }
        public void setDetalles(List<CreateDetalleRequest> detalles) { this.detalles = detalles; }
    }

    public static class CreateDetalleRequest {
        private Long productoId;
        private BigDecimal cantidad;

        // Getters y setters
        public Long getProductoId() { return productoId; }
        public void setProductoId(Long productoId) { this.productoId = productoId; }
        
        public BigDecimal getCantidad() { return cantidad; }
        public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
    }
}