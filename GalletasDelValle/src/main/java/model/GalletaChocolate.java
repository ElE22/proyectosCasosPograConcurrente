package model;

import java.time.LocalDate;

public class GalletaChocolate extends LoteProduccion implements ControlCalidad {
    public GalletaChocolate(String idLote, LocalDate fechaFabricacion, int unidades, double tiempoFabricacion) {
        super(idLote, "Chocolate", fechaFabricacion, unidades, tiempoFabricacion);
        setCostoTotal(calcularCostoProduccion());
    }

    @Override
    public double calcularCostoProduccion() {
        return getUnidades() * 0.20 + getTiempoFabricacion() * 3.0;
    }

    @Override
    public double evaluarCalidad() {
        return getUnidades() > 800 ? 8.5 : 7.0;
    }

    @Override
    public String obtenerPuntajeCalidad() {
        return evaluarCalidad() >= 8 ? "Excelente" : "Aceptable";
    }
}
