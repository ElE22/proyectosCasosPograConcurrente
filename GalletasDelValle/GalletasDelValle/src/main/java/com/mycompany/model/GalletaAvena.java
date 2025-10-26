package com.mycompany.model;

import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author diana
 */

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

