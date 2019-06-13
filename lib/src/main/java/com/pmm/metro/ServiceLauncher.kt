package com.pmm.metro

import android.app.Activity
import android.content.Context
import android.content.ServiceConnection
import android.support.v4.app.Fragment
import android.view.View

/**
 * Author:你需要一台永动机
 * Date:2019-06-13 17:49
 * Description:
 */
class ServiceLauncher(private val dispatcher: Dispatcher) {

    //开启Service
    fun go() {
        val station = dispatcher.getStation() ?: return
        val intent = dispatcher.getTicket().intent
        when (val driver = dispatcher.getDriver()) {
            is Activity -> {
                driver.startService(intent.setClass(driver, station.destination))
            }
            is Fragment -> {
                val target = driver.requireActivity()
                target.startService(intent.setClass(target, station.destination))
            }
            is Context -> {
                driver.startService(intent.setClass(driver, station.destination))
            }
            is View -> {
                val target = driver.context
                target.startService(intent.setClass(target, station.destination))
            }
        }
    }

    //绑定Service
    fun bind(
        conn: ServiceConnection,
        flags: Int = Context.BIND_AUTO_CREATE
    ) {
        val station = dispatcher.getStation() ?: return
        val intent = dispatcher.getTicket().intent
        when (val driver = dispatcher.getDriver()) {
            is Activity -> {
                driver.bindService(intent.setClass(driver, station.destination), conn, flags)
            }
            is Fragment -> {
                val target = driver.requireActivity()
                target.bindService(intent.setClass(target, station.destination), conn, flags)
            }
            is Context -> {
                driver.bindService(intent.setClass(driver, station.destination), conn, flags)
            }
            is View -> {
                val target = driver.context
                target.bindService(intent.setClass(target, station.destination), conn, flags)
            }
        }
    }
}