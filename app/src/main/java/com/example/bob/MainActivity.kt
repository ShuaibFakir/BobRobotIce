package com.example.bob

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnForward = findViewById<Button>(R.id.btnForward)
        val btnBackward = findViewById<Button>(R.id.btnBackward)
        val btnLeft = findViewById<Button>(R.id.btnLeft)
        val btnRight = findViewById<Button>(R.id.btnRight)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnPlayMusic = findViewById<Button>(R.id.btnPlayMusic)
        val btnStopMusic = findViewById<Button>(R.id.btnStopMusic)

        btnForward.setOnClickListener {
            SendGetRequestTask().execute("http://192.168.4.1/MoveForward")
        }

        btnBackward.setOnClickListener {
            SendGetRequestTask().execute("http://192.168.4.1/MoveBackward")
        }

        btnLeft.setOnClickListener {
            SendGetRequestTask().execute("http://192.168.4.1/MoveLeft")
        }

        btnRight.setOnClickListener {
            SendGetRequestTask().execute("http://192.168.4.1/MoveRight")
        }

        btnStop.setOnClickListener {
            SendGetRequestTask().execute("http://192.168.4.1/Stop")
        }

        btnPlayMusic.setOnClickListener {
            SendGetRequestTask().execute("http://192.168.4.1/PlayMusic")
        }

        btnStopMusic.setOnClickListener {
            SendGetRequestTask().execute("http://192.168.4.1/StopMusic")
        }
    }

    inner class SendGetRequestTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            try {
                val url = URL(params[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000 // Set your desired timeout

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                    return response.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return "Error"
        }

        override fun onPostExecute(result: String?) {

        }
    }
}
