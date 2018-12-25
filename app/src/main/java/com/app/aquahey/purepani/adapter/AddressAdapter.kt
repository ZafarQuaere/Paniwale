package com.app.aquahey.purepani.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.model.DeliveryAddress

class AddressAdapter() : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    private var addressList: ArrayList<DeliveryAddress> = ArrayList()
    private lateinit var addressId: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.address_view, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deliveryAddress = addressList[position]

        holder.name.text = deliveryAddress.name
        holder.dAddress.text = deliveryAddress.addressOne
        holder.mobileNo.text = deliveryAddress.mobile

        if (deliveryAddress.isSelected) {
            holder.selected.visibility = View.VISIBLE
            deliveryAddress.isSelected = true
        } else {
            holder.selected.visibility = View.GONE
            deliveryAddress.isSelected = false
        }
        addressId = deliveryAddress.id


        holder.itemView.setOnClickListener {

            if (deliveryAddress.isSelected) {
                holder.selected.visibility = View.GONE
                deliveryAddress.isSelected = false
            } else {
                for (i in addressList.indices) {
                    if (addressList[i].isSelected) {
                        addressList[i].isSelected = false
                    }
                }
                addressId = deliveryAddress.id
                holder.selected.visibility = View.VISIBLE
                deliveryAddress.isSelected = true
            }
            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    fun addAllItem(list: ArrayList<DeliveryAddress>) {
        if (addressList.isNotEmpty()) {
            addressList.clear()
        }
        addressList.addAll(list)
        this.notifyDataSetChanged()
    }

    fun addItem(address: DeliveryAddress) {
        addressList.add(address)
        notifyDataSetChanged()
    }

    fun getAdressID(): String? {
        return addressId
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById(R.id.user_name) as TextView
        val dAddress = itemView.findViewById(R.id.delivery_address) as TextView
        val mobileNo = itemView.findViewById(R.id.phone) as TextView
        val selected = itemView.findViewById(R.id.selected) as RelativeLayout
    }
}
