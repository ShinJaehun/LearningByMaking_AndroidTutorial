package com.shinjaehun.chattingapp

data class Message(
    var message: String?,
    var sendId: String?,
) {
    constructor(): this("", "")
}
