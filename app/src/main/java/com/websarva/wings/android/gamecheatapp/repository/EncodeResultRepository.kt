package com.websarva.wings.android.gamecheatapp.repository

import android.annotation.SuppressLint
import android.os.Build
import android.util.Base64
import com.websarva.wings.android.gamecheatapp.model.EncodeResult
import com.websarva.wings.android.gamecheatapp.model.Result
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.inject.Inject

interface EncodeResultRepository {
    fun encode(result: Result): EncodeResult
}

class EncodeResultRepositoryClient @Inject constructor(): EncodeResultRepository {
    override fun encode(result: Result): EncodeResult {
        return EncodeResult(
            encrypt(result.username),
            encrypt(result.turn.toString()),
            encrypt(result.userHp.toString()),
            encrypt(result.maxDamage.toString())
        )
    }

    @SuppressLint("GetInstance")
    private fun encrypt(src: String): String{
        val somePublicString = "E/AndroidRuntime: FATAL EXCEPTION: main Process: " +
                "com.websarva.wings.android.androidkeystoresample_kotlin, PID: 6147 " +
                "java.lang.RuntimeException"
        val nonSecret: ByteArray = somePublicString.toByteArray(Charsets.ISO_8859_1)

        var secretKey: SecretKey? = null
        val encryptStr: String

        try {
            // 暗号化処理開始
            secretKey = SecureSecretKey(Base64.decode("QzhCc2ZBRjN3UEdGaDZkOHllSzR6aFF4WU4zY21uWGQ=", Base64.DEFAULT), "AES")
            Cipher.getInstance("AES/ECB/PKCS7Padding").apply {
                init(Cipher.ENCRYPT_MODE, secretKey)
                encryptStr = Base64.encodeToString(doFinal(src.toByteArray()), Base64.DEFAULT)
            }
        } finally {
            // メモリからカギを削除
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                secretKey?.destroy()
            } else {
                for (i in secretKey!!.encoded.indices) {
                    secretKey.encoded[i] = nonSecret[i % nonSecret.size]
                }
                FileOutputStream("dev/null").use {
                    it.write(secretKey.encoded)
                    it.flush()
                }
            }
        }
        return encryptStr
    }
}