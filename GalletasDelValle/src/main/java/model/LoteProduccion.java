package model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class LoteProduccion implements  Comparable<LoteProduccion> {
    private String idLote;
    private String tipoGalleta;
    private LocalDate fechaFabricacion;
    private int unidades;
    private double tiempoFabricacion;
    private double costoTotal;

    public LoteProduccion(String idLote, String tipoGalleta, LocalDate fechaFabricacion, int unidades, double tiempoFabricacion) {
        setIdLote(idLote);
        setTipoGalleta(tipoGalleta);
        setFechaFabricacion(fechaFabricacion);
        setUnidades(unidades);
        setTiempoFabricacion(tiempoFabricacion);
    }

    public abstract double calcularCostoProduccion();

    @Override
    public int compareTo(LoteProduccion otro) {
        return this.fechaFabricacion.compareTo(otro.fechaFabricacion);
    }

    @Override
    public String toString() {
        return String.format("Lote %s (%s) - %d unidades - Costo: %.2f - Fecha de fabricacion: %s", idLote, tipoGalleta, unidades, costoTotal, fechaFabricacion);
    }

    // Getters y Setters con validaciones
    public String getIdLote() { return idLote; }
    public void setIdLote(String idLote) {
        if (idLote == null || idLote.isEmpty()) throw new IllegalArgumentException("ID no valido");
        this.idLote = idLote;
    }

    public String getTipoGalleta() { return tipoGalleta; }
    public void setTipoGalleta(String tipoGalleta) {
        if (tipoGalleta == null || tipoGalleta.isEmpty()) throw new IllegalArgumentException("Tipo de galleta no valido");
        this.tipoGalleta = tipoGalleta;
    }

    public LocalDate getFechaFabricacion() { return fechaFabricacion; }
    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        if (fechaFabricacion.isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha no puede ser futura");
        this.fechaFabricacion = fechaFabricacion;
    }

    public int getUnidades() { return unidades; }
    public void setUnidades(int unidades) {
        if (unidades <= 0) throw new IllegalArgumentException("Unidades invalidas");
        this.unidades = unidades;
    }

    public double getTiempoFabricacion() { return tiempoFabricacion; }
    public void setTiempoFabricacion(double tiempoFabricacion) {
        if (tiempoFabricacion <= 0) throw new IllegalArgumentException("Tiempo invalido");
        this.tiempoFabricacion = tiempoFabricacion;
    }

    public double getCostoTotal() { return costoTotal; }
    public void setCostoTotal(double costoTotal) { this.costoTotal = costoTotal; }
}
