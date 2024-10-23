package com.example.productmagazine

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {

    private lateinit var fullImageViewIV: ImageView

    private lateinit var fullNameTV: TextView
    private lateinit var fullPriceTV: TextView
    private lateinit var fullDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fullImageViewIV = findViewById(R.id.fullImageViewIV)

        fullNameTV = findViewById(R.id.fullNameTV)
        fullPriceTV = findViewById(R.id.fullPriceTV)
        fullDescription = findViewById(R.id.fullDescriptionTV)

        val product = intent.getSerializableExtra("product") as Product
        val photoUri = intent.getStringExtra("uri")?.toUri()
        fullImageViewIV.setImageURI(photoUri)
        fullNameTV.text = product.productName
        fullPriceTV.text = product.productPrice
        fullDescription.text = product.productDescription

    }
}