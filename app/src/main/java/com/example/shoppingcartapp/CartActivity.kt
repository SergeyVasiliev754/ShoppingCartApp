package com.example.shoppingcartapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcartapp.CartAdapter
import com.example.shoppingcartapp.databinding.ActivityCartBinding
import com.example.shoppingcartapp.models.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter
    private val cartItems: MutableList<CartItem> = mutableListOf()
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("cart_prefs", MODE_PRIVATE)
        loadCartItems()

        setupRecyclerView()
        updateTotalCost()

        binding.checkoutButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("totalCost", calculateTotalCost())
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = CartAdapter(cartItems, ::onRemove, ::onQuantityChanged)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun onRemove(cartItem: CartItem) {
        cartItems.remove(cartItem)
        adapter.notifyDataSetChanged()
        updateTotalCost()
        saveCartItems() // Сохраняем изменения в корзине
    }

    private fun onQuantityChanged(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity < 1) {
            onRemove(cartItem)
        } else {
            cartItem.quantity = newQuantity
            adapter.notifyDataSetChanged()
            updateTotalCost()
        }
        saveCartItems() // Сохраняем изменения в корзине
    }

    private fun updateTotalCost() {
        binding.totalCostTextView.text = "Общая стоимость: ${calculateTotalCost()} руб."
    }

    private fun calculateTotalCost(): Double {
        return cartItems.sumOf { it.product.id * it.quantity.toDouble() }
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


