package com.example.easysms

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class BasicSmsAdapter(val context: Context, val smsMessages: MutableList<SMS>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.basic_sms_itemview, null)
        itemView.findViewById<TextView>(R.id.from).text = getItem(position).fromAddress
        itemView.findViewById<TextView>(R.id.body).text = getItem(position).body

        return itemView
    }

    override fun getItem(position: Int): SMS {
        return smsMessages[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return smsMessages.size
    }


}
