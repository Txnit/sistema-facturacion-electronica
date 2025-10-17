package com.facturacion.electronica.controller;

import com.facturacion.electronica.entity.Producto;
import com.facturacion.electronica.entity.Producto.TipoProducto;
import com.facturacion.electronica.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar productos
 */
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtener todos los productos
     */
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoRepository.findAll();
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtener productos activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> obtenerActivos() {
        List<Producto> productos = productoRepository.findByActivoTrue();
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtener producto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Buscar producto por código
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Producto> obtenerPorCodigo(@PathVariable String codigo) {
        Optional<Producto> producto = productoRepository.findByCodigo(codigo);
        return producto.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Buscar productos por descripción
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorDescripcion(@RequestParam String descripcion) {
        List<Producto> productos = productoRepository.findByDescripcionContainingIgnoreCase(descripcion);
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtener productos por tipo
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Producto>> obtenerPorTipo(@PathVariable TipoProducto tipo) {
        List<Producto> productos = productoRepository.findByTipo(tipo);
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtener productos con stock bajo
     */
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> obtenerStockBajo() {
        List<Producto> productos = productoRepository.findProductosConStockBajo();
        return ResponseEntity.ok(productos);
    }

    /**
     * Crear nuevo producto
     */
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        try {
            // Verificar si ya existe el código
            if (productoRepository.existsByCodigo(producto.getCodigo())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
            }
            
            Producto productoGuardado = productoRepository.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualizar producto
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        try {
            if (!productoRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            producto.setId(id);
            Producto productoActualizado = productoRepository.save(producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Desactivar producto (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto p = producto.get();
            p.setActivo(false);
            productoRepository.save(p);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Actualizar stock de producto
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> actualizarStock(@PathVariable Long id, @RequestParam BigDecimal nuevoStock) {
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto p = producto.get();
            p.setStock(nuevoStock);
            Producto productoActualizado = productoRepository.save(p);
            return ResponseEntity.ok(productoActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Verificar si existe un producto con el código
     */
    @GetMapping("/existe/{codigo}")
    public ResponseEntity<Boolean> existePorCodigo(@PathVariable String codigo) {
        boolean existe = productoRepository.existsByCodigo(codigo);
        return ResponseEntity.ok(existe);
    }
}