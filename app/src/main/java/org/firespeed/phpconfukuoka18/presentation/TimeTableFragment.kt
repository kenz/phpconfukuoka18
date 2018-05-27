package org.firespeed.phpconfukuoka18.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.adapter.ScrollLayoutManager
import org.firespeed.phpconfukuoka18.adapter.TimeTableAdapter
import org.firespeed.phpconfukuoka18.databinding.FragmentTimeTableBinding
import org.firespeed.phpconfukuoka18.presentation.SessionDetailDialogFragment.Companion.TAG_SESSION_DETAIL
import org.firespeed.phpconfukuoka18.viewmodel.SessionViewModel


/**
 * タイムテーブルの一覧
 */
class TimeTableFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTimeTableBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_table, container, false)
        binding.fragment = this
        listener?.setSupportActionBar(binding.toolbar)


        // タイムテーブルリストの準備
        val adapter = TimeTableAdapter()
        val layoutManager = ScrollLayoutManager(context)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter

        // 現在時刻へスクロール
        binding.scrollNow.setOnClickListener {
            binding.list.smoothScrollToPosition(adapter.getNowPosition())
//            layoutManager.scrollToPosition(adapter.getNowPosition())
        }

        // 罫線
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(itemDecoration)

        activity?.let { activity ->
            // セッションの一覧はViewModel経由で取得する
            val sessionViewModel = ViewModelProviders.of(activity).get(SessionViewModel::class.java).apply {
                getCurrent().observe(this@TimeTableFragment, Observer {
                    it?.let {
                        adapter.setItem(it)
                    }

                })
            }

            adapter.favoriteListener = { session, checked ->
                sessionViewModel.favorite(session, checked)
            }
            adapter.sessionClickListener = { session ->
                SessionDetailDialogFragment.newInstance(session).show(childFragmentManager, TAG_SESSION_DETAIL)
            }
        }

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
        fun newInstance() = TimeTableFragment()
    }


}
