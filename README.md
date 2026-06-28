# AQUILAE — Risk Imperio Romano

Simulador del juego de tablero **Risk** ambientado en el Imperio Romano, desarrollado íntegramente en Java con interfaz gráfica Swing. El proyecto fue utilizado como base técnica en un Trabajo de Fin de Máster sobre lenguas muertas de la Universidad de Valladolid (UVa)
---

## Características

- **Mapa histórico del Imperio Romano** con 28 provincias reales dibujadas como polígonos vectoriales escalables (Lusitania, Tarraconensis, Baetica, Galia, Britania, Armenia, Egipto, etc.)
- **2 a 4 jugadores** con asignación aleatoria de territorios al inicio de cada partida
- **Interfaz gráfica completa** construida con Java Swing:
  - Menú de inicio con selección de jugadores y personalización de nombres
  - Mapa interactivo con efectos de hover y selección
  - Panel de información por jugador (territorios controlados y tropas totales)
  - Editor de territorio mediante diálogo modal
- **Sistema de escalado dinámico**: el mapa se redimensiona proporcionalmente con la ventana
- **Renderizado con antialiasing**, gradientes y sombras para una experiencia visual cuidada
- **Gestión de tropas** por territorio con spinner interactivo

---

## Tecnologías

| Tecnología | Uso |
|---|---|
| Java 17+ | Lenguaje principal |
| Java Swing / AWT | Interfaz gráfica y renderizado |
| Maven | Gestión de dependencias y build |
| Java2D / Graphics2D | Renderizado vectorial del mapa |

---

## Estructura del proyecto

```
TFM_Juan/
├── src/
│   └── main/java/com/mycompany/tfm_juan/
│       └── TFM_Juan.java        # Código fuente completo
├── target/
│   └── TFM_JuanPerez.jar        # JAR ejecutable
└── pom.xml                      # Configuración Maven
```

El proyecto está estructurado en cuatro clases principales dentro de un único archivo fuente:

- **`TFM_Juan`** — Panel principal del mapa. Gestiona los territorios, el renderizado, los eventos de ratón y el escalado.
- **`MainMenuFrame`** — Ventana de inicio con selección de número de jugadores y nombres.
- **`Territory`** — Modelo de territorio: polígono, color, número de tropas y estado de hover.
- **`ColorComboRenderer`** — Renderer personalizado para el selector de color de territorio.

---

## Ejecución

### Con el JAR compilado

```bash
java -jar target/TFM_JuanPerez.jar
```

### Compilando desde fuente

```bash
mvn clean package
java -jar target/TFM_JuanPerez.jar
```

**Requisitos:** Java 17 o superior, Maven 3.6+

---

## Cómo se juega

1. Selecciona el número de jugadores (2, 3 o 4) en el menú de inicio
2. Introduce los nombres de cada jugador
3. Los territorios del Imperio Romano se asignan aleatoriamente al comenzar
4. Haz clic sobre cualquier territorio para editar su propietario y número de tropas
5. El panel de cada jugador muestra en tiempo real los territorios controlados y las tropas totales

---

## Provincias incluidas

El mapa cubre las 28 provincias principales del Imperio Romano en su máxima extensión:

**Península Ibérica:** Lusitania, Tarraconensis, Baetica  
**África del Norte:** Mauritania, África, Cirenaica, Egipto  
**Oriente Próximo:** Arabia, Judea, Siria, Asia, Ponto, Armenia  
**Grecia y Balcanes:** Grecia, Macedonia, Tracia, Dacia, Moesia, Dalmacia, Panonia  
**Italia:** Magna Grecia, Roma, Galia Cisalpina  
**Europa Central y Occidental:** Raetia, Germania, Narbonense, Galia, Britania

---

## Contexto académico

Este simulador fue desarrollado como herramienta de apoyo para un TFM de la UVa centrado en el estudio de lenguas muertas del mundo romano. El mapa histórico permite contextualizar geográficamente las provincias donde se hablaban latín, griego antiguo y otras lenguas del Imperio.

---

## Autor

**Alejandro Villarrubia** — [LinkedIn](https://www.linkedin.com/in/alejandro-villarrubia-garc%C3%ADa-3078892b9/) · [GitHub](https://github.com/villarrubi)

Junior Software Engineer · Java · Python · C · MongoDB · SQL
