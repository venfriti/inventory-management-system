package com.venfriti.inventorymanagement.utils

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import com.venfriti.inventorymanagement.utils.RecordCheck.isAuthenticated


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
                // Check if the message is already a String
                if (msg is String) {
                    // If it's a JSON string, you can optionally parse it to JSONObject
                    try {
                        val jsonObject = JSONObject(msg)
                        val text = jsonObject.getString("text") // Assuming the JSON contains a "text" field
                        onMessageReceived(text)
                    } catch (e: Exception) {
                        // If it's not a JSON string, just handle it as a plain text message
                        onMessageReceived(msg)
                    }
                } else {
                    // Handle other types if necessary
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

//fun sendMessage(message: String) {
//    if (socket.connected()) {
//        val jsonObject = JSONObject()
//        jsonObject.put("text", message)
//        socket.emit("message", jsonObject)
//    }
//}
