package com.satoruchannel.barberinhomebarbeiro

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.text.style.UnderlineSpan
import android.text.SpannableString
import android.widget.TextView
import android.widget.Toast
import com.satoruchannel.barberinhomebarbeiro.model.User
import com.satoruchannel.barberinhomebarbeiro.util.CallRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {

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
                Toast.makeText(this@LoginActivity, getString(R.string.email_empty), Toast.LENGTH_LONG)
                etEmail.requestFocus()
                return@setOnClickListener
            }
            if (etPassword.text.toString().isEmpty() || etPassword.text.length < 7) {
                Toast.makeText(this@LoginActivity, getString(R.string.password_empty), Toast.LENGTH_LONG)
                etEmail.requestFocus()
                return@setOnClickListener
            }

            btLogin.isEnabled=false


            val retrofit = Retrofit.Builder().baseUrl("http://barberinhome.com.br/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            retrofit.create(CallRetrofit::class.java)
                    .getUser(etEmail.text.toString(), etPassword.text.toString())
                    .enqueue(object : Callback<List<User>> {

                        override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                            val login = response?.body()?.get(0)?.nome_barber

                            if(login != null){
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }else{
                                btLogin.isEnabled=true
                                Toast.makeText(this@LoginActivity, getString(R.string.error_invalid_login), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                            Toast.makeText(this@LoginActivity, "Erro 500", Toast.LENGTH_LONG).show()
                        }
                    })
        }
    }
}
