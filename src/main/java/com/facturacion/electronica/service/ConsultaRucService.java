package com.facturacion.electronica.service;

import com.facturacion.electronica.dto.ConsultaRucResponse;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

/**
 * Servicio para consulta de RUC EXCLUSIVAMENTE desde SUNAT oficial
 * NO genera datos falsos ni simulados - Solo consulta real
 */
@Service
public class ConsultaRucService {
    
    /**
     * Consulta datos de RUC desde SUNAT
     */
    public ConsultaRucResponse consultarRuc(String ruc) {
        try {
            // Validar formato de RUC
            if (!validarFormatoRuc(ruc)) {
                return new ConsultaRucResponse(false, "Formato de RUC inválido", null);
            }
            
            // CONSULTA EXCLUSIVAMENTE A SUNAT - NO SIMULACIÓN
            ConsultaRucResponse.RucData dataReal = consultarSunatReal(ruc);
            if (dataReal != null) {
                return new ConsultaRucResponse(true, "Datos obtenidos de SUNAT", dataReal);
            }
            
            // Si SUNAT no devuelve datos, NO INVENTAR - devolver error
            return new ConsultaRucResponse(false, "RUC no encontrado en SUNAT", null);
            
        } catch (Exception e) {
            return new ConsultaRucResponse(false, "Error al consultar SUNAT: " + e.getMessage(), null);
        }
    }

