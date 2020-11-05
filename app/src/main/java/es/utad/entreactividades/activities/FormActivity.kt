package es.utad.entreactividades.activities

import android.app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

import java.io.File

import es.utad.entreactividades.MainActivity
import es.utad.entreactividades.R
import es.utad.entreactividades.model.User
import es.utad.entreactividades.utils.FileManager



class FormActivity : AppCompatActivity() {
    var registro:Boolean = true
    var usuario = User()
    lateinit var editTextUsuario:EditText
    lateinit var editTextPassword:EditText
    lateinit var editTextNombre:EditText
    lateinit var editTextApellidos:EditText
    lateinit var buttonActualizar: Button
    lateinit var buttonAceptar: Button
    lateinit var buttonCancelar: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        editTextApellidos = findViewById<EditText>(R.id.editTextApellidos)
        buttonAceptar = findViewById<Button>(R.id.buttonAceptar)
        buttonActualizar = findViewById<Button>(R.id.buttonActualizar)
        buttonCancelar = findViewById<Button>(R.id.buttonCancelar)
        buttonActualizar.visibility = View.INVISIBLE


        registro = intent.getBooleanExtra("registro", true)

        if (!registro){
            var bundle:Bundle = intent.getBundleExtra("usuario")
            usuario.setBundle(bundle)


            editTextUsuario.setText(usuario.usuario)
            editTextPassword.setText(usuario.password)
            editTextNombre.setText(usuario.nombre)
            editTextApellidos.setText(usuario.apellidos)
            editTextUsuario.isEnabled = false
            buttonAceptar.visibility = View.INVISIBLE
            buttonActualizar.visibility = View.VISIBLE
            buttonCancelar.visibility = View.VISIBLE
        }
    }

    fun onActualizar(view: View) {


        usuario.usuario = editTextUsuario.text.toString()
        usuario.password = editTextPassword.text.toString()
        usuario.nombre = editTextNombre.text.toString()
        usuario.apellidos = editTextApellidos.text.toString()

        var resultIntent = Intent(this, MainActivity::class.java)
        resultIntent.putExtra("usuario", usuario.getBundle())
        setResult(Activity.RESULT_OK, resultIntent)
        val json = FileManager.leer(this)




        for (i in json.indices) {
            val item = json[i]

            if (item.usuario ==usuario.usuario) {
                json[i] = usuario
            }
        }


        FileManager.escribir(this, json)

        finish()
    }



    fun onCancelar(view: View) {
        finish()
    }
    fun onAceptar(view: View) {
        if (registro) {

            usuario.usuario = editTextUsuario.text.toString()
            usuario.password = editTextPassword.text.toString()
            usuario.nombre = editTextNombre.text.toString()
            usuario.apellidos = editTextApellidos.text.toString()

            var resultIntent = Intent(this, MainActivity::class.java)
            resultIntent.putExtra("usuario", usuario.getBundle())
            setResult(Activity.RESULT_OK, resultIntent)
            var JSON : MutableList<User>

            var fichero = "JsonGuardadoUsuarios.json"
            var file = File(getFilesDir().getAbsolutePath(), fichero)
            var siExisteElJSON = file.exists()

            if(siExisteElJSON){
                JSON = FileManager.leer(this)
            } else {
                JSON = mutableListOf()
            }

            JSON.add(usuario)

            FileManager.escribir(this, JSON)
            finish()
        }
        else{
            finish()
        }
    }


}