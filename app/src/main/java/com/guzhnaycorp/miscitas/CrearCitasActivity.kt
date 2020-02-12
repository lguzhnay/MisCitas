package com.guzhnaycorp.miscitas

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_crear_citas.*
import java.util.*

class CrearCitasActivity : AppCompatActivity() {

    private val selectedCalendar = Calendar.getInstance()
    private var selectedRadioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_citas)

        btnNext.setOnClickListener{
            if(etDescription.text.toString().length < 3){
                etDescription.error = getString(R.string.validate_cita_description)
            }else{
                cvStep1.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }
        }

        btnConfirmaCita.setOnClickListener{
            Toast.makeText(this,"Cita registrada correctamente",Toast.LENGTH_SHORT).show()
            finish()
        }

        val especialidadOptions = arrayOf("Especialidad 1", "Especialidad 2", "Especialidad 3")
        spinnerEspecialidades.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, especialidadOptions)

        val doctorOptions = arrayOf("Doctor A", "Doctor B", "Doctor C")
        spinnerDoctors.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, doctorOptions)
    }

    fun onClickScheduledDate(v: View?) {

        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener{ datePicker, y, m, d ->
            // Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            selectedCalendar.set(y, m, d)
            etScheduledDate.setText(resources.getString(
                R.string.date_format,
                y,
                if(m>9) m.toString() else "0$m",
                if(d>9) d.toString() else "0$d")
            )
            displayRadioButtons()
        }

        // new dialog
        val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)
        val datePicker = datePickerDialog.datePicker

        //estableciendo limites
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePicker.minDate = calendar.timeInMillis  // +1 now
        calendar.add(Calendar.DAY_OF_MONTH, 29)
        datePicker.maxDate = calendar.timeInMillis // +30 now
        datePickerDialog.show()
    }

    private fun displayRadioButtons() {
        //      radioGroup.clearCheck()
        //      radioGroup.removeAllViews()

        selectedRadioButton = null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()

        val hours = arrayOf("3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM")
        var goToLeft = true

        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it

            radioButton.setOnClickListener{ view ->
                selectedRadioButton?.isChecked = false

                selectedRadioButton = view as RadioButton?
                selectedRadioButton?.isChecked = true
            }

            if(goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft
        }
    }

    /*private fun Int.twoDigits()
            = if (this>=10) this.toString() else "0$this"
*/
    override fun onBackPressed() {
        if (cvStep2.visibility == View.VISIBLE){
            cvStep2.visibility = View.GONE
            cvStep1.visibility = View.VISIBLE
        }else if(cvStep1.visibility == View.VISIBLE){
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.dialog_exit_title))
            builder.setMessage(getString(R.string.dialog_create_cita_exit_messaje))
            builder.setPositiveButton(getString(R.string.dialog_cita_exit_positive_btn)) { _, _ ->
                finish()
            }
            builder.setNegativeButton(getString(R.string.dialog_cita_exit_negative_btn)) { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}
