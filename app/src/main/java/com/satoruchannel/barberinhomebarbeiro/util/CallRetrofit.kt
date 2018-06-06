package com.satoruchannel.barberinhomebarbeiro.util

import com.satoruchannel.barberinhomebarbeiro.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CallRetrofit {
    @GET("app/rest/wsClients?a=5")
    fun getUser(@Query("login_email") login_email: String,
                @Query("senha") senha: String): Call<List<User>>


}