package com.facturacion.electronica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Entidad para representar un Producto o Servicio
 */
@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String codigo;
    
    @NotBlank
    @Size(max = 300)
    private String descripcion;
    
    @NotNull
    @DecimalMin("0.01")
    @Column(precision = 12, scale = 2)
    private BigDecimal precio;
    
    @NotBlank
    @Size(max = 10)
    private String unidadMedida; // NIU, ZZ, etc.
    
    @NotBlank
    @Size(max = 4)
    private String tipoAfectacionIgv; // 10=Gravado, 20=Exonerado, 30=Inafecto
    
    @Enumerated(EnumType.STRING)
    private TipoProducto tipo;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    // Stock (opcional para productos físicos)
    @Column(precision = 10, scale = 2)
    private BigDecimal stock;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal stockMinimo;
    
    // Categoría o clasificación
    @Size(max = 100)
    private String categoria;
    
    // Relación con Detalles de Comprobantes
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<DetalleComprobante> detallesComprobante;
    
    // Constructores
    public Producto() {}
    
    public Producto(String codigo, String descripcion, BigDecimal precio, 
                   String unidadMedida, TipoProducto tipo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.unidadMedida = unidadMedida;
        this.tipo = tipo;
        this.tipoAfectacionIgv = "10"; // Por defecto gravado
        this.activo = true;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    
    public String getTipoAfectacionIgv() { return tipoAfectacionIgv; }
    public void setTipoAfectacionIgv(String tipoAfectacionIgv) { this.tipoAfectacionIgv = tipoAfectacionIgv; }
    
    public TipoProducto getTipo() { return tipo; }
    public void setTipo(TipoProducto tipo) { this.tipo = tipo; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public BigDecimal getStock() { return stock; }
    public void setStock(BigDecimal stock) { this.stock = stock; }
    
    public BigDecimal getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(BigDecimal stockMinimo) { this.stockMinimo = stockMinimo; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public List<DetalleComprobante> getDetallesComprobante() { return detallesComprobante; }
    public void setDetallesComprobante(List<DetalleComprobante> detallesComprobante) { 
        this.detallesComprobante = detallesComprobante; 
    }
    
    /**
     * Verifica si el producto tiene stock suficiente
     */
    public boolean tieneStockSuficiente(BigDecimal cantidadRequerida) {
        if (tipo == TipoProducto.SERVICIO) {
            return true; // Los servicios no tienen stock
        }
        return stock != null && stock.compareTo(cantidadRequerida) >= 0;
    }
    
    /**
     * Reduce el stock del producto
     */
    public void reducirStock(BigDecimal cantidad) {
        if (tipo == TipoProducto.PRODUCTO && stock != null) {
            this.stock = stock.subtract(cantidad);
        }
    }
    
    /**
     * Verifica si el stock está por debajo del mínimo
     */
    public boolean stockBajoMinimo() {
        return stockMinimo != null && stock != null && 
               stock.compareTo(stockMinimo) < 0;
    }
    
    /**
     * Tipos de producto
     */
    public enum TipoProducto {
        PRODUCTO,
        SERVICIO
    }
}