package model;

import java.time.LocalDate;

public class GalletaSinAzucar extends LoteProduccion implements ControlCalidad {
    public GalletaSinAzucar(String idLote, LocalDate fechaFabricacion, int unidades, double tiempoFabricacion) {
        super(idLote, "Sin Azúcar", fechaFabricacion, unidades, tiempoFabricacion);
        setCostoTotal(calcularCostoProduccion());
    }

    @Override
    public double calcularCostoProduccion() {
        return getUnidades() * 0.22 + getTiempoFabricacion() * 3.2;
    }

    @Override
    public double evaluarCalidad() {
        return getUnidades() > 700 ? 9.5 : 7.5;
    }

    @Override
    public String obtenerPuntajeCalidad() {
        return evaluarCalidad() >= 8 ? "Excelente" : "Aceptable";
    }
}
