package com.app.aquahey.purepani.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.databinding.ActivityMobileNumberBinding
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.app.aquahey.purepani.viewmodel.MobileVerifyModelView

class MobileNumberActivity : BaseActivity(), OnDataLoadCallBack {
    private lateinit var dataBinding: ActivityMobileNumberBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_mobile_number)

        dataBinding.submit.setOnClickListener {

            if (dataBinding.mobile.text.toString().isNotEmpty() && dataBinding.mobile.text.toString().length == 10) {
                openDialog()
                val verify = MobileVerifyModelView()

                verify.setData(this, dataBinding.mobile.text.toString())

            } else {
                dataBinding.mobile.error = "Please Enter Valid Mobile Number"
            }
        }

    }


    override fun onSuccess() {
        hideDialog()
        val txt = dataBinding.mobile.text.toString()
        val intent = Intent(applicationContext, OTPActivity::class.java)
        intent.putExtra("Mobile", txt)
        startActivity(intent)
        finish()

    }

    override fun onFailed(error: String?) {
        hideDialog()
    }
}
