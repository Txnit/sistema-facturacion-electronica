package com.facturacion.electronica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * Entidad para representar un Cliente
 */
@Entity
@Table(name = "clientes")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 11)
    @Column(unique = true)
    private String numeroDocumento; // RUC o DNI
    
    @NotBlank
    @Size(max = 2)
    private String tipoDocumento; // 6=RUC, 1=DNI
    
    @NotBlank
    @Size(max = 200)
    private String razonSocial;
    
    @Size(max = 200)
    private String nombreComercial;
    
    @NotBlank
    @Size(max = 300)
    private String direccion;
    
    @Size(max = 6)
    private String ubigeo;
    
    @Size(max = 100)
    private String distrito;
    
    @Size(max = 100)
    private String provincia;
    
    @Size(max = 100)
    private String departamento;
    
    @Email
    @Size(max = 100)
    private String email;
    
    @Size(max = 20)
    private String telefono;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    // Campos adicionales para facturación electrónica SUNAT
    @Size(max = 100)
    private String tipoContribuyente; // Ej: "SOCIEDAD ANONIMA CERRADA"
    
    @Column(name = "fecha_inscripcion")
    private String fechaInscripcion; // Formato: dd/mm/yyyy
    
    @Column(name = "fecha_inicio_actividades")
    private String fechaInicioActividades; // Formato: dd/mm/yyyy
    
    @Size(max = 50)
    private String estadoContribuyente; // ACTIVO, SUSPENDIDO, INHABILITADO, etc.
    
    @Size(max = 50)
    private String condicionContribuyente; // HABIDO, NO HABIDO, NO HALLADO, etc.
    
    @Size(max = 100)
    private String sistemaEmision; // MANUAL/COMPUTARIZADO, ELECTRONICO, etc.
    
    @Size(max = 100)
    private String actividadComercioExterior; // SIN ACTIVIDAD, CON ACTIVIDAD
    
    @Size(max = 50)
    private String sistemaContabilidad; // COMPUTARIZADO, MANUAL, etc.
    
    // Actividades económicas
    @Size(max = 500)
    private String actividadPrincipal; // Código CIIU y descripción
    
    @Size(max = 500)
    private String actividadSecundaria1;
    
    @Size(max = 500)
    private String actividadSecundaria2;
    
    // Comprobantes autorizados
    @Size(max = 300)
    private String comprobantesAutorizados; // FACTURA, BOLETA, GUIA, etc.
    
    // Sistema de emisión electrónica
    @Size(max = 200)
    private String sistemaEmisionElectronica; // FACTURA PORTAL DESDE dd/mm/yyyy
    
    @Size(max = 50)
    private String emisorElectronicoDesde; // dd/mm/yyyy
    
    @Size(max = 300)
    private String comprobantesElectronicos; // GUIA (desde dd/mm/yyyy), FACTURA (desde dd/mm/yyyy)
    
    @Size(max = 50)
    private String afiliadoPleDesde; // Fecha o "-" si no aplica
    
    @Size(max = 200)
    private String padrones; // NINGUNO o lista de padrones
    
    // Campos para validación de emisión electrónica
    @Column(name = "puede_emitir_electronicamente")
    private Boolean puedeEmitirElectronicamente = false;
    
    @Column(name = "fecha_autorizacion_electronica")
    private String fechaAutorizacionElectronica;
    
    // Relación con Comprobantes
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Comprobante> comprobantes;
    
    // Constructores
    public Cliente() {}
    
    public Cliente(String numeroDocumento, String tipoDocumento, String razonSocial) {
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.razonSocial = razonSocial;
        this.activo = true;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    
    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getUbigeo() { return ubigeo; }
    public void setUbigeo(String ubigeo) { this.ubigeo = ubigeo; }
    
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }
    
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public List<Comprobante> getComprobantes() { return comprobantes; }
    public void setComprobantes(List<Comprobante> comprobantes) { this.comprobantes = comprobantes; }
    
    // Getters y setters para campos de facturación electrónica
    public String getTipoContribuyente() { return tipoContribuyente; }
    public void setTipoContribuyente(String tipoContribuyente) { this.tipoContribuyente = tipoContribuyente; }
    
    public String getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(String fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    
    public String getFechaInicioActividades() { return fechaInicioActividades; }
    public void setFechaInicioActividades(String fechaInicioActividades) { this.fechaInicioActividades = fechaInicioActividades; }
    
    public String getEstadoContribuyente() { return estadoContribuyente; }
    public void setEstadoContribuyente(String estadoContribuyente) { this.estadoContribuyente = estadoContribuyente; }
    
    public String getCondicionContribuyente() { return condicionContribuyente; }
    public void setCondicionContribuyente(String condicionContribuyente) { this.condicionContribuyente = condicionContribuyente; }
    
    public String getSistemaEmision() { return sistemaEmision; }
    public void setSistemaEmision(String sistemaEmision) { this.sistemaEmision = sistemaEmision; }
    
    public String getActividadComercioExterior() { return actividadComercioExterior; }
    public void setActividadComercioExterior(String actividadComercioExterior) { this.actividadComercioExterior = actividadComercioExterior; }
    
    public String getSistemaContabilidad() { return sistemaContabilidad; }
    public void setSistemaContabilidad(String sistemaContabilidad) { this.sistemaContabilidad = sistemaContabilidad; }
    
    public String getActividadPrincipal() { return actividadPrincipal; }
    public void setActividadPrincipal(String actividadPrincipal) { this.actividadPrincipal = actividadPrincipal; }
    
    public String getActividadSecundaria1() { return actividadSecundaria1; }
    public void setActividadSecundaria1(String actividadSecundaria1) { this.actividadSecundaria1 = actividadSecundaria1; }
    
    public String getActividadSecundaria2() { return actividadSecundaria2; }
    public void setActividadSecundaria2(String actividadSecundaria2) { this.actividadSecundaria2 = actividadSecundaria2; }
    
    public String getComprobantesAutorizados() { return comprobantesAutorizados; }
    public void setComprobantesAutorizados(String comprobantesAutorizados) { this.comprobantesAutorizados = comprobantesAutorizados; }
    
    public String getSistemaEmisionElectronica() { return sistemaEmisionElectronica; }
    public void setSistemaEmisionElectronica(String sistemaEmisionElectronica) { this.sistemaEmisionElectronica = sistemaEmisionElectronica; }
    
    public String getEmisorElectronicoDesde() { return emisorElectronicoDesde; }
    public void setEmisorElectronicoDesde(String emisorElectronicoDesde) { this.emisorElectronicoDesde = emisorElectronicoDesde; }
    
    public String getComprobantesElectronicos() { return comprobantesElectronicos; }
    public void setComprobantesElectronicos(String comprobantesElectronicos) { this.comprobantesElectronicos = comprobantesElectronicos; }
    
    public String getAfiliadoPleDesde() { return afiliadoPleDesde; }
    public void setAfiliadoPleDesde(String afiliadoPleDesde) { this.afiliadoPleDesde = afiliadoPleDesde; }
    
    public String getPadrones() { return padrones; }
    public void setPadrones(String padrones) { this.padrones = padrones; }
    
    public Boolean getPuedeEmitirElectronicamente() { return puedeEmitirElectronicamente; }
    public void setPuedeEmitirElectronicamente(Boolean puedeEmitirElectronicamente) { this.puedeEmitirElectronicamente = puedeEmitirElectronicamente; }
    
    public String getFechaAutorizacionElectronica() { return fechaAutorizacionElectronica; }
    public void setFechaAutorizacionElectronica(String fechaAutorizacionElectronica) { this.fechaAutorizacionElectronica = fechaAutorizacionElectronica; }
    
    /**
     * Obtiene el nombre a mostrar (comercial o razón social)
     */
    public String getNombreDisplay() {
        return nombreComercial != null && !nombreComercial.trim().isEmpty() 
            ? nombreComercial : razonSocial;
    }
    
    /**
     * Verifica si es persona jurídica (RUC)
     */
    public boolean esPersonaJuridica() {
        return "6".equals(tipoDocumento);
    }
    
    /**
     * Verifica si puede emitir facturas electrónicas
     */
    public boolean puedeEmitirFacturasElectronicas() {
        return puedeEmitirElectronicamente != null && puedeEmitirElectronicamente &&
               comprobantesElectronicos != null && comprobantesElectronicos.toLowerCase().contains("factura");
    }
    
    /**
     * Verifica si puede emitir boletas electrónicas
     */
    public boolean puedeEmitirBoletasElectronicas() {
        return puedeEmitirElectronicamente != null && puedeEmitirElectronicamente &&
               comprobantesElectronicos != null && comprobantesElectronicos.toLowerCase().contains("boleta");
    }
    
    /**
     * Verifica si puede emitir guías electrónicas
     */
    public boolean puedeEmitirGuiasElectronicas() {
        return puedeEmitirElectronicamente != null && puedeEmitirElectronicamente &&
               comprobantesElectronicos != null && comprobantesElectronicos.toLowerCase().contains("guia");
    }
    
    /**
     * Verifica si el contribuyente está activo y habido
     */
    public boolean estaActivoYHabido() {
        return "ACTIVO".equalsIgnoreCase(estadoContribuyente) && 
               "HABIDO".equalsIgnoreCase(condicionContribuyente);
    }
    
    /**
     * Obtiene la actividad económica principal como código
     */
    public String getCodigoActividadPrincipal() {
        if (actividadPrincipal == null) return null;
        String[] partes = actividadPrincipal.split(" - ");
        return partes.length > 1 ? partes[1] : null; // Retorna el código CIIU
    }
}