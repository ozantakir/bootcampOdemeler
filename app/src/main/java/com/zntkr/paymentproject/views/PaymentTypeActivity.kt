package com.zntkr.paymentproject.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.DigitsKeyListener
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.zntkr.paymentproject.R
import com.zntkr.paymentproject.databinding.ActivityPaymentTypeBinding
import com.zntkr.paymentproject.model.PaymentOperation
import com.zntkr.paymentproject.model.PaymentType

class PaymentTypeActivity : AppCompatActivity() {
    lateinit var binding: ActivityPaymentTypeBinding
    var pt: PaymentType? = null
    var isEdit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val periodList = arrayListOf("Haftalık", "Aylık", "Yıllık")
        val adap : ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, periodList)
        binding.spinner.adapter = adap

        with(intent) {
            pt = getSerializableExtra("paymentType") as? PaymentType
            binding.typeTitle.setText(pt?.title)
            when (pt?.period) {
                "Haftalık" -> {
                    binding.spinner.setSelection(0)
                }
                "Aylık" -> {
                    binding.spinner.setSelection(1)
                }
                "Yıllık" -> {
                    binding.spinner.setSelection(2)
                }
            }
            binding.day.setText(pt?.date)
            isEdit = getBooleanExtra("isEdit", false)
            if (isEdit) {
                binding.deleteType.visibility = View.VISIBLE
            } else {
                binding.deleteType.visibility = View.INVISIBLE
            }

        }

        val po = PaymentOperation(this)

        binding.saveType.setOnClickListener {
            var first = 1
            var last = 0
            when (binding.spinner.selectedItem) {
                "Haftalık" -> {
                    last = 7
                }
                "Aylık" -> {
                    last = 30
                }
                "Yıllık" -> {
                    last = 365
                }
            }
            if (binding.day.text.toString().toInt() in first..last) {
                val id = pt?.id
                val title = binding.typeTitle.text.toString()
                val period = binding.spinner.selectedItem.toString()
                val day = binding.day.text.toString()
                val paymentType = PaymentType(title = title, period = period, date = day)
                if(isEdit) {
                    val payType = PaymentType(id, title, period, day)
                    po.updatePaymentType(payType)
                } else {
                    po.addPaymentType(paymentType)
                }
                finish()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                Toast.makeText(this, "Gün seçimi periyoda göre olmalı", Toast.LENGTH_SHORT).show()
            }

        }

        binding.deleteType.setOnClickListener {
            po.deletePaymentType(pt?.id!!)
            finish()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}