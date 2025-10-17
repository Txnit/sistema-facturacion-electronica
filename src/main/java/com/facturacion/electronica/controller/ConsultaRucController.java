package com.facturacion.electronica.controller;

import com.facturacion.electronica.dto.ConsultaRucResponse;
import com.facturacion.electronica.service.ConsultaRucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consulta-ruc")
@CrossOrigin(origins = "*")
public class ConsultaRucController {
    
    @Autowired
    private ConsultaRucService consultaRucService;
    
    /**
     * Endpoint para consultar RUC en SUNAT
     */
    @GetMapping("/{ruc}")
    public ResponseEntity<ConsultaRucResponse> consultarRuc(@PathVariable String ruc) {
        try {
            ConsultaRucResponse response = consultaRucService.consultarRuc(ruc);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ConsultaRucResponse errorResponse = new ConsultaRucResponse(
                false, 
                "Error interno del servidor: " + e.getMessage(), 
                null
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    
    /**
     * Endpoint para validar formato de RUC
     */
    @GetMapping("/validar/{ruc}")
    public ResponseEntity<Boolean> validarRuc(@PathVariable String ruc) {
        try {
            boolean esValido = ruc != null && ruc.matches("^\\d{11}$");
            return ResponseEntity.ok(esValido);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(false);
        }
    }
}