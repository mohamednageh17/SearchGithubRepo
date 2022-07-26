package com.example.searchgithubrepo.presentation.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.searchgithubrepo.R
import com.example.searchgithubrepo.databinding.FragmentRepoDetailsBinding
import com.example.searchgithubrepo.domain.model.RepoModel

class RepoDetailsFragment : Fragment() {

    private val args: RepoDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentRepoDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(args.repo)
    }


    private fun initView(item: RepoModel) {
        with(binding) {
            Glide.with(avatarIV)
                .load(item.owner!!.avatarURL)
                .placeholder(R.drawable.dp_default)
                .circleCrop()
                .into(avatarIV)
            idTV.text = "ID@/${item.id}"
            stargazerCountTV.text = item.stargazersCount.toString()
            forksCountTV.text = item.forksCount.toString()
            issuesCountTV.text = item.openIssuesCount.toString()
            repoNameTV.text = item.name
            repoDescriptionTV.text = item.description
        }
    }
}