package com.websarva.wings.android.gamecheatapp.model

import androidx.annotation.Keep

@Keep
data class Result(
    var username: String,
    var turn: Int,
    var userHp: Int,
    var maxDamage: Int
)
