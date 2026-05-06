# Red-Green-Refactor: TDD para CartViewModel con MockK

## Autor

- **Nombre:** Jhoseth Esneider Rozo Carrillo
- **Codigo:** 02230131027
- **Programa:** Ingenieria de Sistemas
- **Unidad:** 9: Testing y Aseguramiento de Calidad en Móvil
- **Actividad:** Post-Contenido 1
- **Fecha:** 06/05/2026

---

## Descripcion del Proyecto

Este proyecto aplica el ciclo Red-Green-Refactor de TDD para construir un ViewModel de Android testeado desde el primer commit. Se utiliza MockK para aislar dependencias de repositorio y dispatcher, y se escriben tests que cubren el camino feliz, errores de red y estados de carga.

---

# Objetivo

Aplicar el ciclo **TDD (Test Driven Development)** utilizando la estrategia **Red → Green → Refactor** para desarrollar un `CartViewModel` en Android con Kotlin.

El proyecto implementa pruebas unitarias usando:

- JUnit5
- MockK
- Turbine
- kotlinx-coroutines-test

Además, se validan escenarios de:

- carga correcta del carrito
- manejo de errores de red
- transición de estados Loading → Success

---

### Ciclo TDD Aplicado

1. **RED:** Se escribieron los tests antes de la implementación. Al ejecutar los tests, estos fallaron, validando que el test es capaz de detectar la falta de funcionalidad.
2. **GREEN:** Se realizó la implementación mínima necesaria en `CartViewModel` para que todos los tests pasaran (barra verde).
3. **REFACTOR:** Se mejoró el código de producción utilizando `runCatching`, extrayendo lógica a funciones puras (`calculateTotal`) y mejorando el manejo de errores, asegurando que los tests siguieran pasando.

---

## Tecnologias Utilizadas

- **Kotlin 1.9.22**: Lenguaje de programación
- **Android Studio Hedgehog**: IDE
- **JUnit 5**: Framework de pruebas unitarias
- **MockK 1.13.9**: Librería de mocking
- **Kotlin Coroutines Test**: Soporte para pruebas de corrutinas
- **Turbine 1.1.0**: Librería para testear StateFlow/Flow
- **Gradle 8.7**: Gestor de dependencias

---

## Estructura del Proyecto

- app/src/main/java/com/universidad/red_green_refactor/
- ├── domain/
- │ ├── model/
- │ │ └── CartItem.kt
- │ └── repository/
- │ └── CartRepository.kt
- └── ui/
-     └── cart/
-         ├── CartUiState.kt
-         ├── CartViewModel.kt
-         └── AnalyticsService.kt

- app/src/test/java/com/universidad/red_green_refactor/ui/cart/
- └── CartViewModelTest.kt

---

## Instrucciones de Ejecucion

### 1. Clonar el repositorio

```bash
git clone https://github.com/jerc31/rozo-post1_u9.git
cd rozo-post1_u9
```

### 2. Ejecutar los tests desde la terminal

En PowerShell o terminal de Android Studio:

```bash
./gradlew :app:testDebugUnitTest
```

### 3. Verificar resultados

Al terminar la ejecución, se debe ver un mensaje similar a:

```text
BUILD SUCCESSFUL in Xs
4 tests completed, 0 failed
```

---

## Desarrollo usando TDD

### Fase RED — Tests primero

En esta etapa se escribieron primero los tests antes de crear la implementación de CartViewModel.

Se definieron:

- contratos
- modelos
- estados UI
- comportamiento esperado

**Tests implementados**

1. Success con total correcto

Verifica que:

- se carguen los productos
- el total sea calculado correctamente

@Test
fun `loadCart emits Success state with items and total`() = runTest

2. Error cuando el repositorio falla

Verifica que:

- si ocurre una excepción (IOException)
- el ViewModel emita un estado Error

@Test
fun `loadCart emits Error when repository throws`() = runTest 3. Secuencia Loading → Success

Verifica que:

- primero se emita Loading
- luego Success

@Test
fun `loadCart emits Loading before Success`() = runTest
Resultado del checkpoint RED

Se ejecutó:

./gradlew :app:testDebugUnitTest

Salida observada:

3 tests completed, 3 failed

Esto confirma correctamente la fase RED del ciclo TDD.

### Fase GREEN — Implementación mínima

En esta etapa se implementó el código mínimo necesario para hacer pasar todos los tests.

**Implementación realizada**

Se creó:

- CartViewModel
- manejo de estados
- cálculo del total
- captura de errores

Se utilizó:

- MutableStateFlow
- viewModelScope
- Coroutines
- Resultado del checkpoint GREEN

Se ejecutó:

./gradlew :app:testDebugUnitTest

Resultado:

BUILD SUCCESSFUL

Todos los tests pasaron correctamente.

### Fase REFACTOR - Mejora del código

Con los tests en verde, se realizó refactorización del código sin romper el comportamiento validado.

**Mejoras aplicadas**

- Extracción de calculateTotal() como función pura
- Manejo específico de IOException
- Uso de runCatching
- Código más limpio y mantenible

**Test adicional agregado**

Se agregó un test adicional para validar:

calculateTotal(emptyList()) == 0.0

Esto permite validar correctamente casos límite.

---

## CHECKPOINTS DE VERIFICACION

### Checkpoint 1 - Fase RED

1. Los tests se definieron con el contrato de las interfaces.
2. Fallaron inicialmente al no tener implementación en el ViewModel.
3. Commit: `feat: Agrega tests RED para CartViewModel (TDD paso 1)`

### Checkpoint 2 - Fase GREEN

1. Implementación mínima en `CartViewModel.loadCart()`.
2. Todos los tests pasaron (3 tests completados).
3. Commit: `feat: Implementa CartViewModel GREEN — todos los tests pasan (TDD paso 2)`

### Checkpoint 3 - Fase REFACTOR

1. Refactorización usando `runCatching` y función pura `calculateTotal`.
2. Adición de test para `calculateTotal(emptyList())`.
3. Todos los tests (4) pasan correctamente.
4. Commit: `refactor: Refactoriza CartViewModel (TDD paso 3)`

---

## Capturas de Resultados

Las capturas se encuentran en la carpeta `/evidencias/`:

### Barra Roja en Android Studio

![Barra roja](evidencias/captura_estado_red.png)

### Barra Verde en Android Studio

![Barra Verde](evidencias/captura_estado_green.png)

### Estado Refactor

![refactor](evidencias/captura_estado_refactor.png)
