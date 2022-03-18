package com.websarva.wings.android.gamecheatxor.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.websarva.wings.android.gamecheatxor.R
import com.websarva.wings.android.gamecheatxor.model.Result

class ConfirmDialogFragment(
    private val resultActivity: ResultActivity,
    private val result: Result
): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.confirm)
                .setMessage("username: ${result.username}\nTurn: ${result.turn}\nHP: ${result.userHp}\nmaxDamage: ${result.maxDamage}")
                .setPositiveButton("OK"){_, _ ->
                    resultActivity.dialogResult()
                }
                .setNegativeButton("CANCEL"){_, _ ->
                    Log.i("confirm", "cancel")
                }
                .create()
        }
        return dialog ?: throw IllegalStateException("activity is null")
    }
}