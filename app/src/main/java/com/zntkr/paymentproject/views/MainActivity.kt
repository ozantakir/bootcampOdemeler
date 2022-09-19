package com.zntkr.paymentproject.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zntkr.paymentproject.R
import com.zntkr.paymentproject.adapter.RecyclerViewAdapter
import com.zntkr.paymentproject.databinding.ActivityMainBinding
import com.zntkr.paymentproject.model.PaymentOperation
import com.zntkr.paymentproject.model.PaymentType

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.MyOnClickListener, RecyclerViewAdapter.AddPaymentListener {
    lateinit var binding: ActivityMainBinding
    lateinit var list: ArrayList<PaymentType>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = lm

        val po = PaymentOperation(this)
        list = po.getPaymentTypes()

        binding.recyclerView.adapter?.notifyDataSetChanged()
        binding.recyclerView.adapter = RecyclerViewAdapter(list, this, this)

        binding.addType.setOnClickListener {
            val intent = Intent(this, PaymentTypeActivity::class.java)
            intent.putExtra("isEdit", false)
            startActivity(intent)
        }
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("paymentType", list[position])
        startActivity(intent)
    }

    override fun onClickButton(position: Int) {
        val intent = Intent(this, AddPaymentActivity::class.java)
        intent.putExtra("typeId", list[position].id.toString())
        startActivity(intent)
    }
}