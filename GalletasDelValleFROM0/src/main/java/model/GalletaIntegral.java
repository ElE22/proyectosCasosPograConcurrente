package model;

import java.time.LocalDate;

public class GalletaIntegral extends LoteProduccion implements ControlCalidad {
    public GalletaIntegral(String idLote, LocalDate fechaFabricacion, int unidades, double tiempoFabricacion) {
        super(idLote, "Integral", fechaFabricacion, unidades, tiempoFabricacion);
        setCostoTotal(calcularCostoProduccion());
    }

    @Override
    public double calcularCostoProduccion() {
        return getUnidades() * 0.18 + getTiempoFabricacion() * 2.7;
    }

    @Override
    public double evaluarCalidad() {
        return getUnidades() > 900 ? 8.0 : 6.5;
    }

    @Override
    public String obtenerPuntajeCalidad() {
        return evaluarCalidad() >= 8 ? "Excelente" : "Aceptable";
    }
}
