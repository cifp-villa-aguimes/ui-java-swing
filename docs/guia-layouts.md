# 📐 Guía de Layout Managers

[← Volver al README](../README.md)

> Un **Layout Manager** es el encargado de decidir **dónde** y con **qué tamaño** se colocan los componentes dentro de un contenedor (`JPanel`, `JFrame`…).
>
> En este proyecto se utilizan **5 layout managers** distintos. Aquí se explica cada uno con diagramas y ejemplos de código.

---

## 📋 Índice

| Layout                                               | Idea básica                                   | Ejemplos               |
| ---------------------------------------------------- | --------------------------------------------- | ---------------------- |
| [null (sin layout)](#1-null--posicionamiento-manual) | Tú decides la posición exacta con `setBounds` | 1                      |
| [BorderLayout](#2-borderlayout)                      | 5 zonas: Norte, Sur, Este, Oeste, Centro      | 2–14                   |
| [FlowLayout](#3-flowlayout)                          | Componentes en fila, como texto               | 2, 4, 6, 9, 10, 11, 14 |
| [GridLayout](#4-gridlayout)                          | Cuadrícula de celdas iguales                  | 3, 13, 14              |
| [GridBagLayout](#5-gridbaglayout)                    | Cuadrícula flexible avanzada                  | 12                     |

---

## 1. `null` — Posicionamiento manual

### Idea

No hay layout manager. Tú indicas la posición **(x, y)** y el tamaño **(ancho, alto)** de cada componente con `setBounds()`.

### Diagrama

```
┌─────────────────────────────────────────────┐
│  panel.setLayout(null);                     │
│                                             │
│    ┌──────────┐                             │
│    │ JLabel   │  setBounds(20, 20, 80, 25)  │
│    └──────────┘                             │
│              ┌──────────────────┐           │
│              │ JTextField       │           │
│              └──────────────────┘           │
│                        ┌─────────┐          │
│                        │ JButton │          │
│                        └─────────┘          │
└─────────────────────────────────────────────┘
```

### Código

```java
JPanel panel = new JPanel();
panel.setLayout(null);  // ← Desactivar layout manager

JLabel etiqueta = new JLabel("Nombre:");
etiqueta.setBounds(20, 20, 80, 25);
//                  x   y  ancho alto

JTextField campo = new JTextField();
campo.setBounds(110, 20, 200, 25);

panel.add(etiqueta);
panel.add(campo);
```

### Ventajas y desventajas

| ✅ Ventajas                     | ❌ Desventajas                               |
| ------------------------------- | -------------------------------------------- |
| Control total sobre la posición | No se adapta al redimensionar la ventana     |
| Fácil de entender al principio  | Mucho código (un `setBounds` por componente) |
|                                 | No escala a interfaces complejas             |

> 💡 **Consejo**: usa layout `null` solo para ejemplos muy simples. Para aplicaciones reales, usa `BorderLayout`, `FlowLayout` o combinaciones.

**Aparece en:** Ejemplo 1.

---

## 2. `BorderLayout`

### Idea

Divide el contenedor en **5 zonas** fijas. Es el layout **por defecto** de `JFrame`.

### Diagrama

```
┌─────────────────────────────────────────────┐
│                   NORTH                     │
├────────┬───────────────────────┬────────────┤
│        │                       │            │
│  WEST  │       CENTER          │    EAST    │
│        │   (ocupa todo el      │            │
│        │    espacio sobrante)  │            │
├────────┴───────────────────────┴────────────┤
│                   SOUTH                     │
└─────────────────────────────────────────────┘
```

### Código

```java
JPanel panel = new JPanel(new BorderLayout(10, 10));
//                                     hgap  vgap (espacio entre zonas)

panel.add(titulo,     BorderLayout.NORTH);
panel.add(panelIzq,   BorderLayout.WEST);
panel.add(contenido,  BorderLayout.CENTER);  // ← Ocupa todo el espacio sobrante
panel.add(panelDer,   BorderLayout.EAST);
panel.add(barraEstado, BorderLayout.SOUTH);
```

### Reglas clave

| Regla                   | Detalle                                                         |
| ----------------------- | --------------------------------------------------------------- |
| CENTER se expande       | Ocupa todo el espacio que no usan las otras zonas               |
| NORTH/SOUTH             | Se expanden horizontalmente, pero su alto es el preferido       |
| EAST/WEST               | Se expanden verticalmente, pero su ancho es el preferido        |
| Zonas opcionales        | No es necesario poner componentes en las 5 zonas                |
| Una zona, un componente | Si pones dos componentes en la misma zona, solo se ve el último |

> 💡 **Truco**: para poner varios botones en SOUTH, crea un `JPanel` con `FlowLayout` y ponlo entero en SOUTH.

**Aparece en:** Ejemplos 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 y App.

---

## 3. `FlowLayout`

### Idea

Coloca los componentes **en fila**, de izquierda a derecha, como palabras en un texto. Si no caben, saltan a la siguiente línea.

### Diagrama

```
┌─────────────────────────────────────────────┐
│  [Botón 1] [Botón 2] [Botón 3]             │
│                                             │
│  Si no caben:                               │
│  [Botón 1] [Botón 2]                       │
│  [Botón 3]                                 │
└─────────────────────────────────────────────┘
```

### Código

```java
// FlowLayout centrado (por defecto)
JPanel panel = new JPanel(new FlowLayout());

// FlowLayout alineado a la izquierda con espaciado
JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
//                                     alineación  hgap  vgap
```

### Alineaciones disponibles

| Constante           | Efecto                               |
| ------------------- | ------------------------------------ |
| `FlowLayout.CENTER` | Centra los componentes (por defecto) |
| `FlowLayout.LEFT`   | Alinea a la izquierda                |
| `FlowLayout.RIGHT`  | Alinea a la derecha                  |

> 💡 Es el layout **por defecto** de `JPanel`. Si haces `new JPanel()` sin parámetros, usa `FlowLayout(CENTER)`.

**Aparece en:** Ejemplos 2, 4, 6, 9, 10, 11, 14.

---

## 4. `GridLayout`

### Idea

Crea una **cuadrícula** de celdas **del mismo tamaño**. Los componentes se colocan de izquierda a derecha, de arriba a abajo.

### Diagrama

```
 GridLayout(3, 2) → 3 filas × 2 columnas

┌──────────────┬──────────────┐
│ Componente 1 │ Componente 2 │
├──────────────┼──────────────┤
│ Componente 3 │ Componente 4 │
├──────────────┼──────────────┤
│ Componente 5 │ Componente 6 │
└──────────────┴──────────────┘

 Todas las celdas tienen EXACTAMENTE el mismo tamaño.
```

### Código

```java
// 3 filas, 2 columnas, con espaciado
JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
//                                     filas cols hgap vgap

panel.add(new JButton("1"));  // fila 0, col 0
panel.add(new JButton("2"));  // fila 0, col 1
panel.add(new JButton("3"));  // fila 1, col 0
// ...
```

### Truco: filas automáticas

```java
// 0 filas = las que haga falta, 1 columna
JPanel panelVertical = new JPanel(new GridLayout(0, 1, 5, 5));
// Cada componente que añadas se pone en una nueva fila
```

> 💡 `GridLayout(0, 1)` es la forma más fácil de apilar componentes verticalmente.

**Aparece en:** Ejemplos 3, 13, 14.

---

## 5. `GridBagLayout`

### Idea

La cuadrícula más **potente** (y compleja) de Swing. Cada componente puede ocupar varias celdas, tener distintos pesos de expansión y alineaciones.

### Diagrama

```
 GridBagLayout con restricciones:

┌────────────┬────────────────────────────┐
│  Label     │  TextField (weightx=1.0)   │
│  (col 0)   │  (col 1, se expande →)    │
├────────────┼────────────────────────────┤
│  Label     │  TextField                │
├────────────┼────────────────────────────┤
│  Label     │  TextArea                 │
│            │  (weighty=0.5, crece ↕)   │
└────────────┴────────────────────────────┘
```

### Código

```java
JPanel panel = new JPanel(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(4, 4, 4, 4);  // Márgenes

// Fila 0, columna 0: etiqueta (no se expande)
gbc.gridx = 0;
gbc.gridy = 0;
gbc.fill = GridBagConstraints.NONE;
gbc.weightx = 0;
panel.add(new JLabel("Nombre:"), gbc);

// Fila 0, columna 1: campo (se expande horizontalmente)
gbc.gridx = 1;
gbc.fill = GridBagConstraints.HORIZONTAL;
gbc.weightx = 1.0;  // ← Recibe todo el espacio horizontal sobrante
panel.add(new JTextField(), gbc);
```

### Propiedades de `GridBagConstraints`

| Propiedad        | Qué controla                       | Valores comunes                          |
| ---------------- | ---------------------------------- | ---------------------------------------- |
| `gridx`, `gridy` | Posición en la cuadrícula          | 0, 1, 2…                                 |
| `gridwidth`      | Columnas que ocupa                 | 1 (defecto), 2, 3…                       |
| `gridheight`     | Filas que ocupa                    | 1 (defecto), 2, 3…                       |
| `fill`           | Cómo expandirse dentro de la celda | `NONE`, `HORIZONTAL`, `VERTICAL`, `BOTH` |
| `weightx`        | Peso de expansión horizontal       | 0.0 (no crece) a 1.0 (crece)             |
| `weighty`        | Peso de expansión vertical         | 0.0 (no crece) a 1.0 (crece)             |
| `anchor`         | Alineación dentro de la celda      | `WEST`, `CENTER`, `EAST`…                |
| `insets`         | Márgenes exteriores                | `new Insets(top, left, bottom, right)`   |

> 💡 **Consejo**: reutiliza el mismo objeto `gbc` y ve cambiando solo las propiedades que necesitas en cada fila.

**Aparece en:** Ejemplo 12.

---

## 🧩 Combinación de layouts (patrón común)

La mayoría de interfaces **combinan** varios layouts anidados:

```
JFrame (BorderLayout por defecto)
 └── JPanel principal (BorderLayout)
      ├── NORTH  → JLabel título
      ├── CENTER → JPanel formulario (GridLayout o GridBagLayout)
      │              ├── JLabel + JTextField
      │              ├── JLabel + JTextField
      │              └── JLabel + JTextArea
      └── SOUTH  → JPanel botones (FlowLayout)
                     ├── JButton "Aceptar"
                     └── JButton "Cancelar"
```

```java
// Código del patrón anterior:
JPanel principal = new JPanel(new BorderLayout(10, 10));

JLabel titulo = new JLabel("Formulario", SwingConstants.CENTER);
principal.add(titulo, BorderLayout.NORTH);

JPanel formulario = new JPanel(new GridLayout(3, 2, 5, 5));
formulario.add(new JLabel("Nombre:"));
formulario.add(new JTextField());
formulario.add(new JLabel("Email:"));
formulario.add(new JTextField());
principal.add(formulario, BorderLayout.CENTER);

JPanel botones = new JPanel(new FlowLayout());
botones.add(new JButton("Aceptar"));
botones.add(new JButton("Cancelar"));
principal.add(botones, BorderLayout.SOUTH);
```

> 💡 **Regla de oro**: no intentes hacer todo con un solo layout. Anida `JPanel` con distintos layouts para conseguir la disposición que necesitas.

---

## 📊 ¿Cuál elijo?

```
¿Necesitas posicionar manualmente?
│
├── Sí ──→ null (setBounds) — solo para pruebas rápidas
│
└── No ──→ ¿Cuántos componentes?
             │
             ├── 2-5 zonas diferenciadas ──→ BorderLayout
             │
             ├── Botones en fila ──→ FlowLayout
             │
             ├── Cuadrícula uniforme ──→ GridLayout
             │
             └── Formulario complejo ──→ GridBagLayout
```

---

[← Eventos y Listeners](guia-eventos.md) · [Detalle de cada ejemplo →](guia-ejemplos.md)
