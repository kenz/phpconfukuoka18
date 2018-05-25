package org.firespeed.phpconfukuoka18.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.adapter.TimeTableAdapter
import org.firespeed.phpconfukuoka18.databinding.FragmentTimeTableBinding
import org.firespeed.phpconfukuoka18.viewmodel.SessionViewModel


class TimeTableFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTimeTableBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_table, container, false)
        binding.fragment = this
        listener?.setSupportActionBar(binding.toolbar)

        binding.list.layoutManager = LinearLayoutManager(context)
        val adapter = TimeTableAdapter()
        binding.list.adapter = adapter

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(itemDecoration)

        activity?.let {activity ->
            ViewModelProviders.of(activity).get(SessionViewModel::class.java).apply {
                getCurrent().observe(activity, Observer {
                    it?.let {
                        adapter.setItem(it)
                    }

                })
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
