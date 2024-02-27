package com.example.diaryv2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Define a key for SharedPreferences
private const val PREFS_KEY = "self_plan_items_"

class SelfPlanActivity : ComponentActivity(), GestureDetector.OnGestureListener {
    private lateinit var editText: EditText
    private lateinit var addButton: ImageButton
    private lateinit var checklistLayout: LinearLayout
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selfplanactivity)

        // Initialize GestureDetector
        gestureDetector = GestureDetector(this, this)

        val sidebarListView = findViewById<ListView>(R.id.sidebarListView)

        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)
        checklistLayout = findViewById(R.id.checklistLayout)

        addButton.setOnClickListener {
            val item = editText.text.toString()
            if (item.isNotEmpty()) {
                addChecklistItem(item)
                editText.text.clear()
            }
        }

        // Load checklist items from SharedPreferences
        val selectedDate = intent.getStringExtra("selected_date")
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val checklistItems = prefs.getStringSet(PREFS_KEY + selectedDate, HashSet<String>())?.toList()

        // If checklistItems is not null, populate the checklist with the items
        checklistItems?.forEach { item ->
            addChecklistItem(item)
        }

        val fabMenu = findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        fabMenu.setOnClickListener {
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
            // Perform actions based on the selected item
            when (sidebarItems[position]) {
                "Calendar" -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                }
            }
        }
    }

    // TODO: The edit implementation is crappy and if the entry is removed
    //        the checkbox should go away
    private fun addChecklistItem(item: String) {
        val itemLayout = LinearLayout(this)
        itemLayout.orientation = LinearLayout.HORIZONTAL

        val checkbox = CheckBox(this)
        checkbox.text = item
        checkbox.textSize = 24f // Set the text size here

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            // Save the checkbox state when it changes
            val selectedDate = intent.getStringExtra("selected_date")
            val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean(PREFS_KEY + selectedDate + item, isChecked)
            editor.apply()
        }

        checkbox.setOnLongClickListener {
            val editText = EditText(this)
            editText.setText(checkbox.text)
            editText.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val newText = editText.text.toString().trim()
                    if (newText.isNotEmpty()) {
                        checkbox.text = newText
                    } else {
                        checklistLayout.removeView(itemLayout)
                    }
                    return@setOnKeyListener true
                }
                false
            }
            itemLayout.addView(editText)
            true
        }

        itemLayout.addView(checkbox)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.marginStart = resources.getDimensionPixelSize(R.dimen.checklist_item_margin_start)
        checkbox.layoutParams = params

        checklistLayout.addView(itemLayout)

        // Add the item to SharedPreferences when it is added to the checklist
        val selectedDate = intent.getStringExtra("selected_date")
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isChecked = prefs.getBoolean(PREFS_KEY + selectedDate + item, false)
        checkbox.isChecked = isChecked
        val editor = prefs.edit()
        val checklistItems = prefs.getStringSet(PREFS_KEY + selectedDate, HashSet<String>())?.toMutableSet()
        checklistItems?.add(item)
        editor.putStringSet(PREFS_KEY + selectedDate, checklistItems)
        editor.apply()
    }

    // TODO: Touch works only at the bottom of the screen, Fix this
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

    override fun onShowPress(e: MotionEvent) {Log.d("Gesture", "onShowPress")}

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.d("Gesture", "onSingleTapUp")
        // Start RelationshipsActivity
        val intent = Intent(this, RelationsPlanActivity::class.java)
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

    override fun onLongPress(e: MotionEvent) {Log.d("Gesture", "onLongPress")}

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