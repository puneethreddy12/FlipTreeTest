package android.local.fliptreetest
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getAllData(): Call<product>
}
