package com.facturacion.electronica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

/**
 * Entidad para representar un Comprobante de Pago Electrónico
 */
@Entity
@Table(name = "comprobantes")
public class Comprobante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(unique = true, length = 20)
    private String serie;
    
    @NotNull
    private Integer numero;
    
    @NotBlank
    @Size(max = 2)
    private String tipoDocumento; // 01=Factura, 03=Boleta, 07=Nota Crédito, 08=Nota Débito
    
    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal montoTotal;
    
    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal igv;
    
    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal subtotal;
    
    @NotBlank
    @Size(max = 3)
    private String moneda; // PEN, USD
    
    @NotNull
    private LocalDateTime fechaEmision;
    
    private LocalDateTime fechaVencimiento;
    
    @Enumerated(EnumType.STRING)
    private EstadoComprobante estado;
    
    // Relación con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
    
    // Relación con Detalles del Comprobante
    @OneToMany(mappedBy = "comprobante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleComprobante> detalles;
    
    // Información SUNAT
    private String codigoHash;
    private String xmlFirmado;
    private String respuestaSunat;
    private LocalDateTime fechaEnvioSunat;
    private String estadoSunat;
    
    // Constructores
    public Comprobante() {}
    
    public Comprobante(String serie, Integer numero, String tipoDocumento) {
        this.serie = serie;
        this.numero = numero;
        this.tipoDocumento = tipoDocumento;
        this.estado = EstadoComprobante.BORRADOR;
        this.fechaEmision = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    
    public BigDecimal getIgv() { return igv; }
    public void setIgv(BigDecimal igv) { this.igv = igv; }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    
    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    
    public EstadoComprobante getEstado() { return estado; }
    public void setEstado(EstadoComprobante estado) { this.estado = estado; }
    
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    
    public List<DetalleComprobante> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleComprobante> detalles) { this.detalles = detalles; }
    
    public String getCodigoHash() { return codigoHash; }
    public void setCodigoHash(String codigoHash) { this.codigoHash = codigoHash; }
    
    public String getXmlFirmado() { return xmlFirmado; }
    public void setXmlFirmado(String xmlFirmado) { this.xmlFirmado = xmlFirmado; }
    
    public String getRespuestaSunat() { return respuestaSunat; }
    public void setRespuestaSunat(String respuestaSunat) { this.respuestaSunat = respuestaSunat; }
    
    public LocalDateTime getFechaEnvioSunat() { return fechaEnvioSunat; }
    public void setFechaEnvioSunat(LocalDateTime fechaEnvioSunat) { this.fechaEnvioSunat = fechaEnvioSunat; }
    
    public String getEstadoSunat() { return estadoSunat; }
    public void setEstadoSunat(String estadoSunat) { this.estadoSunat = estadoSunat; }
    
    /**
     * Calcula el total del comprobante basado en los detalles
     */
    public void calcularTotales() {
        if (detalles != null && !detalles.isEmpty()) {
            BigDecimal subtotalCalculado = detalles.stream()
                .map(DetalleComprobante::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            this.subtotal = subtotalCalculado;
            this.igv = subtotalCalculado.multiply(new BigDecimal("0.18")); // IGV 18%
            this.montoTotal = subtotalCalculado.add(this.igv);
        }
    }
    
    /**
     * Genera el número completo del comprobante (Serie-Número)
     */
    public String getNumeroCompleto() {
        return String.format("%s-%08d", serie, numero);
    }
    
    /**
     * Estados posibles de un comprobante
     */
    public enum EstadoComprobante {
        BORRADOR,
        GENERADO,
        FIRMADO,
        ENVIADO_SUNAT,
        ACEPTADO_SUNAT,
        RECHAZADO_SUNAT,
        ANULADO
    }
}