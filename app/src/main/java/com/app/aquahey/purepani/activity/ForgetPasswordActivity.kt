package com.app.aquahey.purepani.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.databinding.ActivityForgetPasswordBinding
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.app.aquahey.purepani.viewmodel.CommanViewModel

class ForgetPasswordActivity : BaseActivity(), OnDataLoadCallBack {
    private lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)

        binding.submit.setOnClickListener {
            if (binding.newPass.text.isNotEmpty() && binding.pass.text.isNotEmpty()) {
                openDialog()
                val commanViewModel = CommanViewModel()
                commanViewModel.changePasswordService(this, "9022335474", binding.newPass.text.toString())
            } else {
                Toast.makeText(applicationContext, "Field Should Blank", Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun onSuccess() {
        hideDialog()
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailed(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

}
