package com.mycompany.controller;

import com.mycompany.model.LoteProduccion;
import com.mycompany.model.EstadoLote;
import java.time.LocalDate;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GestorProduccion {
    private ArrayList<LoteProduccion> lotes = new ArrayList<>();
    private String ruta = "lotes.dat";

    public GestorProduccion() {
        cargarDatos();
    }

    // ----------------- CRUD BASICO ------------------

    public void agregarLote(LoteProduccion lote) {
        boolean yaExiste = lotes.stream()
                .anyMatch(l -> l.getIdLote().equalsIgnoreCase(lote.getIdLote()));

        if (yaExiste) {
            JOptionPane.showMessageDialog(null,"Error: El lote ya existe y no puede ser agregado.", "Lote Duplicado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lotes.add(lote);
        guardarDatos();
        JOptionPane.showMessageDialog(null,"Lote agregado exitosamente a la memoria.","Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void eliminarLote(String idLote) {
        Optional<LoteProduccion> lote = lotes.stream()
                .filter(l -> l.getIdLote().equalsIgnoreCase(idLote))
                .findFirst();

        if (lote.isPresent()) {
            lotes.remove(lote.get());
            guardarDatos();
            JOptionPane.showMessageDialog(null, "Lote eliminado correctamente.","Exito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro el lote con ID: " + idLote,"Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public LoteProduccion buscarPorId(String idLote) {
        return lotes.stream()
                .filter(l -> l.getIdLote().equalsIgnoreCase(idLote))
                .findFirst()
                .orElse(null);
    }

    // ----------------- REPORTES / FILTROS ------------------

    public List<LoteProduccion> listarPorCosto() {
        return lotes.stream()
                .sorted(Comparator.comparingDouble(LoteProduccion::calcularCostoProduccion))
                .toList();
    }

    public List<LoteProduccion> listarPorUnidades() {
        return lotes.stream()
                .sorted(Comparator.comparingInt(LoteProduccion::getUnidades))
                .toList();
    }

    public List<LoteProduccion> listarPorEstado(EstadoLote estado) {
        return lotes.stream()
                .filter(l -> l.getEstado() == estado)
                .toList();
    }

    public List<LoteProduccion> listarCaducados() {
        LocalDate hoy = LocalDate.now();
        return lotes.stream()
                .filter(l -> l.getFechaCaducidad() != null && l.getFechaCaducidad().isBefore(hoy))
                .toList();
    }

    public List<LoteProduccion> listarPendientesCalidad() {
        return lotes.stream()
                .filter(l -> !l.isPasoControlCalidad())
                .toList();
    }

    // ----------------- ACTUALIZACIONES ------------------

    public void actualizarEstado(String idLote, EstadoLote nuevoEstado) {
        LoteProduccion lote = buscarPorId(idLote);
        if (lote == null) {
            JOptionPane.showMessageDialog(null, "No se encontro el lote especificado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lote.setEstado(nuevoEstado);
        guardarDatos();
    }

    public void marcarControlCalidad(String idLote, boolean aprobado) {
        LoteProduccion lote = buscarPorId(idLote);
        if (lote == null) {
            JOptionPane.showMessageDialog(null, "No se encontro el lote especificado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lote.setPasoControlCalidad(aprobado);
        guardarDatos();
    }

    // ----------------- PERSISTENCIA ------------------

    private void guardarDatos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta))) {
            out.writeObject(lotes);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatos() {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            this.lotes = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta))) {
            Object obj = in.readObject();
            if (obj instanceof ArrayList<?>) {
                this.lotes = (ArrayList<LoteProduccion>) obj;
            }
        } catch (Exception e) {
            this.lotes = new ArrayList<>();
        }
    }


    public ArrayList<LoteProduccion> getLotes() {
        return lotes;
    }
}
