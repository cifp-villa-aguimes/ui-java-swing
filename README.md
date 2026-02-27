# UT5.2 – Ejemplos Java Swing

### 1º DAW / DAM · Módulo de Programación · Interfaces Gráficas

<p align="center">
  <img src="https://img.shields.io/badge/Java-11%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 11+">
  <img src="https://img.shields.io/badge/Swing-GUI-007396?style=for-the-badge&logo=java&logoColor=white" alt="Swing GUI">
  <img src="https://img.shields.io/badge/Ejemplos-14-28a745?style=for-the-badge" alt="14 Ejemplos">
  <img src="https://img.shields.io/badge/Nivel-Principiante-blue?style=for-the-badge" alt="Nivel Principiante">
  <img src="https://img.shields.io/badge/Licencia-Educativa-purple?style=for-the-badge" alt="Licencia Educativa">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/VS%20Code-Compatible-007ACC?style=flat-square&logo=visualstudiocode&logoColor=white" alt="VS Code">
  <img src="https://img.shields.io/badge/IntelliJ%20IDEA-Compatible-000000?style=flat-square&logo=intellijidea&logoColor=white" alt="IntelliJ IDEA">
  <img src="https://img.shields.io/badge/Eclipse-Compatible-2C2255?style=flat-square&logo=eclipse&logoColor=white" alt="Eclipse">
  <img src="https://img.shields.io/badge/NetBeans-Compatible-1B6AC6?style=flat-square&logo=apachenetbeans&logoColor=white" alt="NetBeans">
  <img src="https://img.shields.io/badge/Terminal-Compatible-4EAA25?style=flat-square&logo=gnubash&logoColor=white" alt="Terminal">
</p>

<br>

Colección de **14 ejemplos prácticos** de Java Swing con comentarios didácticos, pensados para aprender progresivamente a construir interfaces gráficas de escritorio.

El proyecto incluye un **menú lanzador** ([`App.java`](src/ejemplos/App.java)) que permite ejecutar cualquier ejemplo desde una sola ventana.

<br>

---

## 📋 Índice

