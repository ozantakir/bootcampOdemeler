package com.zntkr.paymentproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.paymentproject.databinding.PaymentTypeRecyclerViewBinding
import com.zntkr.paymentproject.model.PaymentType

class RecyclerViewAdapter(private val list: ArrayList<PaymentType>, val listener: MyOnClickListener, val buttonListener: AddPaymentListener) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    inner class RecyclerViewHolder(val binding: PaymentTypeRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition
                listener.onClick(pos)
            }
            binding.addPayment.setOnClickListener {
                val pos = adapterPosition
                buttonListener.onClickButton(pos)
            }
        }

        fun bindData(title: String, dayInfo: String){
            binding.title.text = title
            binding.dayInfo.text = dayInfo
        }
    }

    interface MyOnClickListener {
        fun onClick(position: Int)
    }
    interface AddPaymentListener {
        fun onClickButton(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = PaymentTypeRecyclerViewBinding.inflate(LayoutInflater.from(parent.context))
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindData(list[position].title, list[position].period + ", " + list[position].date)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}