package com.example.pro1221_android_petshopmanagement.data.data_source.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.pro1221_android_petshopmanagement.domain.model.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Query("select * from table_customer")
    fun getCustomersAsFlow(): Flow<List<Customer>>

    @Query("select * from table_customer")
    fun getCustomerAsList(): List<Customer>

    @Query("delete from table_customer")
    suspend fun deleteAllRecord()

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Insert
    suspend fun addCustomer(customer: Customer)

    @Query("select * from table_customer where id =:id")
    fun getCustomer(id: Int): Customer

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Insert(onConflict = REPLACE)
    suspend fun restoreCustomer(customer: Customer)
}