package com.example.productmagazine

import android.annotation.SuppressLint
import android.app.Person
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {

    private lateinit var imageViewDesIV: ImageView

    private lateinit var productNameDesTV: TextView
    private lateinit var productPriceDesTV: TextView
    private lateinit var productDescriptionDesTV: TextView

    private var product: Product? = null
    var photoUri: Uri? = null
    private var products: ArrayList<Product>? = null
    private var position: Int = -1
    private var check = true


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageViewDesIV = findViewById(R.id.imageViewDesIV)

        productNameDesTV = findViewById(R.id.productNameDesTV)
        productPriceDesTV = findViewById(R.id.productPriceDesTV)
        productDescriptionDesTV = findViewById(R.id.productDescriptionDesTV)

        intent.let {
            product = it.getSerializableExtra("product") as Product
            photoUri = it.getStringExtra("photo")?.toUri()
        }

        productNameDesTV.text = product?.productName
        productPriceDesTV.text = product?.productPrice
        productDescriptionDesTV.text = product?.productDescription
        imageViewDesIV.setImageURI(photoUri)
    }
}