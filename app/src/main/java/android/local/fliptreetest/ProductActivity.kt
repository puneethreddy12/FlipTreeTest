package android.local.fliptreetest

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide



class   ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        var selected = findViewById(R.id.selectedImage) as ImageView
        val i = intent
        val image = i.getStringExtra("image")
        Glide.with(this@ProductActivity).load(image).centerCrop().into(selected)
    }
}