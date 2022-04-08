package com.example.gamerfinder.utils


class HttpListener(var listener: Action) {

    /*fun setActionListener(listener: Action?) {
        this.listener = listener
    }*/
}

interface Action {
    fun onMessage(value: Any?)
}