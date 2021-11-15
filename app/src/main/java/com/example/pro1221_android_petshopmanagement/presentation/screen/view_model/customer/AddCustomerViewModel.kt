package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.pro1221_android_petshopmanagement.domain.use_case.CustomerUseCase
import com.example.pro1221_android_petshopmanagement.presentation.util.AddCustomerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro1221_android_petshopmanagement.domain.model.Pet
import com.example.pro1221_android_petshopmanagement.domain.use_case.PetUseCases
import com.example.pro1221_android_petshopmanagement.presentation.util.AddPetEvent
import kotlinx.coroutines.launch

@HiltViewModel
class AddCustomerViewModel @Inject constructor(
    private val customerUseCase: CustomerUseCase
) : ViewModel() {
    private val _name = mutableStateOf("")
    var name: State<String> = _name

    private val _address = mutableStateOf("")
    var address: State<String> = _name

    private val _phoneNumber = mutableStateOf("")
    var phoneNumber: State<String> = _phoneNumber

    private val _image: MutableState<Bitmap?> = mutableStateOf(null)
    var image: State<Bitmap?> = _image

    suspend fun onEvent(event: AddCustomerEvent) {
        when (event) {
            is AddCustomerEvent.EnteredAddress -> {
                _address.value = event.address
            }
            is AddCustomerEvent.EnteredImage -> {
                _image.value = event.image
            }
            is AddCustomerEvent.EnteredName -> {
                _name.value = event.name
            }
            is AddCustomerEvent.EnteredPhoneNumber -> {
                _phoneNumber.value = event.phoneNumber
            }
            is AddCustomerEvent.SaveCustomer -> {
                viewModelScope.launch {
                    customerUseCase.addCustomer(
                        Customer(
                            id = null,
                            image = image.value,
                            name = name.value,
                            address = address.value,
                            phoneNumber = phoneNumber.value
                        )
                    )
                }
            }
        }
    }
}