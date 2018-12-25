package com.app.aquahey.purepani.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.databinding.ActivitySignUpAvtivityBinding
import com.app.aquahey.purepani.model.SignIn
import com.app.aquahey.purepani.utils.ErrorUtils
import kotlinx.android.synthetic.main.activity_sign_up_avtivity.*

public class SignUpActivity : BaseActivity() {
    private lateinit var bindingData: ActivitySignUpAvtivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingData = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_avtivity)
        bindingData.signup.setOnClickListener {
            if (!validationError()) {
                openDialog()
                val signIn = SignIn()
                signIn.name = userName.text.toString()
                signIn.email = emailId.text.toString()
                signIn.mobile = mobile.text.toString()
                signIn.password = password.text.toString()
                val intent = Intent(applicationContext, OTPActivity::class.java)
                intent.putExtra("SignUp", signIn)
                startActivity(intent)
                finish()

            }
        }

    }


    private fun validationError(): Boolean {
        return when {
            ErrorUtils.emptyCheck(userName) -> true
            ErrorUtils.emptyCheck(mobile) -> true
            ErrorUtils.lengthCheck(mobile, 10) -> true
            ErrorUtils.emptyCheck(emailId) -> true
            ErrorUtils.emailValidator(emailId.text.toString()) -> true
            password.text.toString().isEmpty() -> true
            else -> false
        }
    }

}
