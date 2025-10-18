package com.facturacion.electronica.config;

import com.facturacion.electronica.entity.Producto;
import com.facturacion.electronica.entity.Producto.TipoProducto;
import com.facturacion.electronica.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Inicializador de datos por defecto para el sistema
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen productos
        if (productoRepository.count() == 0) {
            crearProductosPorDefecto();
        }
    }

    private void crearProductosPorDefecto() {
        System.out.println("🏭 Creando productos por defecto...");

        // Producto genérico para servicios
        Producto servicioGenerico = new Producto();
        servicioGenerico.setCodigo("SERV-001");
        servicioGenerico.setDescripcion("Servicio Genérico");
        servicioGenerico.setUnidadMedida("UNI");
        servicioGenerico.setPrecio(new BigDecimal("100.00"));
        servicioGenerico.setTipo(TipoProducto.SERVICIO);
        servicioGenerico.setActivo(true);
        servicioGenerico.setTipoAfectacionIgv("10"); // Gravado
        productoRepository.save(servicioGenerico);

        // Producto genérico para bienes
        Producto productoGenerico = new Producto();
        productoGenerico.setCodigo("PROD-001");
        productoGenerico.setDescripcion("Producto Genérico");
        productoGenerico.setUnidadMedida("UNI");
        productoGenerico.setPrecio(new BigDecimal("50.00"));
        productoGenerico.setTipo(TipoProducto.PRODUCTO);
        productoGenerico.setActivo(true);
        productoGenerico.setTipoAfectacionIgv("10"); // Gravado
        productoRepository.save(productoGenerico);

        System.out.println("✅ Productos por defecto creados exitosamente");
    }
}