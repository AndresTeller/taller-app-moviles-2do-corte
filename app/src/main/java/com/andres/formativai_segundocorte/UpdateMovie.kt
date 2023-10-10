package com.andres.formativai_segundocorte

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast


class UpdateMovie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_movie)

        // Obtener referencias a los elementos de la interfaz de usuario
        val etEditCode = findViewById<EditText>(R.id.etEditCode)
        val etEditMovieName = findViewById<EditText>(R.id.etEditMovieName)
        val etEditDirector = findViewById<EditText>(R.id.etEditDirector)
        val spEditGenreMovie = findViewById<Spinner>(R.id.spEditGenreMovie)
        val btnEditMovie = findViewById<Button>(R.id.btnEditMovie)
        val btnEditCancelMovie = findViewById<Button>(R.id.btnEditCancelMovie)


        // Obtener datos de la película de la intent
        val code = intent.getStringExtra("code")
        val movieName = intent.getStringExtra("movieName")
        val director = intent.getStringExtra("director")
        val genre = intent.getStringExtra("genre")

        // Mostrar datos en los campos
        etEditCode.setText(code)
        etEditMovieName.setText(movieName)
        etEditDirector.setText(director)

        // Deshabilitar interacciones para etEditCode
        etEditCode.isEnabled = false
        etEditCode.isFocusable = false
        etEditCode.isClickable = false


        // Configurar el spinner
        val genres = arrayOf("Acción", "Drama", "Misterio", "Terror")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEditGenreMovie.adapter = adapter

        // Seleccionar el género si coincide con alguno en el spinner
        val genreIndex = genres.indexOf(genre)
        if (genreIndex != -1) {
            spEditGenreMovie.setSelection(genreIndex)
        }

        // Eliminar campos
        btnEditCancelMovie.setOnClickListener {
            // Limpiar los campos
            etEditCode.text.clear()
            etEditMovieName.text.clear()
            etEditDirector.text.clear()
            spEditGenreMovie.setSelection(0)  // Establecer la selección del Spinner al primer elemento
        }

        //Editar los datos
        btnEditMovie.setOnClickListener {
            // Obtener los valores de los campos
            val updatedCode = etEditCode.text.toString()
            val updatedMovieName = etEditMovieName.text.toString()
            val updatedDirector = etEditDirector.text.toString()
            val updatedGenre = spEditGenreMovie.selectedItem.toString()

            if (updatedMovieName.isBlank() || updatedDirector.isBlank()) {
                Toast.makeText(this, "No pueden quedar campos vacios.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear un objeto Movie con los valores actualizados
            val updatedMovie = Movie(updatedCode, updatedMovieName, updatedDirector, updatedGenre)

            // Obtener una instancia de MovieRepository
            val movieRepository = MovieRepository(this)

            // Llamar al método para actualizar la película
            movieRepository.updateMovie(updatedMovie)

            // Mostrar un mensaje de éxito
            Toast.makeText(this, "Movie updated successfully!", Toast.LENGTH_SHORT).show()

            // Enviar la película actualizada de vuelta a ShowMovies
            val resultIntent = Intent()
            resultIntent.putExtra("updated_movie", updatedMovie)
            setResult(Activity.RESULT_OK, resultIntent)

            // Iniciar la actividad MainActivity
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)

            finish() // Finalizar la actividad UpdateMovie
        }


    }
}

