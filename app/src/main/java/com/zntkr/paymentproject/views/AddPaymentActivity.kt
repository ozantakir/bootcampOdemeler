package com.zntkr.paymentproject.views

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zntkr.paymentproject.R
import com.zntkr.paymentproject.databinding.ActivityAddPaymentBinding
import com.zntkr.paymentproject.model.Payment
import com.zntkr.paymentproject.model.PaymentOperation
import java.util.*

class AddPaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddPaymentBinding
    private var date : String? = null
    private lateinit var dateText : String
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0
    lateinit var typeId: String
    var fromDetail = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(intent) {
            typeId = getStringExtra("typeId").toString()
            fromDetail = getBooleanExtra("fromDetail", false)
        }
        val po = PaymentOperation(this)
        binding.selecDate.setOnClickListener {
            getDate()
        }

        binding.savePay.setOnClickListener {
            val pay = Payment(typeId = typeId.toInt(), value = binding.payValue.text.toString(), date = dateText)
            po.addPayment(pay)
            finish()
        }
    }

    private fun getDate(){
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        if(binding.selecDate.text.isNotEmpty()){
            this.selectedYear = year
            this.selectedMonth = month
            this.selectedDay = day
        }
        val listener = DatePickerDialog.OnDateSetListener{ datePicker, selectedYear, selectedMonth, selectedDay ->
            this.selectedYear = selectedYear
            this.selectedMonth = selectedMonth
            this.selectedDay = selectedDay
            if(selectedMonth < 10 && selectedDay < 10){
                dateText = "0$selectedDay-0${selectedMonth + 1}-$selectedYear"
            } else if(selectedMonth < 10){
                dateText = "$selectedDay-0${selectedMonth + 1}-$selectedYear"
            } else if(selectedDay < 10){
                dateText = "0$selectedDay-${selectedMonth + 1}-$selectedYear"
            } else {
                dateText = "$selectedDay-${selectedMonth + 1}-$selectedYear"
            }
            binding.selecDate.text = dateText
        }
        val datePicker = DatePickerDialog(this,listener,year,month,day)
        datePicker.datePicker.maxDate = System.currentTimeMillis()
        datePicker.show()
    }
}