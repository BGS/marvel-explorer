package com.gabi.marvel_explorer.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.gabi.marvel_explorer.databinding.FragmentComicsListBinding
import com.gabi.marvel_explorer.view.adapter.ComicListAdapter
import com.gabi.marvel_explorer.view.models.ComicListViewModel
import kotlinx.android.synthetic.main.fragment_comics_list.*
import org.jetbrains.anko.longToast

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
            adapter.updateRepoList(it)
        })

        viewDataBinding.viewmodel?.toastMessage?.observe(viewLifecycleOwner, Observer {
            activity?.longToast(it)
        })
    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = ComicListAdapter(viewDataBinding.viewmodel!!)
            val layoutManager = GridLayoutManager(activity, 3)
            comic_list_rv.layoutManager = layoutManager
            comic_list_rv.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
            comic_list_rv.adapter = adapter
        }
    }
}
