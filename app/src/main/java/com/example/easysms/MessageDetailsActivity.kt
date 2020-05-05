package com.example.easysms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.basic_sms_itemview.*

class MessageDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_details)

        val receivedSMS = intent.extras?.getParcelable<SMS>(ESConstants.Extras.SMS_RECEIVED)

        receivedSMS?.run {
            findViewById<TextView>(R.id.sender_field_value).text = fromAddress
            findViewById<TextView>(R.id.body_field_value).text = body
        }
    }
}
