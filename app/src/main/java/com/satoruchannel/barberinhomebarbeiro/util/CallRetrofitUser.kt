package com.satoruchannel.barberinhomebarbeiro.util

import com.satoruchannel.barberinhomebarbeiro.model.AddUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by joseotavio on 23/05/2018.
 */
interface CallRetrofitUser {

    @GET("app/rest/wsAdd?a=5")

    fun getAddUser(@Query("nome_barber") nome_barber: String,
                   @Query("login_email") login_email: String,
                   @Query("senha") senha: String,
                   @Query("telefone") telefone: String,
                   @Query("sucesso") sucesso: String)
            : Call<AddUser>

}