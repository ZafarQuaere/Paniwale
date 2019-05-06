package com.app.aquahey.purepani.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.adapter.AddressAdapter
import com.app.aquahey.purepani.model.AddressResponse
import com.app.aquahey.purepani.model.DeliveryAddress
import com.app.aquahey.purepani.model.Order
import com.app.aquahey.purepani.model.Product
import com.app.aquahey.purepani.network.NetworkResponse
import com.app.aquahey.purepani.utils.ErrorUtils
import com.app.aquahey.purepani.utils.LocalConfiq
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.nussd.todo.network.RetrofitBase
import kotlinx.android.synthetic.main.activity_booking.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class BookingActivity : BaseActivity(),
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        View.OnClickListener {


    var geocoder: Geocoder? = null
    var addresses: List<Address>? = null
    private var mylocation: Location? = null
    private var googleApiClient: GoogleApiClient? = null
    private val REQUEST_CHECK_SETTINGS_GPS = 0x1
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2
    private var listener: LocationListener? = null
    private lateinit var deliveryAddress: DeliveryAddress
    var isAddress = false
    var isChange = false

    var count: Int = 1
    var product: Product? = null
    var order: Order? = null
    var addressId: String? = null
    private lateinit var adapter: AddressAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        //order_type.onItemChosenListener = this
        plus.setOnClickListener(this)
        minus.setOnClickListener(this)
        pay.setOnClickListener(this)
        more_address.setOnClickListener(this)
        product = intent.getParcelableExtra("Product")
        p_price.text = product!!.price
        product_name.text = product!!.pName
        number.text = 1.toString()
        total.text = product!!.price
        adapter = AddressAdapter()
        addressList.adapter = adapter
        order = Order()
        listener = this
        deliveryAddress = DeliveryAddress()
        geocoder = Geocoder(applicationContext, Locale.getDefault())
        getAddress()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.plus -> {
                count++
                number.text = count.toString()
                val totalAmount = count * p_price.text.toString().toInt()
                total.text = totalAmount.toString()
            }
            R.id.minus -> if (count > 0) {
                count--
                number.text = count.toString()
                val totalAmount = count * p_price.text.toString().toInt()
                total.text = totalAmount.toString()
            }

            R.id.pay -> {
                if (null != adapter.getAdressID()) {
                    order!!.userId = LocalConfiq.getUserId(applicationContext)
                    order!!.dealerId = product!!.dealerId
                    order!!.addressId = adapter.getAdressID()
                    order!!.productId = product!!.id
                    order!!.quantity = count
                    order!!.amount=Integer.valueOf(total.text.toString())
                    order!!.OrderType = 1
                    val intent = Intent(applicationContext, PaymentActivity::class.java)
                    intent.putExtra("Order", order)
                    intent.putExtra("Product", product)
                    startActivity(intent)
                    finish()
                } else {
                    ErrorUtils.showToast(applicationContext, "Please Select Delivery Address")

                }

            }
            R.id.more_address -> {
                isChange = true
                showCustomAlertDialog(null)

            }
        }
    }


    @Synchronized
    private fun setUpGClient() {
        googleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        googleApiClient!!.connect()
    }

    override fun onLocationChanged(p0: Location?) {
        mylocation = p0
        if (mylocation != null) {
            val latitude = mylocation!!.latitude
            val longitude = mylocation!!.longitude
            deliveryAddress.userId = LocalConfiq.getUserId(applicationContext)
            addresses = geocoder!!.getFromLocation(latitude, longitude, 1)
            deliveryAddress.addressOne = addresses!![0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            deliveryAddress.city = addresses!![0].locality
            deliveryAddress.state = addresses!![0].adminArea
            deliveryAddress.pincode = addresses!![0].postalCode
            deliveryAddress.latitude = latitude
            deliveryAddress.longitude = longitude
            if (!isAddress) {
                showCustomAlertDialog(deliveryAddress.addressOne)
                isAddress = true
                //progressbar_view.visibility = View.GONE
            }

        }
    }

    private fun getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient!!.isConnected) {
                val permissionLocation = ContextCompat.checkSelfPermission(this@BookingActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION)

                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {

                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
                    val locationRequest = LocationRequest()
                    locationRequest.interval = 3000
                    locationRequest.fastestInterval = 3000
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                    val builder = LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest)
                    builder.setAlwaysShow(true)

                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, listener)
                    val result = LocationServices.SettingsApi
                            .checkLocationSettings(googleApiClient, builder.build())

                    result.setResultCallback { p0 ->
                        val status = p0.status
                        when (status.statusCode) {
                            LocationSettingsStatusCodes.SUCCESS -> {
                                val permissionLocation = ContextCompat
                                        .checkSelfPermission(applicationContext,
                                                Manifest.permission.ACCESS_FINE_LOCATION)
                                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                    mylocation = LocationServices.FusedLocationApi
                                            .getLastLocation(googleApiClient)
                                }
                            }
                            LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                                try {
                                    status.startResolutionForResult(this@BookingActivity,
                                            REQUEST_CHECK_SETTINGS_GPS)
                                } catch (e: IntentSender.SendIntentException) {
                                    ErrorUtils.showToast(applicationContext, e.message)
                                }

                            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS_GPS -> when (resultCode) {
                Activity.RESULT_OK -> getMyLocation()
                Activity.RESULT_CANCELED -> finish()
            }
        }
    }


    override fun onConnectionFailed(p0: ConnectionResult) {
        ErrorUtils.showToast(applicationContext, p0.errorMessage)

    }

    override fun onConnected(p0: Bundle?) {
        getMyLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
        ErrorUtils.showToast(applicationContext, "Suspended")
    }


    private fun showCustomAlertDialog(address: String?) {
        val alertDialogBuilder = AlertDialog.Builder(
                this)
        val inflater = this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.location_view, null)
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(false)
        val dialog = alertDialogBuilder.create()
        val edit = view.findViewById(R.id.edit) as Button
        val done = view.findViewById(R.id.done) as Button
        val cancel = view.findViewById(R.id.cancel) as Button
        val submit = view.findViewById(R.id.submit) as Button
        val enteredAddress = view.findViewById(R.id.entered_address) as EditText
        val pincode = view.findViewById(R.id.pincode) as EditText

        val state = view.findViewById(R.id.state) as Spinner
        val city = view.findViewById(R.id.city) as Spinner

        val enteringLayout = view.findViewById(R.id.entering_layout) as LinearLayout
        val currentLocation = view.findViewById(R.id.current_location) as RelativeLayout

        val addressTxt = view.findViewById(R.id.address) as TextView


        if (null == address) {
            currentLocation.visibility = View.GONE
            enteringLayout.visibility = View.VISIBLE
        } else {
            addressTxt.text = address
            currentLocation.visibility = View.VISIBLE
            enteringLayout.visibility = View.GONE
        }

        submit.setOnClickListener {
            if (!ErrorUtils.emptyCheck(enteredAddress)
                    && !ErrorUtils.emptyCheck(pincode)) {
                ErrorUtils.showToast(applicationContext, "Submitted")
                val deliveryAdd = DeliveryAddress()
                deliveryAdd.addressOne = enteredAddress.text.toString() + " " +
                        city.selectedItem.toString() + " " +
                        state.selectedItem.toString() + " " +
                        pincode.text.toString()
                deliveryAdd.pincode = pincode.text.toString()
                deliveryAdd.city = city.selectedItem.toString()
                deliveryAdd.state = state.selectedItem.toString()
                deliveryAdd.userId = LocalConfiq.getUserId(applicationContext)
                address(deliveryAdd)
            }
        }

        done.setOnClickListener {

            address(deliveryAddress)
            dialog.dismiss()
        }
        edit.setOnClickListener {
            currentLocation.visibility = View.GONE
            enteringLayout.visibility = View.VISIBLE
        }
        cancel.setOnClickListener {
            enteringLayout.visibility = View.GONE
            currentLocation.visibility = View.VISIBLE
            if (isChange) {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

/*

    override fun onItemChosen(labelledSpinner: View?, adapterView: AdapterView<*>?, itemView: View?, position: Int, id: Long) {
        val orderType = order_type.spinner.selectedItem.toString()
        if (orderType.equals("Regularly")) {
            day.visibility = View.VISIBLE
        } else {
            day.visibility = View.GONE
        }
    }
*/

    /* override fun onNothingChosen(labelledSpinner: View?, adapterView: AdapterView<*>?) {
     }*/

    private fun address(deliveryAddress: DeliveryAddress) {
        val api = RetrofitBase.create()
        val call = api.address(deliveryAddress)

        call.enqueue(object : Callback<NetworkResponse> {
            override fun onResponse(call: Call<NetworkResponse>, response: Response<NetworkResponse>) {
                if (response.isSuccessful) {
                    val networkResponse = response.body()
                    if (null != networkResponse) {
                        if (networkResponse.success == 1) {
                            adapter.addItem(deliveryAddress)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<NetworkResponse>, t: Throwable) {
                ErrorUtils.showToast(applicationContext, "Error. " + t.message)
            }
        })
    }


    private fun getAddress() {
        val api = RetrofitBase.create()
        val call = api.getAddress(LocalConfiq.getUserId(applicationContext))

        call.enqueue(object : Callback<AddressResponse> {
            override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {
                if (response.isSuccessful) {
                    val addressResponse = response.body()
                    if (null != addressResponse) {
                        if (addressResponse.success == 1) {
                            if (addressResponse.resultArray == null || addressResponse.resultArray.size == 0) {
                                setUpGClient()
                            } else {
                                adapter.addAllItem(addressResponse.resultArray)
                            }
                        } else {
                            setUpGClient()
                        }
                    } else {
                        setUpGClient()
                    }
                }
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                ErrorUtils.showToast(applicationContext, "Error. " + t.message)
            }
        })
    }


}
