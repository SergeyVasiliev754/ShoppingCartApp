package com.example.shoppingcartapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val product: Product,
    var quantity: Int
) : Parcelable
