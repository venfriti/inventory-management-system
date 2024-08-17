package com.venfriti.inventorymanagement.ui.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener


fun setupWebSocket(onLoginSuccess: (Boolean) -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("ws://192.168.0.101:60")
        .build()

    val webSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            if (text.contains("login_success")) {
                onLoginSuccess(true)
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            t.printStackTrace()
        }
    }

    client.newWebSocket(request, webSocketListener)
    client.dispatcher.executorService.shutdown()
}

