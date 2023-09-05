package com.example.moodtrackr.repositories.interfaces

interface ISave<T> {
    fun save(t: T)
}