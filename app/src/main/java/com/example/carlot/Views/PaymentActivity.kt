package com.example.carlot.Views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.carlot.R
import java.sql.Time
import java.text.DateFormat
import java.util.*


class PaymentActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    var btn_time_picker: ImageButton? = null


    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        initViewComponents()

        btn_time_picker?.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)

            hour = calendar.get(Calendar.HOUR)
            minute = calendar.get(Calendar.MINUTE)
            val datePickerDialog = TimePickerDialog(this, this , hour, minute, android.text.format.DateFormat.is24HourFormat(this))
            datePickerDialog.show()
        }
    }

    fun initViewComponents(){
        btn_time_picker = findViewById(R.id.btn_picker)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        /*
        myDay = day
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, this, hour, minute,
            android.text.format.DateFormat.is24HourFormat(this))
        timePickerDialog.show()*/
    }



}