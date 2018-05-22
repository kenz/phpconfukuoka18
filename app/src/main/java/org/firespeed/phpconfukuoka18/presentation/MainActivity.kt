package org.firespeed.phpconfukuoka18.presentation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import org.firespeed.phpconfukuoka18.R
import org.firespeed.phpconfukuoka18.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ExplorerFragment.OnFragmentInteractionListener {

    private lateinit var binding: ActivityMainBinding
    private var explorerFragment: ExplorerFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        explorerFragment = supportFragmentManager.findFragmentByTag(TAG_EXPLORER) as ExplorerFragment?

        binding.activity = this
        binding.navigation.setOnNavigationItemSelectedListener { setFragment(it.itemId) }
        binding.navigation.selectedItemId = savedInstanceState?.getInt(STATE_MENU) ?: R.id.navigation_explorer
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(STATE_MENU, binding.navigation.selectedItemId)
    }

    private fun setFragment(itemId: Int): Boolean =
            when (itemId) {
                R.id.navigation_explorer ->
                    setMainFragment(explorerFragment
                            ?: ExplorerFragment.newInstance(), TAG_EXPLORER)

                else -> {
                    true
                }

            }

    private fun setMainFragment(fragment: Fragment, tag: String): Boolean {
        supportFragmentManager.beginTransaction().replace(binding.mainFragment.id, fragment, tag).commit()
        return true
    }

    companion object {

        private const val STATE_MENU = "menu"
        private const val TAG_EXPLORER = "explorer"
    }
}
