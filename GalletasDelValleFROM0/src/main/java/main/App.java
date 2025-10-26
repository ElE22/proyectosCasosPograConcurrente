package main;

import controller.GestorProduccion;
import model.*;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        GestorProduccion gestor = new GestorProduccion();

        LoteProduccion lote1 = new GalletaAvena("L001", LocalDate.now(), 500, 2);
        LoteProduccion lote2 = new GalletaChocolate("L002", LocalDate.now(), 800, 3);

        gestor.agregarLote(lote1);
        gestor.agregarLote(lote2);

        gestor.getLotes().forEach(System.out::println);
    }
}
