package com.example.catslist.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.catslist.R
import com.example.catslist.databinding.FrameItemCardBinding
import com.example.catslist.db.RoomItem

class ItemListAdapter(

) : ListAdapter<RoomItem, ItemListAdapter.ItemListViewHolder>(ItemListComparator) {

    interface ItemClickListener {
        fun onClick(adId: Long)
    }

    interface BottomButtonRetryClickListener {
        fun onClick()
    }

    class ItemListViewHolder(val binding: FrameItemCardBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {

        val item = getItem(position)

        with(holder.binding) {
            textViewBrandName.text = item.title
            textViewNameItem.text = item.category
            textViewPrice.text = item.price.toString()

            Glide
                .with(root.context)
                .load(item.image)
                .placeholder(R.drawable.ic_outline_image_24)
                .error(R.drawable.ic_outline_image_24)
                .fitCenter()
                .into(imageCard)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemListViewHolder {

        return ItemListViewHolder(
            FrameItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    object ItemListComparator : DiffUtil.ItemCallback<RoomItem>() {
        override fun areItemsTheSame(oldItem: RoomItem, newItem: RoomItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RoomItem, newItem: RoomItem): Boolean {
            return oldItem == newItem
        }
    }

}