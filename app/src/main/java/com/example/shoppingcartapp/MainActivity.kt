package com.example.shoppingcartapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcartapp.ProductAdapter
import com.example.shoppingcartapp.databinding.ActivityMainBinding
import com.example.shoppingcartapp.models.CartItem
import com.example.shoppingcartapp.models.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val products = listOf(
        Product(50, "Хлеб", R.drawable.hleb),
        Product(100, "Молоко", R.drawable.milk),
        Product(200, "Сыр", R.drawable.sir)
    )
    private val cartItems: MutableList<CartItem> = mutableListOf()
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("cart_prefs", MODE_PRIVATE)
        loadCartItems() // Загружаем элементы корзины при старте

        setupRecyclerView()

        binding.goToCartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("cartItems", ArrayList(cartItems)) // Передаем корзину
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ProductAdapter(products) { product ->
            addToCart(product)
        }
    }

    private fun addToCart(product: Product) {
        val existingCartItem = cartItems.find { it.product.id == product.id }
        if (existingCartItem != null) {
            existingCartItem.quantity++
        } else {
            cartItems.add(CartItem(product, 1))
        }
        Toast.makeText(this, "${product.name} добавлен в корзину", Toast.LENGTH_SHORT).show()
        saveCartItems() // Сохраняем актуальную корзину
    }

    private fun saveCartItems() {
        val json = gson.toJson(cartItems)
        sharedPreferences.edit().putString("cart_items", json).apply()
    }

    private fun loadCartItems() {
        val json = sharedPreferences.getString("cart_items", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<CartItem>>() {}.type
            val loadedItems: MutableList<CartItem> = gson.fromJson(json, type)
            cartItems.addAll(loadedItems)
        }
    }
}
