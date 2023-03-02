package android.local.fliptreetest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<productItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.recycler_view)
        list= ArrayList()
        val layoutManager=GridLayoutManager(this,3)
        var adapter=MyAdapter(list)
        recyclerView.layoutManager=layoutManager
        val retrofit:Retrofit=Retrofit.Builder().baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val api:ApiService=retrofit.create(ApiService::class.java)
        val call: Call<product> = api.getAllData()
        call.enqueue(object: Callback<product?>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<product?>, response: Response<product?>) {
                if (response.isSuccessful){
                    list.clear()
                    for(myData in response.body()!!){
                        list.add(myData)
                    }
                   recyclerView.adapter= adapter
                }
            }
            override fun onFailure(call: Call<product?>, t: Throwable) {
               Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_SHORT).show()
            }
        })
        product_search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }
        })
    }
}



