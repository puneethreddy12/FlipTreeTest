package android.local.fliptreetest

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class MyAdapter(val data: ArrayList<productItem>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var productFilterList = ArrayList<productItem>()
    init {
        productFilterList = data
    }
    class MyViewHolder(val itemview: View): RecyclerView.ViewHolder(itemview){
            val title = itemview.findViewById<TextView>(R.id.tvTitle)
           val imageView = itemView.findViewById<ImageView>(R.id.imageView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(v)
    }
    override fun getItemCount(): Int {
        return productFilterList.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=data[position]
        holder.apply {
            title.text=currentItem.title
            Glide.with(imageView.context).load(currentItem.image).centerCrop().into(imageView)
        }
        holder.imageView.setOnClickListener {
            val intent=Intent(it.context,ProductActivity::class.java)
            intent.putExtra("image",currentItem.image)
            it.context.startActivity(intent)

        }
        val selectCountryTextView =
            holder.title.findViewById<TextView>(R.id.tvTitle)
        selectCountryTextView.text = productFilterList[position].toString()
    }
     fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    productFilterList = data
                } else {
                    val resultList = ArrayList<productItem>()
                    for (row in data) {
                        if( row.title.lowercase(Locale.ROOT)
                            .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    productFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = productFilterList
                return filterResults
            }
            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productFilterList = results?.values as ArrayList<productItem>
                notifyDataSetChanged()
            }
        }
    }
}





