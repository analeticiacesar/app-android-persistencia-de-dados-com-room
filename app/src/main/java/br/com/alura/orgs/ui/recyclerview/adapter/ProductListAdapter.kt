package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.databinding.ItemProductBinding
import br.com.alura.orgs.extensions.formatForBrazilianCurrency
import br.com.alura.orgs.extensions.tryToLoadImage
import br.com.alura.orgs.model.Product

class ProductListAdapter(
    private val context: Context,
    products: List<Product> = emptyList(),
    var clickItem: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val product = products.toMutableList()

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    clickItem(product)
                }
            }
        }

        fun bind(product: Product) {
            this.product = product
            val name = binding.produtoItemNome
            name.text = product.name
            val description = binding.produtoItemDescricao
            description.text = product.description
            val value = binding.produtoItemValor
            val currencyValue: String = product.value
                .formatForBrazilianCurrency()
            value.text = currencyValue

            val visibility = if (product.image != null) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.imageView.visibility = visibility

            binding.imageView.tryToLoadImage(product.image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = product[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = product.size

    fun update(products: List<Product>) {
        this.product.clear()
        this.product.addAll(products)
        notifyDataSetChanged()
    }

}
