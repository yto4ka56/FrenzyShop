package com.example.frenzyshop

class User(val login: String, val email: String, val hash: String,
           val salt: String, val code: Int, val access: Int, val path: Int) {

}