package com.mycompany.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author diana
 */

public class MainMenuView extends JFrame {

    private JButton btnAgregarLote;
    private JButton btnAgregarPersona;
    private JButton btnVerLotes;
    private JButton btnControlCalidad;
    private JButton btnSalir;
    private String colorPrimaryBtn = "#4287f5";
    private String colorHoverPrimaryBtn = "#131a29";

    public MainMenuView() {
        setTitle("Sistema de Producción de Galletas - Menú Principal");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        // ---------- PANEL SUPERIOR ----------
        JLabel lblTitulo = new JLabel("Panel Principal de Producción", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE); // Texto blanco

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(46, 46, 46)); // Gris oscuro
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);
        // ---------- PANEL CENTRAL ----------
        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 20, 20));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelCentral.setBackground(Color.decode("#ebebf3"));

        btnAgregarLote = crearBoton("Agregar Lote");
        btnAgregarPersona = crearBoton("Registrar Responsable");
        btnVerLotes = crearBoton("Ver Lotes");
        btnControlCalidad = crearBoton("Control de Calidad");
        btnSalir = crearBoton("Salir");

        panelCentral.add(btnAgregarLote);
        panelCentral.add(btnAgregarPersona);
        panelCentral.add(btnVerLotes);
        panelCentral.add(btnControlCalidad);
        panelCentral.add(btnSalir);

        add(panelCentral, BorderLayout.CENTER);

        // ---------- EVENTOS ----------
        btnSalir.addActionListener(e -> salirDelSistema());
        btnAgregarLote.addActionListener(e -> AppNavigator.mostrar(new AgregarLoteView(gestor)));
        btnAgregarPersona.addActionListener(e -> mostrarMensaje("Abrir formulario para registrar responsable"));
        btnVerLotes.addActionListener(e -> mostrarMensaje("Mostrar lista de lotes registrados"));
        btnControlCalidad.addActionListener(e -> mostrarMensaje("Abrir módulo de control de calidad"));
    }

    // ---------- METODOS AUXILIARES ----------
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setBackground(Color.decode(colorPrimaryBtn));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode(colorPrimaryBtn)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(Color.decode(colorHoverPrimaryBtn));
            }

             @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.decode(colorPrimaryBtn));
            }

           
        });
        return boton;
    }

    private void mostrarMensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Función en desarrollo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void salirDelSistema() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Deseas salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }
}
