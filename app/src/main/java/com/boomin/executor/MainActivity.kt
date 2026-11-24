package com.boomin.executor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private val bg = Color.rgb(7, 10, 16)
    private val blue = Color.rgb(22, 139, 255)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 32, 28, 32)
            setBackgroundColor(bg)
        }

        val title = TextView(this).apply {
            text = "BOOMIN EXECUTOR"
            textSize = 26f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        val subtitle = TextView(this).apply {
            text = "LUAU DEVELOPMENT EDITOR  •  VERSION 1.0"
            textSize = 11f
            setTextColor(blue)
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 30)
        }
        val info = TextView(this).apply {
            text = "A standalone Luau editor with a movable floating window.\n\nThis app edits and stores scripts locally. Its TEST button provides a local preview only; it does not run code inside Roblox."
            textSize = 14f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
            setPadding(4, 8, 4, 28)
        }
        val permission = makeButton("GRANT OVERLAY PERMISSION")
        permission.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            } else {
                startOverlay()
            }
        }
        val launch = makeButton("START FLOATING EDITOR")
        launch.setOnClickListener {
            if (Settings.canDrawOverlays(this)) startOverlay()
            else {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }
        val note = TextView(this).apply {
            text = "Tip: Enable “Display over other apps” for BOOMIN EXECUTOR, then return here."
            textSize = 12f
            setTextColor(Color.GRAY)
            gravity = Gravity.CENTER
            setPadding(0, 22, 0, 0)
        }

        root.addView(title, matchWrap())
        root.addView(subtitle, matchWrap())
        root.addView(info, matchWrap())
        root.addView(permission, matchWrap(58))
        root.addView(launch, matchWrap(58))
        root.addView(note, matchWrap())
        setContentView(root)
    }

    private fun startOverlay() {
        val intent = Intent(this, FloatingService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(intent)
        else startService(intent)
    }

    private fun makeButton(label: String) = Button(this).apply {
        text = label
        isAllCaps = false
        setTextColor(Color.WHITE)
        setBackgroundColor(blue)
    }

    private fun matchWrap(height: Int = ViewGroup.LayoutParams.WRAP_CONTENT) =
        LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height).apply {
            bottomMargin = 12
        }
}
