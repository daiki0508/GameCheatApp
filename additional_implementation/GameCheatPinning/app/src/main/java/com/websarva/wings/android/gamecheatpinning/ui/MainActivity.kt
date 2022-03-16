package com.websarva.wings.android.gamecheatpinning.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.websarva.wings.android.gamecheatpinning.R
import com.websarva.wings.android.gamecheatpinning.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }

        // btStartタップ時の処理
        binding.btStart.setOnClickListener {
            // usernameの文字数チェック
            val username = binding.edUsername.text.toString()
            if (username.isNotEmpty()){
                val intent = Intent(this, FightActivity::class.java).apply {
                    // 送信データの作成
                    putExtra("username", username)
                }
                // 対象アクティビティの起動
                startActivity(intent)
            }else{
                Toast.makeText(this, "UserName has not been entered.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}