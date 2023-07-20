package com.mvvmarchitecture.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvmarchitecture.R
import com.mvvmarchitecture.api.NetworkResult
import com.mvvmarchitecture.model.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val productListResponse = MutableLiveData<NetworkResult<ProductModel>>()

    //MARK: Get product list api call
    fun getProductList(context: Context) {

        viewModelScope.launch {
            repository.getProductList(context).collect {
                productListResponse.postValue(it)
            }
        }
    }
}