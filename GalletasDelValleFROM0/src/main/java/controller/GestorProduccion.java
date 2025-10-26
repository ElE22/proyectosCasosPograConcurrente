package controller;

import model.LoteProduccion;
import java.io.*;
import java.util.*;


import javax.swing.JOptionPane;

public class GestorProduccion {
    private ArrayList<LoteProduccion> lotes = new ArrayList<>();

    public void agregarLote(LoteProduccion lote) {
        lotes.add(lote);
    }

    public ArrayList<LoteProduccion> getLotes() {
        return lotes;
    }

    public List<LoteProduccion> listarPorCosto() {
        try {
            return lotes.stream()
            .sorted(Comparator.comparingDouble(LoteProduccion::calcularCostoProduccion))
            .toList();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar los lotes por costo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return Collections.emptyList();
        }
    }

    public List<LoteProduccion> listarPorUnidades() {
        try {
            return lotes.stream()
            .sorted(Comparator.comparingInt(LoteProduccion::getUnidades))
            .toList();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar los lotes por unidades: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return Collections.emptyList();
        }
        
    }

    public void guardarDatos(String ruta) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta))) {
            out.writeObject(lotes);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
}
