package com.example.shoppingcartapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcartapp.models.CartItem

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onRemove: (CartItem) -> Unit,
    private val onQuantityChanged: (CartItem, Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val increaseButton: Button = itemView.findViewById(R.id.increase_button)
        val decreaseButton: Button = itemView.findViewById(R.id.decrease_button)


        init {
            increaseButton.setOnClickListener {
                val cartItem = cartItems[adapterPosition]
                onQuantityChanged(cartItem, cartItem.quantity + 1)
            }
            decreaseButton.setOnClickListener {
                val cartItem = cartItems[adapterPosition]
                onQuantityChanged(cartItem, cartItem.quantity - 1)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.productImage.setImageResource(cartItem.product.imageResId)
        holder.productName.text = cartItem.product.name
        holder.productQuantity.text = "Количество: ${cartItem.quantity}"
    }

    override fun getItemCount(): Int = cartItems.size
}

