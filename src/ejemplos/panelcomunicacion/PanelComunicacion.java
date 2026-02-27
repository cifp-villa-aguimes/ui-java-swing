package ejemplos.panelcomunicacion;

import javax.swing.*;
import java.awt.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 6 – Comunicación entre paneles (referencia)        ║
 * ║                                                             ║
 * ║  Problema: tenemos un campo de texto en un panel y un       ║
 * ║  botón en otro panel distinto. ¿Cómo puede el botón         ║
 * ║  acceder al contenido del campo si están en paneles         ║
 * ║  diferentes?                                                ║
 * ║                                                             ║
 * ║  SOLUCIÓN A: pasar la REFERENCIA del componente.            ║
 * ║  Creamos el JTextField en un sitio y se lo pasamos como     ║
 * ║  parámetro al método que construye el otro panel.           ║
 * ║                                                             ║
 * ║  ✔ Simple y directo.                                        ║
 * ║  ⚠ Los paneles están ACOPLADOS: el panel de botones         ║
 * ║    conoce directamente el componente del otro panel.        ║
 * ║                                                             ║
 * ║  → Ver Ejemplo 7 para una solución DESACOPLADA.             ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class PanelComunicacion {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 6 – Comunicación por referencia");
        ventana.setSize(580, 360);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  PASO CLAVE: crear el componente compartido ANTES           ║
        // ║                                                             ║
        // ║  Creamos el JTextField aquí, en launch(), y lo pasamos      ║
        // ║  como parámetro a AMBOS métodos que crean los paneles.      ║
        // ║                                                             ║
        // ║  Así, los dos paneles acceden al MISMO objeto JTextField.   ║
        // ║  No es una copia: es la misma referencia en memoria.        ║
        // ╚═════════════════════════════════════════════════════════════╝
        JTextField campoCompartido = new JTextField();
        campoCompartido.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Área de log para ver lo que ocurre
        JTextArea logResultados = new JTextArea();
        logResultados.setEditable(false);
        logResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logResultados.setText("Escribe algo arriba y pulsa algún botón...\n"
                + "──────────────────────────────────────────────\n");
        JScrollPane scrollLog = new JScrollPane(logResultados);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // ──────────────────────────────────────────────────────────────
        //  Construir los paneles, pasándoles la REFERENCIA
        // ──────────────────────────────────────────────────────────────
        JPanel panelEntrada = crearPanelEntrada(campoCompartido);
        JPanel panelBotones = crearPanelBotones(campoCompartido, logResultados, ventana);

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
    // ║  Recibe el JTextField por parámetro y lo coloca en su panel.║
    // ╚═════════════════════════════════════════════════════════════╝
    private static JPanel crearPanelEntrada(JTextField campoTexto) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Panel de entrada"));

        JLabel label = new JLabel("  Texto: ");
        label.setFont(new Font("SansSerif", Font.BOLD, 13));

        panel.add(label, BorderLayout.WEST);
        panel.add(campoTexto, BorderLayout.CENTER);
        return panel;
    }

    // ╔═════════════════════════════════════════════════════════════╗
    // ║  PANEL DE BOTONES                                           ║
    // ║  Recibe el MISMO JTextField que el panel de entrada.        ║
    // ║  Así puede leer/modificar su contenido directamente.        ║
    // ║                                                             ║
    // ║  → Este es el "acoplamiento": este panel conoce y           ║
    // ║    depende directamente del componente del otro panel.      ║
    // ╚═════════════════════════════════════════════════════════════╝
    private static JPanel crearPanelBotones(JTextField campoTexto,
            JTextArea logResultados, JFrame ventana) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Panel de acciones"));

        // Botón 1: Mostrar texto en un diálogo
        JButton botonMostrar = new JButton("Mostrar texto");
        botonMostrar.addActionListener(e -> {
            String texto = campoTexto.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(ventana,
                        "El campo está vacío. Escribe algo primero.",
                        "Campo vacío", JOptionPane.WARNING_MESSAGE);
            } else {
                logResultados.append("▸ Mostrar: \"" + texto + "\"\n");
                JOptionPane.showMessageDialog(ventana,
                        "Texto del campo: " + texto,
                        "Contenido", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Botón 2: Convertir a mayúsculas (modifica el campo directamente)
        JButton botonMayusculas = new JButton("A mayúsculas");
        botonMayusculas.addActionListener(e -> {
            String texto = campoTexto.getText();
            campoTexto.setText(texto.toUpperCase());
            logResultados.append("▸ Convertido a mayúsculas: \""
                    + texto.toUpperCase() + "\"\n");
        });

        // Botón 3: Limpiar campo
        JButton botonLimpiar = new JButton("Limpiar");
        botonLimpiar.addActionListener(e -> {
            logResultados.append("▸ Campo limpiado (antes: \""
                    + campoTexto.getText() + "\")\n");
            campoTexto.setText("");
            campoTexto.requestFocus();
        });

        // Botón 4: Contar caracteres
        JButton botonContar = new JButton("Contar caracteres");
        botonContar.addActionListener(e -> {
            int longitud = campoTexto.getText().length();
            logResultados.append("▸ Longitud del texto: " + longitud + " caracteres\n");
        });

        panel.add(botonMostrar);
        panel.add(botonMayusculas);
        panel.add(botonLimpiar);
        panel.add(botonContar);
        return panel;
    }

    public static void main(String[] args) {
        launch();
    }
}
