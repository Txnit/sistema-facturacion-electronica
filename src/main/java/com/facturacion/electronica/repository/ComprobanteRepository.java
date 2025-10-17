package com.facturacion.electronica.repository;

import com.facturacion.electronica.entity.Comprobante;
import com.facturacion.electronica.entity.Comprobante.EstadoComprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar entidades Comprobante
 */
@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {
    
    /**
     * Buscar comprobante por serie y número
     */
    Optional<Comprobante> findBySerieAndNumero(String serie, Integer numero);
    
    /**
     * Buscar comprobantes por cliente
     */
    List<Comprobante> findByClienteId(Long clienteId);
    
    /**
     * Buscar comprobantes por estado
     */
    List<Comprobante> findByEstado(EstadoComprobante estado);
    
    /**
     * Buscar comprobantes por tipo de documento
     */
    List<Comprobante> findByTipoDocumento(String tipoDocumento);
    
    /**
     * Buscar comprobantes por rango de fechas
     */
    @Query("SELECT c FROM Comprobante c WHERE c.fechaEmision BETWEEN :fechaInicio AND :fechaFin")
    List<Comprobante> findByFechaEmisionBetween(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                               @Param("fechaFin") LocalDateTime fechaFin);
    
    /**
     * Obtener el último número de serie para un tipo de documento y serie
     */
    @Query("SELECT MAX(c.numero) FROM Comprobante c WHERE c.serie = :serie AND c.tipoDocumento = :tipoDocumento")
    Optional<Integer> findMaxNumeroBySerieAndTipoDocumento(@Param("serie") String serie, 
                                                          @Param("tipoDocumento") String tipoDocumento);
    
    /**
     * Buscar comprobantes pendientes de envío a SUNAT
     */
    @Query("SELECT c FROM Comprobante c WHERE c.estado IN ('FIRMADO') AND c.fechaEnvioSunat IS NULL")
    List<Comprobante> findPendientesEnvioSunat();
    
    /**
     * Buscar comprobantes por hash
     */
    Optional<Comprobante> findByCodigoHash(String codigoHash);
}