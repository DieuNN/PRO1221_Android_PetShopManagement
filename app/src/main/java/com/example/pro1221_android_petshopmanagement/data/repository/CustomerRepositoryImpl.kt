package com.example.pro1221_android_petshopmanagement.data.repository

import com.example.pro1221_android_petshopmanagement.data.data_source.dao.CustomerDao
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import com.example.pro1221_android_petshopmanagement.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class CustomerRepositoryImpl(private val customerDao: CustomerDao) : CustomerRepository {

    override suspend fun updateCustomer(customer: Customer) {
        customerDao.updateCustomer(customer = customer)
    }

    override suspend fun addCustomer(customer: Customer) {
        customerDao.addCustomer(customer = customer)
    }

    override fun getCustomer(): Flow<List<Customer>> {
        return customerDao.getCustomers()
    }

    override  fun getCustomer(id: Int): Customer {
        return customerDao.getCustomer(id = id)
    }

    override suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer = customer)
    }

    override suspend fun restoreCustomer(customer: Customer) {
        customerDao.restoreCustomer(customer = customer)
    }
}