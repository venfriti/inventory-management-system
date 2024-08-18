package com.venfriti.inventorymanagement.utils

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject


private lateinit var socket: Socket

fun initSocketIO(onMessageReceived: (String) -> Unit) {
    try {
        socket = IO.socket("http://192.168.0.2:5000")

        socket.on(Socket.EVENT_CONNECT) {
            println("Socket.IO Connected")
        }

        socket.on("message") { args ->
            if (args.isNotEmpty()) {
                val msg = args[0]
                if (msg is String) {
                    try {
                        val jsonObject = JSONObject(msg)
                        val text = jsonObject.getString("text")
                        onMessageReceived(text)
                    } catch (e: Exception) {
                        onMessageReceived(msg)
                    }
                } else {
                    println("Received unknown message type: $msg")
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
