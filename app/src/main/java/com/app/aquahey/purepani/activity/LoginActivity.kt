package com.app.aquahey.purepani.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.model.Login
import com.app.aquahey.purepani.model.LoginResponse
import com.app.aquahey.purepani.utils.ErrorUtils
import com.app.aquahey.purepani.utils.LocalConfiq
import com.nussd.todo.network.RetrofitBase
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        if (LocalConfiq.getBoolean(applicationContext, LocalConfiq.IS_LOGIN)) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        signup.setOnClickListener {
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
            //finish()
        }

        login.setOnClickListener {
            if (!validationLoginError()) {
                openDialog()
                loginData()
            }

        }
        forgot.setOnClickListener {
            val intent = Intent(applicationContext, MobileNumberActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun validationLoginError(): Boolean {
        return when {
            ErrorUtils.emptyCheck(user_name) -> true
            //  ErrorUtils.lengthCheck(dealerContact, 10) -> true
            pass.text.toString().isEmpty() -> true
            else -> false
        }
    }


    private fun login(login: Login) {
        val api = RetrofitBase.create()
        val call = api.login(login)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val networkResponse = response.body()
                    if (null != networkResponse) {
                        if (networkResponse.status == 1) {
                            LocalConfiq.putString(applicationContext, LocalConfiq.USER_ID,
                                    networkResponse.data.id)
                            LocalConfiq.putString(applicationContext, LocalConfiq.USER_NAME,
                                    networkResponse.data.name)
                            LocalConfiq.putString(applicationContext, LocalConfiq.MOBILE,
                                    networkResponse.data.mobile)
                            LocalConfiq.putBoolean(applicationContext, LocalConfiq.IS_LOGIN, true)
                            hideDialog()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            ErrorUtils.showToast(applicationContext, "Invalid User ")
                        }
                    }
                } else {
                    ErrorUtils.showToast(applicationContext, "Server Problem ")

                }
                hideDialog()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                hideDialog()
                ErrorUtils.showToast(applicationContext, "Error. " + t.message)
            }
        })
    }


    private fun loginData() {
        val login = Login()
        login.mobile = user_name.text.toString()
        login.password = pass.text.toString()
        login.userType = "1"
        login(login)
    }
}
