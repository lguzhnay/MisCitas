package com.guzhnaycorp.miscitas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guzhnaycorp.miscitas.R
import com.guzhnaycorp.miscitas.io.ApiService
import com.guzhnaycorp.miscitas.util.PreferenceHelper
import com.guzhnaycorp.miscitas.util.PreferenceHelper.get
import com.guzhnaycorp.miscitas.util.PreferenceHelper.set
import com.guzhnaycorp.miscitas.util.toast
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
/*import javax.security.auth.callback.Callback*/
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private val apiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnCrearCitas.setOnClickListener{
            val intent = Intent(this, CrearCitasActivity::class.java)
            startActivity(intent)
        }

        btnMisCitas.setOnClickListener {
            val intent = Intent(this, CitasActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun performLogout(){
        val jwt = preferences["jwt",""]
        val call = apiService.postLogout("Bearer $jwt")
        call.enqueue(object: Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()

                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }

    private fun clearSessionPreference(){
        /*val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("session", false)
        editor.apply()*/
        val preferences =
            PreferenceHelper.defaultPrefs(this)
        preferences["jwt"] = ""
    }
}
