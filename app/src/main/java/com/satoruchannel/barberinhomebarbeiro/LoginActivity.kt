package com.satoruchannel.barberinhomebarbeiro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.widget.Toast
import com.satoruchannel.barberinhomebarbeiro.model.Barber
import com.satoruchannel.barberinhomebarbeiro.util.CallRetrofitLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : MasterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val content = SpannableString(getString(R.string.signup))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        tvSignup.text = content

        tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btLogin.setOnClickListener {
            if (etEmail.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, getString(R.string.email_empty), Toast.LENGTH_LONG).show()
                etEmail.requestFocus()
                return@setOnClickListener
            }
            if (etPassword.text.toString().isEmpty()) {
                Toast.makeText(this@LoginActivity, getString(R.string.password_empty), Toast.LENGTH_LONG).show()
                etEmail.requestFocus()
                return@setOnClickListener
            }

            btLogin.isEnabled=false


            val retrofit = Retrofit.Builder().baseUrl("http://barberinhome.com.br/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            retrofit.create(CallRetrofitLogin::class.java)
                    .getUser(etEmail.text.toString(), etPassword.text.toString())
                    .enqueue(object : Callback<List<Barber>> {

                        override fun onResponse(call: Call<List<Barber>>?, response: Response<List<Barber>>?) {

                            // TODO: corrigir o 7 chumbado
                            val login = response?.body()?.get(0)?.nome_barber
                            val password = response?.body()?.get(0)?.senha

                            if(login != null){
                                // Salva o login para o próximo acesso
                                val sp = getSharedPreferences(MY_SP_LOGIN, Context.MODE_PRIVATE).edit()
                                sp.putString(MY_LOGIN, login)
                                sp.putString(MY_PASSWORD, password)
                                sp.apply()

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                this@LoginActivity.finish()
                            }else{
                                btLogin.isEnabled=true
                                Toast.makeText(this@LoginActivity, getString(R.string.error_invalid_login), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<List<Barber>>?, t: Throwable?) {
                            Toast.makeText(this@LoginActivity, "Erro 500", Toast.LENGTH_LONG).show()
                        }
                    })
        }
    }
}
