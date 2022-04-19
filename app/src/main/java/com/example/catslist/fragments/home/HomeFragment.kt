package com.example.catslist.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.catslist.R
import com.example.catslist.databinding.FragmentHomeBinding
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

        binding.rvList.layoutManager = layoutManager
        binding.rvList.adapter = adapter

        lifecycleScope.launch{
            viewModel.data.collectLatest {
                it?.let{ items->
                    adapter?.submitList(items)
                }
            }
        }
    }

    override fun onDestroyView() {
        adapter = null
        layoutManager = null
        super.onDestroyView()
    }
}