### Daniel Cornejo García
## REPARACIONES DAYCE
Una aplicación donde los clientes pueden solicitar los servicios de un técnico especializado en la instalación, reparación, mantenimiento y revisión de electrodomésticos,
mientras que los técnicos podrán tener la oportunidad de tener todo su trabajo bien organizado, aceptando y rechazando avisos cuando sea necesario.


# VERSION 1.1

## **Estructura del Proyecto**

- **adapter:**
  - `AdapterAviso`
  - `ViewHAviso`

- **controller:**
  - `Controller`

- **dao:**
  - `AvisoDAO`

- **models:**
  - `Aviso`

- **objects_models:**
  - `Datos`

- **MainActivity**

---

## **Descripción de Cada Clase**

1. **`AdapterAviso`:**  
   - Es el adaptador que se encarga de crear la vista para cada ítem del `RecyclerView`.
   - Asigna los datos de cada aviso a los elementos del diseño individual de cada ítem.

2. **`ViewHAviso`:**  
   - Es el ViewHolder asociado al adaptador. Se encarga de mostrar la información de cada objeto `Aviso` (nombre, dirección, fecha, descripción, imagen).  
   - Configura el botón de eliminar (papelera) para borrar un aviso específico.

3. **`Controller`:**  
   - Gestiona la lógica de la actividad principal (`MainActivity`).
   - Maneja la eliminación de un aviso de la lista al pulsar el botón de borrar.
   - Muestra un `Toast` con el nombre del aviso eliminado como confirmación de la acción.

4. **`AvisoDAO`:**  
   - Proporciona acceso a los datos de la lista de avisos almacenados en el objeto `Datos`.
   - Implementa el método `getDataAvisos()` para acceder a los datos de los avisos.

5. **`Aviso`:**  
   - Representa un aviso. Contiene atributos como `nombre`, `dirección`, `fecha`, `descripción`, y `imagen`.
   - Sobrescribe el método `toString` para proporcionar una representación legible de un aviso.

6. **`Datos`:**  
   - Es un objeto que almacena una lista estática de avisos.
   - Sirve como fuente de datos centralizada para la aplicación.

7. **`MainActivity`:**  
   - Es la actividad principal de la aplicación.
   - Configura el adaptador para el `RecyclerView` mediante el método `setAdapter` del controlador.
   - Configura la disposición de los elementos en una lista vertical.
   - Contiene el botón de "Cerrar Sesión" que redirige al usuario a la actividad de inicio de sesión.

---

## **Estructura de las Vistas**

- **`drawable`:**
  - `papelera.png` - Icono utilizado para el botón de eliminar un aviso.

- **`layout`:**
  - `activity_main.xml` - Diseño de la actividad principal con un `RecyclerView` y un botón para cerrar sesión.
  - `item_aviso.xml` - Diseño individual de cada ítem en el `RecyclerView`. Muestra los detalles del aviso y contiene un botón para eliminarlo.

---

## **Características de la Aplicación**

1. **Lista de Avisos:**
   - Se presenta una lista de avisos en un `RecyclerView`, cada uno mostrando detalles como nombre, dirección, fecha, descripción y una imagen.

2. **Eliminación de Avisos:**
   - Cada ítem tiene un botón de eliminar (papelera) que permite borrar un aviso específico.
   - Se muestra un mensaje (`Toast`) con el nombre del aviso eliminado.

3. **Gestión de Sesión:**
   - La actividad principal incluye un botón para cerrar sesión que redirige al usuario a la pantalla de inicio de sesión (`Login`).

4. **Carga de Imágenes desde URL:**
   - Las imágenes de los avisos se cargan dinámicamente desde URLs usando la biblioteca Glide.

---
Es esencial activar el binding en el archivo build.gradle.kts: 
```kts
 viewBinding {
        enable = true
    }
```

Además de tener las siguientes dependencias:
```kts
dependencies {
    implementation ("androidx.recyclerview:recyclerview:1.3.0")
    implementation (libs.glide)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
```

También tendremos que habilitar el permiso a internet para la carga de las imágenes, tendremos que añadir lo siguiente en el AndroidManifest.xml

```xml
<!-- Permiso para usar Internet -->
    <uses-permission android:name="android.permission.INTERNET" />

```
