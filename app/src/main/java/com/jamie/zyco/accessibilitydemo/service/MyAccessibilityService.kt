package com.jamie.zyco.accessibilitydemo.service

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class MyAccessibilityService : AccessibilityService() {

    companion object {
        val TAG = MyAccessibilityService::class.java.simpleName
        @SuppressLint("StaticFieldLeak")
        var sAccessibilityService: AccessibilityService? = null
    }

    /**
     * 在辅助功能设置界面开启辅助功能后，会在主线程执行该方法
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Log.i(TAG, "onAccessibilityEvent")
        sAccessibilityService = this
    }

    override fun onInterrupt() {

    }
}