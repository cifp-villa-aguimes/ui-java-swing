package ejemplos.panelcomunicacion;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 7 – Comunicación entre paneles (modelo compartido) ║
 * ║                                                             ║
 * ║  Mismo problema que el Ejemplo 6, pero esta vez los         ║
 * ║  paneles NO se pasan componentes entre sí.                  ║
 * ║                                                             ║
 * ║  SOLUCIÓN B: usar un OBJETO MODELO (DatosCompartidos)       ║
 * ║  Los dos paneles acceden al mismo objeto de datos,          ║
 * ║  pero no conocen la existencia del otro panel.              ║
 * ║                                                             ║
 * ║  ✔ DESACOPLADO: cada panel solo depende del modelo.         ║
 * ║  ✔ Más escalable: podríamos añadir más paneles sin          ║
 * ║    modificar los existentes.                                ║
 * ║  ⚠ Algo más de código (necesitamos la clase modelo).        ║
 * ║                                                             ║
 * ║  Concepto clave: desacoplamiento mediante un modelo         ║
 * ║  de datos compartido (patrón similar a MVC).                ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class PanelComunicacionSimple {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 7 – Comunicación por modelo compartido");
        ventana.setSize(580, 360);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  EL MODELO COMPARTIDO                                       ║
        // ║                                                             ║
        // ║  DatosCompartidos es una clase simple con getter/setter.    ║
        // ║  Actúa como "intermediario" entre los paneles:              ║
        // ║                                                             ║
        // ║   ┌──────────┐      ┌──────────────────┐      ┌────────┐    ║
        // ║   │ Panel de │ ───▸ │ DatosCompartidos │ ◂─── │ Panel  │    ║
        // ║   │ entrada  │ set  │   (texto: "...")  │  get │ botones│   ║
        // ║   └──────────┘      └──────────────────┘      └────────┘    ║
        // ║                                                             ║
        // ║  Cada panel solo tiene referencia al modelo, NO al otro.    ║
        // ╚═════════════════════════════════════════════════════════════╝
        DatosCompartidos datos = new DatosCompartidos();

        // Área de log para ver los cambios
        JTextArea logResultados = new JTextArea();
        logResultados.setEditable(false);
        logResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logResultados.setText("Escribe algo arriba y pulsa algún botón...\n"
                + "Los paneles se comunican a través de DatosCompartidos.\n"
                + "──────────────────────────────────────────────\n");
        JScrollPane scrollLog = new JScrollPane(logResultados);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // ──────────────────────────────────────────────────────────────
        //  Construir los paneles pasando solo el MODELO (no componentes)
        // ──────────────────────────────────────────────────────────────
        JPanel panelEntrada = crearPanelEntrada(datos, logResultados);
        JPanel panelBotones = crearPanelBotones(datos, logResultados, ventana);

        // ──────────────────────────────────────────────────────────────
        //  MONTAJE FINAL
        // ──────────────────────────────────────────────────────────────
        panelPrincipal.add(panelEntrada, BorderLayout.NORTH);
        panelPrincipal.add(scrollLog, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  PANEL DE ENTRADA                                           ║
    // ║                                                             ║
    // ║  Este panel actualiza el modelo en tiempo real usando       ║
    // ║  CaretListener: cada vez que el cursor se mueve o el texto  ║
    // ║  cambia, guardamos el valor en DatosCompartidos.            ║
    // ║                                                             ║
    // ║  NOTA: CaretListener es diferente de DocumentListener:      ║
    // ║    • DocumentListener → cambios en el contenido             ║
    // ║    • CaretListener    → cambios en la posición del cursor   ║
    // ║    Aquí lo usamos porque caretUpdate también se dispara     ║
    // ║    cuando el texto cambia (el cursor se mueve).             ║
    // ╚═════════════════════════════════════════════════════════════╝
    private static JPanel crearPanelEntrada(DatosCompartidos datos,
            JTextArea logResultados) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Panel de entrada"));

        JLabel label = new JLabel("  Texto: ");
        label.setFont(new Font("SansSerif", Font.BOLD, 13));

        JTextField campoTexto = new JTextField();
        campoTexto.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // CaretListener actualiza el modelo en tiempo real
        campoTexto.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                datos.setTexto(campoTexto.getText());
            }
        });

        panel.add(label, BorderLayout.WEST);
        panel.add(campoTexto, BorderLayout.CENTER);
        return panel;
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  PANEL DE BOTONES                                           ║
    // ║                                                             ║
    // ║  Este panel NO conoce el JTextField del panel de entrada.   ║
    // ║  Solo conoce el modelo DatosCompartidos.                    ║
    // ║  Lee el texto con datos.getTexto().                         ║
    // ║                                                             ║
    // ║  → DESACOPLAMIENTO: podríamos cambiar el panel de entrada   ║
    // ║    completamente sin tocar este panel.                      ║
    // ╚═════════════════════════════════════════════════════════════╝
    private static JPanel crearPanelBotones(DatosCompartidos datos,
            JTextArea logResultados, JFrame ventana) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Panel de acciones"));

        // Botón 1: Mostrar texto usando datos.getTexto()
        JButton botonMostrar = new JButton("Mostrar texto");
        botonMostrar.addActionListener(e -> {
            // Leemos del MODELO, no del campo directamente
            String texto = datos.getTexto();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(ventana,
                        "El modelo está vacío. Escribe algo primero.",
                        "Sin datos", JOptionPane.WARNING_MESSAGE);
            } else {
                logResultados.append("▸ datos.getTexto() → \"" + texto + "\"\n");
                JOptionPane.showMessageDialog(ventana,
                        "Texto en el modelo: " + texto,
                        "Contenido del modelo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Botón 2: Contar caracteres del modelo
        JButton botonContar = new JButton("Contar caracteres");
        botonContar.addActionListener(e -> {
            String texto = datos.getTexto();
            logResultados.append("▸ datos.getTexto().length() → "
                    + texto.length() + " caracteres\n");
        });

        // Botón 3: Invertir texto del modelo
        JButton botonInvertir = new JButton("Invertir texto");
        botonInvertir.addActionListener(e -> {
            String texto = datos.getTexto();
            String invertido = new StringBuilder(texto).reverse().toString();
            datos.setTexto(invertido);
            logResultados.append("▸ Texto invertido en el modelo: \"" + invertido + "\"\n"
                    + "  (NOTA: el campo NO se actualiza automáticamente,\n"
                    + "   solo cambia el dato en el modelo)\n");
        });

        panel.add(botonMostrar);
        panel.add(botonContar);
        panel.add(botonInvertir);
        return panel;
    }

    public static void main(String[] args) {
        launch();
    }
}

// ╔═════════════════════════════════════════════════════════════╗
// ║  CLASE MODELO: DatosCompartidos                             ║
// ║                                                             ║
// ║  Clase simple con un atributo privado (texto) y sus         ║
// ║  getter/setter. Actúa como intermediario entre paneles.     ║
// ║                                                             ║
// ║  En un proyecto real esta clase podría tener muchos más     ║
// ║  atributos: nombre, email, opciones seleccionadas, etc.     ║
// ║  Aquí la mantenemos simple para ilustrar el concepto.       ║
// ╚═════════════════════════════════════════════════════════════╝
class DatosCompartidos {
    private String texto = "";

    public String getTexto() {
        return texto;
    }

    public void setTexto(String nuevoTexto) {
        this.texto = nuevoTexto;
    }
}
