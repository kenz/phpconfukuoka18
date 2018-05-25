package org.firespeed.phpconfukuoka18.presentation

import android.app.Activity
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.databinding.DataBindingUtil.setContentView
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.databinding.FragmentSessionDetailDialogBinding
import org.firespeed.phpconfukuoka18.model.Session
import org.firespeed.phpconfukuoka18.viewmodel.SessionViewModel

class SessionDetailDialogFragment : AppCompatDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
   }

    private lateinit var binding: FragmentSessionDetailDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
         activity?.let { activity ->
            sessionViewModel = ViewModelProviders.of(activity).get(SessionViewModel::class.java)
            sessionViewModel?.getCurrent()?.observe(this, Observer { result ->

            })
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), R.layout.fragment_session_detail_dialog, null, false) as FragmentSessionDetailDialogBinding
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val arguments = arguments ?: throw NullPointerException()
        session = arguments.getSerializable(ARGS_SESSION_ID) as Session
        binding.toolbar.title = session.title
        binding.toolbar.setNavigationIcon(R.drawable.ic_chevron_left)
        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.session = session
        binding.lblSession.visibility = if (session.body == null) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
        context?.let { context ->
            colorEnable = ContextCompat.getColor(context, R.color.colorFavorite)
            colorDisable = ContextCompat.getColor(context, R.color.colorAccent)
        }
        binding.floatingActionButton.setOnClickListener {
            session.favorite = !session.favorite
            sessionViewModel?.favorite(session, session.favorite)
            updateFavorite()
        }
        updateFavorite()

        return dialog
    }

    private var colorEnable: Int? = null
    private var colorDisable: Int? = null

    private fun updateFavorite() {
        if (session.favorite) {
            binding.floatingActionButton.imageTintList = ColorStateList.valueOf(colorDisable!!)
            binding.floatingActionButton.backgroundTintList = ColorStateList.valueOf(colorEnable!!)
        } else {
            binding.floatingActionButton.imageTintList = ColorStateList.valueOf(colorEnable!!)
            binding.floatingActionButton.backgroundTintList = ColorStateList.valueOf(colorDisable!!)
        }
    }


    private var sessionViewModel: SessionViewModel? = null
    private lateinit var session: Session


    companion object {
        const val ARGS_SESSION_ID = "sessionId"

        /**
         * インスタンスの作成
         * @param requestId 呼び出し元に返す値
         */
        fun newInstance(session: Session): SessionDetailDialogFragment {
            val b = Bundle()
            b.putSerializable(ARGS_SESSION_ID, session)
            val fragment = SessionDetailDialogFragment()
            fragment.arguments = b
            return fragment

        }

    }

}