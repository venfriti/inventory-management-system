package com.venfriti.inventorymanagement.utils

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

fun sendEmail(toEmail: String, subject: String, body: String) {
    CoroutineScope(Dispatchers.IO).launch {
        val properties = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.port", "587")
        }

        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("APP_EMAIL", "APP_EMAIL_KEY")
            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress("admin.inventrid"))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse("ADMIN_EMAIL"))
                setSubject(subject)
                setText(body)
            }
            withContext(Dispatchers.IO){
                Transport.send(message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}