package ejemplos.lookandfeel;

import javax.swing.*;
import java.awt.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 14 – UIManager personalizado                       ║
 * ║                                                             ║
 * ║  UIManager.put(clave, valor) permite personalizar           ║
 * ║  la apariencia de TODOS los componentes de un tipo.         ║
 * ║                                                             ║
 * ║  Funciona como un diccionario global (clave → valor):       ║
 * ║    • "Button.background"  → Color de fondo de botones       ║
 * ║    • "Button.font"        → Fuente de botones               ║
 * ║    • "Label.foreground"   → Color de texto de etiquetas     ║
 * ║    • "Panel.background"   → Color de fondo de paneles       ║
 * ║    • etc.                                                   ║
 * ║                                                             ║
 * ║  IMPORTANTE: UIManager.put() debe llamarse ANTES de crear   ║
 * ║  los componentes. Si se llama después, solo afectan a los   ║
 * ║  componentes creados posteriormente (salvo que se llame     ║
 * ║  a SwingUtilities.updateComponentTreeUI()).                 ║
 * ║                                                             ║
 * ║  NOVEDAD: este ejemplo incluye un selector Modo Claro /     ║
 * ║  Modo Oscuro para demostrar cómo cambiar el tema en         ║
 * ║  tiempo de ejecución con updateComponentTreeUI().           ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploUIManagerPersonalizado {

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  CAMPOS DE INSTANCIA                                        ║
    // ║                                                             ║
    // ║  Se guardan como campos para poder acceder desde los        ║
    // ║  listeners (lambdas) que cambian el tema en ejecución.      ║
    // ╚═════════════════════════════════════════════════════════════╝
    private JFrame frame;
    private JTextArea areaInfo;
    private boolean modoOscuro = false;   // Arranca en modo claro

    public static void launch() {
        SwingUtilities.invokeLater(() -> new EjemploUIManagerPersonalizado().crearVentana());
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  APLICAR TEMA: Modo Claro                                   ║
    // ║                                                             ║
    // ║  Colores suaves sobre fondo blanco / gris claro.            ║
    // ║  Botones con azul oscuro sobre blanco.                      ║
    // ╚═════════════════════════════════════════════════════════════╝
    private void aplicarTemaClaro() {
        // — Botones —
        UIManager.put("Button.background", new Color(52, 73, 94));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 13));
        UIManager.put("Button.margin", new Insets(8, 16, 8, 16));

        // — Etiquetas —
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("Label.foreground", new Color(44, 62, 80));

        // — Campos de texto —
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("TextField.background", new Color(236, 240, 241));
        UIManager.put("TextField.foreground", new Color(44, 62, 80));
        UIManager.put("TextField.caretForeground", Color.BLACK);

        // — Áreas de texto —
        UIManager.put("TextArea.background", new Color(250, 252, 255));
        UIManager.put("TextArea.foreground", new Color(44, 62, 80));
        UIManager.put("TextArea.font", new Font("Monospaced", Font.PLAIN, 11));

        // — Paneles —
        UIManager.put("Panel.background", new Color(245, 248, 250));

        // — CheckBox —
        UIManager.put("CheckBox.background", new Color(245, 248, 250));
        UIManager.put("CheckBox.foreground", new Color(44, 62, 80));

        // — ToolTips —
        UIManager.put("ToolTip.background", new Color(255, 255, 225));
        UIManager.put("ToolTip.foreground", new Color(44, 62, 80));
        UIManager.put("ToolTip.font", new Font("SansSerif", Font.ITALIC, 12));

        // — TitledBorder —
        UIManager.put("TitledBorder.titleColor", new Color(52, 73, 94));
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  APLICAR TEMA: Modo Oscuro                                  ║
    // ║                                                             ║
    // ║  Fondo oscuro (gris carbón) con texto claro.                ║
    // ║  Botones con acento azul brillante.                         ║
    // ╚═════════════════════════════════════════════════════════════╝
    private void aplicarTemaOscuro() {
        Color fondoOscuro     = new Color(43, 43, 43);
        Color fondoPanel      = new Color(50, 50, 50);
        Color fondoCampo      = new Color(60, 63, 65);
        Color textoClaro      = new Color(220, 220, 220);
        Color acento          = new Color(75, 140, 200);

        // — Botones —
        UIManager.put("Button.background", acento);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 13));
        UIManager.put("Button.margin", new Insets(8, 16, 8, 16));

        // — Etiquetas —
        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("Label.foreground", textoClaro);

        // — Campos de texto —
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("TextField.background", fondoCampo);
        UIManager.put("TextField.foreground", textoClaro);
        UIManager.put("TextField.caretForeground", Color.WHITE);

        // — Áreas de texto —
        UIManager.put("TextArea.background", fondoOscuro);
        UIManager.put("TextArea.foreground", textoClaro);
        UIManager.put("TextArea.font", new Font("Monospaced", Font.PLAIN, 11));

        // — Paneles —
        UIManager.put("Panel.background", fondoPanel);

        // — CheckBox —
        UIManager.put("CheckBox.background", fondoPanel);
        UIManager.put("CheckBox.foreground", textoClaro);

        // — ToolTips —
        UIManager.put("ToolTip.background", new Color(70, 70, 70));
        UIManager.put("ToolTip.foreground", textoClaro);
        UIManager.put("ToolTip.font", new Font("SansSerif", Font.ITALIC, 12));

        // — TitledBorder —
        UIManager.put("TitledBorder.titleColor", acento);
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  TEXTO DE REFERENCIA                                        ║
    // ║                                                             ║
    // ║  Genera el String que muestra en el JTextArea las claves    ║
    // ║  UIManager aplicadas para el tema actual.                   ║
    // ╚═════════════════════════════════════════════════════════════╝
    private String textoReferencia() {
        if (modoOscuro) {
            return "Modo actual: OSCURO 🌙\n"
                + "─────────────────────────────────────────\n"
                + "Button.background    → rgb(75, 140, 200)\n"
                + "Button.foreground    → Color.WHITE\n"
                + "Button.font          → SansSerif Bold 13\n"
                + "Label.foreground     → rgb(220, 220, 220)\n"
                + "TextField.background → rgb(60, 63, 65)\n"
                + "TextField.foreground → rgb(220, 220, 220)\n"
                + "TextArea.background  → rgb(43, 43, 43)\n"
                + "TextArea.foreground  → rgb(220, 220, 220)\n"
                + "Panel.background     → rgb(50, 50, 50)\n"
                + "CheckBox.background  → rgb(50, 50, 50)\n"
                + "ToolTip.background   → rgb(70, 70, 70)\n"
                + "TitledBorder.color   → rgb(75, 140, 200)\n"
                + "─────────────────────────────────────────\n"
                + "\nNota: al cambiar de tema se llama a:\n"
                + "  SwingUtilities.updateComponentTreeUI(frame)\n"
                + "para refrescar TODOS los componentes.\n";
        } else {
            return "Modo actual: CLARO ☀️\n"
                + "─────────────────────────────────────────\n"
                + "Button.background    → rgb(52, 73, 94)\n"
                + "Button.foreground    → Color.WHITE\n"
                + "Button.font          → SansSerif Bold 13\n"
                + "Label.foreground     → rgb(44, 62, 80)\n"
                + "TextField.background → rgb(236, 240, 241)\n"
                + "TextField.foreground → rgb(44, 62, 80)\n"
                + "TextArea.background  → rgb(250, 252, 255)\n"
                + "TextArea.foreground  → rgb(44, 62, 80)\n"
                + "Panel.background     → rgb(245, 248, 250)\n"
                + "CheckBox.background  → rgb(245, 248, 250)\n"
                + "ToolTip.background   → rgb(255, 255, 225)\n"
                + "TitledBorder.color   → rgb(52, 73, 94)\n"
                + "─────────────────────────────────────────\n"
                + "\nLos valores se consultan con:\n"
                + "  UIManager.get(\"Button.background\")\n"
                + "Las propiedades se aplican ANTES de crear\n"
                + "los componentes (new JButton…).\n";
        }
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  CAMBIAR TEMA EN EJECUCIÓN                                  ║
    // ║                                                             ║
    // ║  1. Aplica las nuevas propiedades con UIManager.put()       ║
    // ║  2. Llama a updateComponentTreeUI() para refrescar          ║
    // ║  3. Actualiza el texto de referencia                        ║
    // ║                                                             ║
    // ║  updateComponentTreeUI() recorre recursivamente TODOS       ║
    // ║  los componentes hijos del frame y les dice que relean      ║
    // ║  sus propiedades del UIManager.                             ║
    // ╚═════════════════════════════════════════════════════════════╝
    private void cambiarTema(boolean oscuro) {
        modoOscuro = oscuro;
        if (oscuro) {
            aplicarTemaOscuro();
        } else {
            aplicarTemaClaro();
        }
        // Refresca todos los componentes del árbol
        SwingUtilities.updateComponentTreeUI(frame);
        // Actualiza el texto de referencia
        areaInfo.setText(textoReferencia());
        areaInfo.setCaretPosition(0);
        frame.setTitle("Ejemplo 14 – UIManager Personalizado ("
                + (oscuro ? "Oscuro 🌙" : "Claro ☀️") + ")");
    }

    private void crearVentana() {
        // ╔═════════════════════════════════════════════════════════════╗
        // ║  1. APLICAR EL L&F DEL SISTEMA OPERATIVO COMO BASE          ║
        // ║                                                             ║
        // ║  UIManager.getSystemLookAndFeelClassName() devuelve:        ║
        // ║    • macOS  → com.apple.laf.AquaLookAndFeel                 ║
        // ║    • Windows → com.sun.java.swing.plaf.windows.Windows…     ║
        // ║    • Linux  → javax.swing.plaf.metal.MetalLookAndFeel       ║
        // ║                                                             ║
        // ║  Partimos de este L&F y lo personalizamos con put().        ║
        // ╚═════════════════════════════════════════════════════════════╝
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo aplicar L&F del sistema.");
        }

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  2. PERSONALIZAR CON UIManager.put() – MODO CLARO inicial   ║
        // ║                                                             ║
        // ║  Cada propiedad se identifica por una clave String.         ║
        // ║  El valor depende del tipo de propiedad:                    ║
        // ║    • Color  → new Color(r, g, b)                            ║
        // ║    • Font   → new Font("familia", estilo, tamaño)           ║
        // ║    • Insets → new Insets(arriba, izq, abajo, der)           ║
        // ║                                                             ║
        // ║  Para ver TODAS las claves disponibles:                     ║
        // ║    UIManager.getDefaults().keys()                           ║
        // ╚═════════════════════════════════════════════════════════════╝
        aplicarTemaClaro();

        // ──────────────────────────────────────────────────────────────
        //  VENTANA – tamaño amplio para que se vea todo sin recortar
        // ──────────────────────────────────────────────────────────────
        frame = new JFrame("Ejemplo 14 – UIManager Personalizado (Claro ☀️)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(520, 600));

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // ──────────────────────────────────────────────────────────────
        //  Panel superior: selector de tema (Claro / Oscuro)
        // ──────────────────────────────────────────────────────────────
        JPanel panelTema = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        panelTema.setBorder(BorderFactory.createTitledBorder("Seleccionar tema"));

        JButton btnClaro = new JButton("☀️  Modo Claro");
        JButton btnOscuro = new JButton("🌙  Modo Oscuro");

        btnClaro.addActionListener(e -> cambiarTema(false));
        btnOscuro.addActionListener(e -> cambiarTema(true));

        panelTema.add(btnClaro);
        panelTema.add(btnOscuro);

        // ──────────────────────────────────────────────────────────────
        //  Panel de muestra: componentes que reflejan la personalización
        // ──────────────────────────────────────────────────────────────
        JPanel panelMuestra = new JPanel(new GridLayout(0, 1, 6, 6));
        panelMuestra.setBorder(BorderFactory.createTitledBorder("Componentes personalizados"));

        panelMuestra.add(new JLabel("JLabel → Texto con fuente y color personalizado"));
        panelMuestra.add(new JTextField("JTextField → Campo con fondo personalizado"));
        panelMuestra.add(new JButton("JButton → Botón con estilo del tema actual"));

        JButton botonConTooltip = new JButton("Pasa el ratón para ver el ToolTip");
        botonConTooltip.setToolTipText("¡Este ToolTip tiene estilo personalizado!");
        panelMuestra.add(botonConTooltip);

        JCheckBox check = new JCheckBox("JCheckBox → También hereda el fondo del panel", true);
        panelMuestra.add(check);

        // ──────────────────────────────────────────────────────────────
        //  Agrupar tema + muestra en un panel NORTH combinado
        //  para que el CENTER (referencia) tenga todo el espacio
        // ──────────────────────────────────────────────────────────────
        JPanel panelSuperior = new JPanel(new BorderLayout(0, 8));
        panelSuperior.add(panelTema, BorderLayout.NORTH);
        panelSuperior.add(panelMuestra, BorderLayout.CENTER);

        // ──────────────────────────────────────────────────────────────
        //  Panel informativo: muestra las claves aplicadas
        // ──────────────────────────────────────────────────────────────
        areaInfo = new JTextArea();
        areaInfo.setEditable(false);
        areaInfo.setText(textoReferencia());
        areaInfo.setCaretPosition(0);

        JScrollPane scrollInfo = new JScrollPane(areaInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder("Referencia"));
        scrollInfo.setPreferredSize(new Dimension(0, 260));

        // ──────────────────────────────────────────────────────────────
        //  Botón para restaurar los valores por defecto (Metal)
        // ──────────────────────────────────────────────────────────────
        JButton botonRestaurar = new JButton("Restaurar L&F por defecto (Metal)");
        botonRestaurar.addActionListener(e -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                SwingUtilities.updateComponentTreeUI(frame);
                frame.setTitle("Ejemplo 14 – UIManager (restaurado a Metal)");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(botonRestaurar);

        // ──────────────────────────────────────────────────────────────
        //  Montaje final
        // ──────────────────────────────────────────────────────────────
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollInfo, BorderLayout.CENTER);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        frame.add(panelPrincipal);
        frame.pack();                        // Calcula el tamaño ideal
        frame.setLocationRelativeTo(null);   // Centra DESPUÉS de pack()
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
