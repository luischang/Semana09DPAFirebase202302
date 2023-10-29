package com.example.semana09dpafirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semana09dpafirebase.R
import com.example.semana09dpafirebase.model.ProductModel
import com.squareup.picasso.Picasso

class ProductAdapter(private var lstProducts: List<ProductModel>)
    : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        {
            val tvProductDesc: TextView = itemView.findViewById(R.id.tvProductDesc)
            val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
            val ivProduct: ImageView = itemView.findViewById(R.id.ivProduct)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun getItemCount(): Int {
        return lstProducts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemProduct = lstProducts[position]
        holder.tvProductDesc.text = itemProduct.decription
        holder.tvProductPrice.text = itemProduct.price
        Picasso.get().load(itemProduct.imageUrl).into(holder.ivProduct)
    }

}