package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.redfruit.R
import com.example.redfruit.databinding.ImageFragmentBinding
import com.google.android.material.appbar.AppBarLayout


class ImageFragment : Fragment() {

    private var initToolbarHeight: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val url = arguments?.getString(IMAGE_FRAGMENT_KEY) ?: ""

        val binding: ImageFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.image_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item = url

        disableToolbar(activity!!)

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        enableToolbar(activity!!)
    }

    private fun disableToolbar(fragmentActivity: FragmentActivity) {
        val appbarLayout = fragmentActivity.findViewById<AppBarLayout>(R.id.appBarLayout)
        val lp = appbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        initToolbarHeight = lp.height
        lp.height = 0
        appbarLayout.layoutParams = lp
    }

    private fun enableToolbar(fragmentActivity: FragmentActivity) {
        val appbarLayout = fragmentActivity.findViewById<AppBarLayout>(R.id.appBarLayout)
        val lp = appbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = initToolbarHeight
        appbarLayout.layoutParams = lp
    }

    companion object {
        private const val IMAGE_FRAGMENT_KEY = "IMAGE_FRAGMENT_KEY"
        /**
         * Pass image url to new instance
         */
        fun newInstance(url: String): ImageFragment {
            val fragment = ImageFragment()
            fragment.arguments = bundleOf(IMAGE_FRAGMENT_KEY to url)

            return fragment
        }
    }

}
