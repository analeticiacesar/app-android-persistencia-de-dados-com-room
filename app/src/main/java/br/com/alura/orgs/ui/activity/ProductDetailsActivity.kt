package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductDetailsBinding
import br.com.alura.orgs.extensions.formatForBrazilianCurrency
import br.com.alura.orgs.extensions.tryToLoadImage
import br.com.alura.orgs.model.Product

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var product: Product
    private val binding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryToLoadProduct()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(::product.isInitialized){
            val db = AppDatabase.getInstance(this)
            val productDao = db.productDao()
            when(item.itemId) {
                R.id.item_edit -> {

                }
                R.id.item_delete -> {
                    productDao.delete(product)
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun tryToLoadProduct() {
        intent.getParcelableExtra<Product>(PRODUCT_KEY)?.let { loadedProduct ->
            product = loadedProduct
            fillInFields(loadedProduct)
        } ?: finish()
    }

    private fun fillInFields(loadedProduct: Product) {
        with(binding) {
            activityDetalhesProdutoImagem.tryToLoadImage(loadedProduct.image)
            activityDetalhesProdutoNome.text = loadedProduct.name
            activityDetalhesProdutoDescricao.text = loadedProduct.description
            activityDetalhesProdutoValor.text =
                loadedProduct.value.formatForBrazilianCurrency()
        }
    }

}