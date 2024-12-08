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
- **Login**
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
8. **`Login`:**
  - Implementa un flujo básico de inicio de sesión:
    - Verifica que los campos de usuario y contraseña no estén vacíos.
    - Valida las credenciales y redirige a la actividad principal (`MainActivity`) en caso de éxito.
    - Muestra mensajes `Toast` para indicar errores (credenciales incorrectas o campos vacíos).
  - Configura preferencias compartidas (`SharedPreferences`) para almacenamiento de datos persistentes.
  - Ajusta el diseño a pantallas modernas con bordes redondeados o recortes (`enableEdgeToEdge` y `WindowInsetsCompat`).

## **Estructura de las Vistas**

- **`drawable`:**
  - `papelera.png` - Icono utilizado para el botón de eliminar un aviso.

- **`layout`:**
  - `activity_main.xml` - Diseño de la actividad principal con un `RecyclerView` y un botón para cerrar sesión.
  - `item_aviso.xml` - Diseño individual de cada ítem en el `RecyclerView`. Muestra los detalles del aviso y contiene un botón para eliminarlo.
  - `activity_login.xml`:
  - Interfaz de inicio de sesión con campos para usuario y contraseña.
  - Botón para validar las credenciales y proceder a la pantalla principal.
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
# VERSION 1.2


He implementado en una carpeta llamada Dialogues los diferentes Dialogues de Editar, Borrar y Añadir, para de esa forma hacer que el Controller esté algo más limpio, al igual que el MainActivity

A continuación procederé a mostrar como se ven estos Dialogues en nuestro código:

## DialogAddAviso

```kt
package com.example.dayce.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dayce.R
import com.example.dayce.databinding.DialogAddAvisoBinding
import com.example.dayce.models.Aviso

class DialogAddAviso(
    private val onNewAvisoDialog: (Aviso) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myActivity ->
            val builder = AlertDialog.Builder(myActivity)
            val inflater = myActivity.layoutInflater
            val viewDialog = inflater.inflate(R.layout.dialog_add_aviso, null)
            builder.setView(viewDialog)
                .setMessage("Añadir Aviso")
                .setPositiveButton("Aceptar") { _, _ ->
                    val aviso = recoverDataLayout(viewDialog)
                    if (validacion(aviso)) {
                        onNewAvisoDialog(aviso)
                        Toast.makeText(myActivity, "Aviso creado", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(myActivity, "Por favor, rellena todos los campos", Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    Toast.makeText(myActivity, "Añadir cancelado", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun validacion(aviso: Aviso): Boolean {
        return aviso.nombre.isNotEmpty() &&
                aviso.direccion.isNotEmpty() &&
                aviso.fecha.isNotEmpty() &&
                aviso.descripcion.isNotEmpty() &&
                aviso.imagen.isNotEmpty()
    }

    private fun recoverDataLayout(view: View): Aviso {
        val binding = DialogAddAvisoBinding.bind(view)
        return Aviso(
            nombre = binding.etNombre.text.toString(),
            direccion = binding.etDireccion.text.toString(),
            fecha = binding.etFecha.text.toString(),
            descripcion = binding.etDescripcion.text.toString(),
            imagen = binding.etImagen.text.toString()
        )
    }
}

```


## DialogEditAviso

```kt
package com.example.dayce.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.dayce.databinding.DialogEditarAvisoBinding
import com.example.dayce.models.Aviso

class DialogEditAviso(
    private val avisoToEdit: Aviso,
    private val onEditAvisoDialog: (Aviso) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myActivity ->
            val inflater = myActivity.layoutInflater
            val binding = DialogEditarAvisoBinding.inflate(inflater)
            binding.etNombre.setText(avisoToEdit.nombre)
            binding.etDireccion.setText(avisoToEdit.direccion)
            binding.etFecha.setText(avisoToEdit.fecha)
            binding.etDescripcion.setText(avisoToEdit.descripcion)
            binding.etImagen.setText(avisoToEdit.imagen)

            val builder = AlertDialog.Builder(myActivity)
            builder.setView(binding.root)
                .setTitle("Editar Aviso")
                .setPositiveButton("Guardar") { _, _ ->
                    val updatedAviso = recoverDataLayout(binding)
                    if (validacion(updatedAviso)) {
                        onEditAvisoDialog(updatedAviso)
                    } else {
                        Toast.makeText(myActivity, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun validacion(aviso: Aviso): Boolean {
        return aviso.nombre.isNotEmpty() &&
                aviso.direccion.isNotEmpty() &&
                aviso.fecha.isNotEmpty() &&
                aviso.descripcion.isNotEmpty() &&
                aviso.imagen.isNotEmpty()
    }

    private fun recoverDataLayout(binding: DialogEditarAvisoBinding): Aviso {
        return Aviso(
            nombre = binding.etNombre.text.toString(),
            direccion = binding.etDireccion.text.toString(),
            fecha = binding.etFecha.text.toString(),
            descripcion = binding.etDescripcion.text.toString(),
            imagen = binding.etImagen.text.toString()
        )
    }
}
```


DialogDeleteAviso

```kt
package com.example.dayce.dialogues

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogDeleteAviso(
    private val position: Int,
    private val onDeleteAvisoDialog: (Int) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { myActivity ->
            val builder = AlertDialog.Builder(myActivity)
            builder.setMessage("¿Estás seguro que quieres eliminar este aviso?")
                .setPositiveButton("Sí") { _, _ ->
                    onDeleteAvisoDialog(position)
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
```
Como se puede ver tanto en el caso de editar y añadir es obligatorio rellenar todos los campos porque de lo contrario no se realizarán los cambios, y al borrar se nos preguntará si de verdad deseamos borrar el aviso, a diferencia de en la versión 1.1 donde el aviso era eliminado con tan solo pulsar el botón de borrar


