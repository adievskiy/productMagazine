package com.example.productmagazine

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 312
    private lateinit var photoUri: Uri

    private lateinit var toolbar: Toolbar

    private lateinit var fullImageViewIV: ImageView

    private lateinit var fullNameTV: TextView
    private lateinit var fullPriceTV: TextView
    private lateinit var fullDescription: TextView

    private lateinit var saveDetailsBTN: Button

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

        toolbar = findViewById(R.id.toolbarDetails)
        setSupportActionBar(toolbar)
        title = "Шестерочка"

        fullImageViewIV = findViewById(R.id.fullImageViewIV)

        fullNameTV = findViewById(R.id.fullNameTV)
        fullPriceTV = findViewById(R.id.fullPriceTV)
        fullDescription = findViewById(R.id.fullDescriptionTV)

        saveDetailsBTN = findViewById(R.id.saveDetailsBTN)

        val product = intent.getSerializableExtra("product") as Product
        val products = intent.getSerializableExtra("products")
        val item = intent.extras?.getInt("item")
        var check = intent.extras?.getBoolean("check")

        fullImageViewIV.setImageURI(Uri.parse(product.image))
        fullNameTV.text = product.productName
        fullPriceTV.text = product.productPrice
        fullDescription.text = product.productDescription

        fullImageViewIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveDetailsBTN.setOnClickListener {
            val product = Product(
                fullNameTV.text.toString(),
                fullPriceTV.text.toString(),
                fullDescription.text.toString(),
                product.image
            )
            val list: MutableList<Product> = products as MutableList<Product>
            if (item != null) {
                swap(item, product, products)
            }
            check = false
            val intent = Intent(this, Cart::class.java)
            intent.putExtra("list", list as ArrayList<Product>)
            intent.putExtra("newCheck", check)
            startActivity(intent)
            finish()
        }

    }

    private fun swap(item: Int, product: Product, products: MutableList<Product>) {
        products.add(item + 1, product)
        products.removeAt(item)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                photoUri = uri
                fullImageViewIV.setImageURI(photoUri)
            }
        }
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