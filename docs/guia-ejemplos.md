# 🔍 Detalle de cada ejemplo

[← Volver al README](../README.md)

> Ficha completa de cada uno de los **14 ejemplos** del proyecto. Para cada uno se incluye:
> qué demuestra, componentes Swing usados, eventos/listeners, layouts, APIs Java clave y fragmentos de código destacados.

---

## 📋 Índice

| Bloque                                                                  | Ejemplos        |
| ----------------------------------------------------------------------- | --------------- |
| [Primeros pasos](#bloque-1--primeros-pasos)                             | 1 · 2 · 3       |
| [Eventos y comunicación](#bloque-2--eventos-y-comunicación)             | 4 · 5 · 6 · 7   |
| [Ficheros y datos](#bloque-3--ficheros-y-datos)                         | 8 · 9 · 10 · 11 |
| [Apariencia y personalización](#bloque-4--apariencia-y-personalización) | 12 · 13 · 14    |

---

## Bloque 1 — Primeros pasos

---

### Ejemplo 1 · GUI Básica

> 📁 `src/ejemplos/guibasica/EjemploGuiBasica.java`

**¿Qué demuestra?** Los fundamentos de Swing: crear una ventana, colocar componentes manualmente y reaccionar a clics con `ActionListener`.

|                    | Detalle                                                                                            |
| ------------------ | -------------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton` ×3, `JTextArea`, `JScrollPane`, `JSeparator` |
| ⚡ **Eventos**     | `ActionListener` (clases anónimas)                                                                 |
| 📐 **Layout**      | `null` (posicionamiento manual con `setBounds`)                                                    |

**Conceptos clave introducidos:**

- `ActionListener` como **clase anónima** (el enfoque clásico pre-lambda)
- `JFrame` con `DISPOSE_ON_CLOSE` (ventana secundaria)
- `JScrollPane` envolviendo un `JTextArea`
- `SwingUtilities.getWindowAncestor()` para cerrar la ventana desde un botón

**Fragmento destacado — Clase anónima:**

```java
botonSaludar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String nombre = campoNombre.getText().trim();
        areaResultado.append("¡Hola, " + nombre + "!\n");
    }
});
```

---

### Ejemplo 2 · Componentes Básicos

> 📁 `src/ejemplos/componentesbasicos/EjemploComponentesBasicos.java`

**¿Qué demuestra?** Los componentes de entrada más comunes y la evolución de clases anónimas a **expresiones lambda**.

|                    | Detalle                                                                                                                                         |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JPasswordField`, `JCheckBox`, `JRadioButton`, `ButtonGroup`, `JButton`, `JTextArea`, `JScrollPane` |
| ⚡ **Eventos**     | `ActionListener` (**lambdas**)                                                                                                                  |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout`                                                                                                                   |

**Conceptos clave introducidos:**

- **Expresiones lambda** para `ActionListener` (`e -> { ... }`)
- `JPasswordField.getPassword()` → devuelve `char[]` por seguridad
- `ButtonGroup` para exclusión mutua entre `JRadioButton`
- `BorderFactory.createTitledBorder()` para bordes con título

**Fragmento destacado — Lambda:**

```java
// Antes (clase anónima):
boton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) { ... }
});

