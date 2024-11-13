package com.example.shoppingcartapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val imageResId: Int,
    var quantity: Int = 1
) : Parcelable
