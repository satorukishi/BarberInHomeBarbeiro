package com.satoruchannel.barberinhomebarbeiro

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import com.satoruchannel.barberinhomebarbeiro.model.AddUser
import com.satoruchannel.barberinhomebarbeiro.util.CallRetrofitUser
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btCreateAccount.setOnClickListener {
            if (etUsername.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.signup_empty), Toast.LENGTH_LONG)
                etUsername.requestFocus()
                return@setOnClickListener
            }
            if (etEmail.text.toString().isEmpty() || etEmail.text.length < 7) {
                Toast.makeText(this, getString(R.string.email_empty), Toast.LENGTH_LONG)
                etEmail.requestFocus()
                return@setOnClickListener
            }
            if (etPassword.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.password_empty), Toast.LENGTH_LONG)
                etPassword.requestFocus()
                return@setOnClickListener
            }
            if (etCellphone.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.cellphone_empty), Toast.LENGTH_LONG)
                etCellphone.requestFocus()
                return@setOnClickListener
            }

            val retrofit = Retrofit.Builder()
                    .baseUrl("http://barberinhome.com.br/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


            retrofit.create(CallRetrofitUser::class.java)
                    .getAddUser(etUsername.text.toString(),
                            etEmail.text.toString(),
                            etPassword.text.toString(),
                            etCellphone.text.toString(),
                            sucesso = "")
                    .enqueue(object : Callback<AddUser> {

                        override fun onResponse(call: Call<AddUser>?, response: Response<AddUser>?) {
                            val sucesso = response?.body()?.sucesso

                            println(sucesso)

                            if(sucesso == 1 ){
                                // Go to Main Activity
                                val intent = Intent(this@SignupActivity, MainActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this@SignupActivity, getString(R.string.error_signup), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<AddUser>?, t: Throwable?) {
                            Toast.makeText(this@SignupActivity, getString(R.string.error500), Toast.LENGTH_LONG).show()
                            println(t)
                        }

                    })
        }

        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
                this.currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS)

    }
}
