package com.dixitpatel.quidcodemo.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.dixitpatel.quidcodemo.R
import com.dixitpatel.quidcodemo.databinding.ActivityMainBinding
import com.dixitpatel.quidcodemo.network.ApiInterface
import com.dixitpatel.quidcodemo.prefs.PrefEntity
import com.dixitpatel.quidcodemo.prefs.Preferences
import com.dixitpatel.quidcodemo.ui.base.BaseActivity
import com.dixitpatel.quidcodemo.ui.login.LoginActivity
import com.dixitpatel.quidcodemo.utils.Alerts
import com.dixitpatel.quidcodemo.utils.Utils
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityViewModel?>() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var model: MainActivityViewModel

    @Inject
    lateinit var apiInterface : ApiInterface

    override fun getViewModel(): MainActivityViewModel {
        return model
    }

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.viewPager.setSaveFromParentEnabled(false)
        binding.tabs.setupWithViewPager(binding.viewPager)

        // If not login then ask user for login.
        if(Preferences.getPreferenceString(PrefEntity.AUTH_TOKEN)!!.isEmpty())
        {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            this.finish()
        }
        else
        {
            title = Preferences.getPreferenceString(PrefEntity.USER_NAME)
            binding.toolbar.title = Preferences.getPreferenceString(PrefEntity.USER_NAME)
        }
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
    }

    override fun onBackPressed() {
        if (backPressedTime + 2500 > System.currentTimeMillis()) {
            super.onBackPressed()
            ActivityCompat.finishAffinity(this)
        } else {
            Alerts.showSnackBar(
                this@MainActivity, resources.getString(R.string.double_press_exit))
        }
        backPressedTime = System.currentTimeMillis()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        if(Preferences.getPreferenceString(PrefEntity.AUTH_TOKEN)!!.isEmpty() || Preferences.getPreferenceString(PrefEntity.AUTH_TOKEN) == "skip")
        {
            menu.let {
                menu!!.findItem(R.id.action_logout).setVisible(false)
            }
        }
        else
        {
            menu.let {
                menu!!.findItem(R.id.action_logout).setVisible(true)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_logout -> {

                Alerts.showBottomSheetSimpleConfirmationDialog(this,
                    getString(R.string.logout_msg),
                    null, false, getString(R.string.cancel),
                    getString(R.string.logout), object : Alerts.OnConfirmationDialog{
                        override fun onYes() {

                            Preferences.removeAllPreference()
                            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                            this@MainActivity.finish()
                        }

                        override fun onNo() {

                        }
                    })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}