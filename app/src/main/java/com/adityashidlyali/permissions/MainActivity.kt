package com.adityashidlyali.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById<Button>(R.id.button_request_permission)
        button.setOnClickListener {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        if (!hasCameraPermission()) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        if (!hasReadExternalStoragePermission()) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (!hasWriteExternalStoragePermission()) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (!hasLocationForegroundPermission()) {
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (!hasLocationBackgroundPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissionsToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(this.javaClass.simpleName, permissions[i])
                }
            }
        }
    }

    private fun hasCameraPermission() = ActivityCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasReadExternalStoragePermission() = ActivityCompat.checkSelfPermission(
        this, Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasWriteExternalStoragePermission() = ActivityCompat.checkSelfPermission(
        this, Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationForegroundPermission() = ActivityCompat.checkSelfPermission(
        this, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationBackgroundPermission() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
}