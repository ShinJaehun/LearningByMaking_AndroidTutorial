package com.shinjaehun.firebasecrud

data class User(
    var userKey: String,
    var userName: String,
    var userAge: String
) {
    constructor(): this("", "", "")
}
