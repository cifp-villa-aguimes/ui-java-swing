package ejemplos.fileorganizer;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 12 – File Organizer (crear directorios/ficheros)   ║
 * ║                                                             ║
 * ║  Aplicación que crea estructura de carpetas y archivos      ║
 * ║  en disco usando la API java.nio.file.                      ║
 * ║                                                             ║
 * ║  APIs demostradas:                                          ║
 * ║    • Files.createDirectories(path) → crea carpetas          ║
 * ║      (incluye las intermedias, como mkdir -p en Linux)      ║
 * ║    • Files.writeString(path, texto) → escribe un fichero    ║
 * ║    • Path = Paths.get("ruta") / path.resolve("nombre")      ║
 * ║                                                             ║
 * ║  Conceptos Swing:                                           ║
 * ║    • GridBagLayout → posicionar componentes en cuadrícula   ║
 * ║    • JFrame como herencia (extends JFrame)                  ║
 * ║    • SwingUtilities.invokeLater → lanzar la ventana         ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploFileOrganizerApp extends JFrame {

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  ATRIBUTOS DE INSTANCIA                                     ║
    // ║                                                             ║
    // ║  Esta clase extiende JFrame. Los componentes de la UI       ║
    // ║  son atributos de instancia, no variables locales.          ║
    // ║  Así, los métodos privados pueden acceder a ellos           ║
    // ║  sin pasar parámetros.                                      ║
    // ╚═════════════════════════════════════════════════════════════╝
    private JTextField campoCarpeta;
    private JTextField campoSubcarpeta;
    private JTextField campoNombreArchivo;
    private JTextArea areaContenido;
    private JTextArea logResultados;
    private JLabel barraEstado;

    public EjemploFileOrganizerApp() {
        setTitle("Ejemplo 12 – Organizador de Ficheros");
        setSize(600, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  FORMULARIO CON GridBagLayout                               ║
        // ║                                                             ║
        // ║  GridBagLayout permite colocar componentes en una cuadrícula║
        // ║  flexible. Es el layout más potente pero también el más     ║
        // ║  complejo de Swing.                                         ║
        // ║                                                             ║
        // ║  GridBagConstraints configura CADA componente:              ║
        // ║    gridx, gridy  → posición en la cuadrícula (col, fila)    ║
        // ║    fill          → cómo expandirse (HORIZONTAL, BOTH...)    ║
        // ║    weightx       → peso de expansión horizontal             ║
        // ║    insets         → márgenes alrededor del componente       ║
        // ║    gridwidth     → cuántas columnas ocupa                   ║
        // ╚═════════════════════════════════════════════════════════════╝
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la estructura"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 0: Carpeta principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Carpeta:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoCarpeta = new JTextField("data");
        panelFormulario.add(campoCarpeta, gbc);

        // Fila 1: Subcarpeta
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Subcarpeta:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoSubcarpeta = new JTextField("nueva_estructura");
        panelFormulario.add(campoSubcarpeta, gbc);

        // Fila 2: Nombre del archivo
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Archivo:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        campoNombreArchivo = new JTextField("documento.txt");
        panelFormulario.add(campoNombreArchivo, gbc);

        // Fila 3: Contenido del archivo (ocupa más altura)
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Contenido:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        areaContenido = new JTextArea(4, 20);
        areaContenido.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaContenido.setText("Hola, esto es contenido de ejemplo.\nPuedes cambiarlo.");
        panelFormulario.add(new JScrollPane(areaContenido), gbc);

        // ──────────────────────────────────────────────────────────────
        //  Log de acciones realizadas
        // ──────────────────────────────────────────────────────────────
        logResultados = new JTextArea();
        logResultados.setEditable(false);
        logResultados.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollLog = new JScrollPane(logResultados);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log de operaciones"));
        scrollLog.setPreferredSize(new Dimension(0, 120));

        // ──────────────────────────────────────────────────────────────
        //  Botones de acción
        // ──────────────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        JButton botonCrear = new JButton("Crear estructura");
        JButton botonLimpiar = new JButton("Limpiar formulario");
        panelBotones.add(botonCrear);
        panelBotones.add(botonLimpiar);

        // — Barra de estado —
        barraEstado = new JLabel("  Introduce los datos y pulsa \"Crear estructura\"");
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  CREAR LA ESTRUCTURA DE FICHEROS                            ║
        // ║                                                             ║
        // ║  Pasos:                                                     ║
        // ║    1. Construir Path: Paths.get(carpeta, subcarpeta)        ║
        // ║    2. Crear carpetas: Files.createDirectories(dirPath)      ║
        // ║    3. Resolver fichero: dirPath.resolve(nombre)             ║
        // ║    4. Escribir: Files.writeString(filePath, contenido)      ║
        // ╚═════════════════════════════════════════════════════════════╝

        botonCrear.addActionListener(e -> crearEstructura());

        botonLimpiar.addActionListener(e -> {
            campoCarpeta.setText("data");
            campoSubcarpeta.setText("");
            campoNombreArchivo.setText("");
            areaContenido.setText("");
            campoCarpeta.requestFocus();
            barraEstado.setText("  Formulario limpiado");
        });

        // ──────────────────────────────────────────────────────────────
        //  MONTAJE FINAL
        // ──────────────────────────────────────────────────────────────
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelBotones, BorderLayout.NORTH);
        panelSur.add(scrollLog, BorderLayout.CENTER);
        panelSur.add(barraEstado, BorderLayout.SOUTH);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Crea el directorio, subdirectorio y fichero con el contenido indicado.
     */
    private void crearEstructura() {
        String carpeta = campoCarpeta.getText().trim();
        String subcarpeta = campoSubcarpeta.getText().trim();
        String nombre = campoNombreArchivo.getText().trim();
        String contenido = areaContenido.getText();

        if (carpeta.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "La carpeta y el nombre del archivo son obligatorios.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // 1. Construir la ruta del directorio (incluye subcarpeta si existe)
            Path dirPath = subcarpeta.isEmpty()
                    ? Paths.get(carpeta)
                    : Paths.get(carpeta, subcarpeta);

            // 2. Files.createDirectories() crea todas las carpetas intermedias
            //    Si ya existen, no lanza error (a diferencia de createDirectory)
            Files.createDirectories(dirPath);
            logResultados.append("✔ Carpetas creadas: " + dirPath.toAbsolutePath() + "\n");

            // 3. resolve() une la ruta del directorio + nombre del fichero
            Path filePath = dirPath.resolve(nombre);

            // 4. Files.writeString() escribe contenido en el fichero (Java 11+)
            //    Si ya existe, lo sobreescribe por defecto.
            Files.writeString(filePath, contenido, StandardCharsets.UTF_8);
            logResultados.append("✔ Archivo creado: " + filePath.toAbsolutePath() + "\n");
            logResultados.append("  → " + contenido.length() + " caracteres escritos\n");
            logResultados.append("─────────────────────────────────────────\n");
            logResultados.setCaretPosition(logResultados.getDocument().getLength());

            barraEstado.setText("  ✔ Estructura creada: " + filePath.toAbsolutePath());

        } catch (IOException ex) {
            logResultados.append("✘ Error: " + ex.getMessage() + "\n");
            barraEstado.setText("  ✘ Error al crear la estructura");
            JOptionPane.showMessageDialog(this,
                    "Error al crear la estructura:\n" + ex.getMessage(),
                    "Error de E/S", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  SwingUtilities.invokeLater()                               ║
    // ║                                                             ║
    // ║  Garantiza que la creación de componentes Swing se ejecute  ║
    // ║  en el EDT (Event Dispatch Thread), el hilo dedicado a la   ║
    // ║  interfaz gráfica. Es la forma "correcta" de iniciar una    ║
    // ║  ventana Swing, aunque en ejemplos simples funciona sin     ║
    // ║  invokeLater.                                               ║
    // ╚═════════════════════════════════════════════════════════════╝
    public static void launch() {
        SwingUtilities.invokeLater(() -> new EjemploFileOrganizerApp().setVisible(true));
    }

    public static void main(String[] args) {
        launch();
    }
}
