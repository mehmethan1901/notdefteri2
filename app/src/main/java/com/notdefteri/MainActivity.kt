package com.notdefteri

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pad = (16 * resources.displayMetrics.density).toInt()
        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setPadding(pad, pad, pad, pad)

        val title = TextView(this)
        title.text = "Not Defteri"
        title.textSize = 22f
        root.addView(title)

        val input = EditText(this)
        input.hint = "Notunu yaz"
        root.addView(input)

        val save = Button(this)
        save.text = "Kaydet"
        root.addView(save)

        val note = TextView(this)
        note.textSize = 16f
        root.addView(note)

        val prefs = getSharedPreferences("notes", MODE_PRIVATE)
        note.text = prefs.getString("n", "")

        save.setOnClickListener {
            prefs.edit().putString("n", input.text.toString()).apply()
            note.text = input.text.toString()
            input.setText("")
        }

        val btn = Button(this)
        btn.text = "Bildirim erişimi"
        root.addView(btn)
        btn.setOnClickListener {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        setContentView(root)
    }
}
