package com.mvvmarchitecture.api

import com.mvvmarchitecture.model.ProductModel
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProductList() : ProductModel
}