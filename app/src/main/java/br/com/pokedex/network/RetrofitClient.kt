package br.com.pokedex.network

//import com.google.firebase.appdistribution.gradle.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // IMPORTANTE: Coloque o IP da sua VPS aqui (não use localhost, pois localhost é o celular!)
    // Mantenha o "http://" e a porta ":8000/"
    private const val BASE_URL = "http://161.97.156.235:8000/"

    // Cria a instância do Retrofit
    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}