package com.facturacion.electronica.repository;

import com.facturacion.electronica.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar entidades Cliente
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Buscar cliente por número de documento
     */
    Optional<Cliente> findByNumeroDocumento(String numeroDocumento);
    
    /**
     * Buscar clientes activos
     */
    List<Cliente> findByActivoTrue();
    
    /**
     * Buscar clientes por tipo de documento
     */
    List<Cliente> findByTipoDocumento(String tipoDocumento);
    
    /**
     * Buscar clientes por razón social (ignorando mayúsculas/minúsculas)
     */
    @Query("SELECT c FROM Cliente c WHERE UPPER(c.razonSocial) LIKE UPPER(CONCAT('%', :razonSocial, '%'))")
    List<Cliente> findByRazonSocialContainingIgnoreCase(@Param("razonSocial") String razonSocial);
    
    /**
     * Verificar si existe un cliente con el número de documento especificado
     */
    boolean existsByNumeroDocumento(String numeroDocumento);
}