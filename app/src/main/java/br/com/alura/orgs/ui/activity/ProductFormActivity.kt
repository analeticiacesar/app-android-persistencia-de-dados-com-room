package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductFormBinding
import br.com.alura.orgs.extensions.tryToLoadImage
import br.com.alura.orgs.model.Product
import br.com.alura.orgs.ui.dialog.ImageFormDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class ProductFormActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProductFormBinding.inflate(layoutInflater)
    }
    private val productDao by lazy {
        AppDatabase.getInstance(this).productDao()
    }
    private var url: String? = null
    private var productId = 0L
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        setupSaveButton()
        binding.productFormActivityProductImage.setOnClickListener {
            ImageFormDialog(this)
                .show(url) { image ->
                    url = image
                    binding.productFormActivityProductImage.tryToLoadImage(url)
                }
        }
        productId = intent.getLongExtra(PRODUCT_ID_KEY, 0L)
    }

    override fun onResume() {
        super.onResume()
        scope.launch {
            productDao.searchById(productId)?.let {
                withContext(Dispatchers.Main) {
                    fillInFields(it)
                }
            }
        }
    }


    private fun fillInFields(product: Product) {
        url = product.image
        title = "Alterar Produto"
        binding.apply {
            productFormActivityProductImage.tryToLoadImage(product.image)
            productFormActivityProductName.setText(product.name)
            productFormActivityProductDescription.setText(product.description)
            productFormActivityProductValue.setText(product.value.toPlainString())
        }
    }

    private fun setupSaveButton() {
        val saveButton = binding.productFormActivityButtonSave
        saveButton.setOnClickListener {
            val newProduct = createProduct()
            scope.launch {
                productDao.save(newProduct)
                finish()
            }
        }
    }

    private fun createProduct(): Product {
        val fieldName = binding.productFormActivityProductName
        val name = fieldName.text.toString()
        val fieldDescription = binding.productFormActivityProductDescription
        val description = fieldDescription.text.toString()
        val fieldValue = binding.productFormActivityProductValue
        val textValue = fieldValue.text.toString()
        val value = if (textValue.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(textValue)
        }

        return Product(
            id = productId,
            name = name,
            description = description,
            value = value,
            image = url
        )
    }

}