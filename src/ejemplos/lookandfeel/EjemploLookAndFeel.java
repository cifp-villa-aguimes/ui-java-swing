package ejemplos.lookandfeel;

import javax.swing.*;
import java.awt.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 13 – Look & Feel (cambiar la apariencia)           ║
 * ║                                                             ║
 * ║  Cada L&F cambia TODA la apariencia de los componentes      ║
 * ║  Swing: botones, bordes, colores, fuentes, scroll bars…     ║
 * ║                                                             ║
 * ║  L&F disponibles en el JDK:                                 ║
 * ║    • Metal      → el clásico "Java" (por defecto)           ║
 * ║    • Nimbus     → moderno, con degradados (desde Java 6u10) ║
 * ║    • Motif/CDE  → aspecto retro Unix/Solaris                ║
 * ║    • Windows    → solo en Windows                           ║
 * ║    • Mac (Aqua) → solo en macOS                             ║
 * ║                                                             ║
 * ║  Pasos para cambiar el L&F en caliente:                     ║
 * ║    1. UIManager.setLookAndFeel(className)                   ║
 * ║    2. SwingUtilities.updateComponentTreeUI(rootComponent)   ║
 * ║       → recorre todo el árbol de componentes y repinta      ║
 * ║    3. (Opcional) frame.pack() para ajustar tamaños          ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploLookAndFeel {

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  NOMBRES DE CLASE DE LOS L&F ESTÁNDAR                       ║
    // ║                                                             ║
    // ║  Son cadenas con el nombre completo de la clase.            ║
    // ║  Si el L&F no está disponible en tu SO, el botón            ║
    // ║  correspondiente se desactiva automáticamente.              ║
    // ╚═════════════════════════════════════════════════════════════╝
    private static final String[][] LOOK_AND_FEELS = {
            { "Metal (por defecto)", "javax.swing.plaf.metal.MetalLookAndFeel" },
            { "Nimbus (moderno)",    "javax.swing.plaf.nimbus.NimbusLookAndFeel" },
            { "Motif/CDE (retro)",   "com.sun.java.swing.plaf.motif.MotifLookAndFeel" },
            { "Windows",             "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" },
            { "Mac (Aqua)",          "com.apple.laf.AquaLookAndFeel" },
    };

    public static void launch() {
        SwingUtilities.invokeLater(EjemploLookAndFeel::crearVentana);
    }

    private static void crearVentana() {
        JFrame frame = new JFrame("Ejemplo 13 – Look & Feel");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(540, 440);
        frame.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(8, 8));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ──────────────────────────────────────────────────────────────
        //  Panel de botones para cambiar el L&F
        // ──────────────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 4, 4));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Selecciona un Look & Feel"));

        JLabel barraEstado = new JLabel("  L&F actual: " + UIManager.getLookAndFeel().getName());
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        for (String[] laf : LOOK_AND_FEELS) {
            String nombre = laf[0];
            String clase  = laf[1];

            JButton boton = new JButton(nombre);

            // Comprobar si el L&F está disponible en este sistema
            boolean disponible = isLookAndFeelAvailable(clase);
            boton.setEnabled(disponible);
            if (!disponible) {
                boton.setToolTipText("No disponible en " + System.getProperty("os.name"));
            }

            // ╔═════════════════════════════════════════════════════════════╗
            // ║  CAMBIAR L&F EN CALIENTE                                    ║
            // ║                                                             ║
            // ║  UIManager.setLookAndFeel(clase)                            ║
            // ║    → carga la nueva fábrica de componentes                  ║
            // ║                                                             ║
            // ║  SwingUtilities.updateComponentTreeUI(frame)                ║
            // ║    → recorre recursivamente TODOS los componentes del       ║
            // ║      árbol Swing y llama a updateUI() en cada uno,          ║
            // ║      para que se repinten con el nuevo L&F.                 ║
            // ╚═════════════════════════════════════════════════════════════╝
            boton.addActionListener(e -> {
                try {
                    UIManager.setLookAndFeel(clase);
                    SwingUtilities.updateComponentTreeUI(frame);
                    barraEstado.setText("  L&F actual: " + nombre);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame,
                            "No se pudo aplicar el L&F: " + nombre + "\n" + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            panelBotones.add(boton);
        }

        // ──────────────────────────────────────────────────────────────
        //  Panel de muestra: componentes para ver el efecto del L&F
        // ──────────────────────────────────────────────────────────────
        JPanel panelMuestra = new JPanel(new GridLayout(0, 1, 6, 6));
        panelMuestra.setBorder(BorderFactory.createTitledBorder("Vista previa de componentes"));

        panelMuestra.add(new JLabel("JLabel → Etiqueta de ejemplo"));
        panelMuestra.add(new JTextField("JTextField → Campo de texto"));
        panelMuestra.add(new JButton("JButton → Botón de ejemplo"));

        JCheckBox check = new JCheckBox("JCheckBox → Casilla de verificación", true);
        panelMuestra.add(check);

        JRadioButton radio1 = new JRadioButton("JRadioButton → Opción A", true);
        JRadioButton radio2 = new JRadioButton("JRadioButton → Opción B");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radio1);
        grupo.add(radio2);
        panelMuestra.add(radio1);
        panelMuestra.add(radio2);

        JSlider slider = new JSlider(0, 100, 60);
        slider.setMajorTickSpacing(25);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        panelMuestra.add(slider);

        JProgressBar barra = new JProgressBar(0, 100);
        barra.setValue(60);
        barra.setStringPainted(true);
        panelMuestra.add(barra);

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  TAMBIÉN: UIManager.getInstalledLookAndFeels()              ║
        // ║                                                             ║
        // ║  Si no quieres escribir los nombres de clase a mano,        ║
        // ║  puedes iterar los L&F instalados:                          ║
        // ║                                                             ║
        // ║    for (UIManager.LookAndFeelInfo info                      ║
        // ║         : UIManager.getInstalledLookAndFeels()) {           ║
        // ║        System.out.println(info.getName()                    ║
        // ║                + " → " + info.getClassName());              ║
        // ║    }                                                        ║
        // ╚═════════════════════════════════════════════════════════════╝

        // ──────────────────────────────────────────────────────────────
        //  Montaje final
        // ──────────────────────────────────────────────────────────────
        panelPrincipal.add(panelBotones, BorderLayout.WEST);
        panelPrincipal.add(new JScrollPane(panelMuestra), BorderLayout.CENTER);
        panelPrincipal.add(barraEstado, BorderLayout.SOUTH);

        frame.add(panelPrincipal);
        frame.setVisible(true);
    }

    /**
     * Comprueba si un L&F está disponible intentando cargarlo por reflexión.
     */
    private static boolean isLookAndFeelAvailable(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
