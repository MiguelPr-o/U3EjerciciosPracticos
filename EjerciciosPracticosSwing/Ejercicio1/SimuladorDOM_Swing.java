package EjerciciosPracticosSwing.Ejercicio1;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

public class SimuladorDOM_Swing extends JFrame {

    private DefaultMutableTreeNode raiz;
    private DefaultTreeModel modelo;
    private JTree arbol;
    private JTextArea areaHTML;
    private JEditorPane vistaPrevia;

    private JComboBox<String> comboEtiqueta;
    private JTextField campoTexto;

    public SimuladorDOM_Swing() {
        setTitle("Simulación de DOM – Creación de Página Web - Miguel Angel Hernandez Godinez");
        setSize(1150, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarDOM();
        inicializarInterfaz();
        actualizarHTML();
    }

    // ===== DOM INICIAL SIN BUTTON NI IMG =====
    private void inicializarDOM() {
        raiz = new DefaultMutableTreeNode("html");
        modelo = new DefaultTreeModel(raiz);

        modelo.insertNodeInto(new DefaultMutableTreeNode("h1 - Bienvenidos"), raiz, 0);
        modelo.insertNodeInto(new DefaultMutableTreeNode("p - Este es un simulador de DOM"), raiz, 1);
        modelo.insertNodeInto(new DefaultMutableTreeNode("a - https://www.google.com"), raiz, 2);
    }

    private void inicializarInterfaz() {

        // ----- Árbol -----
        arbol = new JTree(modelo);
        JScrollPane scrollArbol = new JScrollPane(arbol);

        // ----- HTML -----
        areaHTML = new JTextArea();
        areaHTML.setEditable(false);
        JScrollPane scrollHTML = new JScrollPane(areaHTML);

        // ----- Vista Previa -----
        vistaPrevia = new JEditorPane();
        vistaPrevia.setContentType("text/html");
        vistaPrevia.setEditable(false);
        JScrollPane scrollVista = new JScrollPane(vistaPrevia);

        JSplitPane splitDerecho = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scrollHTML,
                scrollVista
        );
        splitDerecho.setDividerLocation(280);

        JSplitPane splitPrincipal = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                scrollArbol,
                splitDerecho
        );
        splitPrincipal.setDividerLocation(300);

        add(splitPrincipal, BorderLayout.CENTER);

        // ----- Panel inferior -----
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelInferior.add(new JLabel("Etiqueta:"));

        comboEtiqueta = new JComboBox<>(new String[]{
                "p", "h1", "h2", "a", "div"
        });
        panelInferior.add(comboEtiqueta);

        panelInferior.add(new JLabel("Texto / URL:"));
        campoTexto = new JTextField(25);
        panelInferior.add(campoTexto);

        JButton btnAgregar = new JButton("Agregar Nodo");
        panelInferior.add(btnAgregar);

        add(panelInferior, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarNodo());
    }

    private void agregarNodo() {
        String etiqueta = comboEtiqueta.getSelectedItem().toString();
        String texto = campoTexto.getText();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un texto o URL");
            return;
        }

        modelo.insertNodeInto(
                new DefaultMutableTreeNode(etiqueta + " - " + texto),
                raiz,
                raiz.getChildCount()
        );

        campoTexto.setText("");
        actualizarHTML();
    }

    // ===== GENERADOR DE HTML SIN IMG NI BUTTON =====
    private void actualizarHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n<head>\n<style>\n")
            .append("body{font-family:Arial;padding:10px}\n")
            .append("div{border:1px dashed #999;padding:10px;margin:5px}\n")
            .append("</style>\n</head>\n<body>\n");

        for (int i = 0; i < raiz.getChildCount(); i++) {
            String dato = raiz.getChildAt(i).toString();
            String[] partes = dato.split(" - ");

            String tag = partes[0];
            String texto = partes.length > 1 ? partes[1] : "";

            if (tag.equals("a")) {
                html.append("<a href='").append(texto)
                    .append("' target='_blank'>")
                    .append(texto)
                    .append("</a><br>\n");
            } 
            else {
                html.append("<").append(tag).append(">")
                    .append(texto)
                    .append("</").append(tag).append(">\n");
            }
        }

        html.append("</body>\n</html>");

        areaHTML.setText(html.toString());
        vistaPrevia.setText(html.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new SimuladorDOM_Swing().setVisible(true));
    }
}
