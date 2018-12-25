package com.app.aquahey.purepani.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.adapter.GenericRecyclerview
import com.app.aquahey.purepani.model.MyOrder
import com.app.aquahey.purepani.model.MyViewHolder
import com.app.aquahey.purepani.model.MyorderResponse
import com.app.aquahey.purepani.model.Product
import com.app.aquahey.purepani.utils.ImageUtil
import com.app.aquahey.purepani.utils.LocalConfiq
import com.nostra13.universalimageloader.core.ImageLoader
import com.nussd.todo.network.RetrofitBase
import kotlinx.android.synthetic.main.activity_my_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MyOrderActivity : AppCompatActivity() {
    private val imageLoader = ImageLoader.getInstance()
    private var isMyAqua: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)
        isMyAqua = intent.getBooleanExtra("IsMyOrder", false)
        order_list.layoutManager = LinearLayoutManager(applicationContext)
        myOrders()

    }


    private fun getMyOrder(list: ArrayList<MyOrder>) {

        val myAdapter = object : GenericRecyclerview<MyOrder>(list) {
            override fun getLayoutId(position: Int, obj: MyOrder): Int {
                return R.layout.my_order_view
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                var title: TextView? = null
                var orderDate: TextView? = null
                var deliveryDate: TextView? = null
                var pic: ImageView? = null

                return object : MyViewHolder<MyOrder>(view) {
                    override fun setUpView(view: View) {
                        title = view.findViewById(R.id.product_name)
                        orderDate = view.findViewById(R.id.order_date)
                        deliveryDate = view.findViewById(R.id.delivery_date)
                        pic = view.findViewById(R.id.pic)
                    }

                    @SuppressLint("SetTextI18n")
                    override fun bind(data: MyOrder) {

                        title!!.text = data.pName.toString()
                        orderDate!!.text = data.orderDate.toString()
                        deliveryDate!!.text = data.deliverDate.toString()
                        if (null != data.imagePath && data.imagePath.isNotEmpty()) {
                            imageLoader.displayImage(data.imagePath, pic, ImageUtil.getOption())
                        }
                        view.setOnClickListener {
                            if (isMyAqua) {
                                val  product=Product()
                                product.id=data.productId
                                product.dealerName=data.dName
                                product.addressOne=data.addressOne
                                product.dealerId=data.dealerId
                                product.imagePath=data.imagePath
                                product.pName=data.pName
                                product.price=data.price


                                val intent = Intent(applicationContext, BookingActivity::class.java)
                                intent.putExtra("Product", product)
                                startActivity(intent)

                            } else {
                                val intent = Intent(applicationContext, OrderDetailActivity::class.java)
                                intent.putExtra("MyOrder", data)
                                startActivity(intent)
                            }


                        }
                    }
                }
            }
        }
        order_list.adapter = myAdapter
    }


    private fun myOrders() {
        val api = RetrofitBase.create()
        val id = LocalConfiq.getUserId(applicationContext)
        val call = api.getMyOrders(id)

        call.enqueue(object : Callback<MyorderResponse> {
            override fun onResponse(call: Call<MyorderResponse>, response: Response<MyorderResponse>) {
                if (response.isSuccessful) {
                    val myorderResponse = response.body()
                    if (null != myorderResponse) {
                        if (myorderResponse.success == 1) {
                            if (null != myorderResponse.resultArray && myorderResponse.resultArray.isNotEmpty()) {
                                getMyOrder(myorderResponse.resultArray)
                            } else {
                                Toast.makeText(applicationContext, "No Order", Toast.LENGTH_LONG).show()
                            }

                        } else {
                            Toast.makeText(applicationContext, "No Order", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                progressbar.visibility = View.GONE
            }

            override fun onFailure(call: Call<MyorderResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error. " + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
