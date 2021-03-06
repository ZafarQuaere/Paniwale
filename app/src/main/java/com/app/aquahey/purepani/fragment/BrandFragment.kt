package com.app.aquahey.purepani.fragment


import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.databinding.FragmentBrandBinding
import com.app.aquahey.purepani.utils.ErrorUtils
import com.nussd.emptodo.viewModel.DataViewModel


/**
 * A simple [Fragment] subclass.
 */
class BrandFragment : BaseFragment(), DataViewModel.OnLoadTaskCallback {

    private var dataViewModel: DataViewModel? = null
    lateinit var binding: FragmentBrandBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentBrandBinding>(inflater, R.layout.fragment_brand, container, false)
        openDialog()
        dataViewModel = DataViewModel(activity)
        // dataViewModel.setData(this);
        binding.dataModel = dataViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataViewModel!!.setData(this)
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.brand_list)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        /* val snapHelper = PagerSnapHelper()
         snapHelper.attachToRecyclerView(recyclerView)*/
        // recyclerView.addItemDecoration(LinePagerIndicatorDecoration())
    }

    override fun onSuccess() {
        initRecyclerView(binding.root)
        hideDialog()
    }

    override fun onFailed(error: String?) {
        hideDialog()
        ErrorUtils.showToast(context, error)
    }
}// Required empty public constructor