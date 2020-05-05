package com.example.easysms

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import android.telephony.SmsMessage
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class IncomingSmsHandler : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == SMS_RECEIVED_ACTION) {
            val messages: Array<SmsMessage?> = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val sb = StringBuilder()
            val sender: String? = messages[0]?.originatingAddress
            messages.forEach {
                sb.append(it?.messageBody)
            }
            val messageBody = sb.toString()
//            println("### IncomingSmsHandler.onReceive : $sender --> $messageBody")

            val intent = Intent(context, MessageDetailsActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(ESConstants.Extras.SMS_RECEIVED, SMS(sender ?: "", messageBody))
            }
            val openMessageIntent: PendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


            var builder = NotificationCompat.Builder(context, ESConstants.Notifications.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_send_black)
                .setContentTitle(sender)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(openMessageIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(-1, builder.build())
            }

        }
    }
}