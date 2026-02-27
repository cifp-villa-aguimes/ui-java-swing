# 📦 Guía de Componentes Swing

[← Volver al README](../README.md)

> Referencia visual de **todos los componentes Swing** utilizados en los 14 ejemplos del proyecto.
> Para cada componente se indica: qué es, para qué sirve, cómo se crea y en qué ejemplos aparece.

---

## 📋 Índice rápido

| Categoría                                  | Componentes                                           |
| ------------------------------------------ | ----------------------------------------------------- |
| [Contenedores](#-contenedores)             | JFrame · JPanel · JScrollPane · JSplitPane            |
| [Texto](#-componentes-de-texto)            | JLabel · JTextField · JPasswordField · JTextArea      |
| [Botones](#-botones-y-selección)           | JButton · JCheckBox · JRadioButton · ButtonGroup      |
| [Selección y ajuste](#-selección-y-ajuste) | JComboBox · JList · JSlider · JSpinner · JProgressBar |
| [Datos tabulares](#-datos-tabulares)       | JTable · DefaultTableModel                            |
| [Diálogos](#-diálogos)                     | JOptionPane · JFileChooser                            |
| [Decoración](#-decoración-y-bordes)        | JSeparator · BorderFactory                            |

---

## 📦 Contenedores

Los contenedores son componentes que **contienen otros componentes**. Todo lo que ves en una ventana Swing vive dentro de un contenedor.

---

### `JFrame` — La ventana principal

```
┌──────────────── JFrame ──────────────────┐
│  ┌─ Barra de título ─────────── [–][□][×]│
│  │  Mi Aplicación                        │
│  ├───────────────────────────────────────│
│  │                                       │
│  │     (aquí va el contenido)            │
│  │                                       │
│  └───────────────────────────────────────│
└──────────────────────────────────────────┘
```

**¿Qué es?** La ventana de nivel superior. Toda aplicación Swing tiene al menos un `JFrame`.

```java
JFrame frame = new JFrame("Título de la ventana");
frame.setSize(600, 400);                         // Ancho × alto en píxeles
frame.setLocationRelativeTo(null);               // Centrar en pantalla
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar → salir
frame.setVisible(true);                          // Mostrar (siempre al final)
```

> **`DISPOSE_ON_CLOSE`** vs **`EXIT_ON_CLOSE`**:
>
> - `EXIT_ON_CLOSE` → cierra **toda** la aplicación (para la ventana principal).
> - `DISPOSE_ON_CLOSE` → cierra **solo esa ventana** (para ventanas secundarias).

| Aparece en                       | Ejemplos                                  |
| -------------------------------- | ----------------------------------------- |
| Como composición                 | 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14 |
| Como herencia (`extends JFrame`) | **12**                                    |

---

### `JPanel` — Panel contenedor

**¿Qué es?** Un contenedor "invisible" que agrupa componentes. Puedes anidar paneles dentro de paneles para crear layouts complejos.

```java
JPanel panel = new JPanel();                      // FlowLayout por defecto
JPanel panel = new JPanel(new BorderLayout());    // Con layout específico
panel.setBorder(BorderFactory.createTitledBorder("Título"));
panel.add(boton);
```

**Aparece en:** Todos los ejemplos (1–14).

---

### `JScrollPane` — Panel con scroll

**¿Qué es?** Envuelve un componente y le añade barras de desplazamiento cuando el contenido no cabe.

```java
JTextArea area = new JTextArea(10, 30);
JScrollPane scroll = new JScrollPane(area);  // Scroll automático
```

> 💡 Casi siempre se usa con `JTextArea`, `JList` y `JTable`.

**Aparece en:** 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14.

---

### `JSplitPane` — Panel dividido

**¿Qué es?** Divide el espacio en dos zonas con una barra arrastrable.

```
 VERTICAL_SPLIT (barra horizontal):     HORIZONTAL_SPLIT (barra vertical):
┌────────────────────────┐              ┌───────────┬────────────┐
│     Zona superior      │              │           │            │
├════════════════════════┤              │   Zona    │    Zona    │
│     Zona inferior      │              │   izq.    │    dcha.   │
└────────────────────────┘              └───────────┴────────────┘
```

```java
JSplitPane split = new JSplitPane(
    JSplitPane.VERTICAL_SPLIT,   // Dirección de la división
    panelArriba,                 // Primer componente
    panelAbajo                   // Segundo componente
);
split.setResizeWeight(0.6);      // 60% espacio para el primero
```

**Aparece en:** App, 4, 5.

---

## 📝 Componentes de texto

---

### `JLabel` — Etiqueta de texto

**¿Qué es?** Texto estático (no editable) para mostrar información o etiquetar campos.

```java
JLabel etiqueta = new JLabel("Nombre:");
JLabel titulo = new JLabel("Hola", SwingConstants.CENTER); // Centrado
etiqueta.setFont(new Font("SansSerif", Font.BOLD, 14));
```

**Aparece en:** Todos los ejemplos (1–14).

---

### `JTextField` — Campo de texto (una línea)

**¿Qué es?** Campo donde el usuario escribe texto en una sola línea.

```java
JTextField campo = new JTextField(20);           // 20 columnas de ancho
JTextField campo = new JTextField("Valor inicial");
String texto = campo.getText();                  // Obtener lo que escribió
campo.setText("Nuevo valor");                    // Cambiar el texto
```

**Aparece en:** 1, 2, 4, 5, 6, 7, 8, 9, 11, 12.

---

### `JPasswordField` — Campo de contraseña

**¿Qué es?** Como `JTextField`, pero oculta los caracteres escritos (muestra `●●●●`).

```java
JPasswordField campoPass = new JPasswordField(15);
char[] password = campoPass.getPassword();  // ⚠ Devuelve char[], no String
```

> ⚠ **¿Por qué `char[]` y no `String`?** Por seguridad: un `char[]` se puede borrar de memoria manualmente (`Arrays.fill(password, '\0')`), mientras que un `String` queda en memoria hasta que el Garbage Collector lo recoge.

**Aparece en:** 2.

---

### `JTextArea` — Área de texto (múltiples líneas)

**¿Qué es?** Zona de texto multilínea, editable o de solo lectura (para logs, contenido, etc.).

```java
JTextArea area = new JTextArea(5, 30);         // 5 filas × 30 columnas
area.setLineWrap(true);                        // Salto de línea automático
area.setWrapStyleWord(true);                   // Cortar por palabras
area.setEditable(false);                       // Solo lectura (para logs)
area.append("Nueva línea de texto\n");         // Añadir al final
```

> 💡 Siempre envuélvelo en un `JScrollPane` para que aparezcan las barras de scroll.

**Aparece en:** 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14.

---

## 🔘 Botones y selección

---

### `JButton` — Botón pulsable

**¿Qué es?** El componente de interacción más básico. El usuario hace clic y se ejecuta una acción.

```java
JButton boton = new JButton("Aceptar");
boton.addActionListener(e -> System.out.println("¡Clic!"));
```

**Aparece en:** Todos los ejemplos (1–14).

---

### `JCheckBox` — Casilla de verificación

**¿Qué es?** Casilla que se marca/desmarca. Permite seleccionar **varias** opciones a la vez.

```java
JCheckBox check = new JCheckBox("Acepto los términos", false);
boolean marcado = check.isSelected();  // true si está marcado
```

```
☑ Opción A
☐ Opción B    ← Se pueden marcar varias
☑ Opción C
```

**Aparece en:** 2, 13, 14.

---

### `JRadioButton` + `ButtonGroup` — Selección exclusiva

**¿Qué es?** Botones circulares donde solo **uno** puede estar seleccionado a la vez. Necesitan un `ButtonGroup` para funcionar.

```java
JRadioButton opcionA = new JRadioButton("Opción A", true);  // Seleccionado
JRadioButton opcionB = new JRadioButton("Opción B");

ButtonGroup grupo = new ButtonGroup();  // ← Hace la exclusión mutua
grupo.add(opcionA);
grupo.add(opcionB);
```

```
◉ Opción A    ← Solo una puede estar activa
○ Opción B
```

**Aparece en:** 2, 13.

---

## 🎚 Selección y ajuste

---

### `JComboBox` — Lista desplegable

**¿Qué es?** Un "dropdown" que muestra una lista de opciones al hacer clic.

```java
String[] opciones = {"Rojo", "Verde", "Azul"};
JComboBox<String> combo = new JComboBox<>(opciones);
String seleccion = (String) combo.getSelectedItem();
```

**Aparece en:** 3.

---

### `JList` — Lista visible

**¿Qué es?** Muestra una lista de elementos visibles (sin desplegable). Se puede seleccionar uno o varios.

```java
String[] items = {"Manzana", "Pera", "Naranja"};
JList<String> lista = new JList<>(items);
lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
```

> 💡 Para listas dinámicas (añadir/quitar elementos), usa `DefaultListModel`:
>
> ```java
> DefaultListModel<String> modelo = new DefaultListModel<>();
> modelo.addElement("Nuevo item");
> JList<String> lista = new JList<>(modelo);
> ```

**Aparece en:** 3, 5, App.

---

### `JSlider` — Barra deslizante

**¿Qué es?** Permite seleccionar un valor numérico arrastrando un marcador.

```java
JSlider slider = new JSlider(0, 100, 50);    // mín, máx, valor inicial
slider.setMajorTickSpacing(25);              // Marcas grandes cada 25
slider.setPaintTicks(true);                  // Mostrar las marcas
slider.setPaintLabels(true);                 // Mostrar los números
```

```
|-------|-------|-------|-------|
0       25      50      75     100
                 ▲
```

**Aparece en:** 3, 13.

---

### `JSpinner` — Selector numérico con flechas

**¿Qué es?** Campo numérico con flechas ▲ ▼ para incrementar/decrementar.

```java
SpinnerNumberModel modelo = new SpinnerNumberModel(5, 0, 100, 1);
//                                      valor, mín, máx, paso
JSpinner spinner = new JSpinner(modelo);
int valor = (int) spinner.getValue();
```

**Aparece en:** 3.

---

### `JProgressBar` — Barra de progreso

**¿Qué es?** Muestra un progreso visual (de 0 a N).

```java
JProgressBar barra = new JProgressBar(0, 100);
barra.setValue(75);
barra.setStringPainted(true);  // Muestra "75%" dentro de la barra
```

```
[████████████████████░░░░░░] 75%
```

**Aparece en:** 3, 13.

---

## 📊 Datos tabulares

---

### `JTable` + `DefaultTableModel` — Tabla de datos

**¿Qué es?** Muestra datos en filas y columnas, como una hoja de cálculo.

```java
String[] columnas = {"Nombre", "Precio", "Stock"};
Object[][] datos = {
    {"Manzana", 1.50, 100},
    {"Pera", 2.00, 50}
};

DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
JTable tabla = new JTable(modelo);

// Operaciones CRUD:
modelo.addRow(new Object[]{"Naranja", 1.80, 75});   // Crear
modelo.getValueAt(0, 0);                             // Leer
modelo.removeRow(0);                                 // Eliminar
```

> 💡 `JTable` usa el patrón **MVC** (Modelo-Vista-Controlador):
>
> - **Modelo** → `DefaultTableModel` (los datos)
> - **Vista** → `JTable` (la representación visual)

**Aparece en:** 11.

---

## 💬 Diálogos

---

### `JOptionPane` — Diálogos emergentes

**¿Qué es?** Ventanas emergentes predefinidas para mensajes, confirmaciones y entrada de datos.

```java
// Mensaje informativo
JOptionPane.showMessageDialog(frame, "Operación completada");

// Confirmación (Sí / No)
int respuesta = JOptionPane.showConfirmDialog(frame,
    "¿Estás seguro?", "Confirmar", JOptionPane.YES_NO_OPTION);
if (respuesta == JOptionPane.YES_OPTION) { ... }

// Pedir texto al usuario
String nombre = JOptionPane.showInputDialog(frame, "Tu nombre:");
```

**Aparece en:** 4, 6, 7, 10, 11, 12.

---

### `JFileChooser` — Diálogo de archivos

**¿Qué es?** Abre el explorador de archivos nativo del sistema operativo.

```java
JFileChooser fileChooser = new JFileChooser();
fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));

// Abrir archivo
int resultado = fileChooser.showOpenDialog(frame);
if (resultado == JFileChooser.APPROVE_OPTION) {
    File archivo = fileChooser.getSelectedFile();
}

// Guardar archivo
int resultado = fileChooser.showSaveDialog(frame);
```

**Aparece en:** 10.

---

## 🎨 Decoración y bordes

---

### `JSeparator` — Línea decorativa

**¿Qué es?** Una línea horizontal o vertical para separar visualmente secciones.

```java
panel.add(new JSeparator());  // Línea horizontal
```

**Aparece en:** 1.

---

### `BorderFactory` — Fábrica de bordes

**¿Qué es?** Clase utilitaria que crea distintos tipos de bordes para paneles.

```java
// Borde con título
panel.setBorder(BorderFactory.createTitledBorder("Datos"));

// Margen interno (padding)
panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

// Borde combinado (compound)
panel.setBorder(BorderFactory.createCompoundBorder(
    BorderFactory.createTitledBorder("Exterior"),
    BorderFactory.createEmptyBorder(5, 5, 5, 5)
));
```

**Aparece en:** 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14.

---

## 🔗 Jerarquía visual

Todo componente Swing forma parte de un árbol de contenedores:

```
JFrame
 └── JPanel (panelPrincipal, con BorderLayout)
      ├── NORTH → JLabel (título)
      ├── CENTER → JSplitPane
      │             ├── JScrollPane → JList
      │             └── JScrollPane → JTextArea
      └── SOUTH → JPanel
                   └── JButton
```

> 💡 Consejo: si tu interfaz no se ve como esperas, dibuja primero el árbol de contenedores en papel.

---

[← Volver al README](../README.md) · [Eventos y Listeners →](guia-eventos.md)
