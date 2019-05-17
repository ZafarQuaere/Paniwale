package com.app.aquahey.purepani.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.app.aquahey.purepani.R
import com.app.aquahey.purepani.fragment.HomeFragment
import com.app.aquahey.purepani.utils.ErrorUtils
import com.app.aquahey.purepani.utils.LocalConfiq
import com.app.aquahey.purepani.utils.PermissionUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks {

    private val REQUEST_CHECK_SETTINGS_GPS = 0x1

    private var geocoder: Geocoder? = null
    private var addresses: List<Address>? = null
    private var mylocation: Location? = null
    private var googleApiClient: GoogleApiClient? = null
    private val imageLoader = ImageLoader.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // setSupportActionBar(toolbar)
        geocoder = Geocoder(applicationContext, Locale.getDefault())
        openDialog()

        PermissionUtils.checkAndRequestPermissions(this, 1)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

       // drawer_layout.addDrawerListener(toggle)
       // toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        setUpGClient()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val fragment = supportFragmentManager.findFragmentById(R.id.home_container)
            if (fragment is HomeFragment) {
                finish()
            }else{
                super.onBackPressed()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_help -> {
                return true
            }
            R.id.action_about -> {
                return true
            }
            R.id.action_contact -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.my_order -> {
                val intent = Intent(applicationContext, MyOrderActivity::class.java)
                startActivity(intent)
            }
            R.id.logout -> {
                LocalConfiq.putBoolean(applicationContext, LocalConfiq.IS_LOGIN, false)
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openHomeFragment() {
        val frag = HomeFragment()
        val manager = supportFragmentManager
        val transaction = manager!!.beginTransaction()
        transaction.add(R.id.home_container, frag, "Home")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMyLocation()
                } else {
                    finish()
                }

                return
            }

        }// other 'switch' lines to check for other
        // permissions this app might request
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
            addresses = geocoder!!.getFromLocation(latitude, longitude, 1)
            LocalConfiq.putString(applicationContext, LocalConfiq.PINCODE, addresses!![0].postalCode)
            LocalConfiq.putString(applicationContext, LocalConfiq.CITY, addresses!![0].locality)
            LocalConfiq.putString(applicationContext, LocalConfiq.STATE, addresses!![0].adminArea)
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        ErrorUtils.showToast(applicationContext, p0.errorMessage)

    }

    override fun onConnected(p0: Bundle?) {
        hideDialog()
        openHomeFragment()

        getMyLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
        ErrorUtils.showToast(applicationContext, "Suspended")
    }

    private fun getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient!!.isConnected) {
                val permissionLocation = ContextCompat.checkSelfPermission(this@MainActivity,
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

                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
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
                            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                                status.startResolutionForResult(this@MainActivity,
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
}
