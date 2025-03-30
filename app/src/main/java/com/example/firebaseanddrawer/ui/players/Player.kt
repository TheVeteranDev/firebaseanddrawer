package com.example.firebaseanddrawer.ui.players

import com.example.firebaseanddrawer.R

data class Player(
    var id: String,
    var name: String,
    var number: Int,
    var position: String,
    var status: String,
    var photo: String = "no_player_photo"
) {
    // constructor required for firestore
    constructor() : this("", "", 0, "", "", "no_player_photo")
}
