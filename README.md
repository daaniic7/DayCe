### Daniel Cornejo García
## REPARACIONES DAYCE
Una aplicación donde los clientes pueden solicitar los servicios de un técnico especializado en la instalación, reparación, mantenimiento y revisión de electrodomésticos,
mientras que los técnicos podrán tener la oportunidad de tener todo su trabajo bien organizado, aceptando y rechazando avisos cuando sea necesario.

  ## VERSION 1.3 YA DISPONIBLE, INFORMACIÓN ACTUALIZADA EN EL CÓDIGO!

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


He implementado en una carpeta llamada Dialogues los diferentes Dialogues de Editar, Borrar y Añadir, para de esa forma hacer que el Controller esté algo más limpio ordenado, al igual que el MainActivity, delegando todas las funciones de los mismos hacia los diferentes Dialogues, aplicando el clásico "divide y vencerás"

## Main Activity

```kt
package com.example.dayce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dayce.controller.Controller
import com.example.dayce.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usar ViewBinding para inflar la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el controlador
        controller = Controller(this, binding)

        // Configurar el botón flotante para añadir un nuevo aviso
        binding.btnAddAviso.setOnClickListener {
            controller.addAviso()
        }

        // Configurar el botón para cerrar sesión
        binding.btnVolverLogin.setOnClickListener {
            finish() // Cierra esta actividad para volver a la anterior (Login)
        }
    }
}
``` 
## Controller 

```
package com.example.dayce.controller

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dayce.adapter.AdapterAviso
import com.example.dayce.dao.AvisoDAO
import com.example.dayce.databinding.ActivityMainBinding
import com.example.dayce.dialogues.DialogAddAviso
import com.example.dayce.dialogues.DialogDeleteAviso
import com.example.dayce.dialogues.DialogEditAviso
import com.example.dayce.models.Aviso

class Controller(
    private val context: Context,
    private val binding: ActivityMainBinding
) {

    private lateinit var listaAvisos: MutableList<Aviso>

    init {
        initData()
        setAdapter()
    }

    private fun initData() {
        listaAvisos = AvisoDAO.getDataAvisos().toMutableList() // Acceso correcto y mutable
    }

    private fun setAdapter() {
        val adapter = AdapterAviso(
            listaAvisos,
            { position -> deleteAviso(position) },  // Eliminar aviso
            { position -> editAviso(position) }    // Editar aviso
        )
        binding.myRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myRecyclerView.adapter = adapter
    }

    private fun deleteAviso(position: Int) {
        val dialog = DialogDeleteAviso(position) { pos ->
            val nombreAviso = listaAvisos[pos].nombre
            listaAvisos.removeAt(pos)
            binding.myRecyclerView.adapter?.notifyItemRemoved(pos)
            Toast.makeText(context, "Aviso eliminado: $nombreAviso", Toast.LENGTH_SHORT).show()
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogDeleteAviso")
    }

    private fun editAviso(position: Int) {
        val aviso = listaAvisos[position]
        val dialog = DialogEditAviso(aviso) { updatedAviso ->
            // Actualizar los datos del aviso con los valores editados
            listaAvisos[position] = updatedAviso
            binding.myRecyclerView.adapter?.notifyItemChanged(position)
            Toast.makeText(context, "Aviso actualizado", Toast.LENGTH_SHORT).show()
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogEditAviso")
    }

    fun addAviso() {
        val dialog = DialogAddAviso { newAviso ->
            listaAvisos.add(newAviso)
            binding.myRecyclerView.adapter?.notifyItemInserted(listaAvisos.size - 1)
            Toast.makeText(context, "Nuevo aviso añadido", Toast.LENGTH_SHORT).show()
        }
        dialog.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DialogAddAviso")
    }
}
```



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


## DialogDeleteAviso

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
Como se puede ver tanto en el caso de editar y añadir es obligatorio rellenar todos los campos porque de lo contrario no se realizarán los cambios, y al borrar se nos preguntará si de verdad deseamos borrar el aviso, a diferencia de en la versión 1.1 donde el aviso era eliminado con tan solo pulsar sobre el botón de borrar.

Por último quisiera mostrar como se ven las views en este momento tras realizar las modificaciones pertinentes para esta versión 1.2

