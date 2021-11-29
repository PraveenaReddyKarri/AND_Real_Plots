package com.grgroup.kapilhomes.servicesapi

import android.util.Log
import com.grgroup.kapilhomes.KapilHomesApplication
import com.grgroup.kapilhomes.utils.SharedPref
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    private val retrofit by lazy {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)


        val interceptor =  Interceptor { chain: Interceptor.Chain ->
            val token: String =
                SharedPref(KapilHomesApplication.getInstance()!!).getAccessKey().toString()
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer $token")
                .method(original.method, original.body)
                .build()
            val response = chain.proceed(request)
            Log.d("MyApp", "Code : " + response.code)
            response
        }


        // val interceptor = HttpLoggingInterceptor()
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(interceptor)
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .build()



        Retrofit.Builder()
//            .baseUrl("http://13.234.192.218:65493")// testing
            .baseUrl("http://13.234.192.218:65446")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val api: Connect by lazy {
        retrofit.create(Connect::class.java)
    }

}