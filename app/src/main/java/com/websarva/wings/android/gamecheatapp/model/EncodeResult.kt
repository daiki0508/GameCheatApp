package com.websarva.wings.android.gamecheatapp.model

import androidx.annotation.Keep

@Keep
data class EncodeResult(
    var username: String,
    var turn: String,
    var userHp: String,
    var maxDamage: String
)
