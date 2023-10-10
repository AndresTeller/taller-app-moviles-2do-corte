package com.andres.formativai_segundocorte
import java.io.Serializable

data class Movie(val code: String, val movieName: String, val director: String, val genre: String) : Serializable
