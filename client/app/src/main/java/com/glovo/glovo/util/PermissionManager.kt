package com.glovo.glovo.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


const val REQUEST_ID_PERMISSIONS_LOCATION = 12

class PermissionManager(private val activity: Activity) {
    private var callback: PermissionCallback? = null

    init {
        callback = this.activity as? PermissionCallback
    }

    fun requestPermission(permission: String, requestCode: Int) {

        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                requestCode
            )
        } else {

            callback?.onGranted(permission)
        }

    }


    /**
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {


        when (requestCode) {
            REQUEST_ID_PERMISSIONS_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    callback?.onGranted(permissions[0])
                } else {
                    callback?.onDenied(permissions[0])

                }
                return
            }

        }


    }
}


interface PermissionCallback {
    fun onGranted(permission: String)
    fun onDenied(permission: String)
}