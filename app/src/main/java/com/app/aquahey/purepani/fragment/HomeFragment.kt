package com.app.aquahey.purepani.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.utils.ErrorUtils
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        brand_list.setOnClickListener(this)
        nearby.setOnClickListener(this)
        tanker.setOnClickListener(this)
        my_aqua.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.brand_list -> if (ErrorUtils.isOnline(context)) openFragmentBrand()
            v.id == R.id.nearby -> if (ErrorUtils.isOnline(context)) openFragmentAquaList(1)
            v.id == R.id.tanker -> if (ErrorUtils.isOnline(context)) openFragmentAquaList(2)
            v.id == R.id.my_aqua -> {
                ErrorUtils.showToast(context, "coming soon")
            }
        }
    }

    private fun openFragmentBrand() {
        val frag = BrandFragment()
        val manager = fragmentManager
        val transaction = manager!!.beginTransaction()
        transaction.add(R.id.home_container, frag, "Brands")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun openFragmentAquaList(productType: Int) {
        val frag = AquaListFragment()
        val bundle = Bundle()
        bundle.putInt("ProductType", productType)
        bundle.putInt("IsBrand", 0)

        frag.arguments = bundle
        val manager = fragmentManager
        val transaction = manager!!.beginTransaction()
        transaction.add(R.id.home_container, frag, "Aqua")
        transaction.addToBackStack(null)
        transaction.commit()
    }


}// Required empty public constructor
