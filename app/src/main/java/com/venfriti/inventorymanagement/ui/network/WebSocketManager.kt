package com.venfriti.inventorymanagement.ui.network


import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketManager {

    private val client = OkHttpClient()
    private lateinit var webSocket: WebSocket
    private var messageListener: ((String) -> Unit)? = null

    fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                // Connection established
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // Handle incoming messages
//                if (text == "AUTH_SUCCESS") {
//                    // Trigger login action
//                }
                messageListener?.invoke(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                // Handle binary messages if needed
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                // Handle failure
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // Handle closure
            }
        })
    }

    fun setOnMessageListener(listener: (String) -> Unit) {
        messageListener = listener
    }

    fun close() {
        webSocket.close(1000, "Closing WebSocket")
    }
}
