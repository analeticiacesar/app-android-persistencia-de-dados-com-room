package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductListBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter

class ProductListActivity : AppCompatActivity() {

    private val adapter = ProductListAdapter(context = this)
    private val binding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        setupFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.databaseInstance(this)
        val productDao = db.productDao()
        adapter.update(productDao.searchAll())
    }

    private fun setupFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            goToProductForm()
        }
    }

     private fun goToProductForm() {
        val intent = Intent(this, ProductFormActivity::class.java)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.clickItem = {
            val intent = Intent(
                this,
                ProductDetailsActivity::class.java
            ).apply {
                putExtra(PRODUCT_KEY, it)
            }
            startActivity(intent)
        }
    }

}