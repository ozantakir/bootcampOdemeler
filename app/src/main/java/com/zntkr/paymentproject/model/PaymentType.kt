package com.zntkr.paymentproject.model

import java.io.Serializable

class PaymentType(
    var id: Int? = null,
    var title: String,
    var period: String? = null,
    var date: String? = null
) : Serializable

class Payment(
    var id: Int? = null,
    var typeId: Int,
    var value: String,
    var date: String
) : Serializable