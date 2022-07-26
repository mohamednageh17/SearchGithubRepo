package com.example.searchgithubrepo.presentation.features.search_results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchgithubrepo.R
import com.example.searchgithubrepo.databinding.RepoItemBinding
import com.example.searchgithubrepo.domain.model.RepoModel

class ReposAdapter(private val onItemClicked: (RepoModel) -> Unit) : PagingDataAdapter<RepoModel, ReposAdapter.ViewHolder>(RepoDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            RepoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RepoModel) = with(binding) {
            repoFullNameTV.text = item.name
            repoDescriptionTV.text = item.description
            Glide.with(repoIV)
                .load(item.owner!!.avatarURL)
                .placeholder(R.drawable.dp_default)
                .circleCrop()
                .into(repoIV)
            root.setOnClickListener {
                onItemClicked.invoke(item)
            }
        }
    }

    object RepoDiffUtil : DiffUtil.ItemCallback<RepoModel>() {
        override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel) =
            oldItem == newItem
    }
}
