package com.facturacion.electronica.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultaRucResponse {
    
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("data")
    private RucData data;
    
    // Constructors
    public ConsultaRucResponse() {}
    
    public ConsultaRucResponse(boolean success, String message, RucData data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    
    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public RucData getData() {
        return data;
    }
    
    public void setData(RucData data) {
        this.data = data;
    }
    
    // Inner class for RUC data
    public static class RucData {
        @JsonProperty("ruc")
        private String ruc;
        
        @JsonProperty("razonSocial")
        private String razonSocial;
        
        @JsonProperty("nombreComercial")
        private String nombreComercial;
        
        @JsonProperty("tipoDocumento")
        private String tipoDocumento;
        
        @JsonProperty("estado")
        private String estado;
        
        @JsonProperty("condicion")
        private String condicion;
        
        @JsonProperty("direccion")
        private String direccion;
        
        @JsonProperty("departamento")
        private String departamento;
        
        @JsonProperty("provincia")
        private String provincia;
        
        @JsonProperty("distrito")
        private String distrito;
        
        @JsonProperty("ubigeo")
        private String ubigeo;
        
        @JsonProperty("actividadEconomica")
        private String actividadEconomica;
        
        // Campos adicionales para facturación electrónica
        @JsonProperty("tipoContribuyente")
        private String tipoContribuyente;
        
        @JsonProperty("fechaInscripcion")
        private String fechaInscripcion;
        
        @JsonProperty("fechaInicioActividades")
        private String fechaInicioActividades;
        
        @JsonProperty("sistemaEmision")
        private String sistemaEmision;
        
        @JsonProperty("actividadComercioExterior")
        private String actividadComercioExterior;
        
        @JsonProperty("sistemaContabilidad")
        private String sistemaContabilidad;
        
        @JsonProperty("actividadPrincipal")
        private String actividadPrincipal;
        
        @JsonProperty("actividadSecundaria1")
        private String actividadSecundaria1;
        
        @JsonProperty("actividadSecundaria2")
        private String actividadSecundaria2;
        
        @JsonProperty("comprobantesAutorizados")
        private String comprobantesAutorizados;
        
        @JsonProperty("sistemaEmisionElectronica")
        private String sistemaEmisionElectronica;
        
        @JsonProperty("emisorElectronicoDesde")
        private String emisorElectronicoDesde;
        
        @JsonProperty("comprobantesElectronicos")
        private String comprobantesElectronicos;
        
        @JsonProperty("afiliadoPleDesde")
        private String afiliadoPleDesde;
        
        @JsonProperty("padrones")
        private String padrones;
        
        @JsonProperty("puedeEmitirElectronicamente")
        private Boolean puedeEmitirElectronicamente;
        
        @JsonProperty("telefono")
        private String telefono;
        
        @JsonProperty("email")
        private String email;
        
        // Constructors
        public RucData() {}
        
        // Getters and Setters
        public String getRuc() {
            return ruc;
        }
        
        public void setRuc(String ruc) {
            this.ruc = ruc;
        }
        
        public String getRazonSocial() {
            return razonSocial;
        }
        
        public void setRazonSocial(String razonSocial) {
            this.razonSocial = razonSocial;
        }
        
        public String getNombreComercial() {
            return nombreComercial;
        }
        
        public void setNombreComercial(String nombreComercial) {
            this.nombreComercial = nombreComercial;
        }
        
        public String getTipoDocumento() {
            return tipoDocumento;
        }
        
        public void setTipoDocumento(String tipoDocumento) {
            this.tipoDocumento = tipoDocumento;
        }
        
        public String getEstado() {
            return estado;
        }
        
        public void setEstado(String estado) {
            this.estado = estado;
        }
        
        public String getCondicion() {
            return condicion;
        }
        
        public void setCondicion(String condicion) {
            this.condicion = condicion;
        }
        
        public String getDireccion() {
            return direccion;
        }
        
        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }
        
        public String getDepartamento() {
            return departamento;
        }
        
        public void setDepartamento(String departamento) {
            this.departamento = departamento;
        }
        
        public String getProvincia() {
            return provincia;
        }
        
        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }
        
        public String getDistrito() {
            return distrito;
        }
        
        public void setDistrito(String distrito) {
            this.distrito = distrito;
        }
        
        public String getUbigeo() {
            return ubigeo;
        }
        
        public void setUbigeo(String ubigeo) {
            this.ubigeo = ubigeo;
        }
        
        public String getActividadEconomica() {
            return actividadEconomica;
        }
        
        public void setActividadEconomica(String actividadEconomica) {
            this.actividadEconomica = actividadEconomica;
        }
        
        // Getters y setters para campos de facturación electrónica
        public String getTipoContribuyente() { return tipoContribuyente; }
        public void setTipoContribuyente(String tipoContribuyente) { this.tipoContribuyente = tipoContribuyente; }
        
        public String getFechaInscripcion() { return fechaInscripcion; }
        public void setFechaInscripcion(String fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
        
        public String getFechaInicioActividades() { return fechaInicioActividades; }
        public void setFechaInicioActividades(String fechaInicioActividades) { this.fechaInicioActividades = fechaInicioActividades; }
        
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
        
        public String getTelefono() { return telefono; }
        public void setTelefono(String telefono) { this.telefono = telefono; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}