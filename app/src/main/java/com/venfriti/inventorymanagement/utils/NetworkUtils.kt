package com.venfriti.inventorymanagement.utils

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject


private lateinit var socket: Socket

fun initSocketIO(onMessageReceived: (String, String) -> Unit) {
    try {
        socket = IO.socket("http://192.168.0.101:5000")

        socket.on(Socket.EVENT_CONNECT) {
            println("Socket.IO Connected")
        }

        socket.on("message") { args ->
            if (args.isNotEmpty()) {
                val msg = args[0] as? JSONObject
                if (msg != null) {
                    val sender = msg.getString("name")
                    val text = msg.getString("message")
                    onMessageReceived(sender, text)
                } else {
                    println("No valid message received")
                }
            }
        }

        socket.on(Socket.EVENT_DISCONNECT) {
            println("Socket.IO Disconnected")
        }

        socket.connect()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
