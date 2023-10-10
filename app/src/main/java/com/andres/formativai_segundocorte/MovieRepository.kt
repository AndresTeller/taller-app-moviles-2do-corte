package com.andres.formativai_segundocorte
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MovieRepository(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "movie_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_MOVIES = "movies"
        private const val KEY_CODE = "code"
        private const val KEY_MOVIE_NAME = "movie_name"
        private const val KEY_DIRECTOR = "director"
        private const val KEY_GENRE = "genre"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_MOVIES ($KEY_CODE TEXT PRIMARY KEY, $KEY_MOVIE_NAME TEXT, $KEY_DIRECTOR TEXT, $KEY_GENRE TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIES")
        onCreate(db)
    }

    fun addMovie(movie: Movie) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_CODE, movie.code)
            put(KEY_MOVIE_NAME, movie.movieName)
            put(KEY_DIRECTOR, movie.director)
            put(KEY_GENRE, movie.genre)
        }

        db.insert(TABLE_MOVIES, null, values)
        db.close()
    }

    fun getMovie(code: String): Movie? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_MOVIES,
            arrayOf(KEY_CODE, KEY_MOVIE_NAME, KEY_DIRECTOR, KEY_GENRE),
            "$KEY_CODE=?",
            arrayOf(code),
            null,
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            Movie(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )
        } else null
    }

    fun updateMovie(movie: Movie) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_MOVIE_NAME, movie.movieName)
            put(KEY_DIRECTOR, movie.director)
            put(KEY_GENRE, movie.genre)
        }

        db.update(
            TABLE_MOVIES,
            values,
            "$KEY_CODE = ?",
            arrayOf(movie.code)
        )
        db.close()
    }

    fun deleteMovie(code: String) {
        val db = this.writableDatabase
        db.delete(TABLE_MOVIES, "$KEY_CODE = ?", arrayOf(code))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllMovies(): List<Movie> {
        val movieList = mutableListOf<Movie>()
        val selectQuery = "SELECT * FROM $TABLE_MOVIES"

        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val code = cursor.getString(cursor.getColumnIndex(KEY_CODE))
                val movieName = cursor.getString(cursor.getColumnIndex(KEY_MOVIE_NAME))
                val director = cursor.getString(cursor.getColumnIndex(KEY_DIRECTOR))
                val genre = cursor.getString(cursor.getColumnIndex(KEY_GENRE))

                val movie = Movie(code, movieName, director, genre)
                movieList.add(movie)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return movieList
    }

}