package com.dixitpatel.quidcodemo.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dixitpatel.quidcodemo.R
import com.dixitpatel.quidcodemo.ui.albums.AlbumsFragment
import com.dixitpatel.quidcodemo.ui.artists.ArtistsFragment
import com.dixitpatel.quidcodemo.ui.tracks.TracksFragment

private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
@SuppressLint("WrongConstant")
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_SET_USER_VISIBLE_HINT) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a Fragment (defined as a static inner class below).
        return when (position) {
            0 -> {
                return ArtistsFragment.newInstance()
            }
            1 -> {
                return AlbumsFragment.newInstance()
            }
            2 -> {
                return TracksFragment.newInstance()
            }
            else -> ArtistsFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}