// Después (lambda):
boton.addActionListener(e -> {
    String texto = campo.getText();
    area.append(texto + "\n");
});
```

---

### Ejemplo 3 · Componentes Avanzados

> 📁 `src/ejemplos/componentesavanzados/EjemploComponentesAvanzados.java`

**¿Qué demuestra?** Componentes de selección y ajuste con **eventos en tiempo real**: cada cambio se refleja instantáneamente en un log.

|                    | Detalle                                                                                                                                      |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JComboBox`, `JList`, `JSlider`, `JSpinner` (`SpinnerNumberModel`), `JProgressBar`, `JTextArea`, `JScrollPane` |
| ⚡ **Eventos**     | `ItemListener`, `ListSelectionListener`, `ChangeListener`                                                                                    |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout` + `GridLayout`                                                                                                 |

**Conceptos clave introducidos:**

- Cada componente tiene su **listener específico** (no todo es `ActionListener`)
- `JSlider` sincronizado con `JProgressBar` vía `ChangeListener`
- `getValueIsAdjusting()` para evitar dobles notificaciones
- `SpinnerNumberModel` con valor, mínimo, máximo y paso

**Mapa de componente → listener:**

```
JComboBox   ──→ ItemListener         (itemStateChanged)
JList       ──→ ListSelectionListener (valueChanged)
JSlider     ──→ ChangeListener        (stateChanged)
JSpinner    ──→ ChangeListener        (stateChanged)
```

---

## Bloque 2 — Eventos y comunicación

---

### Ejemplo 4 · Eventos Swing

> 📁 `src/ejemplos/eventos/EjemploEventosSwing.java`

**¿Qué demuestra?** Los 5 tipos principales de eventos en Swing, con un log en tiempo real que muestra cada evento conforme ocurre.

|                    | Detalle                                                                                         |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JTextArea`, `JScrollPane`, `JSplitPane` |
| ⚡ **Eventos**     | `ActionListener` (lambda), `KeyAdapter`, `MouseAdapter`, `MouseMotionAdapter`, `FocusAdapter`   |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout`                                                                   |

**Conceptos clave introducidos:**

- Diferencia entre **interfaz** (`KeyListener`) y **clase adaptadora** (`KeyAdapter`)
- El ejemplo incluye ambas opciones comentadas (Opción A vs Opción B) para comparar
- `doClick()` — simular un clic por código (principio DRY)
- `Cursor.getPredefinedCursor()` para cambiar el cursor del ratón
- Barra de estado que muestra la posición del ratón en tiempo real

**Los 5 listeners del ejemplo:**

```
1. ActionListener      → clic en botón (Opción A: clase anónima / Opción B: lambda)
2. KeyAdapter          → al pulsar tecla en el campo de texto
3. MouseAdapter        → clic, entrada y salida del ratón sobre el panel
4. MouseMotionAdapter  → movimiento y arrastre del ratón
5. FocusAdapter        → cuando el campo gana/pierde el foco
```

---

### Ejemplo 5 · DocumentListener

> 📁 `src/ejemplos/documentlistener/EjemploDocumentListener.java`

**¿Qué demuestra?** Escuchar cambios en un campo de texto **en tiempo real**, implementado como un filtro de búsqueda de países.

|                    | Detalle                                                                                                                       |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JList` (`DefaultListModel`), `JTextArea`, `JScrollPane`, `JSplitPane` |
| ⚡ **Eventos**     | `DocumentListener`, `ActionListener` (lambda)                                                                                 |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout`                                                                                                 |

**Conceptos clave introducidos:**

- `DocumentListener` con sus 3 métodos: `insertUpdate`, `removeUpdate`, `changedUpdate`
- Se escucha el **Document** (modelo), no el componente (vista)
- `setText()` **también** dispara DocumentListener (a diferencia de `KeyListener`)
- `DefaultListModel` para listas dinámicas
- Contador de caracteres con feedback de color

**DocumentListener vs KeyListener:**

```
             ┌─ insertUpdate (al escribir)
Document ────┤─ removeUpdate  (al borrar)
  Listener   └─ changedUpdate (cambio de atributos)

                  ¿Detecta setText()? → ✅ SÍ

             ┌─ keyPressed  (tecla abajo)
Key      ────┤─ keyReleased (tecla arriba)
  Listener   └─ keyTyped    (carácter generado)

                  ¿Detecta setText()? → ❌ NO
```

---

### Ejemplo 6 · Comunicación entre Paneles (referencia directa)

> 📁 `src/ejemplos/panelcomunicacion/PanelComunicacion.java`

**¿Qué demuestra?** Cómo dos paneles pueden comunicarse **pasando la referencia** de un componente compartido (`JTextField`).

|                    | Detalle                                                                              |
| ------------------ | ------------------------------------------------------------------------------------ |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton` ×4, `JTextArea`, `JScrollPane` |
| ⚡ **Eventos**     | `ActionListener` (lambda)                                                            |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout`                                                        |

**Conceptos clave introducidos:**

- Comunicación **acoplada**: un panel recibe directamente el `JTextField` del otro
- 4 acciones sobre el texto compartido: mostrar, mayúsculas, limpiar, contar caracteres
- `JOptionPane.showMessageDialog()` para diálogos informativos
- Log de operaciones

**Diagrama de comunicación:**

```
┌─────────────────┐          ┌─────────────────┐
│  Panel Superior  │          │  Panel Inferior  │
│                 │          │                 │
│  JTextField ────────────────→ (lee/modifica  │
│  (campo)       │  ref.     │  directamente)  │
└─────────────────┘          └─────────────────┘
```

---

### Ejemplo 7 · Comunicación entre Paneles (modelo compartido)

> 📁 `src/ejemplos/panelcomunicacion/PanelComunicacionSimple.java`

**¿Qué demuestra?** Comunicación **desacoplada** entre paneles usando un objeto modelo compartido (`DatosCompartidos`).

|                    | Detalle                                                                           |
| ------------------ | --------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JTextArea`, `JScrollPane` |
| ⚡ **Eventos**     | `CaretListener`, `ActionListener` (lambda)                                        |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout`                                                     |

**Conceptos clave introducidos:**

- Clase `DatosCompartidos` como modelo (getter/setter)
- Los paneles no se conocen entre sí — solo conocen el modelo
- `CaretListener` para sincronizar el texto al escribir
- Patrón similar a **MVC** (Modelo-Vista-Controlador)

**Diagrama de comunicación:**

```
┌──────────────┐      ┌──────────────────┐      ┌──────────────┐
│ Panel Entrada │      │ DatosCompartidos │      │ Panel Acciones│
│              │─────→│                  │←─────│              │
│ escribe texto│ set  │  texto: String   │  get │ lee el texto │
└──────────────┘      └──────────────────┘      └──────────────┘

