package com.facturacion.electronica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal del Sistema de Facturación Electrónica
 * 
 * Sistema que permite:
 * - Generación de Comprobantes Electrónicos
 * - Integración con SUNAT
 * - Firma Digital de documentos
 * - Manejo centralizado de CdPE's
 */
@SpringBootApplication
public class SistemaFacturacionElectronicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaFacturacionElectronicaApplication.class, args);
    }
}