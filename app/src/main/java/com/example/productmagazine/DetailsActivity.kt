package com.example.productmagazine

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

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

        toolbar = findViewById(R.id.toolbarDetails)
        setSupportActionBar(toolbar)
        title = "Шестерочка"

        fullImageViewIV = findViewById(R.id.fullImageViewIV)

        fullNameTV = findViewById(R.id.fullNameTV)
        fullPriceTV = findViewById(R.id.fullPriceTV)
        fullDescription = findViewById(R.id.fullDescriptionTV)

        val product = intent.getSerializableExtra("product") as Product
        fullImageViewIV.setImageURI(Uri.parse(product.image))
        fullNameTV.text = product.productName
        fullPriceTV.text = product.productPrice
        fullDescription.text = product.productDescription

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuExit -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}