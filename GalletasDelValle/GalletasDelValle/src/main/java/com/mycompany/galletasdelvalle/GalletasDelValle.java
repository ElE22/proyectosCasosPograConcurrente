package com.mycompany.galletasdelvalle;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



import com.mycompany.controller.GestorProduccion;
import com.mycompany.model.GalletaAvena;
import com.mycompany.model.LoteProduccion;
import com.mycompany.view.MainMenuView;
import javax.swing.SwingUtilities;

/**
 *
 * @author diana
 */
public class GalletasDelValle {

   public static void main(String[] args){
//       String ruta = "lotes.dat";
//        GestorProduccion gestor = new GestorProduccion();
//
//        LoteProduccion lote1 = new GalletaAvena("L001",  500);
//        //LoteProduccion lote2 = new GalletaChocolate("L002", LocalDate.now(), 800, 3);
//        LoteProduccion lote2 = new GalletaAvena("L003",  500);
////        gestor.agregarLote(lote1);
////        gestor.agregarLote(lote1);
//        //gestor.agregarLote(lote2);
//
////        gestor.agregarLote(lote1);
////        gestor.agregarLote(lote2);
//        System.out.printf("Lectura de los datos desde el archivo:"+ gestor.getLotes());

         SwingUtilities.invokeLater(() -> new MainMenuView().setVisible(true));

       
    }
}
