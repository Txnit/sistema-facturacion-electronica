package com.facturacion.electronica.repository;

import com.facturacion.electronica.entity.DetalleComprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar entidades DetalleComprobante
 */
@Repository
public interface DetalleComprobanteRepository extends JpaRepository<DetalleComprobante, Long> {
    
    /**
     * Buscar detalles por comprobante
     */
    List<DetalleComprobante> findByComprobanteIdOrderByItem(Long comprobanteId);
    
    /**
     * Buscar detalles por producto
     */
    List<DetalleComprobante> findByProductoId(Long productoId);
    
    /**
     * Obtener el último número de item para un comprobante
     */
    @Query("SELECT MAX(d.item) FROM DetalleComprobante d WHERE d.comprobante.id = :comprobanteId")
    Integer findMaxItemByComprobanteId(@Param("comprobanteId") Long comprobanteId);
}