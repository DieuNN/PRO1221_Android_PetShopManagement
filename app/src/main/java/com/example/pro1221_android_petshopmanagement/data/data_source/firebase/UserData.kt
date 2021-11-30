package com.example.pro1221_android_petshopmanagement.data.data_source.firebase

import android.content.Context
import android.util.Log
import com.example.pro1221_android_petshopmanagement.common.collections.ImageUtil
import com.example.pro1221_android_petshopmanagement.data.data_source.AppDatabase
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserData {
    companion object {
        const val USER_DATA_COLLECTION = "users_data"

        // customer keys
        const val CUSTOMER_ID_KEY = "customer_id"
        const val CUSTOMER_NAME_KEY = "customer_name"
        const val CUSTOMER_ADDRESS_KEY = "customer_address"
        const val CUSTOMER_PHONE_NUMBER_KEY = "customer_phone_number"
        const val CUSTOMER_IMAGE_KEY = "customer_image"

        // kind keys
        const val KIND_ID_KEY = "kind_id"
        const val KIND_NAME_KEY = "kind_name"
        const val KIND_DESCRIPTION_KEY = "kind_description"
        const val KIND_IMAGE_KEY = "kind_image"

        // pet keys
        const val PET_ID_KEY = "pet_id"
        const val PET_NAME_KEY = "pet_name"
        const val PET_KIND_KEY = "pet_kind"
        const val PET_DETAIL_KEY = "pet_detail"
        const val PET_IS_SOLD_KEY = "pet_is_sold"
        const val PET_UPDATE_TIME_KEY = "pet_update_time"
        const val PET_IMAGE_KEY = "pet_image"
        const val PET_PRICE_KEY = "pet_price"
        const val PET_CUSTOMER_NAME = "pet_customer_name"
    }

    @DelicateCoroutinesApi
    suspend fun syncCustomer(context: Context) {
        val cloudDB = Firebase.firestore
        val localDB = AppDatabase.getInstance(context = context)
        val mAuth = FirebaseAuth.getInstance()

        val customers = localDB.customerDao.getCustomerAsList()
        val imageUtil = ImageUtil()

        cloudDB.collection(USER_DATA_COLLECTION).document(mAuth.currentUser!!.uid)
            .collection("customer").get().addOnSuccessListener { toDeletDocuments ->
                for (document in toDeletDocuments) {
                    document.reference.delete()
                }
                Log.d("Sync Customer", "Synced! ")

                customers.forEach {
                    cloudDB.collection(USER_DATA_COLLECTION).document(mAuth.currentUser!!.uid)
                        .collection("customer").add(
                            hashMapOf(
                                CUSTOMER_ID_KEY to it.id,
                                CUSTOMER_ADDRESS_KEY to it.address,
                                CUSTOMER_IMAGE_KEY to imageUtil.convertBitmapToBase64(it.image!!),
                                CUSTOMER_PHONE_NUMBER_KEY to it.phoneNumber,
                                CUSTOMER_NAME_KEY to it.name
                            )
                        )
                }
                // delete all in local db, cuz data synced on cloud
                GlobalScope.launch {
                    localDB.customerDao.deleteAllRecord()
                    Log.d("Sync Customer", "Delete all record in local! ")
                }
                // retrieve data
                cloudDB.collection(USER_DATA_COLLECTION).document(mAuth.currentUser!!.uid)
                    .collection("customer").get().addOnSuccessListener { toAddDocuments ->
                        for (document in toAddDocuments) {
                            val customer = Customer(
                                id = (document.get(CUSTOMER_ID_KEY) as Long).toInt(),
                                image = imageUtil.convertBase64ToBitmap(document.get(CUSTOMER_IMAGE_KEY) as String),
                                name = document.get(CUSTOMER_NAME_KEY) as String,
                                phoneNumber = document.get(CUSTOMER_PHONE_NUMBER_KEY) as String,
                                address = document.get(CUSTOMER_ADDRESS_KEY) as String
                            )
                            GlobalScope.launch {
                                localDB.customerDao.addCustomer(customer)
                                Log.d("Sync Customer", "Adding Customer! ")
                            }
                        }
                    }
            }
    }
}