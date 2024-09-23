package com.example.testarcanitonefile.data

import okhttp3.*
import retrofit2.*
import retrofit2.converter.gson.*

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "token $token")
            .build()
        return chain.proceed(request)
    }
}

object RetrofitInstance {
    private const val GITHUB_TOKEN =
        "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"

    val api: GitHubApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(GITHUB_TOKEN))
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }
}