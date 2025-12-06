/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EjerciciosPracticosSwing.Ejercicio3;

/**
 * EJERCICIO 3 - Manejo de Conjuntos en Java Swing
 * ----------------------------------------------
 * Aplicación educativa que simula el registro y manejo de alumnos utilizando
 * estructuras de conjuntos (Set). La aplicación cumple con los requisitos:
 *  - Usa Java Swing para la interfaz gráfica.
 *  - Maneja una entidad con al menos 5 datos (ID, Nombre, Edad, Curso, Email).
 *  - Implementa >=6 operaciones de conjuntos (add, remove, contains, union,
 *    intersection, difference, clear, size).
 *  - Código documentado con Javadoc y comentarios significativos.
 *
 * Cómo usar:
 *  - Ingresa los datos del alumno en los campos inferiores.
 *  - Usa "Agregar a Conjunto A" o "Agregar a Conjunto B" para insertar.
 *  - Usa los botones de operación para realizar unión, intersección, etc.
 *  - Los resultados se muestran en el área central.
 *
 * @author: Miguel Angel Hernandez Godinez
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class ConjuntosSwing extends JFrame {

    /** Representa un alumno con 5 atributos. Igualdad basada en id. */
    public static class Student {
        private final String id;    // identificador único
        private String name;
        private int age;
        private String course;
        private String email;

        /**
         * Crea un nuevo estudiante.
         * @param id Identificador único (no nulo)
         * @param name Nombre completo
         * @param age Edad (número entero)
         * @param course Curso o materia
         * @param email Correo electrónico
         */
        public Student(String id, String name, int age, String course, String email) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.course = course;
            this.email = email;
        }

        // Getters y setters (pocos, por brevedad)
        public String getId() { return id; }
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getCourse() { return course; }
        public String getEmail() { return email; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return id.equals(student.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return String.format("%s | %s | %d | %s | %s", id, name, age, course, email);
        }
    }

    // ==================== Conjuntos ====================
    private final Set<Student> conjuntoA = new HashSet<>();
    private final Set<Student> conjuntoB = new HashSet<>();

    // ==================== Componentes GUI ====================
    private final JTextField txtId = new JTextField(8);
    private final JTextField txtName = new JTextField(15);
    private final JTextField txtAge = new JTextField(3);
    private final JTextField txtCourse = new JTextField(10);
    private final JTextField txtEmail = new JTextField(15);

    private final JTextArea txtArea = new JTextArea();

    /**
     * Constructor: configura la ventana y componentes
     */
    public ConjuntosSwing() {
        super("EJERCICIO 3 - Manejo de Conjuntos (HashSet) - Registro de Alumnos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        initUI();
    }

    /**
     * Inicializa y organiza la interfaz gráfica.
     */
    private void initUI() {
        setLayout(new BorderLayout(8, 8));

        // Panel superior: título e instrucciones
        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Registro y Operaciones con Conjuntos (Alumnos)", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.add(title, BorderLayout.NORTH);

        JLabel instr = new JLabel("Ingresa los datos del alumno y usa los botones para operar sobre Conjunto A y Conjunto B.");
        instr.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
        header.add(instr, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        // Centro: área de resultados (lista y mensajes)
        txtArea.setEditable(false);
        txtArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtArea);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior: formularios y botones
        JPanel bottom = new JPanel(new BorderLayout());

        // Formulario para ingresar los 5 datos (ID, Name, Age, Course, Email)
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.setBorder(BorderFactory.createTitledBorder("Datos del alumno (5 datos)"));
        form.add(new JLabel("ID:")); form.add(txtId);
        form.add(new JLabel("Nombre:")); form.add(txtName);
        form.add(new JLabel("Edad:")); form.add(txtAge);
        form.add(new JLabel("Curso:")); form.add(txtCourse);
        form.add(new JLabel("Email:")); form.add(txtEmail);

        bottom.add(form, BorderLayout.NORTH);

        // Botonera de operaciones
        JPanel ops = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ops.setBorder(BorderFactory.createTitledBorder("Operaciones con conjuntos (>=6)"));

        JButton btnAddA = new JButton("Agregar a Conjunto A");
        JButton btnAddB = new JButton("Agregar a Conjunto B");
        JButton btnRemove = new JButton("Eliminar (por ID)");
        JButton btnSearch = new JButton("Buscar (por ID)");
        JButton btnUnion = new JButton("Unión (A ∪ B)");
        JButton btnInter = new JButton("Intersección (A ∩ B)");
        JButton btnDiff = new JButton("Diferencia (A - B)");
        JButton btnClear = new JButton("Limpiar Todo");
        JButton btnSize = new JButton("Tamaños");
        JButton btnExport = new JButton("Exportar a archivo");

        ops.add(btnAddA); ops.add(btnAddB); ops.add(btnRemove); ops.add(btnSearch);
        ops.add(btnUnion); ops.add(btnInter); ops.add(btnDiff); ops.add(btnClear);
        ops.add(btnSize); ops.add(btnExport);

        bottom.add(ops, BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);

        // ============ Eventos de botones ============

        // Agregar a A
        btnAddA.addActionListener(e -> {
            Student s = readStudentFromForm();
            if (s == null) return;
            boolean added = conjuntoA.add(s); // operación add
            append(String.format("Agregar a A: %s -> %s\n", s.getId(), added ? "OK" : "Ya existía"));
            showSetsBrief();
            clearForm();
        });

        // Agregar a B
        btnAddB.addActionListener(e -> {
            Student s = readStudentFromForm();
            if (s == null) return;
            boolean added = conjuntoB.add(s);
            append(String.format("Agregar a B: %s -> %s\n", s.getId(), added ? "OK" : "Ya existía"));
            showSetsBrief();
            clearForm();
        });

        // Eliminar por ID (de ambos conjuntos)
        btnRemove.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Ingresa ID para eliminar"); return; }
            boolean removedA = removeById(conjuntoA, id);
            boolean removedB = removeById(conjuntoB, id);
            append(String.format("Eliminar ID=%s -> A:%s, B:%s\n", id, removedA, removedB));
            showSetsBrief();
        });

        // Buscar por ID
        btnSearch.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Ingresa ID para buscar"); return; }
            Student a = findById(conjuntoA, id);
            Student b = findById(conjuntoB, id);
            append(String.format("Buscar ID=%s -> A:%s, B:%s\n", id, a==null?"-":a, b==null?"-":b));
        });

        // Unión
        btnUnion.addActionListener(e -> {
            Set<Student> union = new HashSet<>(conjuntoA);
            union.addAll(conjuntoB); // operación union: addAll
            append("UNIÓN (A ∪ B):\n");
            printSet(union);
        });

        // Intersección
        btnInter.addActionListener(e -> {
            Set<Student> inter = new HashSet<>(conjuntoA);
            inter.retainAll(conjuntoB); // operación intersección: retainAll
            append("INTERSECCIÓN (A ∩ B):\n");
            printSet(inter);
        });

        // Diferencia A - B
        btnDiff.addActionListener(e -> {
            Set<Student> diff = new HashSet<>(conjuntoA);
            diff.removeAll(conjuntoB); // operación diferencia: removeAll
            append("DIFERENCIA (A - B):\n");
            printSet(diff);
        });

        // Limpiar
        btnClear.addActionListener(e -> {
            conjuntoA.clear(); // clear
            conjuntoB.clear();
            append("Ambos conjuntos limpiados\n");
            showSetsBrief();
        });

        // Tamaños (size)
        btnSize.addActionListener(e -> {
            append(String.format("Tamaños -> A: %d, B: %d\n", conjuntoA.size(), conjuntoB.size()));
        });

        // Exportar a archivo (simple texto)
        btnExport.addActionListener(e -> {
            exportSetsToFile();
        });

        // Poblado inicial de ejemplo (>=5 datos manejados en la app)
        preloadExampleData();
        showSetsBrief();
    }

    // ==================== Métodos auxiliares ====================

    /**
     * Lee los campos del formulario y construye un Student.
     * Valida campos básicos y muestra mensajes de error si faltan.
     * @return Student o null si hubo error
     */
    private Student readStudentFromForm() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String course = txtCourse.getText().trim();
        String email = txtEmail.getText().trim();

        if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || course.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son requeridos (ID, Nombre, Edad, Curso, Email)");
            return null;
        }
        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad inválida (debe ser un número)");
            return null;
        }
        return new Student(id, name, age, course, email);
    }

    /**
     * Elimina un estudiante por id en el conjunto dado.
     * @param set conjunto donde eliminar
     * @param id identificador
     * @return true si se eliminó alguno
     */
    private boolean removeById(Set<Student> set, String id) {
        Iterator<Student> it = set.iterator();
        boolean removed = false;
        while (it.hasNext()) {
            Student s = it.next();
            if (s.getId().equals(id)) {
                it.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Busca un estudiante por id en el conjunto.
     * @param set conjunto
     * @param id identificador
     * @return Student si lo encuentra o null
     */
    private Student findById(Set<Student> set, String id) {
        for (Student s : set) if (s.getId().equals(id)) return s;
        return null;
    }

    /** Agrega texto al área de salida (con salto de línea) */
    private void append(String s) {
        txtArea.append(s);
        txtArea.setCaretPosition(txtArea.getDocument().getLength());
    }

    /** Muestra un resumen breve de los conjuntos A y B */
    private void showSetsBrief() {
        append("\n=== Conjunto A (" + conjuntoA.size() + ") ===\n");
        printSetBrief(conjuntoA);
        append("=== Conjunto B (" + conjuntoB.size() + ") ===\n");
        printSetBrief(conjuntoB);
        append("\n");
    }

    /** Imprime un conjunto en formato legible (completo) */
    private void printSet(Set<Student> set) {
        if (set.isEmpty()) append("(vacío)\n");
        else {
            for (Student s : set) append(s + "\n");
        }
    }

    /** Imprime versión breve (IDs y nombres) */
    private void printSetBrief(Set<Student> set) {
        if (set.isEmpty()) append("(vacío)\n");
        else {
            for (Student s : set) append(s.getId() + " -> " + s.getName() + "\n");
        }
    }

    /** Limpia los campos del formulario */
    private void clearForm() {
        txtId.setText(""); txtName.setText(""); txtAge.setText(""); txtCourse.setText(""); txtEmail.setText("");
    }

    /** Exporta los conjuntos a un archivo de texto simple (conjuntos_export.txt) */
    private void exportSetsToFile() {
        try (FileWriter fw = new FileWriter("conjuntos_export.txt")) {
            fw.write("Conjunto A:\n");
            for (Student s : conjuntoA) fw.write(s + "\n");
            fw.write("\nConjunto B:\n");
            for (Student s : conjuntoB) fw.write(s + "\n");
            append("Exportado a conjuntos_export.txt\n");
        } catch (IOException ex) {
            append("Error exportando: " + ex.getMessage() + "\n");
        }
    }

    /**
     * Poblado inicial con varios estudiantes de ejemplo.
     * Asegura que la app maneje al menos 5 datos de información y varios elementos.
     */
    private void preloadExampleData() {
        // 7 ejemplos distintos
        Student s1 = new Student("A001", "Ana López", 20, "Programación", "ana.lopez@example.com");
        Student s2 = new Student("A002", "Luis Pérez", 21, "Redes", "luis.perez@example.com");
        Student s3 = new Student("A003", "María Ruiz", 19, "Base de Datos", "maria.ruiz@example.com");
        Student s4 = new Student("A004", "Jorge Díaz", 22, "Sistemas", "jorge.diaz@example.com");
        Student s5 = new Student("A005", "Sofía Gómez", 20, "Matemáticas", "sofia.gomez@example.com");
        Student s6 = new Student("A006", "Carlos Vega", 23, "Programación", "carlos.vega@example.com");
        Student s7 = new Student("A007", "Elena Mora", 20, "Redes", "elena.mora@example.com");

        // Agregamos a A y B para tener datos iniciales
        conjuntoA.add(s1); conjuntoA.add(s2); conjuntoA.add(s3); conjuntoA.add(s4);
        conjuntoB.add(s3); conjuntoB.add(s5); conjuntoB.add(s6); conjuntoB.add(s7);

        append("Poblado inicial realizado (7 estudiantes, 5+ datos por estudiante)\n");
    }

    // ==================== Punto de entrada ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConjuntosSwing app = new ConjuntosSwing();
            app.setVisible(true);
        });
    }
}

