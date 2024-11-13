package com.example.shoppingcartapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingcartapp.models.Product

class ProductAdapter(
private val products: List<Product>,
private val onAddToCart: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val price: TextView = itemView.findViewById(R.id.price) // TextView для цены
        val productId: TextView = itemView.findViewById(R.id.price) // TextView для ID
        val addToCartButton: Button = itemView.findViewById(R.id.add_to_cart_button)

        init {
            addToCartButton.setOnClickListener {
                val product = products[adapterPosition]
                onAddToCart(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productImage.setImageResource(product.imageResId)
        holder.productName.text = product.name
        holder.price.text = "${product.id} руб."

    }

    override fun getItemCount(): Int = products.size
}
