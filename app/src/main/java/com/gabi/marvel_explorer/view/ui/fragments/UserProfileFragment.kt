package com.gabi.marvel_explorer.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.gabi.marvel_explorer.databinding.FargmentUserProfileBinding
import com.gabi.marvel_explorer.view.adapter.ComicListAdapter

import com.gabi.marvel_explorer.view.adapter.UserProfileAdapter


import com.gabi.marvel_explorer.view.models.ProfileViewModel
import kotlinx.android.synthetic.main.fargment_user_profile.*
import org.jetbrains.anko.longToast

class UserProfileFragment : Fragment() {
    private lateinit var viewDataBinding: FargmentUserProfileBinding
    private lateinit var adapter: UserProfileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FargmentUserProfileBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@UserProfileFragment).get(ProfileViewModel::class.java)
            setLifecycleOwner(viewLifecycleOwner)
        }
        return viewDataBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel?.fetchProfileData()

        setupAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewDataBinding.viewmodel?.profileData?.observe(viewLifecycleOwner, Observer {
            adapter.updateFavoriteComicsData(it)
        })

        viewDataBinding.viewmodel?.toastMessage?.observe(viewLifecycleOwner, Observer {
            activity?.longToast(it)
        })
    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = UserProfileAdapter(viewDataBinding.viewmodel!!)
            val layoutManager = GridLayoutManager(activity, 4)

            profileFavoriteComics.layoutManager = layoutManager
            profileFavoriteComics.adapter = adapter
        }
    }

}