package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.orgs.databinding.ActivityProductDetailsBinding
import br.com.alura.orgs.extensions.formatForBrazilianCurrency
import br.com.alura.orgs.extensions.tryToLoadImage
import br.com.alura.orgs.model.Product

class ProductDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryToLoadProduct()
    }

    private fun tryToLoadProduct() {
        intent.getParcelableExtra<Product>(PRODUCT_KEY)?.let { loadedProduct ->
            fillInFields(loadedProduct)
        } ?: finish()
    }

    private fun fillInFields(productCarregado: Product) {
        with(binding) {
            activityDetalhesProdutoImagem.tryToLoadImage(productCarregado.image)
            activityDetalhesProdutoNome.text = productCarregado.name
            activityDetalhesProdutoDescricao.text = productCarregado.description
            activityDetalhesProdutoValor.text =
                productCarregado.value.formatForBrazilianCurrency()
        }
    }

}