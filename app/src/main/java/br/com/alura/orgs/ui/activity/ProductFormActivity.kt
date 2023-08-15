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
    private val productDao by lazy {
        AppDatabase.getInstance(this).productDao()
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
        productId = intent.getLongExtra(PRODUCT_ID_KEY, 0L)
    }

    override fun onResume() {
        super.onResume()
        productDao.searchById(productId)?.let {
            fillInFields(it)
        }
    }


    private fun fillInFields(product: Product) {
        url = product.image
        title = "Alterar Produto"
        binding.apply {
            activityFormularioProdutoImagem.tryToLoadImage(product.image)
            activityFormularioProdutoNome.setText(product.name)
            activityFormularioProdutoDescricao.setText(product.description)
            activityFormularioProdutoValor.setText(product.value.toPlainString())
        }
    }

    private fun setupSaveButton() {
        val saveButton = binding.activityFormularioProdutoBotaoSalvar
        saveButton.setOnClickListener {
            val newProduct = createProduct()
            productDao.save(newProduct)
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