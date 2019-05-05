package com.nussd.todo.network

import com.app.aquahey.purepani.model.*
import com.app.aquahey.purepani.network.NetworkResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by ikram-tiss on 24/1/18.
 */
interface ApiInterface {

    /*@Multipart
    @POST("doc-upload")
    fun uploadDoc(@Part("userId") userId: RequestBody,
                  @Part("sendingFlag") sendingFlag: RequestBody,
                  @Part file: MultipartBody.Part): Call<FileUploadResponse>
*/

    /*@GET("/task-list-category")
    fun issueCat(): Call<IssueResponse>

    @GET("task-list-employee")
    fun employeeList(): Call<EmployeeResppnse>
*/
    @POST("login")
    fun login(@Body login: Login): Call<LoginResponse>

    @POST("insert-address")
    fun address(@Body address: DeliveryAddress?): Call<NetworkResponse>


    @POST("signup")
    fun signin(@Body signIn: SignIn?): Call<LoginResponse>

    @POST("insert-order")
    fun insertOrder(@Body order: Order?): Call<NetworkResponse>


    @GET("nearby-aqua")
    fun product(@Query("pincode") pincode: String,
                @Query("productType") productType: Int,
                @Query("isBrand") isBrand: Int,
                @Query("city") city: String,
                @Query("state") state: String
    ): Call<ProductResponse>

    @GET("address")
    fun getAddress(@Query("userId") userId: String): Call<AddressResponse>

    @GET("user-order")
    fun getMyOrders(@Query("userId") userId: String): Call<MyorderResponse>

    @GET("brands")
    fun getBrands(): Call<BrandData>


    @GET("otp_service")
    fun getOtp(@Query("mobile") mobile: String): Call<OtpResponse>

    @GET("dealerContact-verify")
    fun getMobileNoVerify(@Query("mobile") mobile: String?): Call<NumberVerify>

    @GET("change-password")
    fun changePassword(@Query("mobile") mobile: String,
                       @Query("password") pass: String): Call<NetworkResponse>


/*
    @POST("login-user")
    fun login(@Body login: Login): Call<LoginResponse>

 */   /*@GET("Category.php")
    fun categories():
            Call<CategoryResponse>


    @GET("playlists")
    fun channelPLaylist(@Query("part") part: String,
                        @Query("videosId") channelId: String,
                        @Query("maxResults") maxResults: String,
                        @Query("key") key: String,
                        @Query("nextPageToken") nextPageToken: String):
            Call<ActivityListResponse>

*/
}