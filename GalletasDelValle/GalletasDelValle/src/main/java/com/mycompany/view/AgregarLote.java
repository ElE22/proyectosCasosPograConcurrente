package com.mycompany.view;

import com.mycompany.controller.AppNavigator;
import com.mycompany.controller.LoteFactory;
import com.mycompany.controller.GestorProduccion;
import com.mycompany.model.LoteProduccion;
import com.mycompany.controller.MensajeUI;

import javax.swing.*;
import java.awt.*;

/**
 * Formulario para agregar un nuevo lote.
 * - Recibe una instancia de GestorProduccion para no crear gestores repetidos.
 * - Al guardar crea la clase concreta mediante LoteFactory.
 *
 * Comentarios sin tildes segun tu preferencia.
 */
public class AgregarLoteView extends JFrame {

    private final GestorProduccion gestor;

    private JTextField txtId;
    private JComboBox<String> comboTipo;
    private JTextField txtUnidades;
    private JTextField txtResponsable;
    private JButton btnGuardar;
    private JButton btnVolver;

    public AgregarLoteView(GestorProduccion gestor) {
        this.gestor = gestor;
        initUI();
    }

    private void initUI() {
        setTitle("Agregar Nuevo Lote");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 360);
        setLayout(new BorderLayout());

        // TITULO
        JLabel lblTitulo = new JLabel("AGREGAR LOTE", SwingConstants.CENTER);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(Color.DARK_GRAY);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // FORMULARIO
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panelForm.add(new JLabel("ID Lote:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Tipo Galleta:"));
        comboTipo = new JComboBox<>(new String[] {"Chocolate", "Avena", "Vainilla"});
        panelForm.add(comboTipo);

        panelForm.add(new JLabel("Unidades iniciales:"));
        txtUnidades = new JTextField();
        panelForm.add(txtUnidades);

        panelForm.add(new JLabel("Responsable:"));
        txtResponsable = new JTextField();
        panelForm.add(txtResponsable);

        add(panelForm, BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar Lote");
        btnVolver = new JButton("Volver");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // EVENTOS
        btnGuardar.addActionListener(e -> onGuardar());
        btnVolver.addActionListener(e -> {
            // volver al menu principal
            AppNavigator.mostrar(new MainMenuView(gestor));
        });

        setLocationRelativeTo(null);
    }

    private void onGuardar() {
        try {
            String id = txtId.getText().trim();
            String responsable = txtResponsable.getText().trim();
            String tipo = (String) comboTipo.getSelectedItem();
            if (tipo == null) throw new IllegalArgumentException("Selecciona un tipo de galleta");

            if (id.isEmpty()) throw new IllegalArgumentException("ID de lote requerido");
            if (responsable.isEmpty()) throw new IllegalArgumentException("Responsable requerido");

            int unidadesLimites;
            try {
                unidadesLimites = Integer.parseInt(txtUnidades.getText().trim());
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Unidades debe ser un numero entero valido");
            }

            // Crear la instancia concreta mediante la factory
            LoteProduccion lote = LoteFactory.crear(tipo, id, unidadesLimites);

            // Asignar responsable y otros datos opcionales si quieres
            lote.setResponsableProduccion(responsable);

            // Guardar en el gestor (GestorProduccion se encarga de persistir)
            gestor.agregarLote(lote);

            MensajeUI.info("Lote agregado correctamente");

            // Volver al menu principal automaticamente
            AppNavigator.mostrar(new MainMenuView(gestor));

        } catch (IllegalArgumentException ex) {
            MensajeUI.error(ex.getMessage());
        } catch (Exception ex) {
            MensajeUI.error("Error inesperado: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}