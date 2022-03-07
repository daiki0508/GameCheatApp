package com.websarva.wings.android.gamecheatapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.websarva.wings.android.gamecheatapp.R
import com.websarva.wings.android.gamecheatapp.databinding.ActivityResultBinding
import com.websarva.wings.android.gamecheatapp.model.Result
import com.websarva.wings.android.gamecheatapp.viewmodel.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private val viewModel: ResultViewModel by viewModels()

    private lateinit var result: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater).apply {
            setContentView(this.root)
        }

        var clear: Boolean? = null
        // 送信データのnullチェック
        val extras = intent?.extras
        if (extras != null && extras.containsKey("clear")){
            // game clearフラグの状態によって分岐
            clear = extras.getBoolean("clear")
            if (clear){
                if (extras.containsKey("username") && extras.containsKey("turn") && extras.containsKey("userHp") && extras.containsKey("maxDamage")){
                    // GAME CLEARに変更
                    binding.tvResult.text = getString(R.string.tv_clear)
                    // Send the Resultに変更
                    binding.btResult.text = getString(R.string.bt_send)

                    // グローバル変数に代入
                    result = Result(
                        extras.getString("username")!!,
                        extras.getInt("turn"),
                        extras.getInt("userHp"),
                        extras.getInt("maxDamage")
                    )
                }else{
                    finishApp("Required items are null.")
                }
            }
        }else{
            finishApp("extras is null.")
        }

        // btResultタップ時の処理
        binding.btResult.setOnClickListener {
            if (clear!!){
                ConfirmDialogFragment(
                    this,
                    result
                ).show(supportFragmentManager, "confirmDialogFragment")
            }else{
                if (extras!!.containsKey("username")){
                    val intent = Intent(this, FightActivity::class.java).apply {
                        // 送信データの作成
                        putExtra("username", extras.getString("username"))
                    }
                    // 対象アクティビティの起動
                    startActivity(intent)
                    finish()
                }else{
                    finishApp("Required item is null.")
                }
            }
        }

        // resultの通知
        viewModel.result.observe(this){
            Toast.makeText(this, if (it){
                "Connection Success!!"
            }else{
                "Connection Failure..."
            }, Toast.LENGTH_SHORT).show()
        }
    }

    private fun finishApp(message: String){
        Log.e("ERROR", message)
        finish()
    }

    fun dialogResult(){
        Log.d("test", "called")
        viewModel.connect(result)
    }
}