package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.com.alura.orgs.dao.ProductsDao
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityProductListBinding
import br.com.alura.orgs.model.Product
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import java.math.BigDecimal

class ProductListActivity : AppCompatActivity() {

    private val dao = ProductsDao()
    private val adapter = ProductListAdapter(context = this, products = dao.searchAll())
    private val binding by lazy {
        ActivityProductListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        setupFab()

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "orgs.db"
        ).allowMainThreadQueries()
            .build()
        val productDao = db.productDao()
//        productDao.save(
//            Product(
//                name = "teste nome 1",
//                description = "teste desc 1",
//                value = BigDecimal("20.0")
//            )
//        )
        adapter.update(productDao.searchAll())
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