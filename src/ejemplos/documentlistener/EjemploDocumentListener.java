package ejemplos.documentlistener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 5 – DocumentListener                               ║
 * ║                                                             ║
 * ║  DocumentListener escucha cambios en el MODELO del texto    ║
 * ║  (el Document interno de JTextField / JTextArea),           ║
 * ║  no en el componente directamente.                          ║
 * ║                                                             ║
 * ║  Diferencia clave con KeyListener:                          ║
 * ║    • KeyListener    → detecta pulsaciones de teclas         ║
 * ║      NO se dispara con setText() ni con copiar/pegar        ║
 * ║    • DocumentListener → detecta cualquier cambio en el      ║
 * ║      texto: escritura, borrado, setText(), pegar...         ║
 * ║                                                             ║
 * ║  Ejemplo práctico: buscador en tiempo real que filtra       ║
 * ║  una lista de países conforme el usuario escribe.           ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploDocumentListener {

    public static void launch() {

        // ╔══════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                           ║
        // ╚══════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 5 – DocumentListener");
        ventana.setSize(700, 520);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  ¿QUÉ ES EL "DOCUMENT" DE UN CAMPO DE TEXTO?                ║
        // ║                                                             ║
        // ║  En Swing, todo componente de texto (JTextField, JTextArea) ║
        // ║  almacena su contenido en un objeto separado llamado        ║
        // ║  Document (interfaz). El componente es solo la VISTA;       ║
        // ║  el Document es el MODELO (patrón MVC).                     ║
        // ║                                                             ║
        // ║  Para escuchar cambios en el modelo:                        ║
        // ║    campo.getDocument().addDocumentListener(listener)        ║
        // ║                                                             ║
        // ║  DocumentListener tiene 3 métodos:                          ║
        // ║    • insertUpdate(DocumentEvent e)  → se añadió texto       ║
        // ║    • removeUpdate(DocumentEvent e)  → se borró texto        ║
        // ║    • changedUpdate(DocumentEvent e) → cambió el estilo      ║
        // ║      (solo en documentos con formato: JTextPane)            ║
        // ║      En JTextField/JTextArea NUNCA se dispara.              ║
        // ╚═════════════════════════════════════════════════════════════╝

        // ──────────────────────────────────────────────────────────────
        //  DATOS: lista completa de países (modelo de datos)
        // ──────────────────────────────────────────────────────────────
        String[] paises = {
                "Alemania", "Argentina", "Australia", "Austria", "Bélgica",
                "Bolivia", "Brasil", "Canada", "Chile", "China", "Colombia",
                "Corea del Sur", "Dinamarca", "Ecuador", "Egipto", "España",
                "Estados Unidos", "Finlandia", "Francia", "Grecia", "Holanda",
                "India", "Italia", "Japón", "México", "Noruega", "Perú",
                "Polonia", "Portugal", "Reino Unido", "Rusia", "Suecia",
                "Suiza", "Turquía", "Uruguay", "Venezuela"
        };

        // ──────────────────────────────────────────────────────────────
        //  PANEL SUPERIOR: campo de búsqueda + contador + botón reset
        // ──────────────────────────────────────────────────────────────
        final int MAX_CHARS = 30;

        JLabel labelBuscar = new JLabel("Buscar país: ");
        JTextField campoBusqueda = new JTextField(22);
        campoBusqueda.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoBusqueda.setToolTipText("Escribe para filtrar la lista en tiempo real");

        // Contador de caracteres (se actualiza con DocumentListener)
        JLabel labelContador = new JLabel("0 / " + MAX_CHARS);
        labelContador.setFont(new Font("Monospaced", Font.PLAIN, 12));
        labelContador.setForeground(Color.GRAY);

        JButton botonReset = new JButton("Limpiar");
        botonReset.setToolTipText(
                "Resetea el campo con setText(\"\") – también dispara DocumentListener");

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Buscador en tiempo real"));
        panelSuperior.add(labelBuscar);
        panelSuperior.add(campoBusqueda);
        panelSuperior.add(labelContador);
        panelSuperior.add(botonReset);

        // ──────────────────────────────────────────────────────────────
        //  PANEL CENTRAL: lista filtrada (izq.) + log de eventos (der.)
        // ──────────────────────────────────────────────────────────────

        // — JList con los países filtrados —
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for (String p : paises) {
            modeloLista.addElement(p);
        }
        JList<String> listaFiltrada = new JList<>(modeloLista);
        listaFiltrada.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JScrollPane scrollLista = new JScrollPane(listaFiltrada);
        scrollLista.setBorder(BorderFactory.createTitledBorder(
                "Países (" + paises.length + " en total)"));

        // — Log de eventos de DocumentListener —
        JTextArea logEventos = new JTextArea();
        logEventos.setEditable(false);
        logEventos.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollLog = new JScrollPane(logEventos);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log de DocumentListener"));

        JSplitPane splitCentral = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, scrollLista, scrollLog);
        splitCentral.setDividerLocation(250);

        // — Barra de estado inferior —
        JLabel barraEstado = new JLabel("  " + paises.length + " países cargados");
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  DOCUMENTLISTENER: implementación con clase anónima         ║
        // ║                                                             ║
        // ║  No es una interfaz funcional (tiene 3 métodos abstractos)  ║
        // ║  → NO se puede usar lambda directamente.                    ║
        // ║                                                             ║
        // ║  NOTA: No existe DocumentAdapter con métodos vacíos         ║
        // ║  por defecto, así que debemos implementar los 3 métodos     ║
        // ║  siempre. changedUpdate lo dejamos vacío intencionalmente.  ║
        // ╚═════════════════════════════════════════════════════════════╝

        int[] contadorLog = { 0 }; // array de 1 elemento para poder usarlo en la lambda

        campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                // Se dispara cada vez que se AÑADE texto al campo:
                // escritura normal, pegar con Ctrl+V, arrastrar texto…
                contadorLog[0]++;
                logEventos.append(String.format("[%03d] insertUpdate  → texto: \"%s\"%n",
                        contadorLog[0], campoBusqueda.getText()));
                actualizarUI();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Se dispara cada vez que se ELIMINA texto del campo:
                // Backspace, Supr, seleccionar y borrar, cortar con Ctrl+X…
                contadorLog[0]++;
                logEventos.append(String.format("[%03d] removeUpdate  → texto: \"%s\"%n",
                        contadorLog[0], campoBusqueda.getText()));
                actualizarUI();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Se dispara cuando cambia el FORMATO del texto (negrita, color…)
                // JTextField NO tiene formato → este método NUNCA se ejecuta aquí.
                // Solo ocurre en JTextPane o JEditorPane con StyledDocument.
                contadorLog[0]++;
                logEventos.append(String.format("[%03d] changedUpdate → texto: \"%s\"%n",
                        contadorLog[0], campoBusqueda.getText()));
                actualizarUI();
            }

            // ──────────────────────────────────────────────────────────
            //  Método auxiliar: actualiza lista + contador + colores
            //  Se llama desde los 3 métodos del listener para no
            //  repetir el mismo código (principio DRY).
            // ──────────────────────────────────────────────────────────
            private void actualizarUI() {
                String filtro = campoBusqueda.getText();
                int len = filtro.length();

                // 1. Actualizar el contador de caracteres
                labelContador.setText(len + " / " + MAX_CHARS);

                // 2. Cambiar el color del campo según longitud
                if (len == 0) {
                    campoBusqueda.setBackground(Color.WHITE);
                    labelContador.setForeground(Color.GRAY);
                } else if (len < MAX_CHARS) {
                    campoBusqueda.setBackground(new Color(240, 255, 240)); // verde suave
                    labelContador.setForeground(new Color(0, 130, 0));
                } else {
                    campoBusqueda.setBackground(new Color(255, 230, 230)); // rojo suave
                    labelContador.setForeground(Color.RED);
                    // Limitar a MAX_CHARS (si pega texto muy largo)
                    if (len > MAX_CHARS) {
                        campoBusqueda.setText(filtro.substring(0, MAX_CHARS));
                        return; // setText() volverá a disparar el listener
                    }
                }

                // 3. Filtrar la lista: mostrar solo países que contienen el filtro
                //    (ignorando mayúsculas/minúsculas con toLowerCase)
                modeloLista.clear();
                int coincidencias = 0;
                for (String pais : paises) {
                    if (pais.toLowerCase().contains(filtro.toLowerCase())) {
                        modeloLista.addElement(pais);
                        coincidencias++;
                    }
                }

                // 4. Actualizar barra de estado y título de la lista
                scrollLista.setBorder(BorderFactory.createTitledBorder(
                        "Países (" + coincidencias + " coincidencias)"));
                barraEstado.setText("  Mostrando " + coincidencias + " de "
                        + paises.length + "  |  filtro: \""
                        + (filtro.isEmpty() ? "ninguno" : filtro) + "\"");

                // 5. Auto scroll del log al final
                logEventos.setCaretPosition(logEventos.getDocument().getLength());
            }
        });

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  DEMOSTRACIÓN: setText() TAMBIÉN dispara DocumentListener   ║
        // ║                                                             ║
        // ║  Esta es la gran diferencia con KeyListener:                ║
        // ║    • KeyListener NO se dispara con setText()                ║
        // ║    • DocumentListener SÍ se dispara con setText()           ║
        // ║      porque modifica el Document directamente.              ║
        // ║                                                             ║
        // ║  Al pulsar "Limpiar", setText("") llama a removeUpdate      ║
        // ║  igual que si el usuario hubiera borrado a mano el texto.   ║
        // ╚═════════════════════════════════════════════════════════════╝

        botonReset.addActionListener(e -> {
            // setText("") modifica el Document → dispara removeUpdate
            campoBusqueda.setText("");
            campoBusqueda.requestFocus();
            logEventos.append("──── Limpiar con setText(\"\") → disparó removeUpdate ────\n");
            logEventos.setCaretPosition(logEventos.getDocument().getLength());
        });

        // ──────────────────────────────────────────────────────────────
        //  MONTAJE FINAL de la ventana
        // ──────────────────────────────────────────────────────────────
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(splitCentral, BorderLayout.CENTER);
        panelPrincipal.add(barraEstado, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);

        // Mensaje inicial
        logEventos.setText("Escribe en el campo para ver los eventos...\n"
                + "Prueba también Ctrl+V (pegar) y el botón Limpiar.\n"
                + "──────────────────────────────────────────────\n");
        campoBusqueda.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}
