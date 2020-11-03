package es.utad.entreactividades.utils

import android.app.Activity
import com.google.gson.Gson
import es.utad.entreactividades.model.User
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileManager {
    private val gson = Gson()

    fun leer(activity: Activity): List<User> {


        val stringBuilder: StringBuilder = StringBuilder()
        try {
            val bufferedReader =
                BufferedReader(InputStreamReader(activity.openFileInput("ficheroJSONInterno.json")))


            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            bufferedReader.close()

        } catch (e: IOException) {
            throw e
        }

        return gson.fromJson(stringBuilder.toString(), Array<User>::class.java).toList()
    }


}