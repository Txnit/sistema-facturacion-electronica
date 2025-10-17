package com.facturacion.electronica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador principal para el sistema de facturación electrónica
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redirigir a la página principal (index.html se sirve automáticamente)
        return "forward:/index.html";
    }
    
    @GetMapping("/api/status")
    @ResponseBody
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("aplicacion", "Sistema de Facturación Electrónica");
        response.put("version", "1.0.0");
        response.put("estado", "Funcionando correctamente");
        response.put("timestamp", LocalDateTime.now());
        response.put("urls", Map.of(
            "baseDatos", "/h2-console",
            "documentacionAPI", "/swagger-ui.html",
            "estado", "/health"
        ));
        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
}