package ejemplos.archivos;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 10 – JFileChooser (abrir y guardar archivos)       ║
 * ║                                                             ║
 * ║  JFileChooser abre un DIÁLOGO NATIVO del sistema operativo  ║
 * ║  para seleccionar archivos. Es la forma estándar de pedir   ║
 * ║  al usuario que elija un fichero para abrir o guardar.      ║
 * ║                                                             ║
 * ║  Métodos clave:                                             ║
 * ║    • showOpenDialog(parent)  → diálogo "Abrir"              ║
 * ║    • showSaveDialog(parent)  → diálogo "Guardar"            ║
 * ║    • getSelectedFile()       → File con la selección        ║
 * ║                                                             ║
 * ║  Configuración útil:                                        ║
 * ║    • setFileFilter()         → filtrar por extensión        ║
 * ║    • setCurrentDirectory()   → directorio inicial           ║
 * ║    • setDialogTitle()        → título personalizado         ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploJFileChooser {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 10 – JFileChooser");
        ventana.setSize(650, 480);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ──────────────────────────────────────────────────────────────
        //  PANEL CENTRAL: área de texto para mostrar/editar contenido
        // ──────────────────────────────────────────────────────────────
        JTextArea areaTexto = new JTextArea();
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setText("Pulsa \"Abrir\" para cargar un archivo,\n"
                + "o escribe texto aquí y pulsa \"Guardar\".\n");
        JScrollPane scrollTexto = new JScrollPane(areaTexto);
        scrollTexto.setBorder(BorderFactory.createTitledBorder("Contenido del archivo"));

        // — Barra de estado —
        JLabel barraEstado = new JLabel("  Listo");
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        // ──────────────────────────────────────────────────────────────
        //  PANEL INFERIOR: botones Abrir / Guardar / Limpiar
        // ──────────────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));

        JButton botonAbrir = new JButton("Abrir archivo...");
        JButton botonGuardar = new JButton("Guardar como...");
        JButton botonLimpiar = new JButton("Limpiar");

        panelBotones.add(botonAbrir);
        panelBotones.add(botonGuardar);
        panelBotones.add(botonLimpiar);

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  showOpenDialog() → ABRIR un archivo                        ║
        // ║                                                             ║
        // ║  Devuelve un entero con el resultado:                       ║
        // ║    • JFileChooser.APPROVE_OPTION → el usuario eligió OK     ║
        // ║    • JFileChooser.CANCEL_OPTION  → el usuario canceló       ║
        // ║                                                             ║
        // ║  Si aprueba, getSelectedFile() devuelve el File elegido.    ║
        // ╚═════════════════════════════════════════════════════════════╝

        botonAbrir.addActionListener(e -> {
            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Selecciona un archivo de texto");

            // Filtro para mostrar solo archivos .txt y .java
            // FileNameExtensionFilter("Descripción", extensiones...)
            selector.setFileFilter(
                    new FileNameExtensionFilter("Archivos de texto (*.txt, *.java)", "txt", "java"));

            // Directorio inicial → carpeta actual del proyecto
            selector.setCurrentDirectory(new File("."));

            // Mostrar diálogo → devuelve un int
            int resultado = selector.showOpenDialog(ventana);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoElegido = selector.getSelectedFile();

                // Leer con BufferedReader + FileReader (API clásica)
                try (BufferedReader lector = new BufferedReader(
                        new FileReader(archivoElegido))) {
                    areaTexto.setText(""); // Limpiar antes de cargar
                    String linea;
                    while ((linea = lector.readLine()) != null) {
                        areaTexto.append(linea + "\n");
                    }
                    areaTexto.setCaretPosition(0);
                    barraEstado.setText("  ✔ Abierto: " + archivoElegido.getAbsolutePath()
                            + "  (" + archivoElegido.length() + " bytes)");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ventana,
                            "Error al leer: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                barraEstado.setText("  Apertura cancelada por el usuario");
            }
        });

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  showSaveDialog() → GUARDAR un archivo                      ║
        // ║                                                             ║
        // ║  Mismo flujo que showOpenDialog(), pero con un diálogo      ║
        // ║  de guardar. IMPORTANTE: JFileChooser NO guarda nada por    ║
        // ║  sí solo → solo te da la ruta. El guardado lo haces tú.     ║
        // ╚═════════════════════════════════════════════════════════════╝

        botonGuardar.addActionListener(e -> {
            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Guardar archivo como...");
            selector.setCurrentDirectory(new File("."));
            selector.setSelectedFile(new File("documento.txt"));

            int resultado = selector.showSaveDialog(ventana);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivoDestino = selector.getSelectedFile();

                // Confirmar si ya existe
                if (archivoDestino.exists()) {
                    int confirmar = JOptionPane.showConfirmDialog(ventana,
                            "El archivo ya existe. ¿Sobrescribir?",
                            "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirmar != JOptionPane.YES_OPTION) {
                        barraEstado.setText("  Guardado cancelado (archivo ya existía)");
                        return;
                    }
                }

                // Escribir con Files.writeString (API moderna, Java 11+)
                try {
                    Files.writeString(archivoDestino.toPath(), areaTexto.getText());
                    barraEstado.setText("  ✔ Guardado: " + archivoDestino.getAbsolutePath());
                    JOptionPane.showMessageDialog(ventana,
                            "Archivo guardado correctamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(ventana,
                            "Error al guardar: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                barraEstado.setText("  Guardado cancelado por el usuario");
            }
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

        panelPrincipal.add(scrollTexto, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
