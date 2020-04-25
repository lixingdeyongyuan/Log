package com.aixue.mlog

import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog

class LogLib {

    companion object {

        fun registerInit(
            isDebug: Boolean,
            cacheDir: String,
            logDir: String,
            tag: String?
        ) {
            RLog.init(isDebug, tag)
            if (isDebug) {
                register(
                    isDebug,
                    Xlog.LEVEL_DEBUG,
                    Xlog.AppednerModeAsync,
                    cacheDir,
                    logDir,
                    "logaixue",
                    1,
                    ""
                )
            } else {
                register(
                    isDebug,
                    Xlog.LEVEL_INFO,
                    Xlog.AppednerModeAsync,
                    cacheDir,
                    logDir,
                    "logaixue",
                    1,
                    ""
                )
            }

        }


        fun register(
            isDebug: Boolean,
            level: Int,
            mode: Int,
            cacheDir: String,
            logDir: String,
            nameprefix: String,
            cacheDays: Int,
            pubkey: String
        ) {
            System.loadLibrary("c++_shared");
            System.loadLibrary("marsxlog");
            Xlog.appenderOpen(level, mode, cacheDir, logDir, nameprefix, cacheDays, pubkey)
            Xlog.setConsoleLogOpen(isDebug)
            Log.setLogImp(Xlog())
        }

        fun unregister() {
            Log.appenderClose();
        }

    }

}