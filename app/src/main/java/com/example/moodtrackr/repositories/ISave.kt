package com.example.moodtrackr.repositories

interface ISave<T> {
    fun save(t: T)
}