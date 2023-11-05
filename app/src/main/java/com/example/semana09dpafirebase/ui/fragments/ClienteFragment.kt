package com.example.semana09dpafirebase.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.semana09dpafirebase.R
import com.example.semana09dpafirebase.database.CustomerEntity

class ClienteFragment : Fragment() {
    private lateinit var viewModel: ClienteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_cliente, container, false)

        val etFirstName: EditText = view.findViewById(R.id.etFirstName)
        val etLastName: EditText = view.findViewById(R.id.etLastName)
        val etPhoneNumber: EditText = view.findViewById(R.id.etPhoneNumber)
        val btnSaveCustomer: Button = view.findViewById(R.id.btnSaveCustomer)

        viewModel = ViewModelProvider(this).get(ClienteViewModel::class.java)

        btnSaveCustomer.setOnClickListener {
            val customerEntity =
                CustomerEntity(etFirstName.text.toString(),
                                etLastName.text.toString(),
                                etPhoneNumber.text.toString())
                viewModel.saveCustomer(customerEntity)
        }

        return view
    }
}