Los paneles NO se conocen entre sí. Solo conocen el modelo.
```

---

## Bloque 3 — Ficheros y datos

---

### Ejemplo 8 · Leer Archivo de Texto

> 📁 `src/ejemplos/archivos/EjemploLeerArchivo.java`

**¿Qué demuestra?** Leer un fichero del disco y mostrar su contenido en un `JTextArea`, usando la API `java.nio.file`.

|                    | Detalle                                                                           |
| ------------------ | --------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JTextArea`, `JScrollPane` |
| ⚡ **Eventos**     | `ActionListener` (lambda)                                                         |
| 📐 **Layout**      | `BorderLayout`                                                                    |
| 🔧 **APIs Java**   | `Paths.get()`, `Files.readAllLines()`, `Files.exists()`, `String.join()`          |

**Conceptos clave introducidos:**

- `Files.readAllLines()` devuelve `List<String>` (una línea por elemento)
- 3 alternativas de recorrido comentadas en el código: `for-each`, `String.join()`, `Files.readString()`
- Comprobación de existencia con `Files.exists()`
- Ruta configurable desde un `JTextField`
- Carga automática al abrir la ventana

---

### Ejemplo 9 · Escribir Archivo de Texto

> 📁 `src/ejemplos/archivos/EjemploEscribirArchivo.java`

**¿Qué demuestra?** Guardar el contenido de un `JTextArea` en un fichero en disco.

|                    | Detalle                                                                           |
| ------------------ | --------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton`, `JTextArea`, `JScrollPane` |
| ⚡ **Eventos**     | `ActionListener` (lambda)                                                         |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout`                                                     |
| 🔧 **APIs Java**   | `BufferedWriter`, `FileWriter`, `Paths.get().toAbsolutePath()`                    |

**Conceptos clave introducidos:**

- **try-with-resources** → cierra automáticamente el `BufferedWriter`
- Contraste entre API clásica (`BufferedWriter`+`FileWriter`) y moderna (`Files.writeString()`)
- El código activo usa la API clásica; la moderna aparece como alternativa comentada
- Barra de estado con confirmación de escritura

**try-with-resources explicado:**

```java
// El BufferedWriter se cierra automáticamente al salir del try,
// incluso si hay una excepción.
try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
    bw.write(contenido);
}  // ← Aquí se ejecuta bw.close() automáticamente
```

---

### Ejemplo 10 · JFileChooser

> 📁 `src/ejemplos/archivos/EjemploJFileChooser.java`

**¿Qué demuestra?** Abrir los diálogos nativos del sistema operativo para abrir y guardar archivos.

|                    | Detalle                                                                                         |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JButton` ×2, `JTextArea`, `JScrollPane`, `JFileChooser`          |
| ⚡ **Eventos**     | `ActionListener` (lambda)                                                                       |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout`                                                                   |
| 🔧 **APIs Java**   | `JFileChooser`, `FileNameExtensionFilter`, `BufferedReader`+`FileReader`, `Files.writeString()` |

**Conceptos clave introducidos:**

- `showOpenDialog()` → diálogo para **abrir** (seleccionar un archivo existente)
- `showSaveDialog()` → diálogo para **guardar** (elegir nombre y ubicación)
- `FileNameExtensionFilter` → filtrar por extensiones (`.txt`, `.java`, etc.)
- Confirmación antes de sobrescribir un archivo existente

**Flujo del diálogo:**

```
showOpenDialog(frame)
  │
  ├── APPROVE_OPTION → El usuario eligió un archivo → getSelectedFile()
  └── CANCEL_OPTION  → El usuario canceló → no hacer nada
```

