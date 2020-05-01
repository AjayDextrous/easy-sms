package com.example.easysms

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    var smsMessages = mutableListOf<SMS>()
    lateinit var messagesListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messagesListView = findViewById(R.id.messages_listview)
        messagesListView.adapter = BasicSmsAdapter(this, smsMessages)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_SMS),
                    ESConstants.RequestCodes.READ_SMS_PERMISSION_REQUEST_CODE)

        } else {
            updateInbox()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ESConstants.RequestCodes.READ_SMS_PERMISSION_REQUEST_CODE -> {
                updateInbox()
            }
        }
    }

    private fun updateInbox() {
        val inboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
        inboxCursor?.run {
            val indexBody: Int = getColumnIndex("body")
            val indexAddress: Int = getColumnIndex("address")
            if (indexBody < 0) return
            smsMessages.clear()
            while (moveToNext()){
                smsMessages.add(SMS(getString(indexAddress), getString(indexBody)))
            }
            (messagesListView.adapter as BasicSmsAdapter).notifyDataSetChanged()
        }

    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_SMS),
                ESConstants.RequestCodes.READ_SMS_PERMISSION_REQUEST_CODE)

        } else {
            updateInbox()
        }
    }
}
