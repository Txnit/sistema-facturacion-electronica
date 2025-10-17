package com.facturacion.electronica.repository;

import com.facturacion.electronica.entity.Producto;
import com.facturacion.electronica.entity.Producto.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar entidades Producto
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    /**
     * Buscar producto por código
     */
    Optional<Producto> findByCodigo(String codigo);
    
    /**
     * Buscar productos activos
     */
    List<Producto> findByActivoTrue();
    
    /**
     * Buscar productos por tipo
     */
    List<Producto> findByTipo(TipoProducto tipo);
    
    /**
     * Buscar productos por categoría
     */
    List<Producto> findByCategoria(String categoria);
    
    /**
     * Buscar productos con stock bajo
     */
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo AND p.activo = true")
    List<Producto> findProductosConStockBajo();
    
    /**
     * Buscar productos por descripción (ignorando mayúsculas/minúsculas)
     */
    @Query("SELECT p FROM Producto p WHERE UPPER(p.descripcion) LIKE UPPER(CONCAT('%', :descripcion, '%'))")
    List<Producto> findByDescripcionContainingIgnoreCase(@Param("descripcion") String descripcion);
    
    /**
     * Verificar si existe un producto con el código especificado
     */
    boolean existsByCodigo(String codigo);
}