---

### Ejemplo 11 · JTable

> 📁 `src/ejemplos/jtable/EjemploJTable.java`

**¿Qué demuestra?** Tabla de datos con operaciones CRUD (Crear, Leer, Eliminar) usando `JTable` + `DefaultTableModel`.

|                    | Detalle                                                                              |
| ------------------ | ------------------------------------------------------------------------------------ |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField` ×3, `JButton` ×2, `JTable`, `JScrollPane` |
| ⚡ **Eventos**     | `ActionListener` (lambda)                                                            |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout`                                                        |
| 🔧 **APIs Java**   | `DefaultTableModel`, `addRow()`, `removeRow()`, `getSelectedRow()`                   |

**Conceptos clave introducidos:**

- **Patrón MVC**: `DefaultTableModel` (modelo) + `JTable` (vista)
- Formulario para añadir nuevos productos (nombre, precio, stock)
- Eliminación con selección + `JOptionPane.showConfirmDialog()`
- `SINGLE_SELECTION` para garantizar que solo se selecciona una fila
- Muestra datos de la fila seleccionada en la barra de estado

**Operaciones CRUD:**

```
Crear  → modelo.addRow(new Object[]{"Producto", 9.99, 50})
Leer   → modelo.getValueAt(fila, columna)
Eliminar → modelo.removeRow(fila)
```

---

## Bloque 4 — Apariencia y personalización

---

### Ejemplo 12 · File Organizer App

> 📁 `src/ejemplos/fileorganizer/EjemploFileOrganizerApp.java`

**¿Qué demuestra?** Crear estructuras de directorios y ficheros en disco, con un formulario construido con `GridBagLayout`.

|                    | Detalle                                                                                               |
| ------------------ | ----------------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame` (herencia), `JPanel`, `JLabel`, `JTextField` ×3, `JButton` ×2, `JTextArea` ×2, `JScrollPane` |
| ⚡ **Eventos**     | `ActionListener` (lambda)                                                                             |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout` + **`GridBagLayout`**                                                   |
| 🔧 **APIs Java**   | `Files.createDirectories()`, `Files.writeString()`, `Paths.get()`, `Path.resolve()`                   |

**Conceptos clave introducidos:**

- **`extends JFrame`** → la clase hereda de JFrame (alternativa a composición)
- `GridBagLayout` + `GridBagConstraints` para formularios complejos
- `Files.createDirectories()` crea carpetas intermedias (como `mkdir -p`)
- `Path.resolve()` para unir directorio + nombre de fichero
- `SwingUtilities.invokeLater()` en el `main()`

**Composición vs Herencia:**

```
Composición (Ej. 1-11, 13-14):     Herencia (Ej. 12):
  JFrame frame = new JFrame();       class MiApp extends JFrame {
  frame.add(panel);                      this.add(panel);
  frame.setVisible(true);               setVisible(true);
                                     }
```

---

### Ejemplo 13 · Look & Feel

> 📁 `src/ejemplos/lookandfeel/EjemploLookAndFeel.java`

**¿Qué demuestra?** Cambiar la apariencia completa de la aplicación en tiempo de ejecución.

