package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.extensions.formataParaMoedaBrasileira
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Product

class ProductListAdapter(
    private val context: Context,
    products: List<Product>,
    var quandoClicaNoItem: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val produtos = products.toMutableList()

    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    quandoClicaNoItem(product)
                }
            }
        }

        fun vincula(product: Product) {
            this.product = product
            val nome = binding.produtoItemNome
            nome.text = product.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = product.descricao
            val valor = binding.produtoItemValor
            val valorEmMoeda: String = product.valor
                .formataParaMoedaBrasileira()
            valor.text = valorEmMoeda

            val visibilidade = if (product.imagem != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.imageView.visibility = visibilidade

            binding.imageView.tentaCarregarImagem(product.imagem)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = produtos.size

    fun atualiza(products: List<Product>) {
        this.produtos.clear()
        this.produtos.addAll(products)
        notifyDataSetChanged()
    }

}
