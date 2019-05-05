package com.app.aquahey.purepani.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.model.Order
import com.app.aquahey.purepani.model.Product
import com.app.aquahey.purepani.network.NetworkResponse
import com.nussd.todo.network.RetrofitBase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*

class PaymentActivity : Activity(), PaymentResultListener {

    private var product: Product? = null
    private var order: Order? = null

    @SuppressLint("SetTextI18n")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        Checkout.preload(applicationContext)
        product = intent.getParcelableExtra("Product")
        order = intent.getParcelableExtra("Order")
        product_name.text=product!!.pName
        price.text= "Rs. "+order!!.amount

        // Payment button created by you in XML layout
        val button = findViewById<View>(R.id.btn_pay) as Button

        button.setOnClickListener { startPayment() }
    }

    fun startPayment() {
        val amount = order!!.quantity * Integer.valueOf(product!!.price)!!
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        val activity = this

        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("dealerContact", product!!.pName)
            options.put("description", product!!.price + " x " + order!!.quantity)
            //You can omit the image option to fetch the image from dashboard
            options.put("image", product!!.imagePath)
            options.put("currency", "INR")
            options.put("amount", amount * 100)

            val preFill = JSONObject()
            preFill.put("email", "mominikram@gmail.com")
            preFill.put("contact", "9022335474")

            options.put("prefill", preFill)

            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_SHORT)
                    .show()
            e.printStackTrace()
        }
    }

    /**
     * The dealerContact of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    override fun onPaymentSuccess(razorpayPaymentID: String) {
        try {
            val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
            order!!.paymentId = razorpayPaymentID
            order!!.orderDate = currentDateTimeString
            order()
            Toast.makeText(this, "Payment Successful: $razorpayPaymentID", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentSuccess", e)
        }

    }

    /**
     * The dealerContact of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    override fun onPaymentError(code: Int, response: String) {
        try {
            Toast.makeText(this, "Payment failed: $code $response", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentError", e)
        }
    }

    companion object {
        private val TAG = PaymentActivity::class.java.simpleName
    }

    private fun order() {
        val api = RetrofitBase.create()
        val call = api.insertOrder(order)

        call.enqueue(object : Callback<NetworkResponse> {
            override fun onResponse(call: Call<NetworkResponse>, response: Response<NetworkResponse>) {
                if (response.isSuccessful) {
                    val networkResponse = response.body()
                    if (null != networkResponse) {
                        if (networkResponse.success == 1) {
                        }
                    }
                }
            }

            override fun onFailure(call: Call<NetworkResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error. " + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}
