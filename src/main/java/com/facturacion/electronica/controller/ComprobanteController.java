package com.facturacion.electronica.controller;

import com.facturacion.electronica.entity.Comprobante;
import com.facturacion.electronica.entity.Comprobante.EstadoComprobante;
import com.facturacion.electronica.repository.ComprobanteRepository;
import com.facturacion.electronica.service.FacturacionService;
import com.facturacion.electronica.service.FacturacionService.CreateComprobanteRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para gestionar comprobantes de pago electrónicos
 */
@RestController
@RequestMapping("/api/comprobantes")
@CrossOrigin(origins = "*")
public class ComprobanteController {

    @Autowired
    private ComprobanteRepository comprobanteRepository;
    
    @Autowired
    private FacturacionService facturacionService;

    /**
     * Obtener todos los comprobantes
     */
    @GetMapping
    public ResponseEntity<List<Comprobante>> obtenerTodos() {
        List<Comprobante> comprobantes = comprobanteRepository.findAll();
        return ResponseEntity.ok(comprobantes);
    }

    /**
     * Obtener comprobante por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comprobante> obtenerPorId(@PathVariable Long id) {
        Optional<Comprobante> comprobante = comprobanteRepository.findById(id);
        return comprobante.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Buscar comprobantes por cliente
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Comprobante>> obtenerPorCliente(@PathVariable Long clienteId) {
        List<Comprobante> comprobantes = facturacionService.listarPorCliente(clienteId);
        return ResponseEntity.ok(comprobantes);
    }

    /**
     * Buscar comprobantes por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Comprobante>> obtenerPorEstado(@PathVariable EstadoComprobante estado) {
        List<Comprobante> comprobantes = comprobanteRepository.findByEstado(estado);
        return ResponseEntity.ok(comprobantes);
    }

    /**
     * Buscar comprobantes por tipo de documento
     */
    @GetMapping("/tipo/{tipoDocumento}")
    public ResponseEntity<List<Comprobante>> obtenerPorTipo(@PathVariable String tipoDocumento) {
        List<Comprobante> comprobantes = comprobanteRepository.findByTipoDocumento(tipoDocumento);
        return ResponseEntity.ok(comprobantes);
    }

    /**
     * Crear nuevo comprobante
     */
    @PostMapping
    public ResponseEntity<Comprobante> crear(@Valid @RequestBody CreateComprobanteRequest request) {
        try {
            Comprobante comprobante = facturacionService.crearComprobante(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(comprobante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Firmar comprobante digitalmente
     */
    @PostMapping("/{id}/firmar")
    public ResponseEntity<Comprobante> firmar(@PathVariable Long id) {
        try {
            Comprobante comprobante = facturacionService.firmarComprobante(id);
            return ResponseEntity.ok(comprobante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Enviar comprobante a SUNAT
     */
    @PostMapping("/{id}/enviar-sunat")
    public ResponseEntity<Comprobante> enviarASunat(@PathVariable Long id) {
        try {
            Comprobante comprobante = facturacionService.enviarASunat(id);
            return ResponseEntity.ok(comprobante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Procesar flujo completo: firmar y enviar
     */
    @PostMapping("/{id}/procesar")
    public ResponseEntity<Map<String, Object>> procesarCompleto(@PathVariable Long id) {
        try {
            // Firmar y enviar comprobante
            facturacionService.firmarComprobante(id);
            Comprobante comprobanteEnviado = facturacionService.enviarASunat(id);
            
            Map<String, Object> resultado = new HashMap<>();
            resultado.put("comprobante", comprobanteEnviado);
            resultado.put("mensaje", "Comprobante procesado exitosamente");
            resultado.put("numeroCompleto", comprobanteEnviado.getNumeroCompleto());
            resultado.put("fechaProceso", LocalDateTime.now());
            
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("fechaError", LocalDateTime.now());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Obtener comprobantes pendientes de envío
     */
    @GetMapping("/pendientes-envio")
    public ResponseEntity<List<Comprobante>> obtenerPendientesEnvio() {
        List<Comprobante> pendientes = facturacionService.buscarPendientesEnvio();
        return ResponseEntity.ok(pendientes);
    }

    /**
     * Obtener estadísticas de comprobantes
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        // Contar por estado
        for (EstadoComprobante estado : EstadoComprobante.values()) {
            long cantidad = comprobanteRepository.findByEstado(estado).size();
            stats.put("total_" + estado.name().toLowerCase(), cantidad);
        }
        
        // Total general
        long total = comprobanteRepository.count();
        stats.put("total_general", total);
        stats.put("fecha_consulta", LocalDateTime.now());
        
        return ResponseEntity.ok(stats);
    }

    /**
     * Buscar comprobante por serie y número
     */
    @GetMapping("/buscar/{serie}/{numero}")
    public ResponseEntity<Comprobante> buscarPorSerieNumero(@PathVariable String serie, @PathVariable Integer numero) {
        Optional<Comprobante> comprobante = comprobanteRepository.findBySerieAndNumero(serie, numero);
        return comprobante.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }
}