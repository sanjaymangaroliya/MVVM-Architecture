package com.mvvmarchitecture.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mvvmarchitecture.R
import com.mvvmarchitecture.api.NetworkResult
import com.mvvmarchitecture.databinding.ActivityProductBinding
import com.mvvmarchitecture.extensions.gone
import com.mvvmarchitecture.extensions.showToast
import com.mvvmarchitecture.extensions.visible
import com.mvvmarchitecture.model.ProductsItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : AppCompatActivity(), ProductAdapter.OnItemClickListener {

    private lateinit var binding: ActivityProductBinding
    private var productAdapter: ProductAdapter? = null
    private var arrayList: ArrayList<ProductsItem?> = ArrayList()
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        setupObservers()
        apiCall()
    }

    //MARK: Init UI
    private fun initUI() {
        binding.productViewModel = viewModel
        binding.lifecycleOwner = this

        //MARK: Pull down to refresh data
        binding.swipeContainer.setOnRefreshListener { apiCall() }
        binding.swipeContainer.setColorSchemeResources(R.color.colorPrimary)

        //MARK: ProductAdapter
        productAdapter = ProductAdapter()
        binding.recyclerView.adapter = productAdapter
    }

    //MARK: Api call
    private fun apiCall() {
        viewModel.getProductList(this)
    }

    //MARK: Setup observer
    private fun setupObservers() {
        viewModel.productListResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.visible()
                }

                is NetworkResult.Validation -> {
                    showToast(it.validationMessage)
                }

                is NetworkResult.Failure -> {
                    binding.progressBar.gone()
                    showToast(it.errorMessage)
                    setData()
                }

                is NetworkResult.Success -> {
                    binding.progressBar.gone()
                    arrayList.clear()
                    it.data.products?.let { it1 -> arrayList.addAll(it1) }
                    setData()
                }
            }
        }
    }

    private fun setData() {
        //MARK: Product list show
        if (arrayList.isNotEmpty()) {
            binding.recyclerView.visible()
            binding.imgNoDataFound.gone()
            productAdapter?.setData(arrayList, this)
        } else {
            binding.recyclerView.gone()
            binding.imgNoDataFound.visible()
        }
    }

    override fun onClickItem(productTitle: String) {
        showToast(productTitle)
    }
}