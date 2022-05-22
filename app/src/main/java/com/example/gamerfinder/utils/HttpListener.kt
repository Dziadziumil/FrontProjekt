package com.example.gamerfinder.utils


class HttpListener<T>(var listener: Action<T>) {

    /*fun setActionListener(listener: Action?) {
        this.listener = listener
    }*/
}

interface Action<T> {
    fun onMessage(value: T?)
}