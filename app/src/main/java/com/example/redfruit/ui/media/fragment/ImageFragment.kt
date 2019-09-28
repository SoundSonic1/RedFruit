package com.example.redfruit.ui.media.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.databinding.ImageFragmentBinding
import com.example.redfruit.ui.base.FullScreenFragment


class ImageFragment : FullScreenFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val url = arguments?.getString(IMAGE_FRAGMENT_KEY) ?: ""

        val binding: ImageFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.image_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.url = url

        return binding.root
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
