# ⚡ Guía de Eventos y Listeners

[← Volver al README](../README.md)

> Referencia completa de **todos los eventos y listeners** utilizados en los 14 ejemplos.
> Para cada listener se explica: cuándo se dispara, qué métodos tiene, la diferencia entre interfaz y adapter, y un ejemplo de código.

---

## 📋 Índice rápido

| Listener                                              | Escucha…                  | Ejemplos |
| ----------------------------------------------------- | ------------------------- | -------- |
| [ActionListener](#-actionlistener)                    | Clics en botones          | 1–14     |
| [KeyListener / KeyAdapter](#-keylistener--keyadapter) | Pulsación de teclas       | 4        |
| [MouseAdapter](#-mouseadapter)                        | Clics del ratón           | 4, App   |
| [MouseMotionAdapter](#-mousemotionadapter)            | Movimiento del ratón      | 4        |
| [FocusAdapter](#-focusadapter)                        | Ganar / perder foco       | 4        |
| [DocumentListener](#-documentlistener)                | Cambios en texto (modelo) | 5        |
| [CaretListener](#-caretlistener)                      | Posición del cursor       | 7        |
| [ItemListener](#-itemlistener)                        | Cambio de selección       | 3        |
| [ListSelectionListener](#-listselectionlistener)      | Selección en JList        | 3, App   |
| [ChangeListener](#-changelistener)                    | Cambio de valor numérico  | 3        |

---

## 🧩 Concepto clave: ¿Qué es un Listener?

En Swing, los **eventos** (clics, teclas, movimiento del ratón…) los genera el usuario al interactuar con la interfaz. Un **listener** (oyente) es un objeto que **espera** a que se produzca un evento concreto y ejecuta código cuando ocurre.

```
┌──────────┐    clic    ┌──────────────┐   dispara   ┌──────────────────┐
│  Usuario │ ─────────→ │   JButton    │ ──────────→ │  ActionListener  │
│          │            │  (fuente)    │             │  (tu código)     │
└──────────┘            └──────────────┘             └──────────────────┘
```

**Patrón Observador**: el componente (sujeto) mantiene una lista de listeners (observadores). Cuando algo ocurre, notifica a todos los listeners registrados.

---

## 🔑 Interfaz vs Adapter

Muchos listeners tienen dos formas:

| Forma                        | ¿Qué es?                                                                      | ¿Cuándo usar?                          |
| ---------------------------- | ----------------------------------------------------------------------------- | -------------------------------------- |
| **Interfaz** (`KeyListener`) | Obliga a implementar **TODOS** los métodos                                    | Cuando necesitas todos los métodos     |
| **Adapter** (`KeyAdapter`)   | Clase abstracta con métodos **vacíos** — solo sobreescribes los que necesitas | Cuando solo necesitas 1 o 2 métodos ✅ |

```java
// ❌ Interfaz → hay que implementar 3 métodos aunque solo uses 1
campo.addKeyListener(new KeyListener() {
    public void keyTyped(KeyEvent e)    { }  // vacío
    public void keyPressed(KeyEvent e)  { /* mi código */ }
    public void keyReleased(KeyEvent e) { }  // vacío
});

// ✅ Adapter → solo sobreescribes lo que necesitas
campo.addKeyListener(new KeyAdapter() {
    public void keyPressed(KeyEvent e) { /* mi código */ }
});
```

> 💡 **Regla sencilla**: si el listener tiene **más de 1 método**, usa el Adapter.

---

## 🖱 ActionListener

**El listener más usado en Swing.** Se dispara al hacer **clic** en un botón.

| Propiedad       | Valor                                     |
| --------------- | ----------------------------------------- |
| Interfaz        | `java.awt.event.ActionListener`           |
| Métodos         | `actionPerformed(ActionEvent e)` (solo 1) |
| Adapter         | No tiene (solo 1 método → no hace falta)  |
| Se registra con | `componente.addActionListener(...)`       |

### Tres formas de escribirlo (de más antigua a más moderna):

```java
// 1️⃣ Clase anónima (Java clásico)
boton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Clic");
    }
});

// 2️⃣ Lambda (Java 8+) — RECOMENDADO
boton.addActionListener(e -> System.out.println("Clic"));

// 3️⃣ Lambda multilínea
boton.addActionListener(e -> {
    String texto = campo.getText();
    area.append("Escribiste: " + texto + "\n");
});
```

> 💡 Como `ActionListener` tiene **un solo método abstracto**, es una **interfaz funcional** y admite expresiones lambda.

**Aparece en:** Todos los ejemplos (1–14).

---

## ⌨ KeyListener / KeyAdapter

Escucha **pulsaciones de teclas** cuando un componente tiene el foco.

| Propiedad       | Valor                                   |
| --------------- | --------------------------------------- |
| Interfaz        | `java.awt.event.KeyListener`            |
| Adapter         | `java.awt.event.KeyAdapter` ✅          |
| Métodos         | `keyPressed`, `keyReleased`, `keyTyped` |
| Se registra con | `componente.addKeyListener(...)`        |

### Diferencia entre los 3 métodos:

```
Tecla pulsada ──→ keyPressed  (tecla abajo)
                   keyTyped   (carácter generado, solo letras/números)
Tecla soltada ──→ keyReleased (tecla arriba)
```

### Ejemplo:

```java
campo.addKeyListener(new KeyAdapter() {
    @Override
    public void keyPressed(KeyEvent e) {
        int codigo = e.getKeyCode();
        String nombre = KeyEvent.getKeyText(codigo);
        System.out.println("Tecla: " + nombre);

        if (codigo == KeyEvent.VK_ENTER) {
            // Acción al pulsar Enter
        }
    }
});
```

### Códigos de tecla comunes:

| Constante                    | Tecla       |
| ---------------------------- | ----------- |
| `KeyEvent.VK_ENTER`          | Enter ↵     |
| `KeyEvent.VK_ESCAPE`         | Escape      |
| `KeyEvent.VK_SPACE`          | Espacio     |
| `KeyEvent.VK_BACK_SPACE`     | Retroceso ← |
| `KeyEvent.VK_UP` / `VK_DOWN` | Flechas ↑ ↓ |
| `KeyEvent.VK_A` … `VK_Z`     | Letras A–Z  |

**Aparece en:** 4.

---

## 🖱 MouseAdapter

Escucha **clics y acciones del ratón** sobre un componente.

| Propiedad       | Valor                                                                          |
| --------------- | ------------------------------------------------------------------------------ |
| Interfaz        | `java.awt.event.MouseListener`                                                 |
| Adapter         | `java.awt.event.MouseAdapter` ✅                                               |
| Métodos         | `mouseClicked`, `mousePressed`, `mouseReleased`, `mouseEntered`, `mouseExited` |
| Se registra con | `componente.addMouseListener(...)`                                             |

### Diagrama de eventos:

```
Ratón entra en el componente ──→ mouseEntered
Ratón sale del componente    ──→ mouseExited

Botón pulsado   ──→ mousePressed
Botón soltado   ──→ mouseReleased
Pulsado+Soltado ──→ mouseClicked (solo si fue en el mismo punto)
```

### Ejemplo: detectar doble clic

```java
lista.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {       // Doble clic
            System.out.println("¡Doble clic!");
        }
        if (e.getButton() == MouseEvent.BUTTON3) {  // Clic derecho
            System.out.println("Clic derecho");
        }
    }
});
```

**Aparece en:** 4, App (doble clic en la lista).

---

## 🔄 MouseMotionAdapter

Escucha **movimientos del ratón** (sin necesidad de hacer clic).

| Propiedad       | Valor                                    |
| --------------- | ---------------------------------------- |
| Interfaz        | `java.awt.event.MouseMotionListener`     |
| Adapter         | `java.awt.event.MouseMotionAdapter` ✅   |
| Métodos         | `mouseMoved`, `mouseDragged`             |
| Se registra con | `componente.addMouseMotionListener(...)` |

```
Ratón se mueve (sin pulsar)    ──→ mouseMoved
Ratón se mueve (con botón pulsado) ──→ mouseDragged
```

### Ejemplo:

```java
panel.addMouseMotionListener(new MouseMotionAdapter() {
    @Override
    public void mouseMoved(MouseEvent e) {
        barra.setText("Posición: " + e.getX() + ", " + e.getY());
    }
});
```

**Aparece en:** 4.

---

## 🎯 FocusAdapter

Escucha cuándo un componente **gana** o **pierde** el foco del teclado.

| Propiedad       | Valor                              |
| --------------- | ---------------------------------- |
| Interfaz        | `java.awt.event.FocusListener`     |
| Adapter         | `java.awt.event.FocusAdapter` ✅   |
| Métodos         | `focusGained`, `focusLost`         |
| Se registra con | `componente.addFocusListener(...)` |

```
El usuario hace clic en un campo ──→ focusGained (ese campo)
                                     focusLost (el campo anterior)
```

### Ejemplo: cambiar color cuando tiene foco

```java
campo.addFocusListener(new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        campo.setBackground(Color.YELLOW);
    }
    @Override
    public void focusLost(FocusEvent e) {
        campo.setBackground(Color.WHITE);
    }
});
```

**Aparece en:** 4.

---

## 📄 DocumentListener

Escucha **cualquier cambio** en el texto de un componente (`JTextField`, `JTextArea`). A diferencia de `KeyListener`, también detecta cambios hechos por `setText()`.

| Propiedad       | Valor                                               |
| --------------- | --------------------------------------------------- |
| Interfaz        | `javax.swing.event.DocumentListener`                |
| Adapter         | No tiene (hay que implementar los 3 métodos)        |
| Métodos         | `insertUpdate`, `removeUpdate`, `changedUpdate`     |
| Se registra con | `componente.getDocument().addDocumentListener(...)` |

### ¿Cuándo se dispara cada método?

```
El usuario escribe "abc"   ──→ insertUpdate  (3 veces, una por letra)
El usuario borra "c"       ──→ removeUpdate
Cambio de atributos        ──→ changedUpdate (poco usado en texto plano)
Se llama a setText("hola") ──→ removeUpdate + insertUpdate
```

### Ejemplo:

```java
campo.getDocument().addDocumentListener(new DocumentListener() {
    public void insertUpdate(DocumentEvent e)  { actualizar(); }
    public void removeUpdate(DocumentEvent e)  { actualizar(); }
    public void changedUpdate(DocumentEvent e)  { actualizar(); }
});
```

### DocumentListener vs KeyListener:

|                                | DocumentListener              | KeyListener                 |
| ------------------------------ | ----------------------------- | --------------------------- |
| Detecta `setText()` por código | ✅ Sí                         | ❌ No                       |
| Se registra sobre              | El **Document** (modelo)      | El **componente** (vista)   |
| Ideal para                     | Reaccionar a cambios de texto | Detectar teclas específicas |

**Aparece en:** 5.

---

## ✏ CaretListener

Escucha cambios en la **posición del cursor** (caret) dentro de un campo de texto. Se dispara al escribir, borrar, seleccionar texto o mover el cursor.

| Propiedad       | Valor                              |
| --------------- | ---------------------------------- |
| Interfaz        | `javax.swing.event.CaretListener`  |
| Métodos         | `caretUpdate(CaretEvent e)`        |
| Se registra con | `componente.addCaretListener(...)` |

```java
campo.addCaretListener(e -> {
    int posicion = e.getDot();          // Posición del cursor
    int seleccionDesde = e.getMark();   // Inicio de selección
    System.out.println("Cursor en: " + posicion);
});
```

**Aparece en:** 7.

---

## ☑ ItemListener

Escucha cambios de **selección** en `JComboBox`, `JCheckBox`, `JRadioButton`.

| Propiedad       | Valor                             |
| --------------- | --------------------------------- |
| Interfaz        | `java.awt.event.ItemListener`     |
| Métodos         | `itemStateChanged(ItemEvent e)`   |
| Se registra con | `componente.addItemListener(...)` |

```java
combo.addItemListener(e -> {
    if (e.getStateChange() == ItemEvent.SELECTED) {
        String seleccion = (String) e.getItem();
        System.out.println("Seleccionado: " + seleccion);
    }
});
```

> ⚠ `itemStateChanged` se dispara **dos veces** al cambiar selección en un combo: una para `DESELECTED` y otra para `SELECTED`. Filtra con `getStateChange()`.

**Aparece en:** 3.

---

## 📋 ListSelectionListener

Escucha cambios de **selección** en `JList` o `JTable`.

| Propiedad       | Valor                                     |
| --------------- | ----------------------------------------- |
| Interfaz        | `javax.swing.event.ListSelectionListener` |
| Métodos         | `valueChanged(ListSelectionEvent e)`      |
| Se registra con | `lista.addListSelectionListener(...)`     |

```java
lista.addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {   // Solo cuando terminó de seleccionar
        String valor = lista.getSelectedValue();
        System.out.println("Selección: " + valor);
    }
});
```

> 💡 `getValueIsAdjusting()` devuelve `true` mientras el usuario **arrastra** la selección. Solo procesa cuando devuelve `false`.

**Aparece en:** 3, App.

---

## 🔢 ChangeListener

Escucha cambios de **valor** en `JSlider`, `JSpinner` y `JProgressBar`.

| Propiedad       | Valor                               |
| --------------- | ----------------------------------- |
| Interfaz        | `javax.swing.event.ChangeListener`  |
| Métodos         | `stateChanged(ChangeEvent e)`       |
| Se registra con | `componente.addChangeListener(...)` |

```java
slider.addChangeListener(e -> {
    int valor = slider.getValue();
    barra.setValue(valor);            // Sincronizar con la barra de progreso
});
```

**Aparece en:** 3.

---

## 📊 Resumen visual: ¿Qué listener uso?

```
¿Qué quieres detectar?
│
├── Clic en botón ──────────────→ ActionListener (lambda)
├── Tecla pulsada ──────────────→ KeyAdapter
├── Clic del ratón ─────────────→ MouseAdapter
├── Movimiento del ratón ───────→ MouseMotionAdapter
├── Ganar/perder foco ──────────→ FocusAdapter
├── Cambio en texto ────────────→ DocumentListener
├── Posición del cursor ────────→ CaretListener
├── Selección en combo/check ───→ ItemListener
├── Selección en lista ─────────→ ListSelectionListener
└── Cambio en slider/spinner ───→ ChangeListener
```

---

[← Componentes Swing](guia-componentes.md) · [Layouts →](guia-layouts.md)
