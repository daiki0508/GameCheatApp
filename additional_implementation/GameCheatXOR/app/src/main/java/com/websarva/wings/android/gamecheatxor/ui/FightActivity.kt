package com.websarva.wings.android.gamecheatxor.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import com.websarva.wings.android.gamecheatxor.R
import com.websarva.wings.android.gamecheatxor.databinding.ActivityFightBinding
import dagger.hilt.android.AndroidEntryPoint
import java.security.SecureRandom
import kotlin.random.Random

@AndroidEntryPoint
class FightActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFightBinding

    private lateinit var username: String
    private var damage: Int = 0
    private var maxDamage: Int = 0
    private var userHp: Int = 0
    private var fenrirHp: Int = 0
    private var userRandom: Int = 0
    private var fenrirRandom: Int = 0

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
                // SecureRandomで値を生成
                fenrirRandom = SecureRandom().nextInt()
                // グローバル領域にエンコードして値を保存
                fenrirHp = defaultHp.xor(fenrirRandom)

                // 値をデコードして使用
                max = fenrirHp.xor(fenrirRandom)
                progress = fenrirHp.xor(fenrirRandom)
                binding.tvFenrirHp.text = fenrirHp.xor(fenrirRandom).toString()
            }
            // ユーザーのHP設定
            binding.pbUserHp.apply {
                val defaultHp = Random.nextInt(251) + 250
                // SecureRandomで値を生成
                userRandom = SecureRandom().nextInt()
                // グローバル領域にエンコードして値を保存
                userHp = defaultHp.xor(userRandom)

                // 値をデコードして使用
                max = userHp.xor(userRandom)
                progress = userHp.xor(userRandom)
                binding.tvUserHp.text = userHp.xor(userRandom).toString()
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
                    // 保存した値をデコードして使用
                    putExtra("clear", true)
                    putExtra("username", username)
                    putExtra("turn", binding.tvTurn.text.toString().toInt())
                    putExtra("userHp", userHp.xor(userRandom))
                    putExtra("maxDamage", maxDamage.xor(userRandom))
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
        // HPの更新
        // 値をデコードして使用した後にグローバル領域に再びエンコードして保存
        fenrirHp = (fenrirHp.xor(fenrirRandom) - damage).xor(fenrirRandom)

        // 最大ダメージを保存
        if (damage > maxDamage){
            // グローバル領域にエンコードして値を保存
            maxDamage = damage.xor(userRandom)
        }

        // メッセージの更新
        tvMessage.text = getString(R.string.textMessage2, username, damage)
        // HPテキストの更新
        // 値をデコードして使用
        binding.tvFenrirHp.text = fenrirHp.xor(fenrirRandom).toString()
        // HPのprogressを更新
        // 値をデコードして使用
        binding.pbFenrirHp.progress = fenrirHp.xor(fenrirRandom)
    }
    private fun fenrirAttack(tvMessage: TextView){
        // ダメージを1～150の間のランダムに設定
        damage = Random.nextInt(150) + 1
        // HPの更新
        // 値をデコードして使用した後にグローバル領域に再びエンコードして保存
        userHp = (userHp.xor(userRandom) - damage).xor(userRandom)

        // メッセージの更新
        tvMessage.text = getString(R.string.textMessage3, damage, username)
        // HPテキストの更新
        // 値をデコードして使用
        binding.tvUserHp.text = userHp.xor(userRandom).toString()
        // HPのprogressを更新
        // 値をデコードして使用
        binding.pbUserHp.progress = userHp.xor(userRandom)
    }
}