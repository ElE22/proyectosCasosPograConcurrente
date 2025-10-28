package com.mycompany.controller;

import com.mycompany.model.LoteProduccion;
//import com.mycompany.model.GalletaChocolate;
import com.mycompany.model.GalletaAvena;

/**
 * Factory simple para crear LoteProduccion segun el tipo.
 * Ajustar las clases concretas y constructores segun tu implementacion.
 */
public class LoteFactory {

    public static LoteProduccion crear(String tipo, String idLote, int unidadesLimites) {
        if (tipo == null) throw new IllegalArgumentException("Tipo de galleta nulo");

        return switch (tipo) {
            //case "Chocolate" -> new GalletaChocolate(idLote, unidades);
            case "Avena"     -> new GalletaAvena(idLote, unidadesLimites);
            //case "Vainilla"  -> new GalletaVainilla(idLote, unidades);
            default -> throw new IllegalArgumentException("Tipo de galleta desconocido: " + tipo);
        };
    }
}