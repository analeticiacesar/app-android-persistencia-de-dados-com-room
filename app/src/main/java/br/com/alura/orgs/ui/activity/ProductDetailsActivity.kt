package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.alura.orgs.databinding.ActivityDetalhesProdutoBinding
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Product

class ProductDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto() {
        intent.getParcelableExtra<Product>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            preencheCampos(produtoCarregado)
        } ?: finish()
    }

    private fun preencheCampos(productCarregado: Product) {
        with(binding) {
            activityDetalhesProdutoImagem.tentaCarregarImagem(productCarregado.imagem)
            activityDetalhesProdutoNome.text = productCarregado.nome
            activityDetalhesProdutoDescricao.text = productCarregado.descricao
            activityDetalhesProdutoValor.text =
                productCarregado.valor.formataParaMoedaBrasileira()
        }
    }

}