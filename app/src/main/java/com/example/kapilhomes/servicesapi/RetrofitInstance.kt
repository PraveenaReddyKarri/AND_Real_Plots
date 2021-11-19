package com.example.kapilhomes.servicesapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    private val retrofit by lazy {

//        val interceptor = label@ Interceptor { chain: Interceptor.Chain ->
//            val token: String =
//                SharedPref(requireContext()).getIntrocudedId().toString()
//            val original = chain.request()
//            val request = original.newBuilder()
//                .header("Authorization", "Bearer $token")
//                .method(original.method, original.body)
//                .build()
//            val response = chain.proceed(request)
//            Log.d("MyApp", "Code : " + response.code)
//            response
//        }


        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()



        Retrofit.Builder()
            .baseUrl("http://13.234.192.218:65493")// testing
//            .baseUrl("http://13.234.192.218:65446")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val api: Connect by lazy {
        retrofit.create(Connect::class.java)
    }

}