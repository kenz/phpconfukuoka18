package org.firespeed.phpconfukuoka18.presentation

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.databinding.FragmentExplorerBinding
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import java.util.*


class ExplorerFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentExplorerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_explorer, container, false)
        binding.fragment = this
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

    @Suppress("UNUSED_PARAMETER")
    fun addSchedule(view: View) {
        val intent = Intent(Intent.ACTION_EDIT)
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra("title", getString(R.string.event_name))
        intent.putExtra("eventLocation", getString(R.string.event_location)) // 場所
        val begin = Calendar.getInstance()
        begin.timeZone = TimeZone.getTimeZone("Asia/Tokyo")
        begin.set(2018, 5, 16, 10, 0)
        intent.putExtra("beginTime", begin.timeInMillis) // 開始日時
        val end = Calendar.getInstance()
        end.timeZone = TimeZone.getTimeZone("Asia/Tokyo")
        end.set(2018, 5, 16, 18, 0)
        intent.putExtra("endTime", end.timeInMillis) // 終了日時
        intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
        intent.putExtra("allDay", false)
        startActivity(Intent.createChooser(intent, getString(R.string.select_calender)))

    }

    @Suppress("UNUSED_PARAMETER")
    fun startNavigation(view: View) {
        val location = getString(R.string.location_data)
        val gmmIntentUri = Uri.parse("geo:0,0?q=33.5912207,130.4149073($location)")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.`package` = "com.google.android.apps.maps"
        startActivity(mapIntent)

    }

    @Suppress("UNUSED_PARAMETER")
    fun attendConference(view: View) {
        val uri = Uri.parse("https://eventon.jp/11722/")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    @Suppress("UNUSED_PARAMETER")
    fun attendParty(view: View) {
        val uri = Uri.parse("https://eventon.jp/11973/")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun startActivity(intent: Intent) {
        val packageManager = context?.packageManager ?: return
        if (intent.resolveActivity(packageManager) != null) {
            super.startActivity(intent)
        }
    }

    interface OnFragmentInteractionListener : ActivityInterface

    companion object {
        @JvmStatic
        fun newInstance() = ExplorerFragment()
    }
}
