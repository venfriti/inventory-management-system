package com.venfriti.inventorymanagement.utils

import android.util.Log
import com.venfriti.inventorymanagement.BuildConfig
import com.venfriti.inventorymanagement.utils.Constants.CONNECTION_TIMEOUT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URI
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

private lateinit var webSocketClient: WebSocketClient

fun initWebSocket(onMessageReceived: (String, String) -> Unit) {
    try {
        val uri = URI("ws://192.168.43.23/ws")
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                println("WebSocket Connected")
            }

            override fun onMessage(message: String?) {
                if (message != null) {
                    Log.d("MESSAGE", message)
                    val msg = JSONObject(message)
                    val sender = msg.getString("name")
                    val text = msg.getString("message")
                    onMessageReceived(sender, text)
                } else {
                    println("No valid message received")
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                println("WebSocket Disconnected: $reason")
            }

            override fun onError(ex: Exception?) {
                ex?.printStackTrace()
            }
        }

        webSocketClient.connect()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

suspend fun sendEmail(toEmail: String, subject: String, body: String): Boolean {
    return try {
        val properties = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", BuildConfig.EMAIL_HOST)
            put("mail.smtp.port", BuildConfig.EMAIL_PORT)
            put("mail.smtp.timeout", CONNECTION_TIMEOUT)
            put("mail.smtp.connectiontimeout", CONNECTION_TIMEOUT)

            put("mail.smtp.localhost", "com.venfriti.inventorymanagement")
            put("mail.smtp.from", BuildConfig.APP_EMAIL)
            put("mail.debug", "true")
        }

        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(BuildConfig.APP_EMAIL, BuildConfig.APP_EMAIL_KEY)
            }
        })

        withContext(Dispatchers.IO) {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(BuildConfig.APP_EMAIL))
                setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
                )
                setSubject(subject)
                setText(body)
            }
            Transport.send(message)
        }
        true
    } catch (e: Exception) {
        Log.e("EmailSender", "Failed to send email", e)
        false
    }
}

suspend fun sendEmailWithRetry(
    toEmail: String = BuildConfig.ADMIN_EMAIL,
    subject: String, body: String,
    maxAttempts: Int = 3
): Boolean {
    repeat(maxAttempts) { attempt ->
        try {
            val success = sendEmail(toEmail, subject, body)
            if (success) return true

            if (attempt < maxAttempts - 1) {
                delay(1000L * (attempt + 1))
            }
        } catch (e: Exception) {
            Log.e("EmailSender", "Attempt ${attempt + 1} failed: ${e.message}")
            if (attempt == maxAttempts - 1) return false
            delay(1000L * (attempt + 1))
        }
    }
    return false
}