package com.app.aquahey.purepani.model

import android.support.v7.widget.RecyclerView
import android.view.View
import com.app.aquahey.purepani.adapter.GenericRecyclerview

/**
 * Created by ikram-tiss on 2/4/18.
 */
abstract class MyViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView), GenericRecyclerview.Binder<T> {

    protected abstract fun setUpView(view: View)

    init {
        this.setUpView(itemView)
    }

}