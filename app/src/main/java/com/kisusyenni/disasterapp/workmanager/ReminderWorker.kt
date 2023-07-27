package com.kisusyenni.disasterapp.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.kisusyenni.disasterapp.utils.NotificationHelper

class AlertWorker (val context: Context, private val params: WorkerParameters) : Worker(context, params){
    override fun doWork(): Result {
//        NotificationHelper(context).createNotification(
//            inputData.getString("title").toString(),
//            inputData.getString("message").toString())

        return Result.success()
    }
}