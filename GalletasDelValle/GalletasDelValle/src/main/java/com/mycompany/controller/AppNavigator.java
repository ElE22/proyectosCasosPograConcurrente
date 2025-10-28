package com.mycompany.controller;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Gestor central de ventanas (navigator).
 * - Mantiene la ventana actual y permite mostrar una nueva, cerrando la anterior.
 * - Debe estar en el paquete controller.
 */
public final class AppNavigator {

    private static JFrame currentFrame;

    private AppNavigator() { /* no instanciable */ }

    /**
     * Muestra la ventana indicada y cierra la actual si existe.
     * @param frame la nueva ventana a mostrar
     */
    public static void mostrar(JFrame frame) {
        SwingUtilities.invokeLater(() -> {
            if (currentFrame != null) {
                try {
                    currentFrame.dispose();
                } catch (Exception ignored) { }
            }
            currentFrame = frame;
            currentFrame.setLocationRelativeTo(null);
            currentFrame.setVisible(true);
        });
    }

    /**
     * Cierra la ventana actual sin abrir otra.
     */
    public static void cerrarActual() {
        if (currentFrame != null) {
            try {
                currentFrame.dispose();
            } catch (Exception ignored) { }
            currentFrame = null;
        }
    }

    /**
     * Obtiene la ventana actual.
     */
    public static JFrame obtenerActual() {
        return currentFrame;
    }
}