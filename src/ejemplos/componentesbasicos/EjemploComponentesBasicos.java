package ejemplos.componentesbasicos;

import javax.swing.*;
import java.awt.*;

/**
 * EJEMPLO 2 – COMPONENTES BÁSICOS DE SWING
 * ==========================================
 * Este ejemplo presenta los componentes de entrada y visualización más
 * habituales en una interfaz Swing, organizados con FlowLayout.
 *
 * Componentes que aparecen:
 * - JLabel → Texto estático (etiqueta).
 * - JTextField → Campo de texto de una línea.
 * - JPasswordField → Campo de contraseña (oculta los caracteres).
 * - JButton → Botón que el usuario puede pulsar.
 * - JCheckBox → Casilla de verificación (marcar/desmarcar).
 * - JRadioButton → Botón de opción (solo uno activo a la vez dentro de un
 * ButtonGroup).
 * - JTextArea → Área de texto multilínea (aquí como panel de resultados).
 * - JScrollPane → Envuelve un componente para añadir barras de desplazamiento.
 *
 * Layout utilizado: FlowLayout (coloca los componentes uno tras otro,
 * de izquierda a derecha, saltando de línea automáticamente).
 */
public class EjemploComponentesBasicos {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL (JFrame)                                 ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 2 – Componentes Básicos");
        ventana.setSize(620, 520);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  PANEL PRINCIPAL con BorderLayout                           ║
        // ║  Usamos BorderLayout para dividir la ventana en dos zonas:  ║
        // ║    NORTH  → formulario con los componentes básicos          ║
        // ║    CENTER → área de resultados                              ║
        // ╚═════════════════════════════════════════════════════════════╝
        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ╔════════════════════════════════════════════════════════════════╗
        // ║  PANEL DE FORMULARIO con FlowLayout                            ║
        // ║  FlowLayout coloca los componentes uno tras otro, como texto.  ║
        // ║  en un párrafo. Si no caben en una línea, bajan a la siguiente.║
        // ║  Es el layout por defecto de JPanel.                           ║
        // ╚════════════════════════════════════════════════════════════════╝
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Componentes básicos"));
        panelFormulario.setPreferredSize(new Dimension(540, 270));

        // ── JLabel + JTextField ────────────────────────────────────────────
        // JLabel: texto estático informativo.
        // JTextField(columnas): campo de texto; el número indica el ancho aproximado.
        JLabel etiquetaNombre = new JLabel("Nombre:");
        etiquetaNombre.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JTextField campoNombre = new JTextField(18);
        campoNombre.setToolTipText("Escribe tu nombre aquí");

        panelFormulario.add(etiquetaNombre);
        panelFormulario.add(campoNombre);

        // ── JLabel + JPasswordField ────────────────────────────────────────
        // JPasswordField funciona igual que JTextField, pero oculta los
        // caracteres con un punto (●). Se usa para contraseñas.
        // Para obtener el texto se usa getPassword() (devuelve char[]).
        JLabel etiquetaPass = new JLabel("Contraseña:");
        etiquetaPass.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JPasswordField campoPass = new JPasswordField(14);
        campoPass.setToolTipText("Escribe una contraseña (se ocultan los caracteres)");

        panelFormulario.add(etiquetaPass);
        panelFormulario.add(campoPass);

        // ── JCheckBox ──────────────────────────────────────────────────────
        // JCheckBox: casilla de verificación. Puede estar marcada o no.
        // Se pueden marcar varias a la vez (son independientes entre sí).
        // isSelected() devuelve true si está marcada.
        JLabel etiquetaCheck = new JLabel("Aficiones:");
        etiquetaCheck.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JCheckBox checkDeporte = new JCheckBox("Deporte");
        JCheckBox checkMusica = new JCheckBox("Música");
        JCheckBox checkLectura = new JCheckBox("Lectura");

        panelFormulario.add(etiquetaCheck);
        panelFormulario.add(checkDeporte);
        panelFormulario.add(checkMusica);
        panelFormulario.add(checkLectura);

        // ── JRadioButton + ButtonGroup ─────────────────────────────────────
        // JRadioButton: botón de opción. Solo UNO puede estar seleccionado
        // a la vez dentro del mismo ButtonGroup.
        // ButtonGroup NO es un componente visual, solo agrupa la lógica
        // de exclusión mutua.
        JLabel etiquetaTurno = new JLabel("Turno:");
        etiquetaTurno.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JRadioButton radioMañana = new JRadioButton("Mañana", true); // true → seleccionado por defecto
        JRadioButton radioTarde = new JRadioButton("Tarde");

        // Crear el grupo: solo uno de los dos puede estar activo
        ButtonGroup grupoTurno = new ButtonGroup();
        grupoTurno.add(radioMañana);
        grupoTurno.add(radioTarde);

        panelFormulario.add(etiquetaTurno);
        panelFormulario.add(radioMañana);
        panelFormulario.add(radioTarde);

