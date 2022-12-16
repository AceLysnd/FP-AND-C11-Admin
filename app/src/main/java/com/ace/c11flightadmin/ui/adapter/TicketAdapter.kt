package com.ace.c11flightadmin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ace.c11flightadmin.data.model.Ticket
import com.ace.c11flightadmin.databinding.ItemTicketBinding

class TicketAdapter(
    private var items: MutableList<Ticket>,
    private val onUserClick: (ticket: Ticket) -> Unit
) :
    RecyclerView.Adapter<TicketAdapter.PostViewHolder>() {


    fun setItems(items: List<Ticket>?) {
        this.items.clear()
        if (items != null) {
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    inner class PostViewHolder(private val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Ticket) {
            itemView.setOnClickListener { }
            binding.tvFlightId.text = item.flightId.toString()
            binding.tvType.text = item.type.toString()
            binding.tvPrice.text = item.price.toString()
            binding.tvDesc.text = item.desc.toString()
            binding.tvDateUpdated.text = item.updatedAt

            itemView.setOnClickListener { onUserClick.invoke(item) }

        }
    }
}