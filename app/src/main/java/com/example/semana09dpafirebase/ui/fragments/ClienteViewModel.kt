package com.example.semana09dpafirebase.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.semana09dpafirebase.database.CustomerEntity
import com.example.semana09dpafirebase.database.CustomerRepository

class ClienteViewModel(application: Application): AndroidViewModel(application) {
    private var repository = CustomerRepository(application)
    val customers = repository.getCustomer()

    fun saveCustomer(customerEntity: CustomerEntity){
        repository.insert(customerEntity)
    }
}