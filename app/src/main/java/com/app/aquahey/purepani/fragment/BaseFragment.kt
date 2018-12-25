package com.app.aquahey.purepani.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.app.aquahey.purepani.R


/**
 * A simple [Fragment] subclass.
 */
open class BaseFragment : Fragment() {

    private lateinit var progressDilog: ProgressFragment


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val textView = TextView(activity)
        textView.setText(R.string.hello_blank_fragment)
        return textView
    }



    protected fun openDialog() {
        progressDilog = ProgressFragment()
        progressDilog.isCancelable = false
        progressDilog.show(fragmentManager, "Add")
    }

    protected fun hideDialog() {
        progressDilog.dismiss()
    }


}// Required empty public constructor