        // ── JButton ────────────────────────────────────────────────────────
        // Botón para recoger los datos del formulario.
        JButton botonEnviar = new JButton("📋 Enviar datos");
        botonEnviar.setFont(new Font("SansSerif", Font.BOLD, 13));
        JButton botonLimpiar = new JButton("🔄 Limpiar");
        botonLimpiar.setFont(new Font("SansSerif", Font.PLAIN, 13));

        panelFormulario.add(botonEnviar);
        panelFormulario.add(botonLimpiar);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        // ╔═════════════════════════════════════════════════════════════════╗
        // ║  JTextArea + JScrollPane (área de resultados)                   ║
        // ║  JTextArea: componente multilínea para mostrar o editar texto.  ║
        // ║  JScrollPane: envuelve al JTextArea → barras de desplazamiento. ║
        // ╚═════════════════════════════════════════════════════════════════╝
        JTextArea areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultados.setText("Resultado del formulario:\n"
                + "──────────────────────────────────────\n"
                + "Rellena los campos y pulsa \"Enviar datos\".\n\n"
                + "Componentes de este ejemplo:\n"
                + "  • JLabel         → texto estático\n"
                + "  • JTextField     → campo de texto (1 línea)\n"
                + "  • JPasswordField → campo contraseña\n"
                + "  • JCheckBox      → casilla de verificación\n"
                + "  • JRadioButton   → botón de opción (exclusión mutua)\n"
                + "  • JButton        → botón con ActionListener\n"
                + "  • JTextArea      → texto multilínea (este panel)\n"
                + "  • JScrollPane    → barra de desplazamiento\n"
                + "──────────────────────────────────────\n");

        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Resultados"));
        panelPrincipal.add(scrollResultados, BorderLayout.CENTER);

        // ╔═══════════════════════════════════════════════════════════════════╗
        // ║ EVENTOS con EXPRESIONES LAMBDA                                    ║
        // ║                                                                   ║
        // ║ En el Ejemplo 1 usamos clases anónimas para los ActionListener:   ║
        // ║                                                                   ║
        // ║   boton.addActionListener(new ActionListener() {                  ║
        // ║       @Override                                                   ║
        // ║       public void actionPerformed(ActionEvent e) {                ║
        // ║           // código                                               ║
        // ║       }                                                           ║
        // ║   });                                                             ║
        // ║                                                                   ║
        // ║ Pero ActionListener es una INTERFAZ FUNCIONAL (tiene un solo      ║
        // ║ método abstracto). Java permite sustituir la clase anónima por    ║
        // ║ una EXPRESIÓN LAMBDA, mucho más compacta:                         ║
        // ║                                                                   ║
        // ║   boton.addActionListener(e -> { /* código */ });                 ║
        // ║                                                                   ║
        // ║ Donde "e" es el parámetro ActionEvent (puedes llamarlo como       ║
        // ║ quieras). Si el cuerpo es una sola instrucción, las llaves {}     ║
        // ║ son opcionales:                                                   ║
        // ║                                                                   ║
        // ║   boton.addActionListener(e -> hacerAlgo());                      ║
        // ╚═══════════════════════════════════════════════════════════════════╝

        // Evento del botón "Enviar datos" (lambda con varias instrucciones → llaves {})
        botonEnviar.addActionListener(e -> {
            // Recoger valores de cada componente
            String nombre = campoNombre.getText().trim();
            // getPassword() devuelve char[] por seguridad; lo convertimos a String para
            // mostrar
            String pass = new String(campoPass.getPassword());

            // Construir la lista de aficiones marcadas
            StringBuilder aficiones = new StringBuilder();
            if (checkDeporte.isSelected())
                aficiones.append("Deporte ");
            if (checkMusica.isSelected())
                aficiones.append("Música ");
            if (checkLectura.isSelected())
                aficiones.append("Lectura ");
            if (aficiones.length() == 0)
                aficiones.append("(ninguna)");

            // Obtener el turno seleccionado
            String turno = radioMañana.isSelected() ? "Mañana" : "Tarde";

            // Mostrar resultados en el área de texto
            areaResultados.append("\n▶ Datos recibidos:\n");
            areaResultados.append("   Nombre:     " + (nombre.isEmpty() ? "(vacío)" : nombre) + "\n");
            areaResultados.append("   Contraseña: " + (pass.isEmpty() ? "(vacía)" : "●".repeat(pass.length())) + "\n");
            areaResultados.append("   Aficiones:  " + aficiones.toString().trim() + "\n");
            areaResultados.append("   Turno:      " + turno + "\n");
            areaResultados.append("──────────────────────────────────────\n");
        });

        // Evento del botón "Limpiar" (lambda con varias instrucciones → llaves {})
        botonLimpiar.addActionListener(e -> {
            campoNombre.setText("");
            campoPass.setText("");
            checkDeporte.setSelected(false);
            checkMusica.setSelected(false);
            checkLectura.setSelected(false);
            radioMañana.setSelected(true);
            areaResultados.append("\n🔄 Formulario limpiado.\n");
        });

        // ── Mostrar la ventana ─────────────────────────────────────────────
        ventana.add(panelPrincipal);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
