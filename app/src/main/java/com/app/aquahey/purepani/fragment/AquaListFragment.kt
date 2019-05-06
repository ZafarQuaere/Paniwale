package com.app.aquahey.purepani.fragment


import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.activity.OTPActivity
import com.app.aquahey.purepani.databinding.FragmentAquaListBinding
import com.app.aquahey.purepani.model.Product
import com.app.aquahey.purepani.model.SignIn
import com.app.aquahey.purepani.utils.ErrorUtils
import com.app.aquahey.purepani.utils.LocalConfiq
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.app.aquahey.purepani.view.OnItemClickCallBack
import com.app.aquahey.purepani.view.OnNumberDialogClickListener
import com.app.aquahey.purepani.viewmodel.AquaDataViewModel


/**
 * A simple [Fragment] subclass.
 */
class AquaListFragment : BaseFragment(), OnDataLoadCallBack, OnItemClickCallBack, OnNumberDialogClickListener {


    private lateinit var aquaDataViewModel: AquaDataViewModel
    private lateinit var registerMobileNumberDialog: RegisterMobileNumberDialog
    private lateinit var dealerNumber: String
    lateinit var binding: FragmentAquaListBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate<FragmentAquaListBinding>(inflater, R.layout.fragment_aqua_list, container, false)
        openDialog()
        aquaDataViewModel = AquaDataViewModel(context, this)
        binding.dataModel = aquaDataViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productType = arguments!!.getInt("ProductType")
        val isBrand = arguments!!.getInt("IsBrand")

        aquaDataViewModel.setData(this, LocalConfiq.getString(context, LocalConfiq.PINCODE), productType, isBrand,
                LocalConfiq.getString(context, LocalConfiq.CITY), LocalConfiq.getString(context, LocalConfiq.STATE))
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.aqua_list)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    override fun onSuccess() {
        initRecyclerView(binding.root)

        hideDialog()
    }

    override fun onFailed(error: String?) {
        ErrorUtils.showToast(context, error)

        hideDialog()
    }

    override fun onItemClick(product: Product?) {
        dealerNumber = product!!.dealerContact

        if (!LocalConfiq.getBoolean(context!!, LocalConfiq.IS_LOGIN)) {
            openNumberDialog()
        } else {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:" + dealerNumber)
            startActivity(callIntent)
        }
        hideDialog()

    }

    private fun openNumberDialog() {
        registerMobileNumberDialog = RegisterMobileNumberDialog()
        registerMobileNumberDialog.setNumberDialogListener(this)
        registerMobileNumberDialog.isCancelable = false
        registerMobileNumberDialog.show(fragmentManager, "Add")
    }

    private fun hideNumberDialog() {
        registerMobileNumberDialog.dismiss()
    }

    override fun onSubmitClick(number: String?) {
        val signIn = SignIn()
        /* signIn.email=""
         signIn.name*/
        signIn.mobile = number
        val intent = Intent(context, OTPActivity::class.java)
        intent.putExtra("SignUp", signIn)
        startActivity(intent)
        hideNumberDialog()

    }

    override fun onSkipClick(number: String?) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:" + dealerNumber)
        startActivity(callIntent)
        hideNumberDialog()
    }

}
