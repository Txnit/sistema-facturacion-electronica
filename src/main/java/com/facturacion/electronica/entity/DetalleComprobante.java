package com.facturacion.electronica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Entidad para representar el detalle de un comprobante
 */
@Entity
@Table(name = "detalle_comprobantes")
public class DetalleComprobante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Integer item; // Número de línea
    
    @NotBlank
    @Size(max = 50)
    private String codigo; // Código del producto/servicio
    
    @NotBlank
    @Size(max = 300)
    private String descripcion;
    
    @NotNull
    @DecimalMin("0.01")
    @Column(precision = 10, scale = 2)
    private BigDecimal cantidad;
    
    @NotBlank
    @Size(max = 10)
    private String unidadMedida; // NIU, ZZ, etc.
    
    @NotNull
    @DecimalMin("0.01")
    @Column(precision = 12, scale = 2)
    private BigDecimal precioUnitario;
    
    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal subtotal;
    
    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal igv;
    
    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal total;
    
    @NotBlank
    @Size(max = 4)
    private String tipoAfectacionIgv; // 10=Gravado, 20=Exonerado, 30=Inafecto
    
    // Relación con Comprobante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprobante_id", referencedColumnName = "id")
    private Comprobante comprobante;
    
    // Relación con Producto (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;
    
    // Constructores
    public DetalleComprobante() {}
    
    public DetalleComprobante(Integer item, String codigo, String descripcion, 
                            BigDecimal cantidad, String unidadMedida, BigDecimal precioUnitario) {
        this.item = item;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
        this.precioUnitario = precioUnitario;
        this.tipoAfectacionIgv = "10"; // Por defecto gravado
        calcularTotales();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getItem() { return item; }
    public void setItem(Integer item) { this.item = item; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { 
        this.cantidad = cantidad;
        calcularTotales();
    }
    
    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }
    
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { 
        this.precioUnitario = precioUnitario;
        calcularTotales();
    }
    
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    
    public BigDecimal getIgv() { return igv; }
    public void setIgv(BigDecimal igv) { this.igv = igv; }
    
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    
    public String getTipoAfectacionIgv() { return tipoAfectacionIgv; }
    public void setTipoAfectacionIgv(String tipoAfectacionIgv) { 
        this.tipoAfectacionIgv = tipoAfectacionIgv;
        calcularTotales();
    }
    
    public Comprobante getComprobante() { return comprobante; }
    public void setComprobante(Comprobante comprobante) { this.comprobante = comprobante; }
    
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    
    /**
     * Calcula los totales del detalle basado en cantidad, precio y tipo de afectación
     */
    public void calcularTotales() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = cantidad.multiply(precioUnitario);
            
            // Calcular IGV según tipo de afectación
            if ("10".equals(tipoAfectacionIgv)) { // Gravado
                this.igv = subtotal.multiply(new BigDecimal("0.18"));
            } else { // Exonerado, Inafecto, etc.
                this.igv = BigDecimal.ZERO;
            }
            
            this.total = subtotal.add(igv);
        }
    }
}