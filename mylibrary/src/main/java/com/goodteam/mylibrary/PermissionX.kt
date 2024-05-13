package com.goodteam.mylibrary

import androidx.appcompat.app.AppCompatActivity

object PermissionX {
    private const val TAG = "InvisibleFragment"

    fun request(activity: AppCompatActivity, permission: String, callback:
    PermissionCallback){
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null){
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.requestNow(activity, callback, permission)
    }
}