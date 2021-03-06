package org.firespeed.phpconfukuoka18.presentation

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.firespeed.phpconfukuoka18.BuildConfig
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentAboutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        binding.fragment = this
        binding.versionNumber.text = BuildConfig.VERSION_NAME
        binding.lnkOpenSourceLicense.setOnClickListener { _ ->
            OpenSourceLicenseDialogFragment.newInstance().show(childFragmentManager, OpenSourceLicenseDialogFragment.TAG_OPEN_SOURCE_LICENSE)
        }
        binding.lblGithub.setOnClickListener {
            val uri = Uri.parse(getString(R.string.git_hub_url))
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        listener?.setSupportActionBar(binding.toolbar)
        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener : ActivityInterface

    companion object {
        @JvmStatic
        fun newInstance() = AboutFragment()
    }
}
