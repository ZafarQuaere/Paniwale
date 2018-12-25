package com.app.aquahey.purepani.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by ikram-tiss on 2/4/18.
 */
abstract class GenericRecyclerview<in T> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var listItems = ArrayList<T>()

    constructor(listItems: ArrayList<T>) {
        this.listItems = listItems
    }

    constructor() {
        listItems = ArrayList()
    }


    fun removeItem(t: T) {
        this.listItems.remove(t)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
                , viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }
/*  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(parent!!.context)
                .inflate(viewType, parent, false)
                , viewType)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as Binder<T>).bind(listItems[position])
    }*/

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, listItems[position])
    }

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder

    internal interface Binder<in T> {
        fun bind(data: T)
    }
}