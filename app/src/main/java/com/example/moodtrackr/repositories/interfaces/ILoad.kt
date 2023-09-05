package com.example.moodtrackr.repositories.interfaces

interface ILoad<T> {
    fun load(): T
}