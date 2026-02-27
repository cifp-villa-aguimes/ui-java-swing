package ejemplos.jtable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * ╔═════════════════════════════════════════════════════════════╗
 * ║  EJEMPLO 11 – JTable (tabla de datos)                       ║
 * ║                                                             ║
 * ║  JTable muestra datos en filas y columnas, similar a una    ║
 * ║  hoja de cálculo. Se usa junto con DefaultTableModel,       ║
 * ║  que almacena los datos por separado (patrón MVC).          ║
 * ║                                                             ║
 * ║  Componentes clave:                                         ║
 * ║    • JTable            → la vista (la tabla visual)         ║
 * ║    • DefaultTableModel → el modelo (los datos en memoria)   ║
 * ║    • JScrollPane       → necesario para ver las cabeceras   ║
 * ║                                                             ║
 * ║  Operaciones CRUD demostradas:                              ║
 * ║    • Añadir filas      → modelo.addRow(datos)               ║
 * ║    • Eliminar filas    → modelo.removeRow(indice)           ║
 * ║    • Obtener selección → tabla.getSelectedRow()             ║
 * ║    • Leer celdas       → modelo.getValueAt(fila, col)       ║
 * ╚═════════════════════════════════════════════════════════════╝
 */
public class EjemploJTable {

    public static void launch() {

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  VENTANA PRINCIPAL                                          ║
        // ╚═════════════════════════════════════════════════════════════╝
        JFrame ventana = new JFrame("Ejemplo 11 – JTable");
        ventana.setSize(650, 480);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  DefaultTableModel: el modelo de datos                      ║
        // ║                                                             ║
        // ║  Almacena los datos en un array bidimensional interno.      ║
        // ║  Constructor: DefaultTableModel(Object[][] datos,           ║
        // ║                                  Object[] columnas)         ║
        // ║                                                             ║
        // ║  Métodos importantes:                                       ║
        // ║    addRow(Object[])       → añadir fila al final            ║
        // ║    removeRow(int)         → eliminar fila por índice        ║
        // ║    getValueAt(int, int)   → obtener valor de una celda      ║
        // ║    setValueAt(obj, f, c)  → modificar valor de una celda    ║
        // ║    getRowCount()          → número de filas actuales        ║
        // ╚═════════════════════════════════════════════════════════════╝

        // Datos iniciales de la tabla
        Object[][] datosIniciales = {
                { "001", "Portátil HP Pavilion", 749.99, 5 },
                { "002", "Ratón Logitech MX", 69.99, 25 },
                { "003", "Teclado Mecánico", 89.50, 12 },
                { "004", "Monitor 27\" 4K", 349.00, 3 },
                { "005", "Auriculares Bluetooth", 45.00, 40 }
        };
        String[] columnas = { "ID", "Producto", "Precio (€)", "Stock" };

        // Crear el modelo (NO la tabla directamente con datos)
        DefaultTableModel modelo = new DefaultTableModel(datosIniciales, columnas);

        // ╔═════════════════════════════════════════════════════════════╗
        // ║  JTable: la vista                                           ║
        // ║                                                             ║
        // ║  IMPORTANTE: JTable SIEMPRE dentro de un JScrollPane.       ║
        // ║  Sin JScrollPane, las cabeceras de columna NO se ven.       ║
        // ║                                                             ║
        // ║  La tabla muestra los datos del DefaultTableModel.          ║
        // ║  Si modificas el modelo, la tabla se actualiza sola.        ║
        // ╚═════════════════════════════════════════════════════════════╝
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setRowHeight(24);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Tabla de productos"));

        // ──────────────────────────────────────────────────────────────
        //  PANEL DE FORMULARIO: campos para añadir nuevos productos
        // ──────────────────────────────────────────────────────────────
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Añadir producto"));

        JTextField campoId = new JTextField(4);
        JTextField campoProducto = new JTextField(14);
        JTextField campoPrecio = new JTextField(6);
        JTextField campoStock = new JTextField(4);

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(campoId);
        panelFormulario.add(new JLabel("Producto:"));
        panelFormulario.add(campoProducto);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(campoPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(campoStock);

        // ──────────────────────────────────────────────────────────────
        //  PANEL DE BOTONES: acciones CRUD
        // ──────────────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));

        JButton botonAnadir = new JButton("Añadir");
        JButton botonEliminar = new JButton("Eliminar seleccionada");
        JButton botonMostrar = new JButton("Ver seleccionada");

        panelBotones.add(botonAnadir);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonMostrar);

        // — Barra de estado —
        JLabel barraEstado = new JLabel("  " + modelo.getRowCount() + " productos en la tabla");
        barraEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)));

        // ──────────────────────────────────────────────────────────────
        //  EVENTOS DE LOS BOTONES
        // ──────────────────────────────────────────────────────────────

        // Añadir fila → modelo.addRow(Object[])
        botonAnadir.addActionListener(e -> {
            String id = campoId.getText().trim();
            String producto = campoProducto.getText().trim();
            String precio = campoPrecio.getText().trim();
            String stock = campoStock.getText().trim();

            if (id.isEmpty() || producto.isEmpty()) {
                JOptionPane.showMessageDialog(ventana,
                        "Rellena al menos ID y Producto.",
                        "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // addRow recibe Object[] → cada posición es una columna
            modelo.addRow(new Object[] { id, producto, precio, stock });

            // Limpiar campos tras añadir
            campoId.setText("");
            campoProducto.setText("");
            campoPrecio.setText("");
            campoStock.setText("");
            campoId.requestFocus();

            barraEstado.setText("  ✔ Producto añadido. Total: "
                    + modelo.getRowCount() + " filas");
        });

        // Eliminar fila → modelo.removeRow(indice)
        botonEliminar.addActionListener(e -> {
            // getSelectedRow() devuelve el índice de la fila seleccionada
            // o -1 si no hay ninguna seleccionada
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(ventana,
                        "Selecciona una fila de la tabla para eliminar.",
                        "Sin selección", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Leer datos antes de eliminar (para mostrar confirmación)
            String producto = String.valueOf(modelo.getValueAt(fila, 1));
            int confirmar = JOptionPane.showConfirmDialog(ventana,
                    "¿Eliminar \"" + producto + "\"?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirmar == JOptionPane.YES_OPTION) {
                modelo.removeRow(fila);
                barraEstado.setText("  ✔ Eliminado \""
                        + producto + "\". Total: " + modelo.getRowCount() + " filas");
            }
        });

        // Ver datos de la fila seleccionada → modelo.getValueAt(fila, col)
        botonMostrar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(ventana,
                        "Selecciona una fila para ver sus datos.",
                        "Sin selección", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Leer cada celda de la fila seleccionada
            StringBuilder info = new StringBuilder();
            for (int col = 0; col < modelo.getColumnCount(); col++) {
                info.append(modelo.getColumnName(col)).append(": ")
                        .append(modelo.getValueAt(fila, col)).append("\n");
            }

            JOptionPane.showMessageDialog(ventana, info.toString(),
                    "Datos fila " + (fila + 1), JOptionPane.INFORMATION_MESSAGE);
        });

        // ──────────────────────────────────────────────────────────────
        //  MONTAJE FINAL
        // ──────────────────────────────────────────────────────────────
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelFormulario, BorderLayout.NORTH);
        panelSur.add(panelBotones, BorderLayout.CENTER);
        panelSur.add(barraEstado, BorderLayout.SOUTH);

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
