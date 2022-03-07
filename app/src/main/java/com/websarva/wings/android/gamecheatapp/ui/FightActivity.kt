package com.websarva.wings.android.gamecheatapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.websarva.wings.android.gamecheatapp.databinding.ActivityFightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFightBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }
    }
}