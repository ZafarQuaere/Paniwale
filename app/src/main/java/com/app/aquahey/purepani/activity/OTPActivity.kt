package com.app.aquahey.purepani.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.databinding.ActivityOtpBinding
import com.app.aquahey.purepani.model.SignIn
import com.app.aquahey.purepani.utils.LocalConfiq
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.app.aquahey.purepani.viewmodel.CommanViewModel
import com.app.aquahey.purepani.viewmodel.SignupViewModel

class OTPActivity : BaseActivity(), OnDataLoadCallBack, View.OnClickListener {

    private lateinit var dataBind: ActivityOtpBinding
    private lateinit var signupViewModel: SignupViewModel
    var signIn: SignIn? = null

    private lateinit var commanViewModel: CommanViewModel
    private lateinit var otp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_otp)
        signIn = intent.getParcelableExtra<SignIn>("SignUp")
        val mobile = intent.getStringExtra("Mobile")
        dataBind.submit.setOnClickListener(this)
        dataBind.cancel.setOnClickListener(this)
        commanViewModel = CommanViewModel()
        openDialog()
        if (null != signIn) {
            commanViewModel.otpService(applicationContext, this, signIn!!.mobile)

        } else {
            commanViewModel.otpService(applicationContext, this, mobile)
        }

    }


    override fun onSuccess() {
        otp = LocalConfiq.getString(applicationContext, LocalConfiq.OTP)
        hideDialog()

    }

    override fun onFailed(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
        hideDialog()
    }

    override fun onClick(p0: View?) {
        if (R.id.submit == p0!!.id) {
            if (dataBind.otp.text.toString().isNotEmpty()) {
                if (dataBind.otp.text.toString() == otp) {
                    openDialog()
                    if (null != signIn) {
                        signup()
                    } else {
                        val intent = Intent(applicationContext, ForgetPasswordActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    Toast.makeText(applicationContext, "OTP Not Match", Toast.LENGTH_LONG).show()
                }

            } else {
                Toast.makeText(applicationContext, "Please Enter OTP", Toast.LENGTH_LONG).show()
            }
        } else if (R.id.cancel == p0.id) {
            onBackPressed()
        }
    }

    fun signup() {
        val loadCallBack = object : OnDataLoadCallBack {
            override fun onSuccess() {
                /*val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()*/
                LocalConfiq.putBoolean(applicationContext, LocalConfiq.IS_LOGIN, true)
                hideDialog()
                onBackPressed()
                hideDialog()
            }

            override fun onFailed(error: String) {
                Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
                hideDialog()
            }
        }
        signupViewModel = SignupViewModel(signIn, loadCallBack, applicationContext)
        signupViewModel.signin()
    }
}
