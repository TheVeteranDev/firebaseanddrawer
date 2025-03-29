package com.example.firebaseanddrawer.ui.players

import com.example.firebaseanddrawer.R

data class Player(
    var name: String,
    var number: Int,
    var position: String,
    var status: String,
    var photo: Int
) {
    constructor() : this("", 0, "", "", R.drawable.psu_logo)
}
