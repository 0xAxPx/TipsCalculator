package com.peshale.tipscalc

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.peshale.tipscalc.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set listener for Calculate button
        binding.calculateButton.setOnClickListener { calculateTip() }

        //set listener for keyboard
        binding.costOfServiceEditText.setOnKeyListener { view, code, _ ->
            handleKeyEvent(
                view,
                code
            )
        }
    }

    private fun calculateTip() {
        val string2Int = binding.costOfServiceEditText.text.toString()
        val cost = string2Int.toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }
        val tipPercentage = when (binding.tipsOptions.checkedRadioButtonId) {
            R.id.option_twenty -> 0.20
            R.id.option_eighteen -> 0.18
            else -> 0.15
        }
        var tip = cost * tipPercentage
        if (binding.roundUpSwitch.isChecked) tip = ceil(tip)
        displayTip(tip)

    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}