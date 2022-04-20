package com.example.catslist.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.catslist.R
import com.example.catslist.databinding.FragmentHomeBinding
import com.example.catslist.db.RoomItem
import com.example.catslist.model.CATEGORY_1
import com.example.catslist.model.CATEGORY_2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private var adapter: ItemListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemListAdapter()

        layoutManager = LinearLayoutManager(this.context)

        setCategoriesButtonText()

        binding.rvList.layoutManager = layoutManager
        binding.rvList.adapter = adapter

        lifecycleScope.launch {
            viewModel.data.collectLatest { response ->
                response?.let { dataResponse ->

                    dataResponse.data?.let { items ->
                        setAdapterData(items)
                    }

                    if (!response.fromNetwork)
                        Toast.makeText(
                            this@HomeFragment.context,
                            "NO INTERNET CONNECTION",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }

        binding.rgButtonsChartPeriod.setOnCheckedChangeListener { _, checkedId ->
            viewModel.loadData()
            viewModel.activeCategory =
                if (checkedId == R.id.btnCategory1) CATEGORY_1 else CATEGORY_2

            viewModel.data.value?.data?.let {
                setAdapterData(it)
            }
        }
    }

    private fun setAdapterData(data: List<RoomItem>) {
        adapter?.let {
            it.submitList(data.filter { it.category == viewModel.activeCategory })
            setCategoriesButtonText()
        }
    }

    private fun setCategoriesButtonText() {
        binding.btnCategory1.text = CATEGORY_1
        binding.btnCategory2.text = CATEGORY_2

        val data = viewModel.data.value?.data

        data?.let { data ->

            val countCategory1 = data.filter { it.category == CATEGORY_1 }.size
            val countCategory2 = data.size - countCategory1

            val btnCategory1Text = "$CATEGORY_1($countCategory1)"
            val btnCategory2Text = "$CATEGORY_2($countCategory2)"

            binding.btnCategory1.text = btnCategory1Text
            binding.btnCategory2.text = btnCategory2Text
        }
    }

    override fun onDestroyView() {
        adapter = null
        layoutManager = null
        super.onDestroyView()
    }
}