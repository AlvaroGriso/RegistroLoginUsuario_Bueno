package es.utad.entreactividades.utils

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import es.utad.entreactividades.model.User
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileManager {
    private val gson = Gson()

    fun leer(activity: Activity): MutableList<User> {


        val stringBuilder: StringBuilder = StringBuilder()
        try {
            val bufferedReader =
                BufferedReader(InputStreamReader(activity.openFileInput("JsonGuardadoUsuarios.json")))


            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            bufferedReader.close()

        } catch (e: IOException) {
            throw e
        }

        return gson.fromJson(stringBuilder.toString(), Array<User>::class.java).toMutableList()
    }

    fun escribir(activity: Activity, list: MutableList<User>): Boolean {

        try {
            val jsonString: String = gson.toJson(list)
            var fileOutput =
                activity.openFileOutput("JsonGuardadoUsuarios.json", Context.MODE_PRIVATE)
            fileOutput.write(jsonString.toByteArray())
            fileOutput.close()
            return true
        } catch (e: IOException) {
            return false
        }


    }


}