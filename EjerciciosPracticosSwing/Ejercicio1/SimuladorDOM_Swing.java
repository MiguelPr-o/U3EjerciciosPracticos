/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EjerciciosPracticosSwing.Ejercicio1;

/**
 * @author Miguel
 * Simulación de DOM – Creación de Página Web
 * Versión adaptada exactamente al ejemplo proporcionado por el usuario.
 * Muestra:
 *  - Árbol DOM a la izquierda
 *  - Código HTML en vivo a la derecha (en texto plano)
 *  - Controles inferiores: Etiqueta (combo), Texto y botón Agregar Nodo
 */

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

public class SimuladorDOM_Swing extends JFrame {

    private DefaultMutableTreeNode raiz;
    private DefaultTreeModel modelo;
    private JTree arbol;
    private JTextArea areaHTML;

    private JComboBox<String> comboEtiqueta;
    private JTextField campoTexto;

    public SimuladorDOM_Swing() {
        setTitle("Simulación de DOM – Creación de Página Web");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarDOM();
        inicializarInterfaz();
        actualizarHTML();
    }

    /**
     * Inicializa la estructura base del DOM
     */
    private void inicializarDOM() {
        raiz = new DefaultMutableTreeNode("html");
        modelo = new DefaultTreeModel(raiz);

        modelo.insertNodeInto(new DefaultMutableTreeNode("p - Bienvenido"), raiz, 0);
        modelo.insertNodeInto(new DefaultMutableTreeNode("h1 - Bienvenidos"), raiz, 1);
        modelo.insertNodeInto(new DefaultMutableTreeNode("footer - Copyright"), raiz, 2);
    }

    /**
     * Construye la interfaz gráfica exactamente como el ejemplo
     */
    private void inicializarInterfaz() {
        // Árbol
        arbol = new JTree(modelo);
        JScrollPane scrollArbol = new JScrollPane(arbol);

        // Área de código HTML
        areaHTML = new JTextArea();
        areaHTML.setEditable(false);
        JScrollPane scrollHTML = new JScrollPane(areaHTML);

        // Panel central dividido
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollArbol, scrollHTML);
        split.setDividerLocation(300);
        add(split, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelInferior.add(new JLabel("Etiqueta:"));
        comboEtiqueta = new JComboBox<>(new String[]{"p", "h1", "footer"});
        panelInferior.add(comboEtiqueta);

        panelInferior.add(new JLabel("Texto:"));
        campoTexto = new JTextField(20);
        panelInferior.add(campoTexto);

        JButton btnAgregar = new JButton("Agregar Nodo");
        panelInferior.add(btnAgregar);

        add(panelInferior, BorderLayout.SOUTH);

        // Evento del botón
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarNodo();
            }
        });
    }

    /**
     * Agrega un nodo como hijo directo de html
     */
    private void agregarNodo() {
        String etiqueta = comboEtiqueta.getSelectedItem().toString();
        String texto = campoTexto.getText();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un texto");
            return;
        }

        DefaultMutableTreeNode nuevo = new DefaultMutableTreeNode(etiqueta + " - " + texto);
        modelo.insertNodeInto(nuevo, raiz, raiz.getChildCount());

        campoTexto.setText("");
        actualizarHTML();
    }

    /**
     * Genera el código HTML a partir del árbol
     */
    private void actualizarHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");

        for (int i = 0; i < raiz.getChildCount(); i++) {
            String dato = raiz.getChildAt(i).toString();
            String[] partes = dato.split(" - ");

            String tag = partes[0];
            String texto = partes.length > 1 ? partes[1] : "";

            html.append("<").append(tag).append(">")
                    .append(texto)
                    .append("</").append(tag).append(">\n");
        }

        html.append("</html>");
        areaHTML.setText(html.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimuladorDOM_Swing().setVisible(true));
    }
}

