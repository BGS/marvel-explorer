package com.gabi.marvel_explorer.view.ui.fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.gabi.marvel_explorer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_comic_detail.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk27.coroutines.onClick


class ComicDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comic_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.let { ComicDetailFragmentArgs.fromBundle(it).url }
        val banner_url = arguments?.let { ComicDetailFragmentArgs.fromBundle(it).coverUrl }
        val comic_title =  arguments?.let { ComicDetailFragmentArgs.fromBundle(it).comicTitle }

        comicBannerView.backgroundColor = Color.parseColor("#202020")
        Picasso.get().load(banner_url).fit().into(comicBannerView)
        setupWebView()
        setClickListeners()

        comicContentView.loadUrl(url)
    }

    private fun setClickListeners() {
        comic_back_button.onClick {
            comicContentView.goBack()
        }

        comic_forward_button.onClick {
            comicContentView.goForward()
        }

        comic_refresh_button.onClick {
            comicContentView.reload()
        }
    }

    private fun setupWebView() {
        comicContentView.setInitialScale(1)
        comicContentView.backgroundColor = Color.parseColor("#202020")
        val webSettings = comicContentView.settings
        webSettings.setAppCacheEnabled(false)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true

        comicContentView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (comic_back_button != null && comic_forward_button != null && comicContentView != null && comic_progress_view != null) {
                    comic_back_button.isEnabled = comicContentView.canGoBack()
                    comic_forward_button.isEnabled = comicContentView.canGoForward()
                    comic_progress_view.visibility = View.VISIBLE
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (comic_back_button != null && comic_forward_button != null && comicContentView != null && comic_progress_view != null) {
                    comic_back_button.isEnabled = comicContentView.canGoBack()
                    comic_forward_button.isEnabled = comicContentView.canGoForward()
                    comic_progress_view.visibility = View.GONE
                }
            }
        }
    }
}