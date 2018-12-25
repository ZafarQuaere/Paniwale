package com.app.aquahey.purepani.adapter

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.databinding.BrandViewBinding
import com.app.aquahey.purepani.fragment.AquaListFragment
import com.app.aquahey.purepani.model.BrandData
import com.app.aquahey.purepani.viewmodel.DataItemViewModel
import java.util.*

class DataAdapter(private val context: FragmentActivity?) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {
    private val data: MutableList<BrandData.Brand>

    init {
        this.data = ArrayList<BrandData.Brand>()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.brand_view,
                FrameLayout(parent.context), false)
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val task = data[position]
        holder.setViewModel(DataItemViewModel(task))
        holder.itemView.setOnClickListener {

            val frag = AquaListFragment()
            val bundle = Bundle()
            bundle.putInt("ProductType", 1)
            bundle.putInt("IsBrand", 1)

            frag.arguments = bundle
            val manager = context!!.supportFragmentManager
            val transaction = manager!!.beginTransaction()
            transaction.add(R.id.home_container, frag, "Aqua")
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    fun updateData(data: List<BrandData.Brand>?) {
        if (data == null || data.isEmpty()) {
            this.data.clear()
        } else {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onViewAttachedToWindow(holder: DataViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: DataViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unBind()
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: BrandViewBinding? = null

        init {
            bind()
        }

        fun bind() {
            if (null == binding) {
                binding = DataBindingUtil.bind(itemView)
            }
        }

        fun unBind() {
            if (null != binding) {
                binding!!.unbind()
            }
        }

        fun setViewModel(viewModel: DataItemViewModel) {
            if (null != binding) {
                binding!!.dataModelItem = viewModel
            }
        }
    }

}
