package com.satoruchannel.barberinhomebarbeiro.util

import com.satoruchannel.barberinhomebarbeiro.model.Barber
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CallRetrofitLogin {
    @GET("app/rest/wsClients")
    fun getUser(@Query("login_email") login_email: String,
                @Query("senha") senha: String): Call<List<Barber>>


}