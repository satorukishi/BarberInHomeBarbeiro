package com.satoruchannel.barberinhomebarbeiro

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import android.content.Intent
import android.content.SharedPreferences
import android.view.inputmethod.InputMethodManager
import com.satoruchannel.barberinhomebarbeiro.model.AddBarber
import com.satoruchannel.barberinhomebarbeiro.util.CallRetrofitBarber
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignupActivity : MasterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btCreateAccount.setOnClickListener {
            if (etUsername.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.signup_empty), Toast.LENGTH_SHORT).show()
                etUsername.requestFocus()
            } else if (etEmail.text.length < 7) {
                Toast.makeText(this, getString(R.string.email_empty), Toast.LENGTH_SHORT).show()
                etEmail.requestFocus()
            } else if (etPassword.text.length < 7) {
                Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_SHORT).show()
                etPassword.requestFocus()
            } else if (etCellphone.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.cellphone_empty), Toast.LENGTH_SHORT).show()
                etCellphone.requestFocus()
            } else {
                val retrofit = Retrofit.Builder()
                        .baseUrl("http://barberinhome.com.br/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()


                retrofit.create(CallRetrofitBarber::class.java)
                        .getAddBarber(etUsername.text.toString(),
                                etEmail.text.toString(),
                                etPassword.text.toString(),
                                etCellphone.text.toString(),
                                sucesso = "")
                        .enqueue(object : Callback<AddBarber> {

                            override fun onResponse(call: Call<AddBarber>?, response: Response<AddBarber>?) {
                                val sucesso = response?.body()?.sucesso

                                println(sucesso)

                                if(sucesso == 1 ) {
                                    // Salva o login para logar automaticamente
                                    val editor = getSharedPreferences(MY_SP_LOGIN, Context.MODE_PRIVATE).edit()
                                    editor.putString(MY_LOGIN, etEmail.text.toString())
                                    editor.putString(MY_PASSWORD, etPassword.text.toString())
                                    editor.apply()

                                    // Go to Main Activity
                                    val intent = Intent(this@SignupActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    this@SignupActivity.finish()
                                } else {
                                    Toast.makeText(this@SignupActivity, getString(R.string.error_signup), Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<AddBarber>?, t: Throwable?) {
                                Toast.makeText(this@SignupActivity, getString(R.string.error500), Toast.LENGTH_LONG).show()
                                println(t)
                            }

                        })
            }
        }
    }
}
