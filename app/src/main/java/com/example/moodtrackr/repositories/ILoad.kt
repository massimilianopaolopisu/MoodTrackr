package com.example.moodtrackr.repositories

interface ILoad<T> {
    fun load(): T
}