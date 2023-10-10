package com.andres.formativai_segundocorte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class ShowMovies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_movies)

        val linearLayoutMovies = findViewById<LinearLayout>(R.id.linearLayoutMovies)

        // Obtener la lista de películas desde la base de datos
        val movieRepository = MovieRepository(this)
        val moviesList = movieRepository.getAllMovies()  // Implementa esta función en MovieRepository

        // Mostrar los detalles de cada película en un TextView
        moviesList?.forEach { movie ->
            val movieDetailsLayout = layoutInflater.inflate(R.layout.item_movie, null)
            val textViewCode = movieDetailsLayout.findViewById<TextView>(R.id.textViewCode)
            val textViewMovieName = movieDetailsLayout.findViewById<TextView>(R.id.textViewMovieName)
            val textViewDirector = movieDetailsLayout.findViewById<TextView>(R.id.textViewDirector)
            val textViewGenre = movieDetailsLayout.findViewById<TextView>(R.id.textViewGenre)

            // Mostrar la información de la película
            textViewCode.text = "Code: ${movie.code}"
            textViewMovieName.text = "Name: ${movie.movieName}"
            textViewDirector.text = "Director: ${movie.director}"
            textViewGenre.text = "Genre: ${movie.genre}"

            // Agregar el botón de eliminar con el listener
            val btnDelete = movieDetailsLayout.findViewById<Button>(R.id.btnDelete)
            btnDelete.setOnClickListener {
                // Eliminar la película cuando se hace clic en el botón
                movieRepository.deleteMovie(movie.code)
                // Actualizar la vista
                linearLayoutMovies.removeView(movieDetailsLayout)
            }

            // Agregar el botón de editar con el listener
            val btnEdit = movieDetailsLayout.findViewById<Button>(R.id.btnEdit)
            btnEdit.setOnClickListener {
                // Crear un Intent para abrir UpdateMovie y pasar datos
                val intent = Intent(this@ShowMovies, UpdateMovie::class.java)

                // Pasar los datos de la película al intent
                intent.putExtra("code", movie.code)
                intent.putExtra("movieName", movie.movieName)
                intent.putExtra("director", movie.director)
                intent.putExtra("genre", movie.genre)

                // Iniciar la actividad UpdateMovie
                startActivity(intent)
            }



            linearLayoutMovies.addView(movieDetailsLayout)
        }

    }
}