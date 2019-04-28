package com.app.aquahey.purepani.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.databinding.ActivityMobileNumberBinding
import com.app.aquahey.purepani.model.SignIn
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.app.aquahey.purepani.view.OnNumberDialogClickListener
import com.app.aquahey.purepani.viewmodel.SignupViewModel

class RegisterMobileNumberDialog : DialogFragment(), OnDataLoadCallBack, View.OnClickListener {


    private lateinit var signupViewModel: SignupViewModel
    private var onNumberDialogClickListener: OnNumberDialogClickListener? = null
    private var signIn: SignIn? = null
    private lateinit var binding: ActivityMobileNumberBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate<ActivityMobileNumberBinding>(inflater,
                R.layout.activity_mobile_number, container, false)
        binding.skip.visibility = View.VISIBLE
        binding.helpMsg.visibility = View.VISIBLE
        binding.helpTitle.visibility = View.VISIBLE
        binding.mobile.hint = "Please Enter Your Mobile Number"
        binding.submit.setOnClickListener(this)
        binding.skip.setOnClickListener(this)


        return binding.root
    }

    fun setNumberDialogListener(onNumberDialogClickListener: OnNumberDialogClickListener) {
        this.onNumberDialogClickListener = onNumberDialogClickListener
    }

    override fun onSuccess() {
    }

    override fun onFailed(error: String?) {
    }

    override fun onClick(p0: View?) {
        if (p0!!.id == R.id.submit) {
            if (binding.mobile.text.toString().isNotEmpty()) {
                onNumberDialogClickListener!!.onSubmitClick(binding.mobile.text.toString())
            } else {
                binding.mobile.error = "Please Enter Mobile Number"
            }
        } else if (p0.id == R.id.skip) {

            onNumberDialogClickListener!!.onSkipClick(binding.mobile.text.toString())

        }
    }
}