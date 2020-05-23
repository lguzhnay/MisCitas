package com.guzhnaycorp.miscitas.ui

/*import javax.security.auth.callback.Callback*/
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.guzhnaycorp.miscitas.R
import com.guzhnaycorp.miscitas.io.ApiService
import com.guzhnaycorp.miscitas.model.Cita
import com.guzhnaycorp.miscitas.util.PreferenceHelper
import com.guzhnaycorp.miscitas.util.PreferenceHelper.get
import com.guzhnaycorp.miscitas.util.toast
import kotlinx.android.synthetic.main.activity_citas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CitasActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    private val citaAdapter = CitaAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        loadCitas()


        rvCitas.layoutManager = LinearLayoutManager(this)
        rvCitas.adapter = citaAdapter
    }

    private fun loadCitas(){
        val jwt = preferences["jwt",""]
        val call = apiService.getCitas("Bearer $jwt")
        call.enqueue(object: Callback<ArrayList<Cita>> {
            override fun onFailure(call: Call<ArrayList<Cita>>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArrayList<Cita>>,
                response: Response<ArrayList<Cita>>
            ) {
             if(response.isSuccessful){
                 response.body()?.let {
                     citaAdapter.citas = it
                     citaAdapter.notifyDataSetChanged()
                 }
             }
            }
        })
    }
}
