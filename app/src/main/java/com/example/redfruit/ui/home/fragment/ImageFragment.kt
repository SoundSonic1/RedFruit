package com.example.redfruit.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.databinding.ImageFragmentBinding
import com.example.redfruit.ui.base.BaseFullScreenFragment


class ImageFragment : BaseFullScreenFragment() {

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
