package com.mvvmarchitecture.ui

import android.content.Context
import com.mvvmarchitecture.R
import com.mvvmarchitecture.api.ApiService
import com.mvvmarchitecture.api.NetworkResult
import com.mvvmarchitecture.utils.NetUtils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {

    //MARK: Get product list api call
    suspend fun getProductList(context: Context) = flow {
        if (NetUtils.isNetworkAvailable(context)) {
            emit(NetworkResult.Loading(true))
            val response = apiService.getProductList()
            emit(NetworkResult.Success(response))
        } else {
            emit(NetworkResult.Validation(context.resources.getString(R.string.no_internet_connection)))
        }
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }

}