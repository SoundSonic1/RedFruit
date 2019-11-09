package com.example.redfruit.ui.media.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.example.redfruit.R
import com.example.redfruit.databinding.ImageFragmentBinding
import com.example.redfruit.ui.base.DaggerFullScreenFragment
import com.example.redfruit.util.Constants
import javax.inject.Inject

class ImageFragment : DaggerFullScreenFragment() {

    @Inject
    lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ImageFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.image_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.url = url

        return binding.root
    }

    companion object {
        /**
         * Pass image url to new fragment instance
         */
        fun newInstance(url: String) = ImageFragment().apply {
            arguments = bundleOf(Constants.IMAGE_URL_KEY to url)
        }
    }
}
