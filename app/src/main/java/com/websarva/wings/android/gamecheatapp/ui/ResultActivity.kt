package com.websarva.wings.android.gamecheatapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.websarva.wings.android.gamecheatapp.databinding.ActivityResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }

        // 送信データのnullチェック
        val extras = intent?.extras
        if (extras != null && extras.containsKey("clear")){
            Log.d("test", extras.getBoolean("clear").toString())
        }
    }
}