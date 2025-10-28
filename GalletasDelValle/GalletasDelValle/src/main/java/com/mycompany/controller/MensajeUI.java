package com.mycompany.controller;

import javax.swing.JOptionPane;

public final class MensajeUI {
    private MensajeUI() {}

    public static void info(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void warn(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}