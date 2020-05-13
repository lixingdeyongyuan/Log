package com.aixue.mlog

import android.util.Log
import java.util.*

object RLog {


    private var TAG = "rlog"
    var sIsDebug = true

    @JvmStatic
    fun init(isDebug: Boolean, tag: String?) {
        sIsDebug = isDebug
        TAG = if (tag == null) "rlog" else tag;
    }


    @JvmStatic
    fun d(msg: String) {
        val stackTraceElement = getStackTrace(4)
        val sb = StringBuilder()
        sb.append("(")
        sb.append(stackTraceElement!!.fileName)
        sb.append(":")
        sb.append(stackTraceElement.lineNumber)
        sb.append(")")
        sb.append(msg)
        Log.d(TAG, sb.toString())
    }

    @JvmStatic
    private fun d(msg: String, place: Int) {
        val stackTraceElement = getStackTrace(place)
        val sb = StringBuilder()
        sb.append("(")
        sb.append(stackTraceElement!!.fileName)
        sb.append(":")
        sb.append(stackTraceElement.lineNumber)
        sb.append(")")
        sb.append(msg)
        Log.d(TAG, sb.toString())
    }

    private val sTimeStack = Stack<Long>()

    @JvmStatic
    fun startRecordTime() {
        sTimeStack.push(System.currentTimeMillis())
    }

    @JvmStatic
    fun endRecordTime() {
        endRecordTime("", 6)
    }

    @JvmStatic
    fun endRecordTime(tag: String, vararg place: Int) {
        var isTimeErr = false
        var startTime: Long = 0
        try {
            startTime = sTimeStack.pop()
        } catch (e: Exception) {
            isTimeErr = true
            e.printStackTrace()
        }

        val sb = StringBuilder()
        sb.append(tag)
        sb.append(" cost time:")
        if (isTimeErr) {
            sb.append("Err Time ")
        } else {
            sb.append(System.currentTimeMillis() - startTime)
        }
        sb.append("ms")
        if (place != null && place.size > 0) {
            d(sb.toString(), place[0])
        } else {
            d(sb.toString(), 5)
        }

    }

    @JvmStatic
    fun error(msg: String) {
        Log.e(TAG, msg)
    }


    private fun getStackTrace(place: Int): StackTraceElement? {
        if (!sIsDebug) {
            return null
        }
        val traceElements = Thread.currentThread().stackTrace
        try {
            //判断调用栈的层级，大于place的才打印Log输出
            if (traceElements != null && traceElements.size > place) {
                return traceElements[place]
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return null
    }


}