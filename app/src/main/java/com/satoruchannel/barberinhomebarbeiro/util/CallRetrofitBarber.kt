package com.satoruchannel.barberinhomebarbeiro.util

import com.satoruchannel.barberinhomebarbeiro.model.AddBarber
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by joseotavio on 23/05/2018.
 */
interface CallRetrofitBarber {

    @GET("app/rest/wsAddBarbers")

    fun getAddBarber(@Query("nome_barber") nomeBarber: String,
                   @Query("login_email") loginEmail: String,
                   @Query("senha") senha: String,
                   @Query("telefone") telefone: String,
                   @Query("sucesso") sucesso: String)
            : Call<AddBarber>

}