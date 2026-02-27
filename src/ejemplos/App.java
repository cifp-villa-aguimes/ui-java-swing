package ejemplos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ejemplos.guibasica.EjemploGuiBasica;
import ejemplos.componentesbasicos.EjemploComponentesBasicos;
import ejemplos.componentesavanzados.EjemploComponentesAvanzados;
import ejemplos.eventos.EjemploEventosSwing;
import ejemplos.documentlistener.EjemploDocumentListener;
import ejemplos.panelcomunicacion.PanelComunicacion;
import ejemplos.panelcomunicacion.PanelComunicacionSimple;
import ejemplos.archivos.EjemploLeerArchivo;
import ejemplos.archivos.EjemploEscribirArchivo;
import ejemplos.archivos.EjemploJFileChooser;
import ejemplos.jtable.EjemploJTable;
import ejemplos.fileorganizer.EjemploFileOrganizerApp;
import ejemplos.lookandfeel.EjemploLookAndFeel;
import ejemplos.lookandfeel.EjemploUIManagerPersonalizado;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  APP – LANZADOR PRINCIPAL DE EJEMPLOS SWING                 ║
 * ║  UT5.2 – Interfaces Gráficas (1º DAW/DAM)                   ║
 * ║                                                             ║
 * ║  Esta clase actúa como menú principal del proyecto.         ║
 * ║  Muestra una lista con los 14 ejemplos; al seleccionar uno  ║
 * ║  aparece su descripción y al pulsar "Ejecutar" (o doble     ║
 * ║  clic) se abre la ventana correspondiente.                  ║
 * ║                                                             ║
 * ║  Estructura de datos interna:                               ║
 * ║    • NOMBRES[]      → textos que se ven en la JList         ║
 * ║    • DESCRIPCIONES[] → texto explicativo de cada ejemplo    ║
 * ║    • LANZADORES[]    → Runnable con la referencia al método ║
 * ║                        launch() de cada clase ejemplo       ║
 * ║                                                             ║
 * ║  Los tres arrays comparten el mismo índice:                 ║
 * ║    NOMBRES[i] ←→ DESCRIPCIONES[i] ←→ LANZADORES[i]          ║
 * ║                                                             ║
 * ║  Conceptos Swing que se usan aquí:                          ║
 * ║    • JList + ListSelectionListener (seleccionar)            ║
 * ║    • JSplitPane (dividir lista / descripción)               ║
 * ║    • MouseAdapter (doble clic)                              ║
 * ║    • UIManager + Nimbus (Look & Feel moderno)               ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class App {

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  1. DATOS DE LOS EJEMPLOS                                   ║
    // ║                                                             ║
    // ║  Tres arrays paralelos: NOMBRES, DESCRIPCIONES y LANZADORES ║
    // ║  Todos tienen 14 elementos (uno por ejemplo) y se acceden   ║
    // ║  por el mismo índice.                                       ║
    // ║                                                             ║
    // ║  Son «static final» porque:                                 ║
    // ║    • static → pertenecen a la clase, no a una instancia     ║
    // ║    • final  → la referencia no cambia (son constantes)      ║
    // ╚═════════════════════════════════════════════════════════════╝

    // Nombres que aparecen visualmente en la JList del lanzador
    private static final String[] NOMBRES = {
            " 1.  GUI Básica",
            " 2.  Componentes Básicos",
            " 3.  Componentes Avanzados",
            " 4.  Eventos Swing (ActionListener, KeyListener, MouseListener)",
            " 5.  DocumentListener",
            " 6.  Comunicación entre Paneles (referencia directa)",
            " 7.  Comunicación entre Paneles (objeto compartido)",
            " 8.  Leer Archivo de Texto",
            " 9.  Escribir Archivo de Texto",
            "10.  JFileChooser – Abrir archivo",
            "11.  JTable – Tabla de datos",
            "12.  File Organizer App (crear directorios y ficheros)",
            "13.  Look & Feel – Cambiar apariencia",
            "14.  UIManager Personalizado"
    };

    private static final String[] DESCRIPCIONES = {
            "Ventana interactiva con formulario: JLabel, JTextField, JButton y JTextArea.\n"
                    + "Usa layout nulo (setBounds) para posicionar componentes manualmente.\n"
                    + "Conceptos: JFrame, JPanel, JLabel, JTextField, JButton, JTextArea, ActionListener.",

            "Formulario completo con componentes básicos de entrada de datos.\n"
                    + "Incluye JTextField, JPasswordField, JCheckBox, JRadioButton y ButtonGroup.\n"
                    + "Conceptos: FlowLayout, JPasswordField, JCheckBox, JRadioButton, ButtonGroup, JScrollPane.",

            "Componentes avanzados de selección y ajuste con eventos en tiempo real.\n"
                    + "Incluye JComboBox, JList, JSlider, JSpinner y JProgressBar sincronizados.\n"
                    + "Conceptos: JComboBox, JList, JSlider, JSpinner, JProgressBar, ItemListener, ChangeListener.",

            "Monitor de eventos con log en tiempo real: ActionListener, KeyAdapter,\n"
                    + "MouseAdapter, MouseMotionAdapter y FocusAdapter.\n"
                    + "Conceptos: patrón Observador, interfaz vs clase adaptadora (Adapter), lambda.",

            "Escucha cambios en un campo de texto en tiempo real con DocumentListener.\n"
                    + "Cada vez que el usuario escribe o borra, la etiqueta se actualiza.\n"
                    + "Conceptos: DocumentListener, insertUpdate, removeUpdate, changedUpdate.",

            "Comunicación entre paneles pasando la referencia del componente compartido (JTextField).\n"
                    + "El panel de botones recibe directamente el campo de texto para leer su valor.\n"
                    + "Conceptos: paso de referencias entre métodos, BorderLayout.",

            "Comunicación entre paneles usando un objeto modelo (DatosCompartidos).\n"
                    + "Los paneles no se conocen entre sí; ambos acceden al mismo objeto de datos.\n"
                    + "Conceptos: modelo de datos compartido, CaretListener, desacoplamiento.",

            "Lee un fichero de texto (data/archivo.txt) y muestra su contenido en un JTextArea.\n"
                    + "Usa java.nio.file.Files.readAllLines() para la lectura.\n"
                    + "Conceptos: lectura de archivos, Paths, Files, JTextArea.",

            "Permite escribir texto en un JTextArea y guardarlo en un fichero (data/archivoGuardado.txt).\n"
                    + "Usa BufferedWriter / FileWriter para la escritura.\n"
                    + "Conceptos: escritura de archivos, BufferedWriter, FileWriter.",

            "Abre un diálogo del sistema para seleccionar un archivo y muestra su contenido.\n"
                    + "Usa JFileChooser con showOpenDialog().\n"
                    + "Conceptos: JFileChooser, BufferedReader, FileReader.",

            "Tabla de datos con JTable y DefaultTableModel.\n"
                    + "Permite eliminar filas seleccionadas con un botón.\n"
                    + "Conceptos: JTable, DefaultTableModel, personalización con UIManager.",

            "Aplicación completa que crea directorios y ficheros en disco.\n"
                    + "Usa GridBagLayout para el formulario y java.nio.file para operaciones de ficheros.\n"
                    + "Conceptos: GridBagLayout, Files.createDirectories, Files.writeString, JFrame (herencia).",

            "Permite cambiar el Look & Feel de la ventana en tiempo de ejecución.\n"
                    + "Incluye botones para Metal, Nimbus, Windows, Motif y Mac.\n"
                    + "Conceptos: UIManager.setLookAndFeel, SwingUtilities.updateComponentTreeUI.",

            "Personalización global de componentes Swing con UIManager.put().\n"
                    + "Cambia colores, fuentes y estilos de botones y etiquetas antes de crear la ventana.\n"
                    + "Conceptos: UIManager.put, personalización de apariencia, LookAndFeel del sistema."
    };

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  2. LANZADORES – Array de Runnable con method references    ║
    // ║                                                             ║
    // ║  ¿Qué es «EjemploGuiBasica::launch»?                        ║
    // ║  Es una REFERENCIA A MÉTODO (method reference), equivale a: ║
    // ║                                                             ║
    // ║    () -> EjemploGuiBasica.launch()                          ║
    // ║                                                             ║
    // ║  ¿Por qué funciona con Runnable?                            ║
    // ║  Runnable es una INTERFAZ FUNCIONAL: tiene un solo método   ║
    // ║  abstracto → run() (sin parámetros, sin retorno).           ║
    // ║  Java permite asignarle cualquier método que coincida con   ║
    // ║  esa misma firma. Como launch() tampoco tiene parámetros    ║
    // ║  ni retorno, encaja perfectamente.                          ║
    // ║                                                             ║
    // ║  Ventaja: código más limpio que usar lambdas explícitas.    ║
    // ║                                                             ║
    // ║  Para lanzar un ejemplo basta con:                          ║
    // ║    LANZADORES[idx].run();                                   ║
    // ╚═════════════════════════════════════════════════════════════╝
    private static final Runnable[] LANZADORES = {
            EjemploGuiBasica::launch,
            EjemploComponentesBasicos::launch,
            EjemploComponentesAvanzados::launch,
            EjemploEventosSwing::launch,
            EjemploDocumentListener::launch,
            PanelComunicacion::launch,
            PanelComunicacionSimple::launch,
            EjemploLeerArchivo::launch,
            EjemploEscribirArchivo::launch,
            EjemploJFileChooser::launch,
            EjemploJTable::launch,
            EjemploFileOrganizerApp::launch,
            EjemploLookAndFeel::launch,
            EjemploUIManagerPersonalizado::launch
    };

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  3. MÉTODO MAIN – Punto de entrada de la aplicación         ║
    // ║                                                             ║
    // ║  ¿Por qué usamos SwingUtilities.invokeLater()?              ║
    // ║                                                             ║
    // ║  Swing tiene un hilo especial llamado EDT (Event Dispatch   ║
    // ║  Thread). TODA la creación y manipulación de componentes    ║
    // ║  Swing DEBE hacerse en ese hilo.                            ║
    // ║                                                             ║
    // ║  Problema: el método main() se ejecuta en el hilo "main",  ║
    // ║  que NO es el EDT. Si creamos la interfaz directamente      ║
    // ║  aquí, podríamos tener problemas de concurrencia.           ║
    // ║                                                             ║
    // ║  Solución: invokeLater() ENCOLA la tarea en el EDT.         ║
    // ║  Es decir, le dice a Swing: "cuando puedas, ejecuta este    ║
    // ║  código en tu hilo seguro".                                 ║
    // ║                                                             ║
    // ║  En la práctica, para apps pequeñas la diferencia es        ║
    // ║  imperceptible, pero es la forma CORRECTA de hacerlo        ║
    // ║  y una buena costumbre desde el principio.                  ║
    // ╚═════════════════════════════════════════════════════════════╝

    public static void main(String[] args) {
        // invokeLater() recibe un Runnable (aquí como method reference)
        // y lo encola en el EDT para que se ejecute de forma segura.
        SwingUtilities.invokeLater(App::iniciarAplicacion);
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  3b. CONSTRUCCIÓN DE LA INTERFAZ                            ║
    // ║                                                             ║
    // ║  Este método se ejecuta en el EDT (gracias a invokeLater).  ║
    // ║  Aquí es seguro crear componentes, añadir listeners y       ║
    // ║  mostrar ventanas.                                          ║
    // ║                                                             ║
    // ║  Flujo:                                                     ║
    // ║    1. Aplicar Nimbus como Look & Feel global                ║
    // ║    2. Crear la ventana principal (JFrame)                   ║
    // ║    3. Montar los componentes: lista, descripción, botón     ║
    // ║    4. Registrar listeners: selección, clic, doble clic      ║
    // ║    5. Mostrar la ventana                                    ║
    // ╚═════════════════════════════════════════════════════════════╝

    private static void iniciarAplicacion() {

        // Nimbus da un aspecto moderno con degradados y bordes suaves.
        // Si no está disponible, Java usa Metal (el L&F por defecto).
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Nimbus. Usando LookAndFeel por defecto.");
        }

        // ──────────────────────────────────────────────────────────────
        //  Ventana principal (JFrame)
        //  • EXIT_ON_CLOSE → al cerrar esta ventana se cierra toda la app
        //  • setLocationRelativeTo(null) → centra la ventana en pantalla
        // ──────────────────────────────────────────────────────────────
        JFrame frame = new JFrame("UT5.2 - Ejemplos Java Swing");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // BorderLayout divide el panel en 5 zonas: NORTH, SOUTH, EAST, WEST, CENTER.
        // Los parámetros (10, 10) son el espacio horizontal y vertical entre zonas.
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ──────────────────────────────────────────────────────────────
        //  Título: JLabel centrado en la zona NORTH del BorderLayout
        // ──────────────────────────────────────────────────────────────
        JLabel titulo = new JLabel("UT5.2 – Ejemplos Java Swing", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // ──────────────────────────────────────────────────────────────
        //  JList<String> → lista con los nombres de los 14 ejemplos.
        //  Se envuelve en JScrollPane para que aparezca scroll si
        //  hay más elementos de los que caben en el espacio visible.
        //  SINGLE_SELECTION → solo se puede seleccionar un ejemplo.
        // ──────────────────────────────────────────────────────────────
        JList<String> lista = new JList<>(NOMBRES);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollLista = new JScrollPane(lista);
        scrollLista.setPreferredSize(new Dimension(560, 200));

        // ──────────────────────────────────────────────────────────────
        //  JTextArea (solo lectura) para mostrar la descripción del
        //  ejemplo seleccionado.
        //  • setEditable(false)   → el usuario no puede escribir
        //  • setLineWrap(true)    → el texto salta de línea automátic.
        //  • setWrapStyleWord(true) → corta por palabras, no letras
        // ──────────────────────────────────────────────────────────────
        JTextArea areaDescripcion = new JTextArea("Selecciona un ejemplo de la lista para ver su descripción.");
        areaDescripcion.setEditable(false);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 13));
        areaDescripcion.setBackground(new Color(245, 245, 245));
        JScrollPane scrollDesc = new JScrollPane(areaDescripcion);
        scrollDesc.setPreferredSize(new Dimension(560, 120));

        // ListSelectionListener: se dispara cada vez que cambia la selección.
        // getValueIsAdjusting() devuelve true mientras el usuario arrastra;
        // solo actualizamos cuando YA ha terminado de seleccionar (false).
        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int idx = lista.getSelectedIndex();
                if (idx >= 0 && idx < DESCRIPCIONES.length) {
                    areaDescripcion.setText(DESCRIPCIONES[idx]);
                    areaDescripcion.setCaretPosition(0); // scroll al inicio
                }
            }
        });

        // JSplitPane divide el espacio en dos: lista (arriba) y descripción (abajo).
        // VERTICAL_SPLIT → la barra divisora es horizontal (divide arriba/abajo).
        // setResizeWeight(0.6) → la lista recibe el 60% del espacio disponible.
        // El usuario puede arrastrar la barra para ajustar las proporciones.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollLista, scrollDesc);
        splitPane.setResizeWeight(0.6);
        panelPrincipal.add(splitPane, BorderLayout.CENTER);

        // ──────────────────────────────────────────────────────────────
        //  Botón "Ejecutar" + doble clic en la lista
        //  Ambos llaman al mismo método ejecutarEjemplo(), evitando
        //  duplicar código (principio DRY: Don't Repeat Yourself).
        //
        //  \u25B6 es el carácter Unicode "▶" (triángulo play).
        // ──────────────────────────────────────────────────────────────
        JButton botonEjecutar = new JButton("\u25B6  Ejecutar Ejemplo");
        botonEjecutar.setFont(new Font("SansSerif", Font.BOLD, 14));
        botonEjecutar.addActionListener(e -> ejecutarEjemplo(lista, frame));

        // MouseAdapter: solo sobreescribimos mouseClicked (no necesitamos
        // mousePressed, mouseReleased, etc.).
        // getClickCount() == 2 → detecta el doble clic.
        lista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    ejecutarEjemplo(lista, frame);
                }
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonEjecutar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        frame.add(panelPrincipal);
        frame.setVisible(true); // Mostrar la ventana (siempre al final)
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  4. MÉTODO AUXILIAR – ejecutarEjemplo()                     ║
    // ║                                                             ║
    // ║  Obtiene el índice seleccionado en la JList y ejecuta       ║
    // ║  LANZADORES[idx].run(), que a su vez llama al launch()      ║
    // ║  del ejemplo correspondiente.                               ║
    // ║                                                             ║
    // ║  Si no hay selección (idx < 0), muestra un aviso con        ║
    // ║  JOptionPane.showMessageDialog().                           ║
    // ╚═════════════════════════════════════════════════════════════╝

    private static void ejecutarEjemplo(JList<String> lista, JFrame parent) {
        int idx = lista.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(parent,
                    "Selecciona un ejemplo de la lista antes de ejecutar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        LANZADORES[idx].run();
    }
}
