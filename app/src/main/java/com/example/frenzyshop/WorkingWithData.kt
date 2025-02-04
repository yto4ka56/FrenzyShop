package com.example.frenzyshop
import kotlin.random.Random

interface WorkingWithData {
    fun generateRandomSalt(): String {
        val salt = Random.nextInt(1, 31)
        return salt.toString()
    }
    fun generateHash(password: String, salt: String): String {
        val hash = "PIP" + salt + password
        return hash
    }
}