package ejemplos.archivos;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 9 – Escribir un archivo de texto                   ║
 * ║                                                             ║
 * ║  Demuestra cómo guardar texto desde un JTextArea a disco.   ║
 * ║  Se muestran dos APIs de escritura:                         ║
 * ║                                                             ║
 * ║    API clásica (java.io):                                   ║
 * ║    • BufferedWriter + FileWriter  → escritura con buffer    ║
 * ║    • try-with-resources           → cierre automático       ║
 * ║                                                             ║
 * ║    API moderna (java.nio.file, Java 11+):                   ║
 * ║    • Files.writeString(path, texto) → una sola línea        ║
 * ║                                                             ║
 * ║  También enseña: try-with-resources y la importancia de     ║
 * ║  cerrar siempre los flujos de E/S (streams).                ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploEscribirArchivo {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 9 – Escribir Archivo de Texto");
        ventana.setSize(620, 480);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ──────────────────────────────────────────────────────────────
        //  PANEL SUPERIOR: ruta de destino
        // ──────────────────────────────────────────────────────────────
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 0));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Archivo de destino"));

        JLabel labelRuta = new JLabel("  Ruta: ");
        JTextField campoRuta = new JTextField("data/archivoGuardado.txt");
        campoRuta.setFont(new Font("Monospaced", Font.PLAIN, 13));

        panelSuperior.add(labelRuta, BorderLayout.WEST);
        panelSuperior.add(campoRuta, BorderLayout.CENTER);

        // ──────────────────────────────────────────────────────────────
        //  PANEL CENTRAL: texto a escribir
        // ──────────────────────────────────────────────────────────────
        JTextArea areaTexto = new JTextArea();
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setText("Escribe aquí el contenido que quieres guardar.\n"
                + "Puedes escribir varias líneas.\n\n"
                + "Al pulsar \"Guardar\" se creará el archivo en la ruta indicada.");
        JScrollPane scrollTexto = new JScrollPane(areaTexto);
        scrollTexto.setBorder(BorderFactory.createTitledBorder("Contenido a guardar"));

        // — Barra de estado —
        JLabel barraEstado = new JLabel("  Escribe el contenido y pulsa \"Guardar\"");
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        // ──────────────────────────────────────────────────────────────
        //  PANEL INFERIOR: botones Guardar y Limpiar
        // ──────────────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));

        JButton botonGuardar = new JButton("Guardar archivo");
        JButton botonLimpiar = new JButton("Limpiar texto");

        panelBotones.add(botonGuardar);
        panelBotones.add(botonLimpiar);

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  ESCRIBIR CON BufferedWriter + FileWriter (API clásica)     ║
        // ║                                                             ║
        // ║  try (BufferedWriter bw = new BufferedWriter(               ║
        // ║          new FileWriter("ruta"))) {                         ║
        // ║      bw.write(texto);                                       ║
        // ║  }                                                          ║
        // ║                                                             ║
        // ║  "try-with-resources" (Java 7+):                            ║
        // ║  La variable declarada en el try() se CIERRA automáti-      ║
        // ║  camente al salir del bloque, tanto si fue bien como        ║
        // ║  si lanzó excepción. Equivale a un finally con .close().    ║
        // ║                                                             ║
        // ║  ALTERNATIVA con java.nio (Java 11+):                       ║
        // ║    Files.writeString(Path.of("ruta"), texto);               ║
        // ║  → una sola línea, sin abrir ni cerrar flujos.              ║
        // ╚═════════════════════════════════════════════════════════════╝

        botonGuardar.addActionListener(e -> {
            String ruta = campoRuta.getText().trim();
            String contenido = areaTexto.getText();

            if (ruta.isEmpty()) {
                JOptionPane.showMessageDialog(ventana,
                        "Introduce una ruta de destino.",
                        "Ruta vacía", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Opción A: API clásica (BufferedWriter + FileWriter)
            // FileWriter abre el flujo de escritura al archivo.
            // BufferedWriter añade un buffer → más eficiente para texto largo.
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {
                escritor.write(contenido);

                // Si llegamos aquí, la escritura fue correcta
                Path pathAbsoluto = Paths.get(ruta).toAbsolutePath();
                barraEstado.setText("  ✔ Guardado correctamente: " + pathAbsoluto);
                JOptionPane.showMessageDialog(ventana,
                        "Archivo guardado en:\n" + pathAbsoluto,
                        "Guardado correcto", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                barraEstado.setText("  ✘ Error al guardar el archivo");
                JOptionPane.showMessageDialog(ventana,
                        "Error al guardar:\n" + ex.getMessage(),
                        "Error de escritura", JOptionPane.ERROR_MESSAGE);
            }

            // Opción B (comentada): API moderna con una sola línea
            // try {
            //     Files.writeString(Path.of(ruta), contenido);
            // } catch (IOException ex) {
            //     ex.printStackTrace();
            // }
        });

        botonLimpiar.addActionListener(e -> {
            areaTexto.setText("");
            areaTexto.requestFocus();
            barraEstado.setText("  Texto limpiado");
        });

        // ──────────────────────────────────────────────────────────────
        //  MONTAJE FINAL
        // ──────────────────────────────────────────────────────────────
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelBotones, BorderLayout.NORTH);
        panelInferior.add(barraEstado, BorderLayout.SOUTH);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollTexto, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
