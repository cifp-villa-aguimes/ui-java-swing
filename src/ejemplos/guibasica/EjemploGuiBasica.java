package ejemplos.guibasica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * EJEMPLO 1 – GUI BÁSICA CON SWING
 * ==================================
 * Este ejemplo muestra los elementos fundamentales para crear una interfaz
 * gráfica con Java Swing. Aprenderás:
 *
 * - JFrame : la ventana principal de la aplicación.
 * - JPanel : un contenedor invisible donde se colocan los componentes.
 * - JLabel : texto estático (etiquetas informativas).
 * - JTextField : campo de entrada de texto (una línea).
 * - JButton : botón que el usuario puede pulsar.
 * - JTextArea : área de texto multilínea (aquí se usa como panel informativo).
 * - ActionListener : cómo reaccionar cuando el usuario pulsa un botón.
 *
 * Layout utilizado: null (posicionamiento manual con setBounds).
 * En ejemplos posteriores veremos FlowLayout, BorderLayout, etc.
 */
public class EjemploGuiBasica {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  1. CREAR LA VENTANA (JFrame)                               ║
        // ║  JFrame es el contenedor de nivel superior: la "ventana"    ║
        // ║  que aparece en el escritorio del usuario.                  ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame frame = new JFrame("Ejemplo 1 – GUI Básica");
        frame.setSize(520, 420); // Ancho x Alto en píxeles
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        frame.setResizable(false); // Evitar que se redimensione (opcional)
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar solo esta ventana

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  2. CREAR UN PANEL (JPanel)                                 ║
        // ║  JPanel es un contenedor ligero donde colocamos componentes.║
        // ║  Un JFrame puede tener uno o varios paneles.                ║
        // ╚═════════════════════════════════════════════════════════════╝
        JPanel panel = new JPanel();
        panel.setLayout(null); // Layout nulo → posicionamos cada componente manualmente con setBounds(x, y,
                               // ancho, alto)
        panel.setBackground(new Color(245, 245, 250)); // Color de fondo suave
        frame.add(panel); // Añadir el panel al frame

