package com.grgroup.hexabuild.servicesapi

import android.util.Log
import com.grgroup.hexabuild.KapilHomesApplication
import com.grgroup.hexabuild.utils.SharedPref
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstanceTwo {

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
//            .baseUrl("http://13.234.192.218:65495")// testing
//            .baseUrl("http://13.234.192.218:65448")
            .baseUrl("http://13.234.192.218:65486")//newreff

            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val api: ConnectTwo by lazy {
        retrofit.create(ConnectTwo::class.java)
    }

}