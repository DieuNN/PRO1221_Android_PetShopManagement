package com.example.pro1221_android_petshopmanagement.data.data_source.firebase

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.pro1221_android_petshopmanagement.common.collections.ImageUtil
import com.example.pro1221_android_petshopmanagement.data.data_source.AppDatabase
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserData(
    context: Context,
    private val showProcessDialog: () -> Unit,
    private val currentUserUid: String,
) {
    // fuck firebase, never use it next time lol
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


    private val cloudDB = Firebase.firestore
    private val localDB = AppDatabase.getInstance(context = context)
    private val mAuth = FirebaseAuth.getInstance()
    private val imageUtil = ImageUtil()

    private suspend fun clearLocalData() {
        localDB.apply {
            petDao.deleteAllRecords()
            kindDao.deleteAllRecords()
            customerDao.deleteAllRecord()
        }
        Log.d("local", "clearLocalData: local data cleared")
    }

    @DelicateCoroutinesApi
    suspend fun syncWhenLogin() {
        clearLocalData()
        cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
            .collection("customer").get().addOnSuccessListener { toAddDocuments ->
                for (document in toAddDocuments) {
                    val customer = Customer(
                        id = (document.get(CUSTOMER_ID_KEY) as Long).toInt(),
                        image = imageUtil.convertBase64ToBitmap(
                            document.get(
                                CUSTOMER_IMAGE_KEY
                            ) as String
                        ),
                        name = document.get(CUSTOMER_NAME_KEY) as String,
                        phoneNumber = document.get(CUSTOMER_PHONE_NUMBER_KEY) as String,
                        address = document.get(CUSTOMER_ADDRESS_KEY) as String
                    )
                    GlobalScope.launch {
                        localDB.customerDao.addCustomer(customer)
                        Log.d("Sync Customer", "Customer ${customer.name} added ")
                    }
                }
            }
        cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
            .collection("kind").get().addOnSuccessListener { toAddDocuments ->
                for (document in toAddDocuments) {
                    val kind = Kind(
                        id = (document.get(KIND_ID_KEY) as Long).toInt(),
                        name = document.get(KIND_NAME_KEY) as String,
                        image = imageUtil.convertBase64ToBitmap(document.get(KIND_IMAGE_KEY) as String),
                        description = document.get(KIND_DESCRIPTION_KEY) as String
                    )

                    GlobalScope.launch {
                        localDB.kindDao.addKind(kind)
                    }
                    Log.d("Sync Kinds", "kind ${kind.name} added in local")
                }
            }
        cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
            .collection("pet").get().addOnSuccessListener { toAddDocuments ->
                for (document in toAddDocuments) {
                    val pet = Pet(
                        id = (document.get(PET_ID_KEY) as Long).toInt(),
                        image = (imageUtil.convertBase64ToBitmap(document.get(PET_IMAGE_KEY) as String)),
                        name = document.get(PET_NAME_KEY) as String,
                        updateTime = document.get(PET_UPDATE_TIME_KEY) as String,
                        detail = document.get(PET_DETAIL_KEY) as String,
                        kind = document.get(PET_KIND_KEY) as String,
                        price = (document.get(PET_PRICE_KEY) as Long).toInt(),
                        isSold = document.get(PET_IS_SOLD_KEY) as Boolean,
                        customerName = document.get(PET_CUSTOMER_NAME) as String
                    )

                    GlobalScope.launch {
                        localDB.petDao.addPet(pet = pet)
                    }
                    Log.d("Sync pet", "${pet.name} added to local")
                }
            }
        showProcessDialog.invoke()
    }

    @DelicateCoroutinesApi
    suspend fun syncWhenPressSync() {
        // first, clear cloud, then put local to cloud again
        val customers = localDB.customerDao.getCustomerAsList()
        val kinds: List<Kind> = localDB.kindDao.getKindsAsList()
        val pets = localDB.petDao.getPetsAsList()
        cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
            .collection("customer").get().addOnSuccessListener { toDeleteDocuments ->
                for (document in toDeleteDocuments) {
                    Log.d("cloud", "clearOnCloud: deleting customer")
                    // delete on cloud first
                    document.reference.delete()
                }
                cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
                    .collection("kind").get().addOnSuccessListener { toDeleteKinds ->
                        for (document in toDeleteKinds) {
                            Log.d("cloud", "clearOnCloud: deleting kind")
                            document.reference.delete()
                        }
                        cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
                            .collection("pet")
                            .get().addOnSuccessListener { toDeletePets ->
                                for (document in toDeletePets) {
                                    Log.d("cloud", "clearOnCloud: deleting pet")
                                    document.reference.delete()
                                }
                                customers.forEach {
                                    cloudDB.collection(USER_DATA_COLLECTION)
                                        .document(mAuth.currentUser!!.uid)
                                        .collection("customer").add(
                                            hashMapOf(
                                                CUSTOMER_ID_KEY to it.id,
                                                CUSTOMER_ADDRESS_KEY to it.address,
                                                CUSTOMER_IMAGE_KEY to imageUtil.convertBitmapToBase64(
                                                    it.image!!
                                                ),
                                                CUSTOMER_PHONE_NUMBER_KEY to it.phoneNumber,
                                                CUSTOMER_NAME_KEY to it.name
                                            )
                                        )
                                    Log.d("Sync customer", "added ${it.name} on cloud")
                                }
                                kinds.forEach {
                                    cloudDB.collection(USER_DATA_COLLECTION)
                                        .document(mAuth.currentUser!!.uid)
                                        .collection("kind").add(
                                            hashMapOf(
                                                KIND_ID_KEY to it.id,
                                                KIND_DESCRIPTION_KEY to it.description,
                                                KIND_NAME_KEY to it.name,
                                                KIND_IMAGE_KEY to imageUtil.convertBitmapToBase64(it.image!!)
                                            )
                                        )
                                    Log.d("Sync Kinds", "added ${it.name} on cloud")
                                }
                                pets.forEach {
                                    cloudDB.collection(USER_DATA_COLLECTION)
                                        .document(mAuth.currentUser!!.uid)
                                        .collection("pet").add(
                                            hashMapOf(
                                                PET_ID_KEY to it.id,
                                                PET_NAME_KEY to it.name,
                                                PET_KIND_KEY to it.kind,
                                                PET_DETAIL_KEY to it.detail,
                                                PET_IS_SOLD_KEY to it.isSold,
                                                PET_UPDATE_TIME_KEY to it.updateTime,
                                                PET_IMAGE_KEY to imageUtil.convertBitmapToBase64(it.image!!),
                                                PET_PRICE_KEY to it.price,
                                                PET_CUSTOMER_NAME to it.customerName
                                            )
                                        )
                                    Log.d("Sync pet", "pet ${it.name} added on cloud")
                                }
                                GlobalScope.launch {
                                    clearLocalData()
                                    Log.d("local", " local cleared")
                                }
                                cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
                                    .collection("customer").get()
                                    .addOnSuccessListener { toAddDocuments ->
                                        for (document in toAddDocuments) {
                                            val customer = Customer(
                                                id = (document.get(CUSTOMER_ID_KEY) as Long).toInt(),
                                                image = imageUtil.convertBase64ToBitmap(
                                                    document.get(
                                                        CUSTOMER_IMAGE_KEY
                                                    ) as String
                                                ),
                                                name = document.get(CUSTOMER_NAME_KEY) as String,
                                                phoneNumber = document.get(CUSTOMER_PHONE_NUMBER_KEY) as String,
                                                address = document.get(CUSTOMER_ADDRESS_KEY) as String
                                            )
                                            GlobalScope.launch {
                                                localDB.customerDao.addCustomer(customer)
                                                Log.d(
                                                    "Sync Customer",
                                                    "Customer ${customer.name} added "
                                                )
                                            }
                                        }
                                    }
                                cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
                                    .collection("kind").get()
                                    .addOnSuccessListener { toAddDocuments ->
                                        for (document in toAddDocuments) {
                                            val kind = Kind(
                                                id = (document.get(KIND_ID_KEY) as Long).toInt(),
                                                name = document.get(KIND_NAME_KEY) as String,
                                                image = imageUtil.convertBase64ToBitmap(
                                                    document.get(
                                                        KIND_IMAGE_KEY
                                                    ) as String
                                                ),
                                                description = document.get(KIND_DESCRIPTION_KEY) as String
                                            )

                                            GlobalScope.launch {
                                                localDB.kindDao.addKind(kind)
                                            }
                                            Log.d("Sync Kinds", "kind ${kind.name} added in local")
                                        }
                                    }
                                cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
                                    .collection("pet").get()
                                    .addOnSuccessListener { toAddDocuments ->
                                        for (document in toAddDocuments) {
                                            val pet = Pet(
                                                id = (document.get(PET_ID_KEY) as Long).toInt(),
                                                image = (imageUtil.convertBase64ToBitmap(
                                                    document.get(
                                                        PET_IMAGE_KEY
                                                    ) as String
                                                )),
                                                name = document.get(PET_NAME_KEY) as String,
                                                updateTime = document.get(PET_UPDATE_TIME_KEY) as String,
                                                detail = document.get(PET_DETAIL_KEY) as String,
                                                kind = document.get(PET_KIND_KEY) as String,
                                                price = (document.get(PET_PRICE_KEY) as Long).toInt(),
                                                isSold = document.get(PET_IS_SOLD_KEY) as Boolean,
                                                customerName = document.get(PET_CUSTOMER_NAME) as String
                                            )

                                            GlobalScope.launch {
                                                localDB.petDao.addPet(pet = pet)
                                            }
                                            Log.d("Sync pet", "${pet.name} added to local")
                                        }
                                        showProcessDialog.invoke()
                                    }
                            }
                    }
            }

    }

    @DelicateCoroutinesApi
    suspend fun syncWhenLogout() {
        val customers = localDB.customerDao.getCustomerAsList()
        val kinds: List<Kind> = localDB.kindDao.getKindsAsList()
        val pets = localDB.petDao.getPetsAsList()
        cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
            .collection("customer").get().addOnSuccessListener { toDeleteDocuments ->
                for (document in toDeleteDocuments) {
                    Log.d("cloud", "clearOnCloud: deleting customer")
                    // delete on cloud first
                    document.reference.delete()
                }
                cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
                    .collection("kind").get().addOnSuccessListener { toDeleteKinds ->
                        for (document in toDeleteKinds) {
                            Log.d("cloud", "clearOnCloud: deleting kind")
                            document.reference.delete()
                        }
                        cloudDB.collection(USER_DATA_COLLECTION).document(currentUserUid)
                            .collection("pet")
                            .get().addOnSuccessListener { toDeletePets ->
                                for (document in toDeletePets) {
                                    Log.d("cloud", "clearOnCloud: deleting pet")
                                    document.reference.delete()
                                }
                                customers.forEach {
                                    cloudDB.collection(USER_DATA_COLLECTION)
                                        .document(mAuth.currentUser!!.uid)
                                        .collection("customer").add(
                                            hashMapOf(
                                                CUSTOMER_ID_KEY to it.id,
                                                CUSTOMER_ADDRESS_KEY to it.address,
                                                CUSTOMER_IMAGE_KEY to imageUtil.convertBitmapToBase64(
                                                    it.image!!
                                                ),
                                                CUSTOMER_PHONE_NUMBER_KEY to it.phoneNumber,
                                                CUSTOMER_NAME_KEY to it.name
                                            )
                                        )
                                    Log.d("Sync customer", "added ${it.name} on cloud")
                                }
                                kinds.forEach {
                                    cloudDB.collection(USER_DATA_COLLECTION)
                                        .document(mAuth.currentUser!!.uid)
                                        .collection("kind").add(
                                            hashMapOf(
                                                KIND_ID_KEY to it.id,
                                                KIND_DESCRIPTION_KEY to it.description,
                                                KIND_NAME_KEY to it.name,
                                                KIND_IMAGE_KEY to imageUtil.convertBitmapToBase64(it.image!!)
                                            )
                                        )
                                    Log.d("Sync Kinds", "added ${it.name} on cloud")
                                }
                                pets.forEach {
                                    cloudDB.collection(USER_DATA_COLLECTION)
                                        .document(mAuth.currentUser!!.uid)
                                        .collection("pet").add(
                                            hashMapOf(
                                                PET_ID_KEY to it.id,
                                                PET_NAME_KEY to it.name,
                                                PET_KIND_KEY to it.kind,
                                                PET_DETAIL_KEY to it.detail,
                                                PET_IS_SOLD_KEY to it.isSold,
                                                PET_UPDATE_TIME_KEY to it.updateTime,
                                                PET_IMAGE_KEY to imageUtil.convertBitmapToBase64(it.image!!),
                                                PET_PRICE_KEY to it.price,
                                                PET_CUSTOMER_NAME to it.customerName
                                            )
                                        )
                                    Log.d("Sync pet", "pet ${it.name} added on cloud")
                                }
                                GlobalScope.launch {
                                    clearLocalData()
                                    Log.d("local", " local cleared")
                                }
                            }
                    }
            }
    }
}
