package es.utad.entreactividades.activities


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import es.utad.entreactividades.MainActivity
import es.utad.entreactividades.R
import es.utad.entreactividades.model.User
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class FormActivity : AppCompatActivity() {
    var registro:Boolean = true
    var usuario = User()

    lateinit var editTextUsuario:EditText
    lateinit var editTextPassword:EditText
    lateinit var editTextNombre:EditText
    lateinit var editTextApellidos:EditText

    lateinit var buttonCancelar: Button
    lateinit var buttonActualizar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        editTextApellidos = findViewById<EditText>(R.id.editTextApellidos)

        buttonCancelar = findViewById<Button>(R.id.buttonCancelar)


        registro = intent.getBooleanExtra("registro", true)

        if (!registro){
            var bundle:Bundle = intent.getBundleExtra("usuario")
            usuario.setBundle(bundle)

            editTextUsuario.setText(usuario.usuario)
            editTextPassword.setText(usuario.password)
            editTextNombre.setText(usuario.nombre)
            editTextApellidos.setText(usuario.apellidos)

            editTextUsuario.isEnabled = false
            editTextPassword.isEnabled = false
            editTextNombre.isEnabled = false
            editTextApellidos.isEnabled = false

            buttonCancelar.visibility = View.INVISIBLE


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




        val json = JSONArray()



        //creando el objeto nuevo
        var jsonObjeto = JSONObject()

        jsonObjeto.put("usuario", usuario.usuario)
        jsonObjeto.put("password", usuario.password);
        jsonObjeto.put("nombre", usuario.nombre);
        jsonObjeto.put("apellidos", usuario.apellidos);

        for (i in 0 until json.length()) {
            val item = json.getJSONObject(i)

            if (item.get("usuario") == jsonObjeto.get("usuario")) {

                json.put(i, jsonObjeto)
            }


        }


        var nombreFichero = "ficheroJSONInterno.json"

        val jsonString: String = json.toString()

        val file= File(nombreFichero)
        file.writeText(jsonString)




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

            val json = JSONArray(leer())

            //return jsonObject;


            //creando el objeto nuevo
            var jsonObjeto = JSONObject()

            jsonObjeto.put("usuario", usuario.usuario)
            jsonObjeto.put("password", usuario.password);
            jsonObjeto.put("nombre", usuario.nombre);
            jsonObjeto.put("apellidos", usuario.apellidos);

            json.put(jsonObjeto)

            var jsonInterno = "ficheroJSONInterno.json"

            val jsonString: String = json.toString()

            var fileOutput = openFileOutput(jsonInterno, Context.MODE_PRIVATE)
            fileOutput.write(jsonString.toByteArray())
            fileOutput.close()


            finish()
        }
        else{
            finish()
        }
    }

    private fun leer(): String {

        val stringBuilder: StringBuilder = StringBuilder()
        try {
            var bufferedReader = BufferedReader(InputStreamReader(openFileInput("ficheroJSONInterno.json")))


            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            bufferedReader.close()
            return stringBuilder.toString()
        } catch (e: IOException) {
            throw e
        }


    }


}