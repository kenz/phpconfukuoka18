package org.firespeed.phpconfukuoka18.presentation

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.databinding.FragmentOpenSourceLicenseDialogBinding

class OpenSourceLicenseDialogFragment : AppCompatDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private lateinit var binding: FragmentOpenSourceLicenseDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), R.layout.fragment_open_source_license_dialog, null, false) as FragmentOpenSourceLicenseDialogBinding
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        binding.toolbar.setTitle(R.string.open_source_license)
        binding.toolbar.setNavigationIcon(R.drawable.ic_chevron_left)
        binding.toolbar.setNavigationOnClickListener { dismiss() }

        return dialog
    }


    companion object {
        const val TAG_OPEN_SOURCE_LICENSE = "openSourceLicense"

        /**
         * インスタンスの作成
         */
        fun newInstance(): OpenSourceLicenseDialogFragment = OpenSourceLicenseDialogFragment()

    }

}