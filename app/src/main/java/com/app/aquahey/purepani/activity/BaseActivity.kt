package com.app.aquahey.purepani.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.app.aquahey.purepani.fragment.ProgressFragment

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var progressDilog: ProgressFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun openDialog() {
        progressDilog = ProgressFragment()
        progressDilog.isCancelable = false
        progressDilog.show(supportFragmentManager, "Add")
    }

    protected fun hideDialog() {
        progressDilog.dismiss()
    }


}
