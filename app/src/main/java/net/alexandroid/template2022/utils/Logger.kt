package net.alexandroid.template2022.utils

import android.util.Log
import net.alexandroid.template2022.BuildConfig
import net.alexandroid.template2022.utils.Logger.logIt
import net.alexandroid.template2022.utils.Logger.whoIsCallingMe
import java.io.PrintWriter
import java.io.StringWriter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Logger {
    private const val packageName = BuildConfig.APPLICATION_ID
    private val isLogsShown = BuildConfig.DEBUG

    private const val tag: String = "MyLog"

    private const val isThreadNameVisible = false
    private const val isTimeVisible = true
    private const val isPackageNameVisible = false
    private const val isClassNameVisible = true
    private const val isMethodNameVisible = true
    private const val isSpacingEnabled = true
    private const val isLengthShouldWrap = true

    private const val classNameLength = 15
    private const val packageAndClassNameLength = 25
    private const val methodNameLength = 20
    private const val threadNameLength = 6
    private const val timeFormat = "HH:mm:ss.SSS"

    @Suppress("unused")
    internal fun whoIsCallingMe() {
        if (!isLogsShown) return

        val result = StringBuilder()
        val stackTrace = Thread.currentThread().stackTrace
        val startIndex = (stackTrace.size - 1).coerceAtMost(10)
        for (i in startIndex downTo 2) {
            val className = stackTrace[i].className ?: ""
            if (isClassShouldBeSkipped(className)) continue
            if (className.contains("DispatchedTask") || className.contains("BaseContinuationImpl")) continue
            val element = stackTrace[i]
            addClassName(element, result, false)
            addMethodName(element, result, false)
            result.append(" > ")
        }
        Log.println(Log.WARN, tag, result.toString())
    }

    internal fun logIt(level: Int, msg: String, t: Throwable?, customTag: String?) {
        if (!isLogsShown) return

        val stackTrace = Thread.currentThread().stackTrace
        val elementIndex: Int = getElementIndex(stackTrace)
        if (elementIndex == 0) return

        val element = stackTrace[elementIndex]
        val result = StringBuilder()

        if (isTimeVisible) result.append(getTime()).append(" - ")
        if (isThreadNameVisible) result.append("T:").append(getThreadId()).append(" | ")
        if (isClassNameVisible) addClassName(element, result)
        if (isMethodNameVisible) addMethodName(element, result)

        addMessage(msg, result)
        addExceptionIfNotNull(t, result)

        val tag = customTag ?: tag

        Log.println(level, tag, result.toString())
    }

    private fun getElementIndex(stackTrace: Array<StackTraceElement>?): Int {
        if (stackTrace == null) return 0
        for (i in 2 until stackTrace.size) {
            val className = stackTrace[i].className ?: ""
            if (isClassShouldBeSkipped(className)) continue
            return i
        }
        return 0
    }

    private fun isClassShouldBeSkipped(className: String) =
        (className.contains(this.javaClass.simpleName)
                || className.contains("Logger")
                || className == "$packageName.util.L")

    private fun getThreadId(): StringBuilder {
        val name = Thread.currentThread().name
        val threadId = StringBuilder(name)
        addSpaces(threadId, threadNameLength - name.length)
        return threadId
    }

    private fun addClassName(
        element: StackTraceElement,
        result: StringBuilder,
        spacesOn: Boolean = true
    ) {
        val fullClassName = element.className
        val maxLength = if (isPackageNameVisible) packageAndClassNameLength else classNameLength

        var classNameFormatted = if (isPackageNameVisible) {
            fullClassName.replace(packageName, "")
        } else {
            fullClassName.substringAfterLast(".")
        }

        if (element.methodName == "invokeSuspend") classNameFormatted =
            classNameFormatted.substringBefore("$")

        if (isLengthShouldWrap) classNameFormatted = wrapString(classNameFormatted, maxLength)

        result.append(classNameFormatted)

        if (spacesOn) addSpaces(result, maxLength - classNameFormatted.length)
        result.append(" # ")
    }


    private fun addMethodName(
        element: StackTraceElement,
        result: StringBuilder,
        spacesOn: Boolean = true
    ) {
        var methodName = if (element.methodName == "invokeSuspend") {
            element.className.substringAfter("$").substringBefore("$")
        } else {
            element.methodName
        }

        if (isLengthShouldWrap) methodName = wrapString(methodName, methodNameLength)
        result.append("$methodName()")
        if (spacesOn) addSpaces(result, methodNameLength - methodName.length)
    }

    private fun addMessage(msg: String, result: StringBuilder) {
        if (msg.isNotEmpty() && msg != "Empty/NULL log message") {
            result.append("=> ")
            result.append(msg)
        }
    }

    private fun addExceptionIfNotNull(t: Throwable?, result: StringBuilder) {
        if (t != null) {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            t.printStackTrace(pw)
            pw.flush()
            result.append("\n Throwable: ")
            result.append(sw.toString())
        }
    }

    private fun wrapString(string: String, maxLength: Int): String {
        return string.substring(0, string.length.coerceAtMost(maxLength))
    }

    private fun addSpaces(result: StringBuilder, spaces: Int) {
        if (isSpacingEnabled && spaces > 0) result.append(" ".repeat(spaces))
    }

    private fun getTime(): String? {
        val df: DateFormat = SimpleDateFormat(timeFormat, Locale.getDefault())
        return df.format(Calendar.getInstance().time)
    }
}

@Suppress("unused")
fun logV(msg: String = "", customTag: String? = null, t: Throwable? = null) {
    logIt(Log.VERBOSE, msg, t, customTag)
}

@Suppress("unused")
fun logD(msg: String = "", customTag: String? = null, t: Throwable? = null) {
    logIt(Log.DEBUG, msg, t, customTag)
}

@Suppress("unused")
fun logI(msg: String = "", customTag: String? = null, t: Throwable? = null) {
    logIt(Log.INFO, msg, t, customTag)
}

@Suppress("unused")
fun logW(msg: String = "", customTag: String? = null, t: Throwable? = null) {
    logIt(Log.WARN, msg, t, customTag)
}

@Suppress("unused")
fun logE(msg: String = "", customTag: String? = null, t: Throwable? = null) {
    logIt(Log.ERROR, msg, t, customTag)
}

@Suppress("unused")
fun logA(msg: String = "", customTag: String? = null, t: Throwable? = null) {
    logIt(Log.ASSERT, msg, t, customTag)
}

@Suppress("unused")
fun logWhoIs() {
    whoIsCallingMe()
}
