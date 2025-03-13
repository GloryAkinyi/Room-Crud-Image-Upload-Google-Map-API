package com.glory.maliyako.viewmodel

import androidx.lifecycle.*
import com.glory.maliyako.model.Product
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _allProducts = MutableLiveData<List<Product>>(emptyList())
    val allProducts: LiveData<List<Product>> = _allProducts

    fun addProduct(name: String, price: Double, imageUri: String) {
        val newList = _allProducts.value.orEmpty().toMutableList()
        newList.add(Product(0, name, price, imageUri))
        _allProducts.value = newList
    }

    fun updateProduct(updatedProduct: Product) {
        val newList = _allProducts.value?.map { if (it.id == updatedProduct.id) updatedProduct else it }
        _allProducts.value = newList
    }

    fun deleteProduct(product: Product) {
        val newList = _allProducts.value.orEmpty().toMutableList()
        newList.removeIf { it.id == product.id }
        _allProducts.value = newList
    }
}
