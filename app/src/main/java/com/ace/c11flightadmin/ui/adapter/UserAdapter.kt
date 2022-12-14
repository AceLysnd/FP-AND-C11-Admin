package com.ace.c11flightadmin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ace.c11flightadmin.R
import com.ace.c11flightadmin.data.model.Account
import com.ace.c11flightadmin.data.model.User
import com.ace.c11flightadmin.databinding.ItemUsersBinding
import com.bumptech.glide.Glide

class UserAdapter(
    private var items: MutableList<Account>,
    private val onUserClick: (user: Account) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.PostViewHolder>() {


    fun setItems(items: List<Account>?) {
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
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    inner class PostViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Account) {
            itemView.setOnClickListener { }
            binding.tvUsername.text = item.username
            binding.tvFirstName.text = item.firstName.toString()
            binding.tvLastName.text = item.lastName.toString()
            binding.tvId.text = item.id.toString()

            if (item.photo != null) {
                if (item.photo.contains("https")){
                    Glide.with(binding.ivUser.context).load(item.photo).into(binding.ivUser)
                } else {
                    val photoUrl = item.photo.replace("http","https")
                    Glide.with(binding.ivUser.context).load(photoUrl).into(binding.ivUser)
                }
            }
            itemView.setOnClickListener { onUserClick.invoke(item) }

        }
    }
}