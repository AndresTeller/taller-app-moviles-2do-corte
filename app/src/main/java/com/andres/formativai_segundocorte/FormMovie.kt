package com.andres.formativai_segundocorte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class FormMovie : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_movie)

        // Obtener referencias a los elementos de la interfaz de usuario
        val etCode = findViewById<EditText>(R.id.etCode)
        val etMovieName = findViewById<EditText>(R.id.etMovieName)
        val etDirector = findViewById<EditText>(R.id.etDirector)
        val spGenreMovie = findViewById<Spinner>(R.id.spGenreMovie)
        val btnAddMovie = findViewById<Button>(R.id.btnAddMovie)
        val btnCancelMovie = findViewById<Button>(R.id.btnCancelMovie)

        // Configurar valores para el Spinner
        val genres = arrayOf("ACCION", "DRAMA", "MISTERIO", "TERROR")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spGenreMovie.adapter = adapter

        // Configurar acción para el botón "Add Movie"
        btnAddMovie.setOnClickListener {
            val code = etCode.text.toString()
            val movieName = etMovieName.text.toString()
            val director = etDirector.text.toString()
            val genre = spGenreMovie.selectedItem.toString()

            val movie = Movie(code, movieName, director, genre)

            // Obtener una instancia de MovieRepository
            val movieRepository = MovieRepository(this)

            // Llamar a la función para agregar la película a la base de datos
            movieRepository.addMovie(movie)

            // Mostrar un mensaje de confirmación
            Toast.makeText(this, "Movie added successfully!", Toast.LENGTH_SHORT).show()



            // Leer la película recién agregada y mostrar sus detalles
            val addedMovie = movieRepository.getMovie(code)
            if (addedMovie != null) {
                val movieDetailsMessage = "Added Movie Details:\n" +
                        "Code: ${addedMovie.code}\n" +
                        "Movie name: ${addedMovie.movieName}\n" +
                        "Director: ${addedMovie.director}\n" +
                        "Genre: ${addedMovie.genre}"
                Toast.makeText(this, movieDetailsMessage, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to retrieve added movie details.", Toast.LENGTH_LONG).show()
            }


        }

        // Configurar acción para el botón "Cancel Movie"
        btnCancelMovie.setOnClickListener {
            etCode.text.clear()
            etMovieName.text.clear()
            etDirector.text.clear()
            spGenreMovie.setSelection(0)
        }
    }

}