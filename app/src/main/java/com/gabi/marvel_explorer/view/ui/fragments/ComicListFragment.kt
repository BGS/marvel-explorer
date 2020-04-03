package com.gabi.marvel_explorer.view.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gabi.marvel_explorer.R
import com.gabi.marvel_explorer.databinding.FragmentComicsListBinding
import com.gabi.marvel_explorer.utils.FirebaseAuthUtil
import com.gabi.marvel_explorer.view.adapter.ComicListAdapter
import com.gabi.marvel_explorer.view.models.ComicListViewModel
import com.gabi.marvel_explorer.view.ui.activities.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_comics_list.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.support.v4.act

class ComicListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentComicsListBinding
    private lateinit var adapter: ComicListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragmentComicsListBinding.inflate(inflater, container, false).apply {
            viewmodel = ViewModelProviders.of(this@ComicListFragment).get(ComicListViewModel::class.java)
            setLifecycleOwner(viewLifecycleOwner)
        }



        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel?.fetchComicList()



        setupAdapter()
        setupObservers()
    }

    private fun setupObservers() {
        viewDataBinding.viewmodel?.comicListLive?.observe(viewLifecycleOwner, Observer {
            adapter.updateComicList(it)
        })

        viewDataBinding.viewmodel?.toastMessage?.observe(viewLifecycleOwner, Observer {
            activity?.longToast(it)
        })
    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = ComicListAdapter(viewDataBinding.viewmodel!!)
            val layoutManager = GridLayoutManager(activity, 2)

            comic_list_rv.layoutManager = layoutManager
            comic_list_rv.adapter = adapter
        }
    }
}
