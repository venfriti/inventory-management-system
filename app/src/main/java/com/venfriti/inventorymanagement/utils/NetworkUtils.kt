package com.venfriti.inventorymanagement.utils

import android.util.Log
import java.net.URI
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject

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
