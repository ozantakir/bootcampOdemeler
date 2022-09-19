package com.zntkr.paymentproject.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.zntkr.paymentproject.R
import com.zntkr.paymentproject.databinding.ActivityDetailsBinding
import com.zntkr.paymentproject.model.Payment
import com.zntkr.paymentproject.model.PaymentOperation
import com.zntkr.paymentproject.model.PaymentType

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    lateinit var typeId: String
    var pt: PaymentType?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(intent){
            pt = getSerializableExtra("paymentType") as? PaymentType
            binding.titleEdit.text = pt?.title
            binding.dayEdit.text = pt?.period + ", " + pt?.date
            typeId = pt?.id.toString()
        }

        val po = PaymentOperation(this)
        val list = po.getPayments()
        val resultList = ArrayList<String>()
        val idList = ArrayList<Int>()
        list.forEach {
            if (it.typeId == pt?.id) {
                val result = it.date + ", " + it.value
                resultList.add(result)
                idList.add(it.id!!)
            }
        }
        binding.listView.deferNotifyDataSetChanged()
        binding.listView.adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, resultList)

        binding.listView.setOnItemClickListener { adapterView, view, position, l ->

            val adb : AlertDialog.Builder = AlertDialog.Builder(this)
            adb.setTitle("SİL")
            adb.setMessage("${resultList[position]} Elemanını silmek istiyor musunuz?")

            adb.setPositiveButton("Evet"
            ) { dialogInterface, i ->
                resultList.removeAt(position)
                po.deletePayment(idList[position])

                (binding.listView.adapter as ArrayAdapter<String>).notifyDataSetChanged()
            }

            adb.setNegativeButton("Hayır", null)
            adb.show()

        }


        binding.addPay.setOnClickListener {
            val intent = Intent(this, AddPaymentActivity::class.java)
            intent.putExtra("typeId", typeId)
            intent.putExtra("fromDetail", true)
            startActivity(intent)
        }

        binding.editButton.setOnClickListener {
            val intent = Intent(this, PaymentTypeActivity::class.java)
            intent.putExtra("paymentType", pt)
            intent.putExtra("isEdit", true)
            startActivity(intent)
        }
    }
}