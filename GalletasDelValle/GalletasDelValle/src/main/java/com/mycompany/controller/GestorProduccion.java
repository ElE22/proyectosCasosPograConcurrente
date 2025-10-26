package com.mycompany.controller;

import com.mycompany.model.LoteProduccion;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GestorProduccion {
    private ArrayList<LoteProduccion> lotes = new ArrayList<>();
    private String ruta = "lotes.dat";

    public GestorProduccion() {
        cargarDatos();
    }

    public void agregarLote(LoteProduccion lote) {
        // 1. Validar si el lote ya existe en la lista de memoria
        boolean yaExiste = lotes.stream()
                            .anyMatch(l -> l.getIdLote().equals(lote.getIdLote()));

        if (yaExiste) {
            // 2. Si existe, mostrar error y NO hacer nada más.
            JOptionPane.showMessageDialog(null, 
                "Error: El lote ya existe y no puede ser agregado.", 
                "Lote Duplicado", 
                JOptionPane.ERROR_MESSAGE);
        } else {
            
            lotes.add(lote);
            JOptionPane.showMessageDialog(null, 
                "Lote agregado exitosamente a la memoria.", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            //actualizar el archivo
            guardarDatos(); 
            
            
        }
    }

 
    private void guardarDatos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta))) {
            // Escribe la LISTA ENTERA, no un solo lote
            out.writeObject(lotes);
            JOptionPane.showMessageDialog(null, 
                "Datos guardados exitosamente en el archivo.", 
                "Guardado", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cargarDatos() {
        File archivo = new File(ruta);
        // Si el archivo no existe, no hay nada que cargar.
        if (!archivo.exists()) {
            this.lotes = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta))) {
            Object obj = in.readObject();
            if (obj instanceof ArrayList<?>) {
                this.lotes = (ArrayList<LoteProduccion>) obj; // Carga la lista
            } else {
                JOptionPane.showMessageDialog(null, "El archivo no contiene una lista de lotes válida.", "Error", JOptionPane.ERROR_MESSAGE);
                this.lotes = new ArrayList<>(); // Inicia vacío si el archivo es corrupto
            }
        } catch (FileNotFoundException e) {
            // Normal si es la primera vez que se ejecuta, inicia con lista vacía
            this.lotes = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.lotes = new ArrayList<>(); // Inicia vacío si hay error
        }
    }

    /**
     * Simplemente devuelve la lista que está en memoria.
     * YA NO llama a lecturaDatos().
     */
    public ArrayList<LoteProduccion> getLotes() {
        return lotes;
    }

    // --- Tus métodos de listado (estos estaban bien) ---
    
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
}