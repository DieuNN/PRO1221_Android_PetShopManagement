package com.example.pro1221_android_petshopmanagement.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pro1221_android_petshopmanagement.common.collections.RC_SIGN_IN
import com.example.pro1221_android_petshopmanagement.data.data_source.firebase.firebaseAuthWithGoogle
import com.example.pro1221_android_petshopmanagement.presentation.screen.navigation.LoginNavigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import kotlin.system.exitProcess

class LoginActivity : ComponentActivity() {
    companion object {
        const val READ_EXTERNAL_REQUEST_CODE = 101
    }

    private var requestedTime = 0

    override fun onStart() {
        super.onStart()
        requestPermission()
    }

    @ExperimentalMaterialApi @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // LoginNavigation not LoginScreen
            LoginNavigation()
        }
    }

    // start google login dialog
    @OptIn(ExperimentalMaterialApi::class)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!, this)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    }

    // request READ_EXTERNAL_STORAGE to get image
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // exit if request time == 3
            if (requestedTime == 3) {
                AlertDialog.Builder(this).apply {
                    setTitle("Bye")
                    setMessage("Bye")
                    setPositiveButton("Bye") {_, _ ->
                        this@LoginActivity.finishAffinity()
                        exitProcess(0)
                    }
                }
            }
            AlertDialog.Builder(this).apply {
                setTitle("Cấp quyền truy cập")
                setMessage("Ứng dụng cần quyền truy cập vào bộ nhớ để có thể sử dụng ảnh, hoặc thôi khỏi dùng cho nhanh!")
                setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this@LoginActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        READ_EXTERNAL_REQUEST_CODE
                    )
                    requestedTime++
                }
                setNegativeButton("Không") { _, _ ->
                    this@LoginActivity.finishAffinity()
                    exitProcess(0)
                }
            }.create().show()
        }
    }

    // if user keep denying permission, fuck them :)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_EXTERNAL_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                android.app.AlertDialog.Builder(this).apply {
                    setTitle("Thôi nào")
                    setMessage("Không lấy dữ liệu đâu mà lo")
                    setNegativeButton("Thôi") { _, _ ->
                        this@LoginActivity.finishAffinity()
                    }
                    setPositiveButton("Ok") { _, _ ->
                        ActivityCompat.requestPermissions(
                            this@LoginActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            READ_EXTERNAL_REQUEST_CODE
                        )
                    }
                }.create().show()
            }
        }
    }
}



