package com.example.catslist.fragments.explore

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.catslist.R
import com.example.catslist.databinding.FragmentExploreBinding
import com.example.catslist.db.RoomItem
import com.example.catslist.fragments.home.ItemListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private val binding by viewBinding(FragmentExploreBinding::bind)
    private val viewModel: ExploreViewModel by viewModels()

    private var adapter: ItemListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemListAdapter()

        layoutManager = LinearLayoutManager(this.context)

        binding.rvList.layoutManager = layoutManager
        binding.rvList.adapter = adapter

        lifecycleScope.launch {
            viewModel.data.collectLatest { response ->
                response?.let { dataResponse ->

                    dataResponse.data?.let { items ->
                        setAdapterData(items, "")
                    }

                    if (!response.fromNetwork)
                        Toast.makeText(
                            this@ExploreFragment.context,
                            "NO INTERNET CONNECTION",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }

        binding.buttonClose.isVisible = false

        binding.editTextExplore.addTextChangedListener {
            binding.buttonClose.isVisible = it?.isNotEmpty() ?: false
        }

        binding.buttonClose.setOnClickListener {
            binding.editTextExplore.text.clear()
            viewModel.data.value?.data?.let {
                setAdapterData(it, "")
            }
        }

        binding.buttonExplore.setOnClickListener {
            viewModel.data.value?.data?.let {
                setAdapterData(it, binding.editTextExplore.text.toString())
            }
        }

        lifecycleScope.launchWhenResumed {
            binding.editTextExplore.text.clear()
        }
    }

    private fun setAdapterData(data: List<RoomItem>, search: String) {
        adapter?.let {
            if (search.isEmpty())
                it.submitList(data)
            else {
                val s1 = data.filter { it.title.contains(search) }.toMutableList()

                val splitted = search.split(" ")

                for (c in splitted) {
                    val a = data.filter { it.title.contains(c) }

                    for (item in a) {
                        if (!s1.contains(item))
                            s1 += item
                    }
                }

                val a = data.filter { it.description.contains(search) }
                for (item in a) {
                    if (!s1.contains(item))
                        s1 += item
                }

                for (c in splitted) {
                    val a = data.filter { it.description.contains(c) }

                    for (item in a) {
                        if (!s1.contains(item))
                            s1 += item
                    }
                }

                it.submitList(s1)
                if(s1.isEmpty())
                    binding.tvNoItems.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        adapter = null
        layoutManager = null
        super.onDestroyView()
    }
}