    /**
     * Consulta real a la página de SUNAT usando web scraping
     */
    private ConsultaRucResponse.RucData consultarSunatReal(String ruc) {
        try {
            System.out.println("🔍 Consultando RUC real en SUNAT: " + ruc);
            
            // Método 1: Consulta a SUNAT oficial
            ConsultaRucResponse.RucData resultado = consultarSunatOficial(ruc);
            if (resultado != null) {
                return resultado;
            }
            
            // Método 2: APIs públicas alternativas (solo si SUNAT oficial falla)
            resultado = consultarApiPublica(ruc);
            if (resultado != null) {
                return resultado;
            }
            
            System.out.println("⚠️ RUC no encontrado en fuentes oficiales");
            return null;
            
        } catch (Exception e) {
            System.out.println("❌ Error consultando SUNAT real: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Consulta a la página oficial de SUNAT
     */
    private ConsultaRucResponse.RucData consultarSunatOficial(String ruc) {
        try {
            System.out.println("🏛️ Consultando página oficial de SUNAT para RUC: " + ruc);
            
            // Primer paso: Obtener la página de consulta
            String urlConsulta = "https://e-consultaruc.sunat.gob.pe/cl-ti-itmrconsruc/FrameCriterioBusquedaWeb.jsp";
            
            Document pageConsulta = Jsoup.connect(urlConsulta)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .header("Accept-Language", "es-ES,es;q=0.9,en;q=0.8")
                .header("Cache-Control", "no-cache")
                .timeout(15000)
                .get();
            
            // Segundo paso: Hacer POST con el RUC
            String urlResultado = "https://e-consultaruc.sunat.gob.pe/cl-ti-itmrconsruc/jcrS00Alias";
            
            Document resultado = Jsoup.connect(urlResultado)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8")
                .header("Accept-Language", "es-ES,es;q=0.9,en;q=0.8")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Origin", "https://e-consultaruc.sunat.gob.pe")
                .header("Referer", urlConsulta)
                .data("accion", "consPorRuc")
                .data("nroRuc", ruc)
                .data("activo", "1")
                .post();
            
            // Parsear el resultado
            ConsultaRucResponse.RucData datos = parsearRespuestaSunat(resultado, ruc);
            
            if (datos != null && datos.getRazonSocial() != null && !datos.getRazonSocial().trim().isEmpty()) {
                System.out.println("✅ RUC encontrado en SUNAT: " + datos.getRazonSocial());
                return datos;
            }
            
            System.out.println("⚠️ No se encontraron datos válidos para el RUC en SUNAT");
            return null;
            
        } catch (Exception e) {
            System.out.println("❌ Error consultando SUNAT oficial: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Consulta a APIs públicas alternativas REALES
     */
    private ConsultaRucResponse.RucData consultarApiPublica(String ruc) {
        try {
            System.out.println("🔍 Consultando APIs públicas para RUC: " + ruc);
            
            // APIs públicas reales que requieren configuración:
            // - https://dniruc.apisperu.com/api/v1/ruc/{ruc} (requiere token)
            // - https://api.reniec.cloud/ruc/{ruc} (requiere API key)
            // - https://api.sunat.cloud/ruc/{ruc} (requiere autenticación)
            
            System.out.println("⚠️ APIs públicas requieren configuración:");
            System.out.println("   - Tokens de autenticación");
            System.out.println("   - Rate limiting");
            System.out.println("   - Configuración de endpoints");
            System.out.println("   - Manejo de errores por cuotas");
            
            return null;
            
        } catch (Exception e) {
            System.out.println("❌ Error en APIs públicas: " + e.getMessage());
            return null;
        }
    }

    /**
     * Parsea la respuesta HTML de SUNAT
     */
    private ConsultaRucResponse.RucData parsearRespuestaSunat(Document doc, String ruc) {
        try {
            ConsultaRucResponse.RucData data = new ConsultaRucResponse.RucData();
            data.setRuc(ruc);
            data.setTipoDocumento("6"); // RUC
            
            System.out.println("🔍 Analizando HTML de respuesta SUNAT...");
            
            // Buscar mensaje de error primero
            Elements errorMsgs = doc.select(".alert-danger, .error, .mensaje-error");
            if (!errorMsgs.isEmpty()) {
                System.out.println("⚠️ SUNAT reporta error: " + errorMsgs.text());
                return null;
            }
            
            // Buscar tabla de resultados con diferentes selectores
            Elements tables = doc.select("table.listResult, table.list-group, table, .panel-body table");
            System.out.println("📊 Encontradas " + tables.size() + " tablas en la respuesta");
            
            for (Element table : tables) {
                Elements rows = table.select("tr");
                System.out.println("🔍 Procesando tabla con " + rows.size() + " filas");
                
                for (Element row : rows) {
                    Elements cells = row.select("td, th");
                    if (cells.size() >= 2) {
                        String campo = cells.get(0).text().trim();
                        String valor = cells.get(1).text().trim();
                        
                        System.out.println("📋 Campo: [" + campo + "] -> Valor: [" + valor + "]");
                        
                        if (!valor.isEmpty() && !valor.equals("-") && !valor.equals("N/A")) {
                            mapearCampoSunat(data, campo.toLowerCase(), valor);
                        }
                    }
                }
            }
            
            // Si no hay tabla, buscar datos en divs o spans
            if (data.getRazonSocial() == null) {
                System.out.println("🔍 Buscando datos en otros elementos HTML...");
                
                // Buscar por clases específicas de SUNAT
                Elements dataElements = doc.select(".data-value, .field-value, span[class*='data'], td[class*='data']");
                for (Element element : dataElements) {
                    String texto = element.text().trim();
                    if (texto.length() > 10 && !texto.matches("\\d+")) { // Probablemente razón social
                        data.setRazonSocial(texto);
                        System.out.println("✅ Razón social encontrada: " + texto);
                        break;
                    }
                }
            }
            
            // Validar que se encontraron datos mínimos
            if (data.getRazonSocial() == null || data.getRazonSocial().isEmpty()) {
                System.out.println("❌ No se encontraron datos válidos en la respuesta de SUNAT");
                System.out.println("🔍 HTML de respuesta (primeros 500 chars): " + doc.html().substring(0, Math.min(500, doc.html().length())));
                return null;
            }
            
            System.out.println("✅ Datos extraídos exitosamente de SUNAT");
            return data;
            
        } catch (Exception e) {
            System.out.println("❌ Error parseando respuesta SUNAT: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Mapea un campo de SUNAT a nuestro modelo de datos
     */
    private void mapearCampoSunat(ConsultaRucResponse.RucData data, String campo, String valor) {
        if (campo.contains("razón social") || campo.contains("razon social")) {
            data.setRazonSocial(valor);
        } else if (campo.contains("nombre comercial")) {
            data.setNombreComercial(valor);
        } else if (campo.contains("estado")) {
            data.setEstado(valor);
        } else if (campo.contains("condición") || campo.contains("condicion")) {
            data.setCondicion(valor);
        } else if (campo.contains("dirección") || campo.contains("direccion") || campo.contains("domicilio fiscal")) {
            data.setDireccion(valor);
        } else if (campo.contains("departamento")) {
            data.setDepartamento(valor);
        } else if (campo.contains("provincia")) {
            data.setProvincia(valor);
        } else if (campo.contains("distrito")) {
            data.setDistrito(valor);
        } else if (campo.contains("ubigeo")) {
            data.setUbigeo(valor);
        } else if (campo.contains("actividad") && campo.contains("económica")) {
            data.setActividadEconomica(valor);
        }
        // Agregar más mapeos según la estructura real de SUNAT
    }

    /**
     * Valida el formato del RUC peruano
     */
    private boolean validarFormatoRuc(String ruc) {
        if (ruc == null || ruc.trim().isEmpty()) {
            return false;
        }
        
        // RUC debe tener 11 dígitos
        Pattern pattern = Pattern.compile("^\\d{11}$");
        return pattern.matcher(ruc.trim()).matches();
    }
}