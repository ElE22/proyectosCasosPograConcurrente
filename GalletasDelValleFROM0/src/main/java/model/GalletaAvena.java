package model;

import java.time.LocalDate;

public class GalletaAvena extends LoteProduccion implements ControlCalidad {
    public GalletaAvena(String idLote, LocalDate fechaFabricacion, int unidades, double tiempoFabricacion) {
        super(idLote, "Avena", fechaFabricacion, unidades, tiempoFabricacion);
        setCostoTotal(calcularCostoProduccion());
    }

    @Override
    public double calcularCostoProduccion() {
        return getUnidades() * 0.15 + getTiempoFabricacion() * 2.5;
    }

    @Override
    public double evaluarCalidad() {
        return getUnidades() > 1000 ? 9.0 : 7.0;
    }

    @Override
    public String obtenerPuntajeCalidad() {
        return evaluarCalidad() >= 8 ? "Excelente" : "Aceptable";
    }
}
