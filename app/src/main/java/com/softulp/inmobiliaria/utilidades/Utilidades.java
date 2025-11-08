package com.softulp.inmobiliaria.utilidades;

import java.text.ParseException;

public class Utilidades {


    /**
     * Convierte una fecha en formato YYYY-MM-DD del API a formato DD/MM/YYYY
     * @param fechaApi String de fecha recibida del API (ej: "2025-08-01").
     * @return String con la fecha formateada (ej: "01/08/2025").
     */
    public static String formatearFecha(String fechaApi) {
        if (fechaApi == null || fechaApi.isEmpty() || !fechaApi.contains("-")) {
            return fechaApi; // Devuelve original si es nulo o no es formato guion
        }

        try {
            // Divide la cadena usando el guion: ["2025", "08", "01"]
            String[] partes = fechaApi.substring(0, 10).split("-");

            // Reordena a DD/MM/YYYY:
            // partes[2] = Día, partes[1] = Mes, partes[0] = Año
            return partes[2] + "/" + partes[1] + "/" + partes[0];

        } catch (Exception e) {
            e.printStackTrace();
            return fechaApi; // Devuelve original si hay cualquier error
        }
    }
    public static String formatearMoneda(double monto) {
        try {
            java.text.NumberFormat nf = java.text.NumberFormat.getCurrencyInstance();
            nf.setMaximumFractionDigits(2);
            return nf.format(monto);   // Ejemplo: $1,200.00 o según configuración regional
        } catch (Exception e) {
            return "$" + monto;
        }
    }

}