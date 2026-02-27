package ejemplos.componentesavanzados;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * EJEMPLO 3 – COMPONENTES AVANZADOS DE SWING
 * =============================================
 * Tras conocer los componentes básicos (Ejemplo 2), aquí vemos componentes
 * de selección y ajuste más elaborados:
 *
 * - JComboBox → Lista desplegable (selección única).
 * - JList → Lista visible con selección simple o múltiple.
 * - JSlider → Barra deslizante para elegir un valor numérico.
 * - JSpinner → Campo numérico con flechas arriba/abajo.
 * - JProgressBar → Barra de progreso (aquí controlada por el JSlider).
 *
 * También se introduce:
 * - ItemListener → detecta cambios en JComboBox.
 * - ListSelectionListener → detecta cambios de selección en JList.
 * - ChangeListener → detecta cambios en JSlider / JSpinner.
 * - TitledBorder → borde con título para agrupar componentes.
 *
 * Layout: combinamos un BorderLayout principal con paneles interiores
 * que usan FlowLayout y GridLayout.
 */
public class EjemploComponentesAvanzados {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 3 – Componentes Avanzados");
        ventana.setSize(600, 520);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(8, 8));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  PANEL SUPERIOR: JComboBox + JSpinner                       ║
        // ╚═════════════════════════════════════════════════════════════╝
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Selección y ajuste"));

        // ── JComboBox ──────────────────────────────────────────────────────
        // JComboBox: lista desplegable. El usuario pulsa y elige UNA opción.
        // Usamos un array de String para definir las opciones disponibles.
        JLabel etiquetaCombo = new JLabel("Lenguaje:");
        etiquetaCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        String[] lenguajes = { "Java", "Python", "JavaScript", "C#", "C++", "Kotlin", "Swift", "PHP" };
        JComboBox<String> comboLenguaje = new JComboBox<>(lenguajes);
        comboLenguaje.setToolTipText("Selecciona un lenguaje de programación");

        panelSuperior.add(etiquetaCombo);
        panelSuperior.add(comboLenguaje);

        // Pequeño separador visual
        panelSuperior.add(Box.createHorizontalStrut(20));

        // ── JSpinner ───────────────────────────────────────────────────────
        // JSpinner: campo numérico con flechas ▲▼ para incrementar/decrementar.
        // SpinnerNumberModel(valorInicial, mínimo, máximo, paso)
        JLabel etiquetaSpinner = new JLabel("Experiencia (años):");
        etiquetaSpinner.setFont(new Font("SansSerif", Font.PLAIN, 13));
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(1, 0, 50, 1);
        JSpinner spinnerExp = new JSpinner(modeloSpinner);
        spinnerExp.setPreferredSize(new Dimension(60, 25));
        spinnerExp.setToolTipText("Ajusta con las flechas o escribe un número");

        panelSuperior.add(etiquetaSpinner);
        panelSuperior.add(spinnerExp);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  PANEL CENTRAL: JList (izquierda) + Info (derecha)          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JPanel panelCentral = new JPanel(new BorderLayout(8, 0));

        // ── JList ──────────────────────────────────────────────────────────
        // JList: lista visible (no desplegable). El usuario puede ver todas
        // las opciones a la vez y seleccionar una o varias.
        // SINGLE_SELECTION → solo se puede seleccionar un elemento a la vez.
        String[] frameworks = {
                "Spring Boot", "Hibernate", "Jakarta EE",
                "React", "Angular", "Vue.js",
                "Django", "Flask", "Express.js",
                "ASP.NET", "Ruby on Rails", "Laravel"
        };
        JList<String> listaFrameworks = new JList<>(frameworks);
        listaFrameworks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaFrameworks.setFont(new Font("SansSerif", Font.PLAIN, 13));
        listaFrameworks.setToolTipText("Haz clic para seleccionar un framework");

        // Envolvemos la JList en un JScrollPane (importante si la lista es larga)
        JScrollPane scrollLista = new JScrollPane(listaFrameworks);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Frameworks (JList)"));
        scrollLista.setPreferredSize(new Dimension(200, 0)); // Ancho fijo

        panelCentral.add(scrollLista, BorderLayout.WEST);

        // ── Panel de información (derecha) ─────────────────────────────────
        JTextArea areaInfo = new JTextArea();
        areaInfo.setEditable(false);
        areaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaInfo.setLineWrap(true);
        areaInfo.setWrapStyleWord(true);
        areaInfo.setText("Panel de información:\n"
                + "──────────────────────────────────────\n"
                + "Interactúa con los componentes para\n"
                + "ver los eventos que se generan.\n\n"
                + "Componentes de este ejemplo:\n"
                + "  • JComboBox   → lista desplegable\n"
                + "  • JSpinner    → selector numérico\n"
                + "  • JList       → lista visible\n"
                + "  • JSlider     → barra deslizante\n"
                + "  • JProgressBar→ barra de progreso\n"
                + "──────────────────────────────────────\n");

        JScrollPane scrollInfo = new JScrollPane(areaInfo);
        scrollInfo.setBorder(BorderFactory.createTitledBorder("Eventos detectados"));

        panelCentral.add(scrollInfo, BorderLayout.CENTER);

        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  PANEL INFERIOR: JSlider + JProgressBar                     ║
        // ╚═════════════════════════════════════════════════════════════╝
        JPanel panelInferior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelInferior.setBorder(BorderFactory.createTitledBorder("Slider y barra de progreso"));

        // ── JSlider ────────────────────────────────────────────────────────
        // JSlider: barra deslizante. El usuario arrastra el control para
        // elegir un valor dentro de un rango (mín, máx).
        // setMajorTickSpacing → marcas grandes; setMinorTickSpacing → marcas pequeñas.
        // setPaintTicks(true) → mostrar las marcas; setPaintLabels(true) → mostrar
        // números.
        JPanel filaSlider = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        JLabel etiquetaSlider = new JLabel("Nivel (0-100):");
        etiquetaSlider.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
        slider.setMajorTickSpacing(25); // Marcas grandes cada 25
        slider.setMinorTickSpacing(5); // Marcas pequeñas cada 5
        slider.setPaintTicks(true); // Dibujar las marcas
        slider.setPaintLabels(true); // Dibujar los números
        slider.setPreferredSize(new Dimension(400, 50));
        slider.setToolTipText("Arrastra para ajustar el nivel");

        filaSlider.add(etiquetaSlider);
        filaSlider.add(slider);
        panelInferior.add(filaSlider);

        // ── JProgressBar ───────────────────────────────────────────────────
        // JProgressBar: barra de progreso. Muestra visualmente un porcentaje.
        // setStringPainted(true) → muestra el valor numérico dentro de la barra.
        // Aquí la conectamos al JSlider para que se muevan juntos.
        JPanel filaProgress = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        JLabel etiquetaProgress = new JLabel("Progreso:       ");
        etiquetaProgress.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JProgressBar barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setValue(25); // Valor inicial igual que el slider
        barraProgreso.setStringPainted(true); // Mostrar "25%" dentro de la barra
        barraProgreso.setPreferredSize(new Dimension(400, 25));

        filaProgress.add(etiquetaProgress);
        filaProgress.add(barraProgreso);
        panelInferior.add(filaProgress);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        // ╔═══════════════════════════════════════════════════════════════╗
        // ║  EVENTOS DE LOS COMPONENTES AVANZADOS                         ║
        // ║                                                               ║
        // ║  Cada componente avanzado tiene su propio tipo de listener:   ║
        // ║  • JComboBox  → ItemListener     (cambio de elemento)         ║
        // ║  • JList      → ListSelectionListener (cambio de selección)   ║
        // ║  • JSlider    → ChangeListener   (cambio de valor)            ║
        // ║  • JSpinner   → ChangeListener   (cambio de valor)            ║
        // ╚═══════════════════════════════════════════════════════════════╝

        // ── Evento de JComboBox (ItemListener) ─────────────────────────────
        // Se dispara cuando el usuario selecciona un elemento distinto.
        comboLenguaje.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Solo nos interesa el evento SELECTED (no el DESELECTED)
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String seleccion = (String) comboLenguaje.getSelectedItem();
                    areaInfo.append("▶ ComboBox → Lenguaje seleccionado: " + seleccion + "\n");
                }
            }
        });

        // ── Evento de JList (ListSelectionListener) ────────────────────────
        // Se dispara cuando el usuario hace clic en un elemento de la lista.
        listaFrameworks.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // getValueIsAdjusting() es true mientras el usuario sigue arrastrando/pulsando.
                // Solo procesamos cuando ya terminó (false).
                if (!e.getValueIsAdjusting()) {
                    String seleccion = listaFrameworks.getSelectedValue();
                    if (seleccion != null) {
                        areaInfo.append("▶ JList → Framework seleccionado: " + seleccion + "\n");
                    }
                }
            }
        });

        // ── Evento de JSlider (ChangeListener) ────────────────────────────
        // Se dispara cada vez que el valor del slider cambia (al arrastrar).
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int valor = slider.getValue();
                barraProgreso.setValue(valor); // Sincronizar barra de progreso con el slider
                if (!slider.getValueIsAdjusting()) { // Solo al soltar
                    areaInfo.append("▶ Slider → Nivel ajustado a: " + valor + "%\n");
                }
            }
        });

        // ── Evento de JSpinner (ChangeListener) ───────────────────────────
        // Se dispara cuando el usuario modifica el valor con las flechas o escribiendo.
        spinnerExp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int valor = (int) spinnerExp.getValue();
                areaInfo.append("▶ Spinner → Experiencia: " + valor + " año(s)\n");
            }
        });

        // ── Mostrar la ventana ─────────────────────────────────────────────
        ventana.add(panelPrincipal);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
