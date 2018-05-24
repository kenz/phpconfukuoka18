package org.firespeed.phpconfukuoka18.presentation

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.Disposable
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.databinding.ActivityMainBinding
import org.firespeed.phpconfukuoka18.viewmodel.SessionViewModel

class MainActivity : AppCompatActivity(),
        ExplorerFragment.OnFragmentInteractionListener,
        TimeTableFragment.OnFragmentInteractionListener,
        FavoriteFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener {

    private lateinit var binding: ActivityMainBinding
    private var explorerFragment: ExplorerFragment? = null
    private var timeTableFragment: TimeTableFragment? = null
    private var favoriteFragment: FavoriteFragment? = null
    private var aboutFragment: AboutFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        explorerFragment = supportFragmentManager.findFragmentByTag(TAG_EXPLORER) as ExplorerFragment?
        timeTableFragment= supportFragmentManager.findFragmentByTag(TAG_TIME_TABLE) as TimeTableFragment?
        favoriteFragment = supportFragmentManager.findFragmentByTag(TAG_FAVORITE) as FavoriteFragment?
        aboutFragment = supportFragmentManager.findFragmentByTag(TAG_ABOUT) as AboutFragment?

        binding.activity = this
        binding.navigation.setOnNavigationItemSelectedListener { setFragment(it.itemId) }
        binding.navigation.selectedItemId = savedInstanceState?.getInt(STATE_MENU) ?: R.id.navigation_explorer

    }

    override fun onStart() {
        super.onStart()
        val sessionViewModel = ViewModelProviders.of(this).get(SessionViewModel::class.java).apply {
            getCurrent().observe(this@MainActivity, Observer {} )
        }
        disposable = sessionViewModel.getAll {  }
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    private var disposable: Disposable? = null



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(STATE_MENU, binding.navigation.selectedItemId)
    }

    private fun setFragment(itemId: Int): Boolean =
            when (itemId) {
                R.id.navigation_explorer ->
                    setMainFragment(explorerFragment ?: ExplorerFragment.newInstance(), TAG_EXPLORER, R.string.title_explorer)
                R.id.navigation_time_table->
                    setMainFragment(timeTableFragment?: TimeTableFragment.newInstance(), TAG_TIME_TABLE, R.string.title_time_table)
                R.id.navigation_favorite->
                    setMainFragment(favoriteFragment?: FavoriteFragment.newInstance(), TAG_FAVORITE, R.string.title_favorite)
                R.id.navigation_about->
                    setMainFragment(aboutFragment?: AboutFragment.newInstance(), TAG_ABOUT, R.string.title_about)
                else -> {
                    false
                }

            }

    private fun setMainFragment(fragment: Fragment, tag: String,@StringRes titleId:Int): Boolean {
        supportFragmentManager.beginTransaction().replace(binding.mainFragment.id, fragment, tag).commit()
        setTitle(titleId)
        return true
    }

    companion object {

        private const val STATE_MENU = "menu"
        private const val TAG_EXPLORER = "explorer"
        private const val TAG_TIME_TABLE= "timeTable"
        private const val TAG_FAVORITE = "favorite"
        private const val TAG_ABOUT= "about"
    }
}
