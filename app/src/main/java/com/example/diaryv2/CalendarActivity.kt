package com.example.diaryv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.diaryv2.ui.theme.Diaryv2Theme
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import java.text.SimpleDateFormat

var initialSelectedDate = "1"

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendaractivity)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val planButton = findViewById<Button>(R.id.buttonPlan)
        val journalButton = findViewById<Button>(R.id.buttonJournal)
        val sidebarListView = findViewById<ListView>(R.id.sidebarListView)

        initialSelectedDate = "${formatDate(System.currentTimeMillis())}"

        // Button click listeners
        planButton.setOnClickListener {
            // Start PlanActivity
            val intent = Intent(this, SelfPlanActivity::class.java)
            intent.putExtra("selected_date", "${initialSelectedDate}")
            startActivity(intent)
        }

        journalButton.setOnClickListener {
            // Start JournalActivity
            val intent = Intent(this, HighlightJournalActivity::class.java)
            intent.putExtra("selected_date", "${initialSelectedDate}")
            startActivity(intent)
        }
        // CalendarView listener
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Update selected date text
            initialSelectedDate = "${formatDate(year, month, dayOfMonth)}"

            // Show or hide buttons based on date selection
            planButton.visibility = View.VISIBLE
            journalButton.visibility = View.VISIBLE
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val menuButton: ImageButton = findViewById(R.id.menuButton)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        menuButton.setOnClickListener {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }

        // Sidebar items
        val sidebarItems = arrayOf("Calendar", "Habit Tracker")

        // Create adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sidebarItems)
        sidebarListView.adapter = adapter

        sidebarListView.setOnItemClickListener { _, _, position, _ ->
            // Handle item click
            val selectedItem = sidebarItems[position]
            // Perform actions based on the selected item
            when (selectedItem) {
                "Calendar" -> {
                     startActivity(Intent(this, CalendarActivity::class.java))
                }
            }
        }
    }

    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun formatDate(timeInMillis: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date(timeInMillis))
    }

    // When a back is detected, start calendar
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }
}