|                    | Detalle                                                                                                                                        |
| ------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton` ×5, `JCheckBox`, `JRadioButton`, `ButtonGroup`, `JSlider`, `JProgressBar`, `JScrollPane` |
| ⚡ **Eventos**     | `ActionListener` (lambda)                                                                                                                      |
| 📐 **Layout**      | `BorderLayout` + `GridLayout`                                                                                                                  |
| 🔧 **APIs Java**   | `UIManager.setLookAndFeel()`, `SwingUtilities.updateComponentTreeUI()`, `Class.forName()`                                                      |

**Conceptos clave introducidos:**

- 5 Look & Feels: Metal (clásico), Nimbus (moderno), Motif (retro), Windows, Mac
- L&Fs no disponibles en el SO se desactivan automáticamente (`Class.forName()`)
- **Cambio en caliente**: `setLookAndFeel()` + `updateComponentTreeUI(frame)`
- Panel de vista previa con componentes variados para ver el efecto del L&F

**Los 5 Look & Feels:**

```
┌──────────────────┬──────────────────────────────────────────────┐
│  Metal           │  El clásico "Java". Disponible en todos.     │
│  Nimbus          │  Moderno con degradados. Desde Java 6u10.    │
│  Motif / CDE     │  Aspecto retro Unix/Solaris.                │
│  Windows         │  Solo en Windows.                           │
│  Mac (Aqua)      │  Solo en macOS.                             │
└──────────────────┴──────────────────────────────────────────────┘
```

---

### Ejemplo 14 · UIManager Personalizado

> 📁 `src/ejemplos/lookandfeel/EjemploUIManagerPersonalizado.java`

**¿Qué demuestra?** Personalizar globalmente colores, fuentes y estilos de componentes Swing con `UIManager.put()`.

|                    | Detalle                                                                                                                                |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------------------- |
| 📦 **Componentes** | `JFrame`, `JPanel`, `JLabel`, `JTextField`, `JButton` ×2, `JCheckBox`, `JTextArea`, `JScrollPane`                                      |
| ⚡ **Eventos**     | `ActionListener` (lambda)                                                                                                              |
| 📐 **Layout**      | `BorderLayout` + `FlowLayout` + `GridLayout`                                                                                           |
| 🔧 **APIs Java**   | `UIManager.put()`, `UIManager.getSystemLookAndFeelClassName()`, `UIManager.setLookAndFeel()`, `SwingUtilities.updateComponentTreeUI()` |

**Conceptos clave introducidos:**

- `UIManager.put("clave", valor)` como diccionario global de estilos
- Se aplica **antes de crear** los componentes (importante)
- L&F del sistema como base + personalizaciones encima
- Botón para restaurar a Metal y ver la diferencia

**Propiedades personalizadas en el ejemplo:**

```
Button.background   → Color(52, 73, 94)    (fondo oscuro)
Button.foreground   → Color.WHITE           (texto blanco)
Button.font         → SansSerif Bold 13
Button.margin       → Insets(8, 16, 8, 16)
Label.font          → SansSerif Plain 13
Label.foreground    → Color(44, 62, 80)
TextField.font      → SansSerif Plain 13
TextField.background→ Color(236, 240, 241)
Panel.background    → Color(245, 248, 250)
ToolTip.background  → Color(255, 255, 225)
ToolTip.font        → SansSerif Italic 12
```

---

## 📊 Matriz resumen: Componentes × Ejemplo

|                | 1   | 2   | 3   | 4   | 5   | 6   | 7   | 8   | 9   | 10  | 11  | 12  | 13  | 14  |
| -------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| JFrame         | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  |
| JPanel         | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  |
| JLabel         | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  |
| JButton        | ✅  | ✅  |     | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  |
| JTextField     | ✅  | ✅  |     | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  |     | ✅  | ✅  | ✅  | ✅  |
| JTextArea      | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  |     | ✅  |     | ✅  |
| JScrollPane    | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  |
| JPasswordField |     | ✅  |     |     |     |     |     |     |     |     |     |     |     |     |
| JCheckBox      |     | ✅  |     |     |     |     |     |     |     |     |     |     | ✅  | ✅  |
| JRadioButton   |     | ✅  |     |     |     |     |     |     |     |     |     |     | ✅  |     |
| JComboBox      |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |     |
| JList          |     |     | ✅  |     | ✅  |     |     |     |     |     |     |     |     |     |
| JSlider        |     |     | ✅  |     |     |     |     |     |     |     |     |     | ✅  |     |
| JSpinner       |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |     |
| JProgressBar   |     |     | ✅  |     |     |     |     |     |     |     |     |     | ✅  |     |
| JTable         |     |     |     |     |     |     |     |     |     |     | ✅  |     |     |     |
| JSplitPane     |     |     |     | ✅  | ✅  |     |     |     |     |     |     |     |     |     |
| JFileChooser   |     |     |     |     |     |     |     |     |     | ✅  |     |     |     |     |
| JSeparator     | ✅  |     |     |     |     |     |     |     |     |     |     |     |     |     |

---

## 📊 Matriz resumen: Listeners × Ejemplo

|                       | 1   | 2   | 3   | 4   | 5   | 6   | 7   | 8   | 9   | 10  | 11  | 12  | 13  | 14  |
| --------------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| ActionListener        | ✅  | ✅  |     | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  | ✅  |
| KeyAdapter            |     |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |
| MouseAdapter          |     |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |
| MouseMotionAdapter    |     |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |
| FocusAdapter          |     |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |
| DocumentListener      |     |     |     |     | ✅  |     |     |     |     |     |     |     |     |     |
| CaretListener         |     |     |     |     |     |     | ✅  |     |     |     |     |     |     |     |
| ItemListener          |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |     |
| ListSelectionListener |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |     |
| ChangeListener        |     |     | ✅  |     |     |     |     |     |     |     |     |     |     |     |

---

[← Layouts](guia-layouts.md) · [Volver al README](../README.md)
