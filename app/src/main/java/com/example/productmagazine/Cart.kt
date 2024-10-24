package com.example.productmagazine

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Cart : AppCompatActivity(), Updatable, Removable {

    private val GALLERY_REQUEST = 302
    private var photoUri: Uri? = null
    private val products: MutableList<Product> = mutableListOf()
    private var listAdapter: ListAdapter? = null
    private var item: Int? = null

    private lateinit var toolbarCart: Toolbar
    private lateinit var editImageIV: ImageView
    private lateinit var listViewLV: ListView
    private lateinit var productNameET: EditText
    private lateinit var productPriceET: EditText
    private lateinit var productDescriptionET: EditText
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
        productDescriptionET = findViewById(R.id.productDescriptionET)
        saveBTN = findViewById(R.id.saveBTN)

        editImageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener {
            val product = createProduct()
            products.add(product)

            listAdapter = ListAdapter(this@Cart, products)
            listViewLV.adapter = listAdapter
            eraseEditableFields()
            editImageIV.setImageResource(R.drawable.ic_add)
            listAdapter?.notifyDataSetChanged()
        }

        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val product = listAdapter!!.getItem(position)
                item = position
                val dialog = MyAlertDialog()
                val args = Bundle()
                args.putSerializable("product", product)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
        }
    }

    private fun createProduct(): Product {
        val productName = productNameET.text.toString()
        val productPrice = productPriceET.text.toString()
        val productDescription = productDescriptionET.text.toString()
        val personImage = photoUri.toString()
        val product = Product(productName, productPrice, productDescription, personImage)
        return product
    }

    private fun eraseEditableFields() {
        productNameET.text.clear()
        productPriceET.text.clear()
        productDescriptionET.text.clear()
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            photoUri = data?.data
            editImageIV.setImageURI(photoUri)
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

    override fun remove(product: Product) {
        listAdapter?.remove(product)
    }

    override fun update(product: Product) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("product", product)
        intent.putExtra("uri", photoUri)
        startActivity(intent)
    }
}