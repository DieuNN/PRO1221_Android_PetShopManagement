package com.example.pro1221_android_petshopmanagement.data.data_source.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.example.pro1221_android_petshopmanagement.common.collections.RC_SIGN_IN
import com.example.pro1221_android_petshopmanagement.common.collections.isNetworkAvailable
import com.example.pro1221_android_petshopmanagement.presentation.activity.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


fun getCurrentUser(): FirebaseUser? {
    val mAuth = FirebaseAuth.getInstance()
    return mAuth.currentUser
}

// must be hardcode lmao
fun getGoogleSignInConfigure(context: Context): GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("573584192313-j3e53i722d1cspt13vpdbdn7m572q381.apps.googleusercontent.com")
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}

fun signInWithGoogle(context: Context) {
    (context as? Activity)?.startActivityForResult(
        getGoogleSignInConfigure(context = context).signInIntent,
        RC_SIGN_IN
    )
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
fun firebaseAuthWithGoogle(idToken: String, context: Context) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    val auth = FirebaseAuth.getInstance()
    auth.signInWithCredential(credential)
        .addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("LoginActivity", "signInWithCredential:success")
                (context).startActivity(Intent(context, MainActivity::class.java))
//                context.finishAffinity()
            } else {
                // If sign in fails, display a message to the user.
                Log.w("LoginActivity", "signInWithCredential:failure", task.exception)
            }
        }
}

fun signUpWithEmailAndPassword(
    email: String,
    password: String,
    context: Context,
    navigateOnSuccess: () -> Unit
) {
    val mAuth = FirebaseAuth.getInstance()
    mAuth.createUserWithEmailAndPassword(email.trim(), password.trim())
        .addOnCompleteListener(context as Activity) {
            if (it.isSuccessful) {
                Toast.makeText(
                    context,
                    "Đăng ký thành công! Đăng nhập để sử dụng ứng dụng!",
                    Toast.LENGTH_SHORT
                ).show()
                navigateOnSuccess.invoke()
            } else {
                Toast.makeText(
                    context,
                    "Đăng ký thất bại! Kiểm tra lại đường truyền Internet!",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("Sign Up With Email Failed", "signUpWithEmailAndPassword: ${it.exception}")
            }
        }
}

fun loginWithEmailAndPassword(
    email: String,
    password: String,
    context: Context,
    onSuccessful: () -> Unit,
) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(context as Activity) {
            if (it.isSuccessful) {
                Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                onSuccessful.invoke()
            } else{
                if (isNetworkAvailable(context = context) != true) {
                    Toast.makeText(context, "Đăng nhập thất bại! Kiểm tra lại đường truyền!", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                } else {
                    Toast.makeText(context, "Đăng nhập thất bại! Kiểm tra lại mật khẩu và email!", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }
            }
        }
}

fun forgetPassword(email:String, onSuccessful: () -> Unit) {
    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener {
        onSuccessful()
    }
}
