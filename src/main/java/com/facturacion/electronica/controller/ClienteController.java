package com.facturacion.electronica.controller;

import com.facturacion.electronica.entity.Cliente;
import com.facturacion.electronica.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar clientes
 */
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Obtener todos los clientes
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Obtener clientes activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> obtenerActivos() {
        List<Cliente> clientes = clienteRepository.findByActivoTrue();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Obtener cliente por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Buscar cliente por número de documento
     */
    @GetMapping("/documento/{numeroDocumento}")
    public ResponseEntity<Cliente> obtenerPorDocumento(@PathVariable String numeroDocumento) {
        Optional<Cliente> cliente = clienteRepository.findByNumeroDocumento(numeroDocumento);
        return cliente.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Buscar clientes por razón social
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarPorRazonSocial(@RequestParam String razonSocial) {
        List<Cliente> clientes = clienteRepository.findByRazonSocialContainingIgnoreCase(razonSocial);
        return ResponseEntity.ok(clientes);
    }

    /**
     * Crear nuevo cliente
     */
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        try {
            // Verificar si ya existe el número de documento
            if (clienteRepository.existsByNumeroDocumento(cliente.getNumeroDocumento())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
            }
            
            Cliente clienteGuardado = clienteRepository.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Actualizar cliente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        try {
            if (!clienteRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            
            cliente.setId(id);
            Cliente clienteActualizado = clienteRepository.save(cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Desactivar cliente (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            Cliente c = cliente.get();
            c.setActivo(false);
            clienteRepository.save(c);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Verificar si existe un cliente con el número de documento
     */
    @GetMapping("/existe/{numeroDocumento}")
    public ResponseEntity<Boolean> existePorDocumento(@PathVariable String numeroDocumento) {
        boolean existe = clienteRepository.existsByNumeroDocumento(numeroDocumento);
        return ResponseEntity.ok(existe);
    }
}