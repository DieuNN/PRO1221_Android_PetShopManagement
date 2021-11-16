package com.example.pro1221_android_petshopmanagement.ui.model

import android.content.Context
import android.graphics.BitmapFactory
import com.example.pro1221_android_petshopmanagement.R
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.model.Kind
import com.example.pro1221_android_petshopmanagement.domain.model.Pet

class FakeDataReposotory {
    companion object {
        fun getCustomer(ctx: Context): MutableList<Customer> {
            return mutableListOf(
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ), Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                ),
                Customer(
                    id = 0, name = "DieuNN",
                    address = "YB",
                    image = BitmapFactory.decodeResource(
                        ctx.resources, R.drawable.manuel_vivo
                    ),
                    phoneNumber = "0965343641"
                )
            )
        }


        fun getKinds(ctx: Context): MutableList<Kind> {
            return mutableListOf(
                Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ),
                Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ),
                Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ), Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ), Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ), Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ), Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ), Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ), Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                ), Kind(
                    id = 0,
                    name = "Doge",
                    image = null
                )
            )
        }
    }


}