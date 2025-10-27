package com.mycompany.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

/**
 * Clase abstracta que representa un lote de produccion de galletas.
 * Contiene informacion sobre fechas, unidades, costos y responsable.
 * 
 * Cada atributo cuenta con validaciones logicas en sus setters
 * para garantizar la integridad de los datos.
 * 
 * En las clases hijas se puede ajustar el limite de unidades y 
 * los meses de caducidad segun el tipo de producto.
 * 
 * @author diana
 */
public abstract class LoteProduccion implements Serializable, Comparable<LoteProduccion> {

    private String idLote;
    private String tipoGalleta;
    private int unidades;
    private int limiteUnidades;               // limite maximo definido por la orden de produccion
    private double costoTotal;
    private String responsableProduccion;
    private LocalDate fechaFabricacion;       // inicio del proceso
    private LocalDate fechaHoraFinProduccion; // fin del proceso
    private LocalDate fechaCaducidad;         // vencimiento del lote
    private double tiempoFabricacion;         // duracion en horas o minutos
    private EstadoLote estado;                // nuevo: estado actual del lote
    private boolean pasoControlCalidad;       // nuevo: indica si paso control de calidad

    // CONSTRUCTOR
    public LoteProduccion(String idLote, String tipoGalleta, int limitesUnidades) {
        setIdLote(idLote);
        setTipoGalleta(tipoGalleta);
        setLimiteUnidades(limitesUnidades);
        this.estado = EstadoLote.PLANIFICADO; // estado inicial por defecto
        this.pasoControlCalidad = false; // valor inicial por defecto
    }

    // --- METODO ABSTRACTO ---
    public abstract double calcularCostoProduccion();

    // --- COMPARACION Y REPRESENTACION ---
    @Override
    public int compareTo(LoteProduccion otro) {
        return this.fechaFabricacion.compareTo(otro.fechaFabricacion);
    }

    @Override
    public String toString() {
        return String.format(
            "Lote[id=%s, tipo=%s, estado=%s, unidades=%d/%d, costo=%.2f, fechaFab=%s, fin=%s, cad=%s, calidad=%s]",
            idLote,
            tipoGalleta,
            (estado != null ? estado : "ND"),
            unidades,
            limiteUnidades,
            costoTotal,
            fechaFabricacion != null ? fechaFabricacion : "ND",
            fechaHoraFinProduccion != null ? fechaHoraFinProduccion : "ND",
            fechaCaducidad != null ? fechaCaducidad : "ND",
            pasoControlCalidad ? "Aprobado" : "Pendiente"
        );
    }

    //////////////////// GETTERS Y SETTERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    // ---- ID y tipo ----
    public String getIdLote() { return idLote; }
    public void setIdLote(String idLote) {
        if (idLote == null || idLote.trim().isEmpty())
            throw new IllegalArgumentException("ID de lote no valido. No puede estar vacio ni ser nulo.");
        this.idLote = idLote.trim();
    }

    public String getTipoGalleta() { return tipoGalleta; }
    public void setTipoGalleta(String tipoGalleta) {
        if (tipoGalleta == null || tipoGalleta.trim().isEmpty())
            throw new IllegalArgumentException("Tipo de galleta no valido. Debe especificarse un nombre.");
        this.tipoGalleta = tipoGalleta.trim();
    }

    // ---- Fechas ----
    public LocalDate getFechaFabricacion() { return fechaFabricacion; }
    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        if (fechaFabricacion == null)
            throw new IllegalArgumentException("La fecha de fabricacion no puede ser nula.");
        this.fechaFabricacion = fechaFabricacion;
    }

    public LocalDate getFechaHoraFinProduccion() { return fechaHoraFinProduccion; }
    public void setFechaHoraFinProduccion(LocalDate fechaHoraFinProduccion) {
        if (fechaHoraFinProduccion != null && fechaFabricacion != null
                && fechaHoraFinProduccion.isBefore(fechaFabricacion))
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de fabricacion.");
        this.fechaHoraFinProduccion = fechaHoraFinProduccion;
    }

    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(int plusCaducidad) {
        if (fechaHoraFinProduccion == null)
            throw new IllegalArgumentException("No se puede calcular la fecha de caducidad sin haber finalizado la produccion.");
        if (plusCaducidad <= 0)
            throw new IllegalArgumentException("Los meses de caducidad deben ser mayores que cero.");
        this.fechaCaducidad = fechaHoraFinProduccion.plusMonths(plusCaducidad);
    }

    // ---- Tiempo ----
    public double getTiempoFabricacion() { return tiempoFabricacion; }
    public void setTiempoFabricacion(double tiempoFabricacion) {
        if (tiempoFabricacion <= 0)
            throw new IllegalArgumentException("El tiempo de fabricacion debe ser mayor a cero.");
        this.tiempoFabricacion = tiempoFabricacion;
    }

    // total de fabricacion
    public double getTiempoTotalFabricacion() {
        if (fechaHoraFinProduccion == null || fechaFabricacion == null)
            return 0.0; // lote no completado
        return Duration.between(
            fechaFabricacion.atStartOfDay(),
            fechaHoraFinProduccion.atStartOfDay()
        ).toHours();
    }

    // ---- Produccion ----
    public int getUnidades() { return unidades; }
    public void setUnidades(int unidades) {
        if (unidades <= 0)
            throw new IllegalArgumentException("La cantidad de unidades debe ser mayor que cero.");
        if (limiteUnidades > 0 && unidades > limiteUnidades)
            throw new IllegalArgumentException("No se pueden asignar mas unidades que el limite permitido (" + limiteUnidades + ").");
        this.unidades = unidades;
    }

    public int getLimiteUnidades() { return limiteUnidades; }
    public void setLimiteUnidades(int limiteUnidades) {
        if (limiteUnidades <= 0)
            throw new IllegalArgumentException("El limite de unidades debe ser mayor que cero.");
        this.limiteUnidades = limiteUnidades;
    }

    // ---- Estado ----
    public EstadoLote getEstado() { return estado; }
    public void setEstado(EstadoLote estado) {
        if (estado == null)
            throw new IllegalArgumentException("El estado del lote no puede ser nulo.");
        this.estado = estado;
    }

    // ---- Control de calidad ----
    public boolean isPasoControlCalidad() { return pasoControlCalidad; }
    public void setPasoControlCalidad(boolean pasoControlCalidad) {
        this.pasoControlCalidad = pasoControlCalidad;
    }

    // ---- Costos ----
    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) {
        if (costoTotal < 0)
            throw new IllegalArgumentException("El costo total no puede ser negativo.");
        this.costoTotal = costoTotal;
    }

    public double getCostoUnitario() {
        return unidades > 0 ? costoTotal / unidades : 0.0;
    }

    // ---- Responsable ----
    public String getResponsableProduccion() { return responsableProduccion; }
    public void setResponsableProduccion(String responsableProduccion) {
        if (responsableProduccion == null || responsableProduccion.trim().isEmpty())
            throw new IllegalArgumentException("Debe especificarse el responsable de produccion.");
        this.responsableProduccion = responsableProduccion.trim();
    }
}