- [Requisitos](#-requisitos)
- [Cómo ejecutar](#-cómo-ejecutar)
- [Lista de ejemplos](#-lista-de-ejemplos)
- [Mapa de conceptos](#-mapa-de-conceptos)
- [Documentación detallada](#-documentación-detallada)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Licencia](#-licencia)

---

## 🛠 Requisitos

| Requisito               | Versión mínima |
| ----------------------- | -------------- |
| Java (JDK)              | **11+**        |
| VS Code                 | Última estable |
| Extension Pack for Java | Última versión |

> **Nota:** El proyecto se ha desarrollado con JDK 23, pero es compatible con JDK 11+.

---

## 🚀 Cómo ejecutar

### Visual Studio Code (recomendado)

1. Abre la carpeta del proyecto en **VS Code**.
2. Comprueba que tienes instalado el **Extension Pack for Java** (`vscjava.vscode-java-pack`).
3. Abre [`src/ejemplos/App.java`](src/ejemplos/App.java).
4. Pulsa **▶ Run** (o haz clic en _Run_ encima del `main`).
5. En la ventana del lanzador, selecciona un ejemplo y pulsa **"▶ Ejecutar Ejemplo"** o haz **doble clic**.

### IntelliJ IDEA

1. **File → Open** y selecciona la carpeta del proyecto.
2. Marca `src` como **Sources Root**: clic derecho sobre `src` → _Mark Directory as → Sources Root_.
3. Abre `src/ejemplos/App.java` y pulsa el icono **▶** verde junto al `main`.

### Apache NetBeans

1. **File → Open Project** (o importa como proyecto Java con fuentes existentes).
2. En la configuración del proyecto, establece `src` como carpeta de fuentes.
3. Haz clic derecho sobre `App.java` → **Run File**.

### Eclipse

1. **File → Import → General → Existing Projects into Workspace** (o crea un nuevo proyecto Java y copia la carpeta `src`).
2. Asegúrate de que `src` está marcado como _source folder_ en **Build Path**.
3. Clic derecho sobre `App.java` → **Run As → Java Application**.

### Desde la terminal

```bash
# Compilar (desde la raíz del proyecto)
javac -d bin -sourcepath src src/ejemplos/App.java

# Ejecutar
java -cp bin ejemplos.App
```

> 💡 Cada ejemplo también puede ejecutarse de forma individual desde su propio `main()`.

---

## 📚 Lista de ejemplos

### Bloque 1 — Primeros pasos

| Nº  | Ejemplo                   | Qué aprenderás                                                                  |
| --- | ------------------------- | ------------------------------------------------------------------------------- |
| 1   | **GUI Básica**            | Crear una ventana, colocar componentes con `setBounds`, reaccionar a clics      |
| 2   | **Componentes Básicos**   | Campos de texto, contraseñas, checkboxes, radio buttons, **expresiones lambda** |
| 3   | **Componentes Avanzados** | JComboBox, JList, JSlider, JSpinner, JProgressBar sincronizados                 |

### Bloque 2 — Eventos y comunicación

| Nº  | Ejemplo                       | Qué aprenderás                                                |
| --- | ----------------------------- | ------------------------------------------------------------- |
| 4   | **Eventos Swing**             | 5 tipos de listener: Action, Key, Mouse, MouseMotion, Focus   |
| 5   | **DocumentListener**          | Escuchar cambios en texto en tiempo real (filtro de búsqueda) |
| 6   | **Comunicación (referencia)** | Pasar un `JTextField` entre paneles (acoplado)                |
| 7   | **Comunicación (modelo)**     | Modelo de datos compartido entre paneles (desacoplado)        |

### Bloque 3 — Ficheros y datos

| Nº  | Ejemplo              | Qué aprenderás                                           |
| --- | -------------------- | -------------------------------------------------------- |
| 8   | **Leer Archivo**     | `Files.readAllLines()`, `Paths`, `String.join()`         |
| 9   | **Escribir Archivo** | `BufferedWriter`, `FileWriter`, try-with-resources       |
| 10  | **JFileChooser**     | Diálogos abrir/guardar del sistema, filtros de extensión |
| 11  | **JTable**           | Tabla con operaciones CRUD, `DefaultTableModel`          |

### Bloque 4 — Apariencia y personalización

| Nº  | Ejemplo                     | Qué aprenderás                                                   |
| --- | --------------------------- | ---------------------------------------------------------------- |
| 12  | **File Organizer**          | `GridBagLayout`, `Files.createDirectories`, herencia de `JFrame` |
| 13  | **Look & Feel**             | Cambiar la apariencia completa de la app en caliente             |
| 14  | **UIManager Personalizado** | Personalizar colores, fuentes y estilos globalmente              |

---

## 🗺 Mapa de conceptos

### Componentes Swing utilizados

```
┌─────────────────────────────────────────────────────────────┐
│                    COMPONENTES SWING                        │
├─────────────────────┬───────────────────────────────────────┤
│  Contenedores       │  JFrame · JPanel · JScrollPane        │
│                     │  JSplitPane                           │
├─────────────────────┼───────────────────────────────────────┤
│  Texto              │  JLabel · JTextField · JPasswordField  │
│                     │  JTextArea                            │
├─────────────────────┼───────────────────────────────────────┤
│  Botones            │  JButton · JCheckBox · JRadioButton    │
│                     │  ButtonGroup                          │
├─────────────────────┼───────────────────────────────────────┤
│  Selección/Ajuste   │  JComboBox · JList · JSlider          │
│                     │  JSpinner · JProgressBar              │
├─────────────────────┼───────────────────────────────────────┤
│  Datos              │  JTable · DefaultTableModel            │
├─────────────────────┼───────────────────────────────────────┤
│  Diálogos           │  JOptionPane · JFileChooser            │
├─────────────────────┼───────────────────────────────────────┤
│  Decoración         │  JSeparator · BorderFactory            │
└─────────────────────┴───────────────────────────────────────┘
```

### Eventos y Listeners

```
┌─────────────────────────────────────────────────────────────┐
│                  EVENTOS Y LISTENERS                        │
├──────────────────────────┬──────────────────────────────────┤
│  ActionListener          │  Clics en botones (Ej. 1-14)    │
│  KeyAdapter / KeyListener│  Pulsación de teclas (Ej. 4)    │
│  MouseAdapter            │  Clics del ratón (Ej. 4, App)   │
│  MouseMotionAdapter      │  Movimiento del ratón (Ej. 4)   │
│  FocusAdapter            │  Ganar/perder foco (Ej. 4)      │
│  DocumentListener        │  Cambios en texto (Ej. 5)       │
│  CaretListener           │  Posición del cursor (Ej. 7)    │
│  ItemListener            │  Selección combo/check (Ej. 3)  │
│  ListSelectionListener   │  Selección en lista (Ej. 3,App) │
│  ChangeListener          │  Cambio de valor (Ej. 3)        │
└──────────────────────────┴──────────────────────────────────┘
```

### Layout Managers

```
┌─────────────────────────────────────────────────────────────┐
│                   LAYOUT MANAGERS                           │
├──────────────────┬──────────────────────────────────────────┤
│  null            │  Posicionamiento manual – setBounds()    │
│  BorderLayout    │  5 zonas: N, S, E, W, Center             │
│  FlowLayout      │  Componentes en fila (flujo horizontal)  │
│  GridLayout      │  Cuadrícula de celdas iguales            │
│  GridBagLayout   │  Cuadrícula flexible con restricciones   │
└──────────────────┴──────────────────────────────────────────┘
```

---

## 📖 Documentación detallada

Para no sobrecargar este README, la documentación ampliada está organizada en guías independientes:

| Guía                                                | Contenido                                                                                       |
| --------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| 📦 [Componentes Swing](docs/guia-componentes.md)    | Todos los componentes usados, qué hacen, en qué ejemplos aparecen y cómo se usan                |
| ⚡ [Eventos y Listeners](docs/guia-eventos.md)      | Los 10 tipos de listener del proyecto: qué escuchan, cuándo se disparan, interfaz vs adapter    |
| 📐 [Layouts](docs/guia-layouts.md)                  | Los 5 layout managers explicados con diagramas ASCII y ejemplos de código                       |
| 🔍 [Detalle de cada ejemplo](docs/guia-ejemplos.md) | Ficha completa de cada uno de los 14 ejemplos con componentes, eventos, APIs y código destacado |

---

## 📁 Estructura del proyecto

```
src/
└── ejemplos/
    ├── App.java                          ← Menú lanzador principal
    │
    ├── guibasica/
    │   └── EjemploGuiBasica.java         ← Ej. 1
    ├── componentesbasicos/
    │   └── EjemploComponentesBasicos.java ← Ej. 2
    ├── componentesavanzados/
    │   └── EjemploComponentesAvanzados.java  ← Ej. 3
    ├── eventos/
    │   └── EjemploEventosSwing.java      ← Ej. 4
    ├── documentlistener/
    │   └── EjemploDocumentListener.java  ← Ej. 5
    ├── panelcomunicacion/
    │   ├── PanelComunicacion.java        ← Ej. 6
    │   └── PanelComunicacionSimple.java  ← Ej. 7 (incluye DatosCompartidos)
    ├── archivos/
    │   ├── EjemploLeerArchivo.java       ← Ej. 8
    │   ├── EjemploEscribirArchivo.java   ← Ej. 9
    │   └── EjemploJFileChooser.java      ← Ej. 10
    ├── jtable/
    │   └── EjemploJTable.java            ← Ej. 11
    ├── fileorganizer/
    │   └── EjemploFileOrganizerApp.java  ← Ej. 12
    └── lookandfeel/
        ├── EjemploLookAndFeel.java       ← Ej. 13
        └── EjemploUIManagerPersonalizado.java ← Ej. 14

data/
    └── archivo.txt                       ← Archivo de ejemplo para lectura

docs/
    ├── guia-componentes.md               ← Guía de componentes Swing
    ├── guia-eventos.md                   ← Guía de eventos y listeners
    ├── guia-layouts.md                   ← Guía de layout managers
    └── guia-ejemplos.md                  ← Ficha detallada de cada ejemplo
```

---

## 📜 Licencia

Proyecto educativo para uso en el aula. Libre distribución con fines docentes.
