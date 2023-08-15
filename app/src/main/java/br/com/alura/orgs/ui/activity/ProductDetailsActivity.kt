package br.com.alura.orgs.ui.activity

import android.content.Intent
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

    private var productId: Long = 0L
    private var product: Product? = null
    private val binding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }
    private val productDao by lazy {
        AppDatabase.getInstance(this).productDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryToLoadProduct()
    }

    override fun onResume() {
        super.onResume()
        searchProduct()
    }

    private fun searchProduct() {
        product = productDao.searchById(productId)
        product?.let {
            fillInFields(it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId) {
                R.id.item_edit -> {
                    Intent(this, ProductFormActivity::class.java).apply {
                        putExtra(PRODUCT_ID_KEY, productId)
                        startActivity(this)
                    }
                }
                R.id.item_delete -> {
                    product?.let {
                        productDao.delete(it)
                    }
                    finish()
                }
            }
        return super.onOptionsItemSelected(item)
    }
    private fun tryToLoadProduct() {
        productId = intent.getLongExtra(PRODUCT_ID_KEY, 0L)
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