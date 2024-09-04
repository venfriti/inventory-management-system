package com.venfriti.inventorymanagement.utils

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject


private lateinit var socket: Socket

fun initSocketIO(onMessageReceived: (String, String) -> Unit) {
    try {
        socket = IO.socket("ws://192.168.43.24:5000")

        socket.on(Socket.EVENT_CONNECT) {
            println("Socket.IO Connected")
        }

        socket.on("message") { args ->
            if (args.isNotEmpty()) {
                Log.d("ERROR", args.toString())
                val messageString = args[0] as? String
                if (messageString != null) {
                    val msg = JSONObject(messageString)
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
