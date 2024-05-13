package com.goodteam.mylibrary

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {

    private var callback: PermissionCallback? = null
    private var requestPermission: String? = null

    // Register the contract in your fragment/activity and handle the result
    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            doPermissionAction()
        }

    private fun doPermissionAction() {
        val deniedList = ArrayList<String>()
        if (requestPermission != null){
            if (!isPermissionGranted(requestPermission!!)){
                deniedList.add(requestPermission!!)
            }
        }
        val allGranted = deniedList.isEmpty()
        callback?.let { it(allGranted, deniedList) }
    }

    fun requestNow(context: Context, cb: PermissionCallback, permission: String) {
        callback = cb
        requestPermission = permission
        if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(context).apply {
                setTitle("Need Permission")
                setMessage("You need to grant access by accepting the permission")
                setCancelable(false)
                setPositiveButton("Grant permission"){dialog, which->
                    permissionRequest.launch(permission)
                }
                setNegativeButton("Cancel"){dialog, which->

                }
                show()
            }
        } else {
            permissionRequest.launch(permission)
        }
    }

    // permission is granted
    private fun isPermissionGranted(name: String) = ContextCompat.checkSelfPermission(
        requireContext(), name
    ) == PackageManager.PERMISSION_GRANTED
}