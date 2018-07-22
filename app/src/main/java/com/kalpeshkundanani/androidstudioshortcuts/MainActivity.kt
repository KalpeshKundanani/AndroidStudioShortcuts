package com.kalpeshkundanani.androidstudioshortcuts

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.text.SpannableString
import android.view.ViewGroup
import android.widget.TextView




class MainActivity : AppCompatActivity() {
    private var values: Array<String>? = null
    private var selectedShortcuts: String? = null
    private val DARK_THEME_NAME = "Dark - Inspired by Darcula"
    private val LIGHT_THEME_NAME = "Light - Inspired by Intellij"


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getThemeId())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        values = resources.getStringArray(R.array.shortcuts_base_array)

        selectedShortcuts =
                if (savedInstanceState == null) getString(R.string.general)
                else savedInstanceState.getString("selectedShortcuts")

        populateList()

        fab.setOnClickListener { showShortcutList() }
        tv_selected_shortcut_title.setOnClickListener { showShortcutList() }
    }

    private fun getThemeId(): Int {
        val themeName: String = PreferenceManager.getDefaultSharedPreferences(this).getString("AppTheme", DARK_THEME_NAME)
        return when (themeName) {
            DARK_THEME_NAME -> R.style.AppTheme_Darcula
            LIGHT_THEME_NAME-> R.style.AppTheme_Intellij
            else -> R.style.AppTheme_Darcula
        }
    }

    private fun showShortcutList() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_sheet_list_view)

        val shortcutListView = dialog.findViewById<ListView>(R.id.lv_shortcut_list)
        shortcutListView?.adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values)

        shortcutListView?.onItemClickListener = OnItemClickListener { _, _, position, _ ->

            selectedShortcuts = shortcutListView?.getItemAtPosition(position) as String

            populateList()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun populateList() {
        val shortcutList: ArrayList<Shortcut> = getShortcutList(selectedShortcuts)
        rv_shortcut_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_shortcut_list.adapter = ShortcutListAdapter(shortcutList)
        tv_selected_shortcut_title.text = selectedShortcuts
    }


    private fun getShortcutList(shortcutTitle: String?): ArrayList<Shortcut> {
        val strArr = getShortcutArray(shortcutTitle)
        val shortcutList: ArrayList<Shortcut> = ArrayList()
        strArr?.forEach { shortcutList.add(Shortcut(it)) }
        return shortcutList
    }

    private fun getShortcutArray(shortcutTitle: String?): Array<String>? = when (shortcutTitle) {
        "General" -> resources.getStringArray(R.array.shortcuts_general)
        "Refactoring" -> resources.getStringArray(R.array.shortcuts_refactoring)
        "Debugging" -> resources.getStringArray(R.array.shortcuts_debugging)
        "Build and run" -> resources.getStringArray(R.array.shortcuts_build_and_run)
        "Writing code" -> resources.getStringArray(R.array.shortcuts_writing_code)
        "Navigating and searching within Studio" -> resources.getStringArray(R.array.shortcuts_navigating_and_searching_within_studio)
        "Version control / local history" -> resources.getStringArray(R.array.shortcuts_version_control_or_local_history)
        else -> null
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return showAboutDialog()
            R.id.action_change_theme -> return showThemeSelectorDialog()
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showAboutDialog(): Boolean {
        val message = TextView(this)
        val s = SpannableString(this.getText(R.string.about_dialog_message))
        Linkify.addLinks(s, Linkify.WEB_URLS)
        message.text = s
        message.movementMethod = LinkMovementMethod.getInstance()
        val linearLayout = LinearLayout(this)
        val padding = 32
        linearLayout.setPadding(padding,padding,padding,padding)
        linearLayout.addView(message)
        val builder = AlertDialog.Builder(this)
                .setTitle("About App")
                .setCancelable(true)
                .setView(linearLayout)
                .create()
        builder.show()
        return true
    }

    private fun showThemeSelectorDialog(): Boolean {
        val items = arrayOf(DARK_THEME_NAME,LIGHT_THEME_NAME)

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Themes")
                .setItems(items) { _, item ->
                    PreferenceManager.getDefaultSharedPreferences(this).edit().putString("AppTheme", items[item]).apply()
                    recreate()
                }
        builder.create().show()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("selectedShortcuts", selectedShortcuts)
    }
}
