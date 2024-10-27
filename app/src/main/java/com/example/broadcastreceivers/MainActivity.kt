package com.example.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var chargingReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chargingStatus: TextView = findViewById(R.id.chargingStatus)
        chargingStatus.text = "Waiting for charging status..."

        chargingReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                val isFull = status == BatteryManager.BATTERY_STATUS_FULL

                chargingStatus.text = when {
                    isCharging -> "Device is charging \uD83D\uDD0B"
                    isFull -> "Device is fully charged"
                    else -> "Device is not charging \uD83D\uDD0C"
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(chargingReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(chargingReceiver)
    }
}