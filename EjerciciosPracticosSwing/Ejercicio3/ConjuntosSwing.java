/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EjerciciosPracticosSwing.Ejercicio3;

/**
 * @author Miguel Angel Hernandez Godinez
 * EJERCICIO 3 - Uso de Conjuntos en Java con Interfaz Swing
 * Tema: Registro de alumnos en un salón de clases
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class ConjuntosSwing extends JFrame {

    // ============================
    // Conjuntos de alumnos
    // ============================
    Set<String> grupoA = new HashSet<>();
    Set<String> grupoB = new HashSet<>();

    // ============================
    // Componentes Gráficos
    // ============================
    JTextField txtNombre;
    JTextArea areaResultado;

    public ConjuntosSwing() {

        // ----------------------------
        // Configuración de la Ventana
        // ----------------------------
        setTitle("Manejo de Conjuntos - Registro de Alumnos");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ----------------------------
        // Panel Principal
        // ----------------------------
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // ----------------------------
        // Panel Superior (Entrada)
        // ----------------------------
        JPanel panelSuperior = new JPanel();

        panelSuperior.add(new JLabel("Nombre del Alumno: "));
        txtNombre = new JTextField(15);
        panelSuperior.add(txtNombre);

        JButton btnAgregarA = new JButton("Agregar a Grupo A");
        JButton btnAgregarB = new JButton("Agregar a Grupo B");

        panelSuperior.add(btnAgregarA);
        panelSuperior.add(btnAgregarB);

        panel.add(panelSuperior, BorderLayout.NORTH);

        // ----------------------------
        // Área de Resultados
        // ----------------------------
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        panel.add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        // ----------------------------
        // Panel Inferior (Operaciones)
        // ----------------------------
        JPanel panelInferior = new JPanel();

        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnUnion = new JButton("Unión");
        JButton btnInterseccion = new JButton("Intersección");
        JButton btnDiferencia = new JButton("Diferencia");
        JButton btnLimpiar = new JButton("Limpiar Todo");

        panelInferior.add(btnEliminar);
        panelInferior.add(btnBuscar);
        panelInferior.add(btnUnion);
        panelInferior.add(btnInterseccion);
        panelInferior.add(btnDiferencia);
        panelInferior.add(btnLimpiar);

        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);

        // ============================
        // EVENTOS
        // ============================

        // ➕ Agregar alumno al Grupo A
        btnAgregarA.addActionListener(e -> {
            String nombre = txtNombre.getText();
            grupoA.add(nombre);
            mostrarDatos();
        });

        // ➕ Agregar alumno al Grupo B
        btnAgregarB.addActionListener(e -> {
            String nombre = txtNombre.getText();
            grupoB.add(nombre);
            mostrarDatos();
        });

        // ❌ Eliminar alumno de ambos grupos
        btnEliminar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            grupoA.remove(nombre);
            grupoB.remove(nombre);
            mostrarDatos();
        });

        // 🔍 Buscar alumno
        btnBuscar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            boolean enA = grupoA.contains(nombre);
            boolean enB = grupoB.contains(nombre);

            areaResultado.setText(
                "Resultado de Búsqueda:\n" +
                "Grupo A: " + enA + "\n" +
                "Grupo B: " + enB
            );
        });

        // 📦 Unión de conjuntos
        btnUnion.addActionListener(e -> {
            Set<String> union = new HashSet<>(grupoA);
            union.addAll(grupoB);

            areaResultado.setText("UNIÓN:\n" + union);
        });

        // 🔁 Intersección
        btnInterseccion.addActionListener(e -> {
            Set<String> interseccion = new HashSet<>(grupoA);
            interseccion.retainAll(grupoB);

            areaResultado.setText("INTERSECCIÓN:\n" + interseccion);
        });

        // ➖ Diferencia (A - B)
        btnDiferencia.addActionListener(e -> {
            Set<String> diferencia = new HashSet<>(grupoA);
            diferencia.removeAll(grupoB);

            areaResultado.setText("DIFERENCIA (A - B):\n" + diferencia);
        });

        // 🧹 Limpiar todos los conjuntos
        btnLimpiar.addActionListener(e -> {
            grupoA.clear();
            grupoB.clear();
            areaResultado.setText("Todos los conjuntos fueron limpiados");
        });
    }

    // ============================
    // Método para mostrar datos
    // ============================
    private void mostrarDatos() {
        areaResultado.setText(
            "GRUPO A:\n" + grupoA + "\n\n" +
            "GRUPO B:\n" + grupoB
        );
        txtNombre.setText("");
    }

    // ============================
    // Método Principal
    // ============================
    public static void main(String[] args) {
        new ConjuntosSwing().setVisible(true);
    }
}

