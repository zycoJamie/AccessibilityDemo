package com.jamie.zyco.accessibilitydemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.jamie.zyco.accessibilitydemo.service.MyAccessibilityService
import com.jamie.zyco.accessibilitydemo.utils.AccessibilityUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mTimer: Timer
    private lateinit var mTimerTask: TimerTask

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }

    private fun initView() {
        mOpenBtn.setOnClickListener {
            if (!AccessibilityUtil.isGrantAccessibility(this@MainActivity)) {
                go2Setting()
                startTimer()
            } else {
                Toast.makeText(this@MainActivity, "辅助功能已经打开", Toast.LENGTH_SHORT).show()
            }
        }
        mSimulationBtn.setOnClickListener {
            if (AccessibilityUtil.isGrantAccessibility(this@MainActivity)) {
                it.postDelayed({
                    AccessibilityUtil.operatorById(MyAccessibilityService.sAccessibilityService!!, "$packageName:id/mOneCb")
                }, 500L)
                it.postDelayed({
                    AccessibilityUtil.operatorById(MyAccessibilityService.sAccessibilityService!!, "$packageName:id/mTwoCb")
                }, 1000L)
                it.postDelayed({
                    AccessibilityUtil.operatorById(MyAccessibilityService.sAccessibilityService!!, "$packageName:id/mThreeCb")
                }, 1500L)
                it.postDelayed({
                    AccessibilityUtil.operatorById(MyAccessibilityService.sAccessibilityService!!, "$packageName:id/mFourCb")
                }, 2000L)
            } else {
                Toast.makeText(this@MainActivity, "请先打开辅助功能", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun go2Setting() {
        val intent = Intent()
        intent.action = Settings.ACTION_ACCESSIBILITY_SETTINGS
        startActivity(intent)
    }

    private fun startTimer() {
        mTimer = Timer()
        mTimerTask = object : TimerTask() {
            override fun run() {
                val isGranted = AccessibilityUtil.isGrantAccessibility(this@MainActivity)
                if (isGranted) {
                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                }
            }
        }
        mTimer.schedule(mTimerTask, 0L, 1000L)
    }

    override fun onRestart() {
        super.onRestart()
        mTimer?.cancel()
        mTimerTask?.cancel()
        if (AccessibilityUtil.isGrantAccessibility(this@MainActivity)) {
            Toast.makeText(this@MainActivity, "辅助功能已经打开", Toast.LENGTH_SHORT).show()
        }
    }
}
