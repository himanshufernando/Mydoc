package himanshu.project.mydoc.services.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import himanshu.project.mydoc.BuildConfig
import himanshu.project.mydoc.data.dataModel.Data

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface APIInterface {
    @GET("home.json")
    suspend fun getNews(@Query("api-key") apiKey: String): Data

    companion object {
        val baseUrl = BuildConfig.API_BASE_URL
        fun create(): APIInterface = create(baseUrl.toHttpUrlOrNull()!!)
        fun create(httpUrl: HttpUrl): APIInterface {

            val client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface::class.java)

            // CODE FOR CASH  retrieved DATA USING RETROFIT CASH
            /* var cacheSize = 10 * 1024 * 1024
             var httpCacheDirectory = File(app.cacheDir, "responses")
             var cache = Cache(httpCacheDirectory, cacheSize.toLong())

             var client = OkHttpClient.Builder()
                 .connectTimeout(100, TimeUnit.SECONDS)
                 .readTimeout(100, TimeUnit.SECONDS)
                 .cache(cache).addNetworkInterceptor { chain->
                     val response = chain.proceed(chain.request())
                     val maxAge = 0 // read from cache for 60 seconds even if there is internet connection
                     response.newBuilder()
                         .header("Cache-Control", "public, max-age=$maxAge")
                         .removeHeader("Pragma")
                         .build() }.addInterceptor { chain->
                     var request = chain.request()
                     if (!InternetConnection.checkInternetConnection()) {
                         val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                         request = request.newBuilder()
                             .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                             .removeHeader("Pragma")
                             .build()
                     }
                     chain.proceed(request) }.build()*/
        }



    }

}