package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductListBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        val db = AppDatabase.getInstance(this)
        val productDao = db.productDao()
        val scope = MainScope()
        scope.launch {
            val products = withContext(Dispatchers.IO) {
                productDao.searchAll()
            }
            adapter.update(products)
        }
    }

    private fun setupFab() {
        val fab = binding.productListActivityFab
        fab.setOnClickListener {
            goToProductForm()
        }
    }

     private fun goToProductForm() {
        val intent = Intent(this, ProductFormActivity::class.java)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.productListActivityRecyclerView
        recyclerView.adapter = adapter
        adapter.clickItem = {
            val intent = Intent(
                this,
                ProductDetailsActivity::class.java
            ).apply {
                putExtra(PRODUCT_ID_KEY, it.id)
            }
            startActivity(intent)
        }
    }

}