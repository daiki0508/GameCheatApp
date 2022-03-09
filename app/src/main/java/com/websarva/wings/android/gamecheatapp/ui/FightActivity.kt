package com.websarva.wings.android.gamecheatapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import com.websarva.wings.android.gamecheatapp.R
import com.websarva.wings.android.gamecheatapp.databinding.ActivityFightBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class FightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFightBinding

    private lateinit var username: String
    private var damage: Int = 0
    private var maxDamage: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFightBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }

        // 送信データのnullチェック
        val extras = intent?.extras
        if (extras != null && extras.containsKey("username")){
            // usernameをセット
            username = extras.getString("username")!!
            binding.tvUsername.text = username

            // フェンリルのHPを設定
            binding.pbFenrirHp.apply {
                val defaultHp = Random.nextInt(501) + 500
                max = defaultHp
                progress = defaultHp
                binding.tvFenrirHp.text = defaultHp.toString()
            }
            // ユーザーのHP設定
            binding.pbUserHp.apply {
                val defaultHp = Random.nextInt(251) + 250
                max = defaultHp
                progress = defaultHp
                binding.tvUserHp.text = defaultHp.toString()
            }

            // メッセージの初期化
            binding.tvMessage.text = getString(R.string.textMessage)
        }else{
            Log.e("ERROR", "extras is null.")
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN){
            // textMessageの内容によって分岐
            binding.tvMessage.also { tvMessage ->
                when(tvMessage.text.toString()){
                    getString(R.string.textMessage) -> {
                        userAttack(tvMessage)
                    }
                    getString(R.string.textMessage2, username, damage) -> {
                        fenrirAttack(tvMessage)
                    }
                    getString(R.string.textMessage3, damage, username) ->{
                        userAttack(tvMessage)
                        // Turnの更新
                        binding.tvTurn.apply {
                            text = (text.toString().toInt() + 1).toString()
                        }
                    }
                }
            }
            // ゲームクリア・ゲームオーバー検知
            if (binding.pbFenrirHp.progress <= 0){
                Log.i("result", "game clear")
                val intent = Intent(this, ResultActivity::class.java).apply {
                    // 送信データの作成
                    putExtra("clear", true)
                    putExtra("username", username)
                    putExtra("turn", binding.tvTurn.text.toString().toInt())
                    putExtra("userHp", binding.pbUserHp.progress)
                    putExtra("maxDamage", maxDamage)
                }
                // 対象アクティビティの起動
                startActivity(intent)
                finish()
            }else if (binding.pbUserHp.progress <= 0){
                Log.i("result", "game over")
                val intent = Intent(this, ResultActivity::class.java).apply {
                    // 送信データの作成
                    putExtra("clear", false)
                    putExtra("username", username)
                }
                // 対象アクティビティの起動
                startActivity(intent)
                finish()
            }
        }

        return true
    }

    private fun userAttack(tvMessage: TextView){
        // ダメージを1～100の間のランダムに設定
        damage = Random.nextInt(100) + 1
        // 最大ダメージを保存
        if (damage > maxDamage){
            maxDamage = damage
        }

        // メッセージの更新
        tvMessage.text = getString(R.string.textMessage2, username, damage)
        // HPの更新
        binding.tvFenrirHp.also { tvFenrir ->
            tvFenrir.text = (tvFenrir.text.toString().toInt() - damage).toString()
        }
        binding.pbFenrirHp.progress -= damage
    }
    private fun fenrirAttack(tvMessage: TextView){
        // ダメージを1～100の間のランダムに設定
        damage = Random.nextInt(150) + 1

        // メッセージの更新
        tvMessage.text = getString(R.string.textMessage3, damage, username)
        // HPの更新
        binding.tvUserHp.also { tvUser ->
            tvUser.text = (tvUser.text.toString().toInt() - damage).toString()
        }
        binding.pbUserHp.progress -= damage
    }
}