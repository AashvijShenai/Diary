package com.example.diaryv2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.diaryv2.ui.theme.Diaryv2Theme
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Define a key for SharedPreferences
private const val PREFS_KEY = "thoughts_journal_"

class ThoughtsJournalActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var editText: EditText
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highlightjournalactivity)

        // Initialize GestureDetector
        gestureDetector = GestureDetector(this, this)

        val editSubtitle = findViewById<TextView>(R.id.highlightsText)
        editSubtitle.text = "Notes/Thoughts"

        editText = findViewById(R.id.highsTextBody)
        editText.hint = "Anything on your mind ..."

        val selectedDate = intent.getStringExtra("selected_date")
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedText = prefs.getString(PREFS_KEY + selectedDate, "")
        editText.setText(savedText)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No implementation needed
            }

            override fun afterTextChanged(s: Editable?) {
                val textToSave = editText.text.toString()
                val editor = prefs.edit()
                editor.putString(PREFS_KEY + selectedDate, textToSave)
                editor.apply()
            }
        })

        val sidebarListView = findViewById<ListView>(R.id.sidebarListView)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val menuButton: ImageButton = findViewById(R.id.menuButton)
        setSupportActionBar(toolbar)
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
        val adapter = ArrayAdapter(this, R.layout.sidebar_item_layout, R.id.sidebar_item_text, sidebarItems)
        sidebarListView.adapter = adapter

        sidebarListView.setOnItemClickListener { _, _, position, _ ->
            // Handle item click
            // Perform actions based on the selected item
            when (sidebarItems[position]) {
                "Calendar" -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Log touch event for debugging
        Log.d("TouchEvent", "Action: ${event.action}, X: ${event.x}, Y: ${event.y}")

        // Pass the touch event to the GestureDetector
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        Log.d("Gesture", "onDown")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        Log.d("Gesture", "onShowPress")}

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.d("Gesture", "onSingleTapUp")
        // Start RelationshipsActivity
        val intent = Intent(this, HighlightJournalActivity::class.java)
        val selectedDate = intent.getStringExtra("selected_date")
        intent.putExtra("selected_date", selectedDate)
        startActivity(intent)
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d("Gesture", "onScroll")
        return false
    }

    override fun onLongPress(e: MotionEvent) {
        Log.d("Gesture", "onLongPress")}

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d("Gesture", "onFling")
        val sensitivity = 50
        val diffX = e2?.x?.minus(e1?.x ?: 0f) ?: 0f
        val diffY = e2?.y?.minus(e1?.y ?: 0f) ?: 0f
        if (diffX > sensitivity && diffY < sensitivity) {
            // Swipe right detected, log the message
            Log.d("Swipe", "Swipe right detected")
        }
        return false
    }

    // When a back is detected, start calendar
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, CalendarActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }

    // Override onDestroy to clear SharedPreferences when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
    }
}