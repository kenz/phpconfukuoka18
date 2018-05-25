package org.firespeed.phpconfukuoka18.adapter

import android.databinding.ViewDataBinding
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.firespeed.phpconfukuoka18.databinding.ItemTimeTableSessionBinding
import org.firespeed.phpconfukuoka18.databinding.ItemTimeTableTimeBinding
import org.firespeed.phpconfukuoka18.model.Session
import org.firespeed.phpconfukuoka18.model.Time

class TimeTableAdapter : RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {


    private val itemList: MutableList<Any> = ArrayList()
    fun setItem(sessionList:List<Session>){
        val newList: MutableList<Any> = ArrayList()
        var timeTable:Int? = null
        sessionList.forEach{
            if(timeTable!= it.timeTableSort){
                timeTable = it.timeTableSort
                newList.add(Time(it.timeTable))
            }
            newList.add(it)
        }
        if(itemList.size!=newList.size){
            itemList.clear()
            itemList.addAll(newList)
            notifyDataSetChanged()
        }else{
            itemList.forEachIndexed{index,element->
                if(element != newList[index]){
                    notifyItemChanged(index)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int =
            when (itemList[position]) {
                is Time -> VIEW_TYPE_TIME
                else -> VIEW_TYPE_SESSION
            }


    private var attachedRecyclerView: RecyclerView? = null
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        attachedRecyclerView = recyclerView
    }


    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        attachedRecyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ViewDataBinding> {
        val binding: ViewDataBinding = when (viewType) {

            VIEW_TYPE_TIME-> ItemTimeTableTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            VIEW_TYPE_SESSION-> ItemTimeTableSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            else -> throw IllegalStateException()
        }
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        when (holder.binding) {
            is ItemTimeTableTimeBinding-> {
                holder.binding.time = itemList[position] as Time
            }
            is ItemTimeTableSessionBinding-> {
                holder.binding.session = itemList[position] as Session
           }
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    companion object {
        const val VIEW_TYPE_TIME = 0
        const val VIEW_TYPE_SESSION = 1
    }


}