## activity main
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <!-- FrameLayout para el contenido principal -->
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toTopOf="@+id/btn_volver_login">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/my_recycler_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="60dp"
            android:padding="25dp" />

        <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="40dp"
            android:textSize="20sp"
            android:textStyle="bold"
            android:layout_marginStart="70dp"
            android:text="Lista de Avisos" />

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="40dp"
            android:layout_height="47dp"
            android:layout_marginTop="800dp"
            android:layout_marginStart="370dp" />

    </FrameLayout>

    <!-- FloatingActionButton para añadir nuevo aviso -->
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/btn_add_aviso"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        app:layout_constraintBottom_toTopOf="@id/btn_volver_login"
        app:layout_constraintEnd_toEndOf="parent"
        android:contentDescription="Añadir aviso"
        app:srcCompat="@drawable/agregar" />


    <!-- Botón de cerrar sesión -->
    <Button
        android:id="@+id/btn_volver_login"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="32dp"
        android:text="Cerrar Sesión"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>

```

## dialog add
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".dialogues.DialogAddAviso">

    <LinearLayout
        android:layout_width="313dp"
        android:layout_height="224dp"
        android:orientation="vertical">

        <LinearLayout
            android:layout_width="294dp"
            android:layout_height="219dp"
            android:orientation="horizontal"
            android:paddingBottom="8dp"
            android:weightSum="5">

            <LinearLayout
                android:layout_width="271dp"
                android:layout_height="match_parent"
                android:layout_weight="2.7"
                android:orientation="vertical"
                android:padding="4dp">

                <EditText
                    android:id="@+id/etNombre"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:paddingBottom="7dp"
                    android:hint="Nombre"
                    android:textSize="18sp" />

                <EditText
                    android:id="@+id/etDireccion"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:paddingBottom="7dp"
                    android:hint="Dirección"
                    android:textSize="16sp" />

                <EditText
                    android:id="@+id/etFecha"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:paddingBottom="7dp"
                    android:hint="Fecha"
                    android:textSize="14sp" />

                <EditText
                    android:id="@+id/etDescripcion"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:paddingBottom="7dp"
                    android:hint="Descripción"
                    android:textSize="14sp" />

                <EditText
                    android:id="@+id/etImagen"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:paddingBottom="7dp"
                    android:hint="URL de la imagen"
                    android:textSize="14sp" />

            </LinearLayout>
        </LinearLayout>
    </LinearLayout>
</FrameLayout>
```


## dialog borrar

```<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".dialogues.DialogDeleteAviso">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp"
        android:gravity="center">

        <TextView
            android:id="@+id/tvDeleteMessage"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:text="¿Estás seguro de que quieres eliminar este aviso?"
            android:textSize="16sp"
            android:paddingBottom="16dp" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center">

            <Button
                android:id="@+id/btnCancelDelete"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Cancelar"
                android:layout_marginEnd="16dp" />

            <Button
                android:id="@+id/btnConfirmDelete"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Eliminar" />
        </LinearLayout>
    </LinearLayout>
</FrameLayout>
```

## dialog editar

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".dialogues.DialogEditAviso">

    <LinearLayout
        android:layout_width="307dp"
        android:layout_height="264dp"
        android:orientation="horizontal"
        android:paddingBottom="8dp"
        android:weightSum="5">

        <LinearLayout
            android:layout_width="261dp"
            android:layout_height="258dp"
            android:layout_weight="2.7"
            android:orientation="vertical"
            android:padding="4dp">

            <EditText
                android:id="@+id/etNombre"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingBottom="7dp"
                android:hint="Nombre"
                android:textSize="18sp" />

            <EditText
                android:id="@+id/etDireccion"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingBottom="7dp"
                android:hint="Dirección"
                android:textSize="16sp" />

            <EditText
                android:id="@+id/etFecha"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingBottom="7dp"
                android:hint="Fecha"
                android:textSize="14sp" />

            <EditText
                android:id="@+id/etDescripcion"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingBottom="7dp"
                android:hint="Descripción"
                android:textSize="14sp" />

            <EditText
                android:id="@+id/etImagen"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:paddingBottom="7dp"
                android:hint="URL de la imagen"
                android:textSize="14sp" />
        </LinearLayout>
    </LinearLayout>
</FrameLayout>
```
# VERSION 1.3

En esta versión he implementado un Login y un Registro mediante Firebase, para ello he tenido que ir a la página de Firebase y seguir una serie de pasos descargando un json que se encuentra en la vista de proyecto, lamentablemente no puedo mostrarlo por aquí ya que eso vulneraría la seguridad de mi App.

Para el Login además he implementado Preferencias Compartidas para que después de loguearnos, si cerramos la aplicación y la volvemos a abrir no empezaremos desde el Login sino que empezamos directamente con el Activity de los avisos.

Para iniciar sesión nos debemos registrar y para ello nos llegará un correo a Gmail con el enlace que activará nuestra cuenta una vez nos hayamos registrado.
Todas estas funcionalidades se muestran en el video que he subido a mi tarea de Moddle y para cualquier duda bastará con revisar el código
