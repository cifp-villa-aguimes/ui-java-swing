package ejemplos.eventos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 4 – EVENTOS EN SWING                               ║
 * ║                                                             ║
 * ║  Este ejemplo enseña el modelo de eventos de Java Swing.    ║
 * ║  Veremos diferentes tipos de listeners y dos formas         ║
 * ║  de implementarlos: interfaz completa vs clase adaptadora.  ║
 * ║                                                             ║
 * ║  Listeners usados:                                          ║
 * ║    • ActionListener      → clic en botón / Enter en campo   ║
 * ║    • KeyAdapter          → pulsaciones de teclas            ║
 * ║    • MouseAdapter        → clics, entrada/salida del ratón  ║
 * ║    • MouseMotionAdapter  → movimiento y arrastre del ratón  ║
 * ║    • FocusAdapter        → ganancia/pérdida de foco         ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploEventosSwing {

    /* Área de log compartida por todos los listeners */
    private static JTextArea logEventos;
    private static int contadorEventos = 0;

    public static void launch() {

        // Reiniciar estado (por si se abre varias veces desde el lanzador)
        contadorEventos = 0;

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 4 – Eventos en Swing");
        ventana.setSize(780, 560);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  EL MODELO DE EVENTOS EN JAVA                               ║
        // ║                                                             ║
        // ║  Swing usa el patrón Observador:                            ║
        // ║    1. FUENTE   → componente que genera el evento            ║
        // ║    2. EVENTO   → objeto con info (ActionEvent, KeyEvent…)   ║
        // ║    3. LISTENER → objeto que "escucha" y reacciona           ║
        // ║                                                             ║
        // ║  Registro:  fuente.addXxxListener(miListener);              ║
        // ║                                                             ║
        // ║  3 formas de implementar un listener:                       ║
        // ║    a) Interfaz completa  – KeyListener: 3 métodos           ║
        // ║       ⚠ Debes implementar TODOS aunque no los uses          ║
        // ║    b) Clase adaptadora   – KeyAdapter: solo los necesarios  ║
        // ║       ✔ Sobrescribes únicamente los que te interesan        ║
        // ║    c) Lambda             – si la interfaz es funcional      ║
        // ║       ✔ ActionListener tiene 1 solo método → e -> { }       ║
        // ╚═════════════════════════════════════════════════════════════╝

        // ──────────────────────────────────────────────────────────────
        //  ZONA SUPERIOR: campo de texto + botones
        // ──────────────────────────────────────────────────────────────
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 0));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Entrada de texto"));

        JTextField campoTexto = new JTextField();
        campoTexto.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campoTexto.setToolTipText("Escribe algo y pulsa Enter o el botón Agregar");

        JButton botonAgregar = new JButton("Agregar al log");
        JButton botonLimpiar = new JButton("Limpiar log");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        panelBotones.add(botonAgregar);
        panelBotones.add(botonLimpiar);

        panelSuperior.add(new JLabel("  Texto: "), BorderLayout.WEST);
        panelSuperior.add(campoTexto, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.EAST);

        // ──────────────────────────────────────────────────────────────
        //  ZONA CENTRAL: panel de ratón (izq.) + log de eventos (der.)
        // ──────────────────────────────────────────────────────────────

        // — Panel interactivo para eventos de ratón —
        JLabel labelCoords = new JLabel("Mueve el ratón aquí", SwingConstants.CENTER);
        labelCoords.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel panelRaton = new JPanel(new BorderLayout());
        panelRaton.setBackground(new Color(230, 240, 255));
        panelRaton.setBorder(BorderFactory.createTitledBorder("Zona de ratón"));
        panelRaton.setPreferredSize(new Dimension(280, 0));
        panelRaton.add(labelCoords, BorderLayout.CENTER);

        // — Log de eventos (JTextArea de solo lectura) —
        logEventos = new JTextArea();
        logEventos.setEditable(false);
        logEventos.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollLog = new JScrollPane(logEventos);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log de eventos"));

        JSplitPane splitCentral = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, panelRaton, scrollLog);
        splitCentral.setDividerLocation(280);

        // — Barra de estado (inferior) —
        JLabel barraEstado = new JLabel("  Estado: listo");
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  1. ActionListener (repaso de los Ejemplos 1 y 2)           ║
        // ║                                                             ║
        // ║  Interfaz funcional (1 solo método) → se puede usar LAMBDA. ║
        // ║  Fuentes que lo disparan:                                   ║
        // ║    • JButton     → al hacer clic                            ║
        // ║    • JTextField  → al pulsar Enter dentro del campo         ║
        // ╚═════════════════════════════════════════════════════════════╝

        // Botón "Agregar" → lambda (ya aprendido en el Ejemplo 2)
        botonAgregar.addActionListener(e -> {
            String texto = campoTexto.getText().trim();
            if (texto.isEmpty()) {
                log("⚠ ActionEvent → botón pulsado pero campo vacío");
                JOptionPane.showMessageDialog(ventana,
                        "Escribe algo antes de agregar.",
                        "Campo vacío", JOptionPane.WARNING_MESSAGE);
            } else {
                log("✔ ActionEvent → texto agregado: \"" + texto + "\"");
            }
            campoTexto.setText("");
            campoTexto.requestFocus(); // Devolvemos el foco al campo
        });

        // Botón "Limpiar" → lambda
        botonLimpiar.addActionListener(e -> {
            logEventos.setText("");
            contadorEventos = 0;
            log("🗑 Log limpiado");
        });

        // JTextField también dispara ActionEvent al pulsar Enter
        // → Reutilizamos la acción del botón con doClick()
        campoTexto.addActionListener(e -> botonAgregar.doClick());

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  2. KeyListener vs KeyAdapter                               ║
        // ║                                                             ║
        // ║  KeyListener es una INTERFAZ con 3 métodos:                 ║
        // ║    • keyTyped(KeyEvent)    → carácter producido             ║
        // ║    • keyPressed(KeyEvent)  → tecla pulsada (sin soltar)     ║
        // ║    • keyReleased(KeyEvent) → tecla soltada                  ║
        // ║                                                             ║
        // ║  ⚠ Si implementas KeyListener DEBES definir los 3 métodos   ║
        // ║    aunque no los uses → código innecesario.                 ║
        // ║                                                             ║
        // ║  ✔ SOLUCIÓN → usar KeyAdapter (clase abstracta).            ║
        // ║    Tiene los 3 métodos vacíos por defecto;                  ║
        // ║    solo sobrescribes (@Override) los que necesites.         ║
        // ║                                                             ║
        // ║  ⚠ KeyListener NO es interfaz funcional (tiene 3 métodos)   ║
        // ║    → NO se puede usar lambda.                               ║
        // ╚═════════════════════════════════════════════════════════════╝

        // ── OPCIÓN A: KeyListener (interfaz completa) ─────────────────
        // Si usáramos la INTERFAZ directamente, debemos implementar
        // los 3 métodos obligatoriamente, aunque 2 de ellos queden vacíos:
        //
        //  campoTexto.addKeyListener(new KeyListener() {
        //      @Override
        //      public void keyTyped(KeyEvent e) {
        //          // No nos interesa, pero OBLIGATORIO declararlo.
        //      }
        //
        //      @Override
        //      public void keyPressed(KeyEvent e) {
        //          if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        //              campoTexto.setText("");
        //              log("⌨ KeyEvent → ESC: campo limpiado");
        //          }
        //          String nombreTecla = KeyEvent.getKeyText(e.getKeyCode());
        //          barraEstado.setText("  Última tecla: " + nombreTecla
        //                  + " (código: " + e.getKeyCode() + ")");
        //      }
        //
        //      @Override
        //      public void keyReleased(KeyEvent e) {
        //          // No nos interesa, pero OBLIGATORIO declararlo.
        //      }
        //  });

        // ── OPCIÓN B: KeyAdapter (clase adaptadora) ← usamos esta ────
        // KeyAdapter implementa KeyListener con los 3 métodos vacíos.
        // Solo sobrescribimos (@Override) el que necesitamos → más limpio.
        campoTexto.addKeyListener(new KeyAdapter() {
            // Solo necesitamos keyPressed → no implementamos los otros dos
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Escape → limpiar campo
                    campoTexto.setText("");
                    log("⌨ KeyEvent → ESC: campo limpiado");
                }
                // Mostramos información de la tecla en la barra de estado
                String nombreTecla = KeyEvent.getKeyText(e.getKeyCode());
                barraEstado.setText("  Última tecla: " + nombreTecla
                        + " (código: " + e.getKeyCode() + ")");
            }
        });

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  3. MouseListener / MouseAdapter                            ║
        // ║                                                             ║
        // ║  MouseListener es una INTERFAZ con 5 métodos:               ║
        // ║    • mouseClicked(MouseEvent)  → clic completo              ║
        // ║    • mousePressed(MouseEvent)  → botón pulsado              ║
        // ║    • mouseReleased(MouseEvent) → botón soltado              ║
        // ║    • mouseEntered(MouseEvent)  → cursor entra               ║
        // ║    • mouseExited(MouseEvent)   → cursor sale                ║
        // ║                                                             ║
        // ║  MouseAdapter = clase adaptadora (igual que KeyAdapter).    ║
        // ║  Solo sobrescribimos mouseClicked, mouseEntered y           ║
        // ║  mouseExited. Los otros quedan vacíos por herencia.         ║
        // ╚═════════════════════════════════════════════════════════════╝

        panelRaton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Identificamos qué botón del ratón se pulsó
                String boton;
                if (e.getButton() == MouseEvent.BUTTON1) {
                    boton = "izquierdo";
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    boton = "derecho";
                } else {
                    boton = "central";
                }

                if (e.getClickCount() == 2) {
                    // Doble clic → cambiar a un color aleatorio
                    panelRaton.setBackground(new Color(
                            (int) (Math.random() * 200 + 55),
                            (int) (Math.random() * 200 + 55),
                            (int) (Math.random() * 200 + 55)));
                    log("🖱 MouseEvent → DOBLE clic " + boton
                            + " (" + e.getX() + "," + e.getY() + ") → color aleatorio");
                } else {
                    log("🖱 MouseEvent → clic " + boton
                            + " en (" + e.getX() + ", " + e.getY() + ")");
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Al entrar → resaltar panel y cambiar cursor
                panelRaton.setBackground(new Color(200, 225, 255));
                panelRaton.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                log("🖱 MouseEvent → ratón ENTRÓ en la zona");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Al salir → restaurar color y cursor
                panelRaton.setBackground(new Color(230, 240, 255));
                panelRaton.setCursor(Cursor.getDefaultCursor());
                labelCoords.setText("Mueve el ratón aquí");
                log("🖱 MouseEvent → ratón SALIÓ de la zona");
            }
        });

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  4. MouseMotionListener / MouseMotionAdapter                ║
        // ║                                                             ║
        // ║  Complementa a MouseListener con eventos de MOVIMIENTO:     ║
        // ║    • mouseMoved(MouseEvent)   → ratón se mueve sin pulsar   ║
        // ║    • mouseDragged(MouseEvent) → ratón se arrastra pulsado   ║
        // ║                                                             ║
        // ║  Se registra con addMouseMotionListener() (no confundir     ║
        // ║  con addMouseListener, que es para clics).                  ║
        // ║                                                             ║
        // ║  NOTA: mouseMoved se dispara muchísimas veces por segundo.  ║
        // ║  Evita hacer operaciones costosas dentro de este método.    ║
        // ╚═════════════════════════════════════════════════════════════╝

        panelRaton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Actualizar coordenadas en pantalla (NO en el log → demasiados eventos)
                labelCoords.setText("(" + e.getX() + ", " + e.getY() + ")");
                barraEstado.setText("  Ratón en (" + e.getX() + ", " + e.getY()
                        + ") → doble clic para color aleatorio");
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                labelCoords.setText("Arrastrando → (" + e.getX() + ", " + e.getY() + ")");
            }
        });

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  5. FocusListener / FocusAdapter                            ║
        // ║                                                             ║
        // ║  Detecta cuándo un componente GANA o PIERDE el foco:        ║
        // ║    • focusGained(FocusEvent) → recibe foco (clic o Tab)     ║
        // ║    • focusLost(FocusEvent)   → pierde foco                  ║
        // ║                                                             ║
        // ║  Útil para: validaciones al salir de un campo,              ║
        // ║  resaltar visualmente el campo activo, etc.                 ║
        // ╚═════════════════════════════════════════════════════════════╝

        campoTexto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campoTexto.setBackground(new Color(255, 255, 220)); // Amarillo suave
                log("👁 FocusEvent → campo de texto GANÓ el foco");
            }

            @Override
            public void focusLost(FocusEvent e) {
                campoTexto.setBackground(Color.WHITE); // Restaurar blanco
                log("👁 FocusEvent → campo de texto PERDIÓ el foco");
            }
        });

        // ──────────────────────────────────────────────────────────────
        //  MONTAJE FINAL de la ventana
        // ──────────────────────────────────────────────────────────────
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(splitCentral, BorderLayout.CENTER);
        panelPrincipal.add(barraEstado, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);

        // Mensajes iniciales en el log
        log("Aplicación iniciada – interactúa con los componentes");
        log("────────────────────────────────────────────────────");
        campoTexto.requestFocus();
    }

    /**
     * Añade un mensaje numerado al log de eventos
     * y hace scroll automático al final.
     */
    private static void log(String mensaje) {
        contadorEventos++;
        logEventos.append(String.format("[%03d] %s%n", contadorEventos, mensaje));
        // Auto-scroll: mover el cursor al final del texto
        logEventos.setCaretPosition(logEventos.getDocument().getLength());
    }

    public static void main(String[] args) {
        launch();
    }
}
