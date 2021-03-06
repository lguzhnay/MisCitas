package com.guzhnaycorp.miscitas.io

import com.guzhnaycorp.miscitas.io.response.LoginResponse
import com.guzhnaycorp.miscitas.model.Cita
import com.guzhnaycorp.miscitas.model.Doctor
import com.guzhnaycorp.miscitas.model.Schedule
import com.guzhnaycorp.miscitas.model.Specialty
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("especialidades")
    fun getEspecialidades(): Call<ArrayList<Specialty>>

    @GET("especialidades/{especialidad}/doctors")
    fun getDoctors(@Path("especialidad") especialdadId: Int): Call<ArrayList<Doctor>>

    @GET("schedule/hours")
    fun getHours(@Query("doctor_id") doctorId: Int, @Query("date") date: String):
            Call<Schedule>

    @POST("login")
    fun postLogin(@Query("email") email: String, @Query("password") password: String):
            Call<LoginResponse>

    @POST("logout")
    fun postLogout(@Header("Authorization") authHeader: String):Call<Void>

    @GET("citas")
    fun getCitas(@Header("Authorization") authHeader: String):Call<ArrayList<Cita>>

    companion object Factory{
        private const val BASE_URL = "http://citasmedicas.guzhnaycorp.com/api/"

        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}