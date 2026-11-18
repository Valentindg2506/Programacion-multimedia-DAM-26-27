package com.example.tarea2tema2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tarea2tema2.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShoppingListViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    fun addProduct(name: String) {
        if (name.isBlank()) return
        val newProduct = Product(name = name)
        _products.update { currentList ->
            currentList + newProduct
        }
        Log.d("ListaCompra", "Producto añadido: $name")
    }

    fun removeProduct(product: Product) {
        _products.update { currentList ->
            currentList.filterNot { it.id == product.id }
        }
        Log.d("ListaCompra", "Producto eliminado: ${product.name}")
    }

    fun toggleProductPurchased(product: Product) {
        _products.update { currentList ->
            currentList.map { item ->
                if (item.id == product.id) {
                    item.copy(isPurchased = !item.isPurchased)
                } else {
                    item
                }
            }
        }
    }
}
