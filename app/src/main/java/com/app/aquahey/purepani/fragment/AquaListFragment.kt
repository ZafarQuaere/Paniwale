package com.app.aquahey.purepani.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.databinding.FragmentAquaListBinding
import com.app.aquahey.purepani.utils.LocalConfiq
import com.app.aquahey.purepani.view.OnDataLoadCallBack
import com.app.aquahey.purepani.viewmodel.AquaDataViewModel


/**
 * A simple [Fragment] subclass.
 */
class AquaListFragment : BaseFragment(), OnDataLoadCallBack {


    private lateinit var aquaDataViewModel: AquaDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentAquaListBinding>(inflater, R.layout.fragment_aqua_list, container, false)
        openDialog()
        aquaDataViewModel = AquaDataViewModel(context)
        binding.dataModel = aquaDataViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        val productType=arguments!!.getInt("ProductType")
        val isBrand=arguments!!.getInt("IsBrand")

        aquaDataViewModel.setData(this, LocalConfiq.getString(context, LocalConfiq.PINCODE),productType,isBrand)
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.aqua_list)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    override fun onSuccess() {
        hideDialog()
    }

    override fun onFailed(error: String?) {
        hideDialog()
    }

}
