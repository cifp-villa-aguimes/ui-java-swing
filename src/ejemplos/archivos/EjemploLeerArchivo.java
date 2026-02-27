package ejemplos.archivos;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 8 – Leer un archivo de texto                       ║
 * ║                                                             ║
 * ║  Demuestra cómo leer un fichero desde disco y mostrarlo     ║
 * ║  en un JTextArea. Usa la API moderna java.nio.file:         ║
 * ║                                                             ║
 * ║    • Paths.get("ruta", "archivo") → crea un objeto Path     ║
 * ║    • Files.readAllLines(path)     → devuelve List<String>   ║
 * ║    • Files.readString(path)       → devuelve un String      ║
 * ║                                                             ║
 * ║  También muestra control de errores con try-catch y         ║
 * ║  múltiples maneras de recorrer el contenido (for-each,      ║
 * ║  String.join, readString directo).                          ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploLeerArchivo {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 8 – Leer Archivo de Texto");
        ventana.setSize(620, 440);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ──────────────────────────────────────────────────────────────
        //  PANEL SUPERIOR: ruta del archivo + botón leer
        // ──────────────────────────────────────────────────────────────
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 0));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Archivo a leer"));

        JLabel labelRuta = new JLabel("  Ruta: ");
        JTextField campoRuta = new JTextField("data/archivo.txt");
        campoRuta.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JButton botonLeer = new JButton("Leer archivo");

        panelSuperior.add(labelRuta, BorderLayout.WEST);
        panelSuperior.add(campoRuta, BorderLayout.CENTER);
        panelSuperior.add(botonLeer, BorderLayout.EAST);

        // ──────────────────────────────────────────────────────────────
        //  PANEL CENTRAL: contenido del archivo
        // ──────────────────────────────────────────────────────────────
        JTextArea areaContenido = new JTextArea();
        areaContenido.setEditable(false);
        areaContenido.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollContenido = new JScrollPane(areaContenido);
        scrollContenido.setBorder(BorderFactory.createTitledBorder("Contenido del archivo"));

        // — Barra de estado —
        JLabel barraEstado = new JLabel("  Introduce la ruta y pulsa \"Leer archivo\"");
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  LEER EL ARCHIVO CON java.nio.file                          ║
        // ║                                                             ║
        // ║  Pasos:                                                     ║
        // ║    1. Crear un Path con Paths.get(ruta)                     ║
        // ║    2. Comprobar que el archivo existe con Files.exists()    ║
        // ║    3. Leer con Files.readAllLines() → List<String>          ║
        // ║       o con Files.readString() → String (Java 11+)          ║
        // ║    4. Capturar IOException por si falla la lectura          ║
        // ╚═════════════════════════════════════════════════════════════╝

        botonLeer.addActionListener(e -> {
            String ruta = campoRuta.getText().trim();
            if (ruta.isEmpty()) {
                JOptionPane.showMessageDialog(ventana,
                        "Introduce una ruta de archivo.",
                        "Ruta vacía", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Paths.get() crea un objeto Path a partir de la ruta.
            // Puede ser relativa (desde el directorio del proyecto)
            // o absoluta (ruta completa del sistema).
            Path path = Paths.get(ruta);

            // Método 1: Files.readAllLines() → devuelve cada línea como un String
            // Útil cuando necesitas procesar el archivo línea a línea.
            try {
                List<String> lineas = Files.readAllLines(path);

                // Construir el texto. Alternativas:
                // a) Bucle for-each (la más didáctica):
                StringBuilder sb = new StringBuilder();
                for (String linea : lineas) {
                    sb.append(linea).append("\n");
                }
                areaContenido.setText(sb.toString());

                // b) String.join() (más compacto):
                //    areaContenido.setText(String.join("\n", lineas));

                // c) Files.readString(path) (Java 11+, todo de golpe):
                //    areaContenido.setText(Files.readString(path));

                barraEstado.setText("  ✔ Leído: " + path.toAbsolutePath()
                        + "  |  " + lineas.size() + " líneas");
                areaContenido.setCaretPosition(0); // scroll al inicio

            } catch (IOException ex) {
                // IOException cubre: archivo no encontrado, permisos, etc.
                areaContenido.setText("");
                barraEstado.setText("  ✘ Error al leer el archivo");
                JOptionPane.showMessageDialog(ventana,
                        "Error al leer el archivo:\n" + ex.getMessage(),
                        "Error de lectura", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Permitir pulsar Enter en el campo de ruta para leer
        campoRuta.addActionListener(e -> botonLeer.doClick());

        // ──────────────────────────────────────────────────────────────
        //  MONTAJE FINAL
        // ──────────────────────────────────────────────────────────────
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollContenido, BorderLayout.CENTER);
        panelPrincipal.add(barraEstado, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);

        // Leer el archivo automáticamente al abrir
        botonLeer.doClick();
    }

    public static void main(String[] args) {
        launch();
    }
}
