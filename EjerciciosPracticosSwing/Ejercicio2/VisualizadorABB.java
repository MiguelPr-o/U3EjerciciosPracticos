/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EjerciciosPracticosSwing.Ejercicio2;

/**
 * @author Miguel Angel Hernandez Godinez
 * Visualizador de Árbol Binario de Búsqueda (ABB)
 * Aplicación dinámica para comprender árboles, árboles binarios y ABB.
 * Incluye:
 * - Insertar
 * - Eliminar
 * - Buscar con resaltado
 * - Recorridos: InOrden, PreOrden, PostOrden
 * - Visualización gráfica del árbol
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class VisualizadorABB extends JFrame {

    // ================== ESTRUCTURA DEL ABB ==================
    class Nodo {
        int valor;
        Nodo izq, der;
        int x, y;     // posiciones para dibujar
        boolean resaltado = false;

        Nodo(int v) {
            valor = v;
        }
    }

    private Nodo raiz;

    // ================== COMPONENTES DE LA GUI ==================
    private JTextField txtValor;
    private JTextArea areaRecorridos;
    private PanelDibujo panelDibujo;

    public VisualizadorABB() {
        setTitle("Visualizador de Árbol Binario de Búsqueda (ABB) - Miguel Angel Hernandez Godinez");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        crearPanelSuperior();
        crearPanelCentral();
        crearPanelInferior();
    }

    // ================== PANEL SUPERIOR ==================
    private void crearPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Valor:"));
        txtValor = new JTextField(5);
        panel.add(txtValor);

        JButton btnInsertar = new JButton("+ Insertar");
        JButton btnEliminar = new JButton("- Eliminar");
        JButton btnBuscar   = new JButton("Buscar");
        JButton btnLimpiar  = new JButton("Limpiar Árbol");

        panel.add(btnInsertar);
        panel.add(btnEliminar);
        panel.add(btnBuscar);
        panel.add(btnLimpiar);

        panel.add(new JLabel("   Recorridos:"));

        JButton btnIn = new JButton("Recorrido InOrden");
        JButton btnPre = new JButton("Recorrido PreOrden");
        JButton btnPost = new JButton("Recorrido PostOrden");

        panel.add(btnIn);
        panel.add(btnPre);
        panel.add(btnPost);

        add(panel, BorderLayout.NORTH);

        // ================== EVENTOS ==================
        btnInsertar.addActionListener(e -> insertar());
        btnEliminar.addActionListener(e -> eliminar());
        btnBuscar.addActionListener(e -> buscar());
        btnLimpiar.addActionListener(e -> limpiar());

        btnIn.addActionListener(e -> mostrarInOrden());
        btnPre.addActionListener(e -> mostrarPreOrden());
        btnPost.addActionListener(e -> mostrarPostOrden());
    }

    // ================== PANEL CENTRAL ==================
    private void crearPanelCentral() {
        panelDibujo = new PanelDibujo();
        add(panelDibujo, BorderLayout.CENTER);
    }

    // ================== PANEL INFERIOR ==================
    private void crearPanelInferior() {
        areaRecorridos = new JTextArea(3, 80);
        areaRecorridos.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaRecorridos);
        add(scroll, BorderLayout.SOUTH);
    }

    // ================== OPERACIONES DEL ABB ==================
    private void insertar() {
        if (txtValor.getText().isEmpty()) return;
        int v = Integer.parseInt(txtValor.getText());
        raiz = insertarRec(raiz, v);
        txtValor.setText("");
        panelDibujo.repaint();
    }

    private Nodo insertarRec(Nodo n, int v) {
        if (n == null) return new Nodo(v);

        if (v < n.valor) n.izq = insertarRec(n.izq, v);
        else if (v > n.valor) n.der = insertarRec(n.der, v);
        // duplicados se ignoran

        return n;
    }

    private void eliminar() {
        if (txtValor.getText().isEmpty()) return;
        int v = Integer.parseInt(txtValor.getText());
        raiz = eliminarRec(raiz, v);
        txtValor.setText("");
        panelDibujo.repaint();
    }

    private Nodo eliminarRec(Nodo r, int v) {
        if (r == null) return null;

        if (v < r.valor) r.izq = eliminarRec(r.izq, v);
        else if (v > r.valor) r.der = eliminarRec(r.der, v);
        else {
            // Caso 1 y 2
            if (r.izq == null) return r.der;
            if (r.der == null) return r.izq;

            // Caso 3: dos hijos
            r.valor = sucesor(r.der);
            r.der = eliminarRec(r.der, r.valor);
        }
        return r;
    }

    private int sucesor(Nodo r) {
        int min = r.valor;
        while (r.izq != null) {
            min = r.izq.valor;
            r = r.izq;
        }
        return min;
    }

    private void buscar() {
        limpiarResaltado(raiz);
        if (txtValor.getText().isEmpty()) return;
        int v = Integer.parseInt(txtValor.getText());
        Nodo n = buscarRec(raiz, v);

        if (n != null) {
            n.resaltado = true;
        } else {
            JOptionPane.showMessageDialog(this, "Valor no encontrado");
        }
        panelDibujo.repaint();
    }

    private Nodo buscarRec(Nodo r, int v) {
        if (r == null) return null;
        if (v == r.valor) return r;
        if (v < r.valor) return buscarRec(r.izq, v);
        return buscarRec(r.der, v);
    }

    private void limpiar() {
        raiz = null;
        areaRecorridos.setText("");
        panelDibujo.repaint();
    }

    private void limpiarResaltado(Nodo r) {
        if (r == null) return;
        r.resaltado = false;
        limpiarResaltado(r.izq);
        limpiarResaltado(r.der);
    }

    // ================== RECORRIDOS ==================
    private void mostrarInOrden() {
        StringBuilder sb = new StringBuilder();
        inOrden(raiz, sb);
        areaRecorridos.setText("Recorrido InOrden: " + sb);
    }

    private void mostrarPreOrden() {
        StringBuilder sb = new StringBuilder();
        preOrden(raiz, sb);
        areaRecorridos.setText("Recorrido PreOrden: " + sb);
    }

    private void mostrarPostOrden() {
        StringBuilder sb = new StringBuilder();
        postOrden(raiz, sb);
        areaRecorridos.setText("Recorrido PostOrden: " + sb);
    }

    private void inOrden(Nodo r, StringBuilder sb) {
        if (r != null) {
            inOrden(r.izq, sb);
            sb.append(r.valor).append(" ");
            inOrden(r.der, sb);
        }
    }

    private void preOrden(Nodo r, StringBuilder sb) {
        if (r != null) {
            sb.append(r.valor).append(" ");
            preOrden(r.izq, sb);
            preOrden(r.der, sb);
        }
    }

    private void postOrden(Nodo r, StringBuilder sb) {
        if (r != null) {
            postOrden(r.izq, sb);
            postOrden(r.der, sb);
            sb.append(r.valor).append(" ");
        }
    }

    // ================== PANEL DE DIBUJO ==================
    class PanelDibujo extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (raiz != null) {
                calcularPosiciones(raiz, getWidth() / 2, 40, getWidth() / 4);
                dibujarArbol(g, raiz);
            }
        }

        private void calcularPosiciones(Nodo n, int x, int y, int separacion) {
            if (n == null) return;
            n.x = x;
            n.y = y;

            calcularPosiciones(n.izq, x - separacion, y + 70, separacion / 2);
            calcularPosiciones(n.der, x + separacion, y + 70, separacion / 2);
        }

        private void dibujarArbol(Graphics g, Nodo n) {
            if (n == null) return;

            if (n.izq != null) {
                g.drawLine(n.x, n.y, n.izq.x, n.izq.y);
            }
            if (n.der != null) {
                g.drawLine(n.x, n.y, n.der.x, n.der.y);
            }

            if (n.resaltado)
                g.setColor(Color.RED);
            else
                g.setColor(Color.BLUE);

            g.fillOval(n.x - 15, n.y - 15, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(n.valor), n.x - 7, n.y + 5);

            dibujarArbol(g, n.izq);
            dibujarArbol(g, n.der);
        }
    }

    // ================== MAIN ==================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisualizadorABB().setVisible(true));
    }
}

