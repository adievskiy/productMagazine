package com.example.productmagazine

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class Cart : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    private var bitmap: Bitmap? = null
    private val products: MutableList<Product> = mutableListOf()

    private lateinit var toolbarCart: Toolbar
    private lateinit var editImageIV: ImageView
    private lateinit var listViewLV: ListView
    private lateinit var productNameET: EditText
    private lateinit var productPriceET: EditText
    private lateinit var saveBTN: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarCart = findViewById(R.id.toolbarCart)
        setSupportActionBar(toolbarCart)
        title = "Шестерочка"

        editImageIV = findViewById(R.id.editImageIV)
        listViewLV = findViewById(R.id.listViewLV)
        productNameET = findViewById(R.id.productNameET)
        productPriceET = findViewById(R.id.productPriceET)
        saveBTN = findViewById(R.id.saveBTN)

        editImageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener {
            val productName = productNameET.text.toString()
            val productPrice = productPriceET.text.toString()
            val personImage = bitmap
            val product = Product(productName, productPrice, personImage)
            products.add(product)

            val listAdapter = ListAdapter(this@Cart, products)
            listViewLV.adapter = listAdapter
            listAdapter.notifyDataSetChanged()
            productNameET.text.clear()
            productPriceET.text.clear()
            editImageIV.setImageResource(R.drawable.ic_add)
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageIV = findViewById(R.id.editImageIV)
        when(requestCode) {
            GALLERY_REQUEST -> if (requestCode === RESULT_OK) {
                val selectedImage: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                editImageIV.setImageBitmap(bitmap)
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