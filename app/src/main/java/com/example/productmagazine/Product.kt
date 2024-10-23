package com.example.productmagazine

import java.io.Serializable

class Product (
    val productName: String,
    val productPrice: String,
    val productDescription: String,
    val image: String?
) : Serializable