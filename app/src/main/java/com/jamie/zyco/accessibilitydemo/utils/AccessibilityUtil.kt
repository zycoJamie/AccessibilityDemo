package com.jamie.zyco.accessibilitydemo.utils

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.jamie.zyco.accessibilitydemo.service.MyAccessibilityService

class AccessibilityUtil {

    companion object {
        val TAG = MyAccessibilityService::class.java.simpleName
        @JvmField
        val sServiceName = MyAccessibilityService::class.java.canonicalName

        fun isGrantAccessibility(context: Context): Boolean {
            //判断辅助服务是否可用
            val accessibilityEnable = Settings.Secure.getInt(context.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
            if (accessibilityEnable == 1) {
                val completeName = "${context.packageName}/$sServiceName"
                //获取已经授权的辅助服务的列表
                val enableList = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
                Log.i(TAG, enableList)
                val splitter = TextUtils.SimpleStringSplitter(':')
                splitter.setString(enableList)
                while (splitter.hasNext()) {
                    if (splitter.next() == completeName) {
                        return true
                    }
                }
            }
            return false
        }

        /**
         * 获取root节点，通过id找到指定view并进行模拟点击
         */
        fun operatorById(accessibilityService: AccessibilityService, viewId: String) {
            val rootNode: AccessibilityNodeInfo = accessibilityService.rootInActiveWindow
            val nodeList = rootNode.findAccessibilityNodeInfosByViewId(viewId)
            nodeList.forEach {
                it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }

    }
}