        // ── Construir los componentes dentro del panel ─────────────────────
        colocarComponentes(panel);

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  3. MOSTRAR LA VENTANA                                      ║
        // ║  setVisible(true) hace que la ventana aparezca en pantalla. ║
        // ║  ¡Siempre debe ser la ÚLTIMA instrucción!                   ║
        // ╚═════════════════════════════════════════════════════════════╝
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Método que crea y coloca todos los componentes en el panel.
     * Usamos setBounds(x, y, ancho, alto) para posicionar cada uno manualmente.
     *
     * x → distancia en píxeles desde el borde izquierdo del panel
     * y → distancia en píxeles desde el borde superior del panel
     */
    private static void colocarComponentes(JPanel panel) {

        // ── TÍTULO (JLabel) ────────────────────────────────────────────────
        // JLabel muestra texto estático que el usuario no puede editar.
        // Se puede personalizar con setFont() y setForeground() (color de texto).
        JLabel titulo = new JLabel("Mi primera GUI con Swing");
        titulo.setBounds(120, 10, 300, 30);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setForeground(new Color(51, 51, 51));
        panel.add(titulo);

        // ── SEPARADOR VISUAL ───────────────────────────────────────────────
        // JSeparator dibuja una línea horizontal para organizar visualmente.
        JSeparator separador = new JSeparator();
        separador.setBounds(20, 45, 470, 2);
        panel.add(separador);

        // ── ETIQUETA INFORMATIVA (JLabel) ──────────────────────────────────
        JLabel etiquetaNombre = new JLabel("Escribe tu nombre:");
        etiquetaNombre.setBounds(20, 60, 150, 25);
        etiquetaNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(etiquetaNombre);

        // ── CAMPO DE TEXTO (JTextField) ────────────────────────────────────
        // JTextField permite al usuario escribir texto en una sola línea.
        // El usuario puede escribir, borrar y seleccionar texto libremente.
        JTextField campoNombre = new JTextField();
        campoNombre.setBounds(170, 60, 200, 28);
        campoNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(campoNombre);

        // ── ETIQUETA DE RESULTADO (JLabel) ─────────────────────────────────
        // Esta etiqueta se actualizará dinámicamente cuando el usuario pulse el botón.
        JLabel etiquetaSaludo = new JLabel("Aquí aparecerá el saludo...");
        etiquetaSaludo.setBounds(20, 105, 470, 25);
        etiquetaSaludo.setFont(new Font("SansSerif", Font.ITALIC, 14));
        etiquetaSaludo.setForeground(new Color(100, 100, 100));
        panel.add(etiquetaSaludo);

        // ── BOTÓN "SALUDAR" (JButton) ──────────────────────────────────────
        // JButton es un botón que el usuario puede pulsar.
        // Para que haga algo, necesitamos asociarle un ActionListener.
        JButton botonSaludar = new JButton("Saludar");
        botonSaludar.setBounds(380, 60, 100, 28);
        botonSaludar.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(botonSaludar);

        // ── ÁREA INFORMATIVA (JTextArea + JScrollPane) ─────────────────────
        // JTextArea es un componente de texto multilínea.
        // Lo envolvemos en JScrollPane para que tenga barras de desplazamiento
        // si el contenido crece más que el espacio visible.
        JLabel etiquetaInfo = new JLabel("Registro de acciones:");
        etiquetaInfo.setBounds(20, 145, 200, 20);
        etiquetaInfo.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(etiquetaInfo);

        JTextArea areaInfo = new JTextArea();
        areaInfo.setEditable(false); // Solo lectura: el usuario no puede escribir aquí
        areaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaInfo.setBackground(new Color(255, 255, 255));
        areaInfo.setText("Bienvenido/a al Ejemplo 1 – GUI Básica.\n"
                + "──────────────────────────────────────\n"
                + "• Escribe tu nombre en el campo de texto.\n"
                + "• Pulsa \"Saludar\" para ver el resultado.\n"
                + "• Pulsa \"Limpiar\" para resetear todo.\n"
                + "• Pulsa \"Cerrar\" para cerrar la ventana.\n"
                + "──────────────────────────────────────\n");

        // JScrollPane: envuelve al JTextArea para añadir scroll automático
        JScrollPane scrollArea = new JScrollPane(areaInfo);
        scrollArea.setBounds(20, 168, 470, 150);
        panel.add(scrollArea);

        // ── BOTONES INFERIORES ─────────────────────────────────────────────
        JButton botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setBounds(140, 335, 100, 30);
        botonLimpiar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(botonLimpiar);

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.setBounds(260, 335, 100, 30);
        botonCerrar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(botonCerrar);

        // ╔════════════════════════════════════════════════════════════════╗
        // ║  4. EVENTOS (ActionListener)                                   ║
        // ║  Un ActionListener "escucha" cuando el usuario pulsa un botón  ║
        // ║  y ejecuta el código del método actionPerformed().             ║
        // ║                                                                ║
        // ║  Patrón:  boton.addActionListener(new ActionListener() {       ║
        // ║               public void actionPerformed(ActionEvent e) {     ║
        // ║                   // código a ejecutar al pulsar               ║
        // ║               }                                                ║
        // ║           });                                                  ║
        // ╚════════════════════════════════════════════════════════════════╝

        // Evento del botón "Saludar"
        botonSaludar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText().trim(); // .trim() elimina espacios al inicio/final
                if (nombre.isEmpty()) {
                    etiquetaSaludo.setText("⚠ Escribe un nombre primero.");
                    etiquetaSaludo.setForeground(new Color(200, 50, 50)); // Rojo
                    areaInfo.append("⚠ Se pulsó Saludar sin escribir un nombre.\n");
                } else {
                    etiquetaSaludo.setText("¡Hola, " + nombre + "! Bienvenido/a a Swing.");
                    etiquetaSaludo.setForeground(new Color(0, 120, 60)); // Verde
                    areaInfo.append("✔ Saludo generado para: " + nombre + "\n");
                }
            }
        });

        // Evento del botón "Limpiar"
        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoNombre.setText(""); // Vaciar el campo de texto
                etiquetaSaludo.setText("Aquí aparecerá el saludo...");
                etiquetaSaludo.setForeground(new Color(100, 100, 100));
                areaInfo.append("🔄 Se ha limpiado el formulario.\n");
            }
        });

        // Evento del botón "Cerrar"
        botonCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // SwingUtilities.getWindowAncestor() obtiene la ventana que contiene
                // al componente. dispose() cierra solo esta ventana (no toda la app).
                SwingUtilities.getWindowAncestor(panel).dispose();
            }
        });
    }
}
