package com.example.pro1221_android_petshopmanagement.presentation.screen.view_model.customer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.use_case.CustomerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerUseCase: CustomerUseCase
) : ViewModel() {
    private val _customerState = mutableStateOf(emptyList<Customer>())
    val customerState: State<List<Customer>> = _customerState

    private var job: Job? = null

    fun onEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.DeleteCustomer -> {
                viewModelScope.launch {
                    customerUseCase.deleteCustomer(customer = event.customer)
                }
            }
            is CustomerEvent.RestoreCustomer -> {
                viewModelScope.launch {
                    customerUseCase.addCustomer(customer = event.customer)
                }
            }
        }
    }

    fun getCustomers() {
        job?.cancel()

        job = customerUseCase.getCustomers().onEach {
            _customerState.value = it
        }.launchIn(viewModelScope)
    }
}