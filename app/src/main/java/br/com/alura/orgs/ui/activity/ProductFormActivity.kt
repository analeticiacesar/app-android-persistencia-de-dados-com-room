package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductFormBinding
import br.com.alura.orgs.extensions.tryToLoadImage
import br.com.alura.orgs.model.Product
import br.com.alura.orgs.ui.dialog.ImageFormDialog
import java.math.BigDecimal

class ProductFormActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProductFormBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var productId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        setupSaveButton()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            ImageFormDialog(this)
                .show(url) { image ->
                    url = image
                    binding.activityFormularioProdutoImagem.tryToLoadImage(url)
                }
        }
        intent.getParcelableExtra<Product>(PRODUCT_KEY)?.let { loadedProduct ->
            productId = loadedProduct.id
            url = loadedProduct.image
            title = "Alterar Produto"
            binding.apply {
                activityFormularioProdutoImagem.tryToLoadImage(loadedProduct.image)
                activityFormularioProdutoNome.setText(loadedProduct.name)
                activityFormularioProdutoDescricao.setText(loadedProduct.description)
                activityFormularioProdutoValor.setText(loadedProduct.value.toPlainString())
            }

        }
    }

    private fun setupSaveButton() {
        val saveButton = binding.activityFormularioProdutoBotaoSalvar
        val db = AppDatabase.getInstance(this)
        val productDao = db.productDao()
        saveButton.setOnClickListener {
            val newProduct = createProduct()
            if(productId > 0) {
                productDao.update(newProduct)
            }
            else {
                productDao.save(newProduct)
            }
            finish()
        }
    }

    private fun createProduct(): Product {
        val fieldName = binding.activityFormularioProdutoNome
        val name = fieldName.text.toString()
        val fieldDescription = binding.activityFormularioProdutoDescricao
        val description = fieldDescription.text.toString()
        val fieldValue = binding.activityFormularioProdutoValor
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