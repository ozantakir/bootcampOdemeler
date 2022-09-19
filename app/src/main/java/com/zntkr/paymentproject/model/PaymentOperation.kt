package com.zntkr.paymentproject.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class PaymentOperation(context: Context) {
    var PaymentDatabase : SQLiteDatabase? = null
    var dbOpenHelper : DatabaseOpenHelper

    init {
        dbOpenHelper = DatabaseOpenHelper(context, "PaymentDB", null, 1)
    }

    fun open()
    {
        PaymentDatabase = dbOpenHelper.writableDatabase
    }

    fun close()
    {
        if (PaymentDatabase != null && PaymentDatabase!!.isOpen)
        {
            PaymentDatabase!!.close()
        }
    }

    fun addPaymentType(paymentType: PaymentType) : Long
    {
        val cv = ContentValues()
        cv.put("title", paymentType.title)
        cv.put("period", paymentType.period)
        cv.put("date", paymentType.date)

        open()
        val record = PaymentDatabase!!.insert("PaymentType", null, cv)
        close()

        return record
    }

    fun addPayment(payment: Payment) : Long
    {
        val cv = ContentValues()
        cv.put("typeId", payment.typeId)
        cv.put("value", payment.value)
        cv.put("date", payment.date)

        open()
        val record = PaymentDatabase!!.insert("Payment", null, cv)
        close()

        return record
    }

    fun updatePaymentType(paymentType: PaymentType)
    {
        val cv = ContentValues()
        cv.put("title", paymentType.title)
        cv.put("period", paymentType.period)
        cv.put("date", paymentType.date)

        open()
        PaymentDatabase!!.update("PaymentType", cv, "id = ?", arrayOf(paymentType.id.toString()))
        close()
    }

    fun updatePayment(payment: Payment)
    {
        val cv = ContentValues()
        cv.put("typeId", payment.typeId)
        cv.put("value", payment.value)
        cv.put("date", payment.date)

        open()
        PaymentDatabase!!.update("Payment", cv, "id = ?", arrayOf(payment.id.toString()))
        close()
    }

    fun deletePaymentType(id : Int)
    {
        open()
        PaymentDatabase!!.delete("PaymentType", "id = ?", arrayOf(id.toString()))
        close()
    }

    fun deletePayment(id : Int)
    {
        open()
        PaymentDatabase!!.delete("Payment", "id = ?", arrayOf(id.toString()))
        close()
    }

    private fun getAllPaymentTypes(): Cursor
    {
        val sorgu = "Select * from  PaymentType"
        return PaymentDatabase!!.rawQuery(sorgu,null)
    }

    @SuppressLint("Range")
    fun getPaymentTypes():ArrayList<PaymentType>
    {
        val paymentTypes : ArrayList<PaymentType> = ArrayList()
        var paymentType  : PaymentType
        open()
        val c : Cursor = getAllPaymentTypes()
        if (c.moveToFirst()) {
            do {
                paymentType = PaymentType(id = c.getInt(0), title = c.getString(c.getColumnIndex("title")),
                period = c.getString(c.getColumnIndex("period")), date = c.getString(c.getColumnIndex("date")))
                paymentTypes.add(paymentType)
            }
            while (c.moveToNext())
        }
        close()
        return paymentTypes

    }

    private fun getAllPayments(): Cursor
    {
        val sorgu = "Select * from  Payment"
        return PaymentDatabase!!.rawQuery(sorgu,null)
    }

    @SuppressLint("Range")
    fun getPayments():ArrayList<Payment>
    {
        val payments : ArrayList<Payment> = ArrayList()
        var payment  : Payment
        open()
        val c : Cursor = getAllPayments()
        if (c.moveToFirst()) {
            do {
                payment = Payment(id = c.getInt(0), typeId = c.getInt(c.getColumnIndex("typeId")),
                    value = c.getString(c.getColumnIndex("value")), date = c.getString(c.getColumnIndex("date")))
                payments.add(payment)
            }
            while (c.moveToNext())
        }
        close()
        return payments

    }

}