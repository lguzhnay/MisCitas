package com.guzhnaycorp.miscitas.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.guzhnaycorp.miscitas.R
import com.guzhnaycorp.miscitas.io.ApiService
import com.guzhnaycorp.miscitas.io.response.LoginResponse
import com.guzhnaycorp.miscitas.util.PreferenceHelper
import com.guzhnaycorp.miscitas.util.PreferenceHelper.get
import com.guzhnaycorp.miscitas.util.PreferenceHelper.set
import com.guzhnaycorp.miscitas.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }
    private val snackbar by lazy {
        Snackbar.make(mainLayout,
            R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences =
            PreferenceHelper.defaultPrefs(this)

        if (preferences["jwt",""].contains("."))
            goToMenuActivity()

        btnLogin.setOnClickListener{
            // validates
            performLogin()
        }

        tvGoToRegister.setOnClickListener {
            Toast.makeText(this, getString(R.string.please_fill_your_register_data), Toast.LENGTH_SHORT).show()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin(){
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if(email.trim().isEmpty()){
            toast(getString(R.string.error_empty_credentials))
            return
        }

        val call = apiService.postLogin(email,password)
        call.enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        toast(getString(R.string.error_login_response))
                        return
                    }
                    if (loginResponse.success) {
                        createSessionPreference(loginResponse.jwt)
                        toast("Bienvenido ${loginResponse.user.name}")
                        goToMenuActivity()
                    }else{
                        toast(getString(R.string.error_invalid_credentials))
                    }
                } else {
                    toast(getString(R.string.error_login_response))
                }
            }

        })
    }

    private fun createSessionPreference(jwt: String){
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["jwt"] = jwt
    }

    private fun goToMenuActivity(){
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if(snackbar.isShown)
            super.onBackPressed()
        else
            snackbar.show()

    }
}
