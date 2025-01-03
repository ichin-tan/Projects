package com.example.a2_chintan.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2_chintan.BookEntity
import com.example.a2_chintan.databinding.BookRowBinding
import com.example.a2_chintan.interfaces.ClickDetectorInterface

class BookAdapter(var myItems:List<BookEntity>, val clickInterface: ClickDetectorInterface) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: BookRowBinding) : RecyclerView.ViewHolder (binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BookRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book: BookEntity = myItems.get(position)

        holder.binding.tvBookTitle.text = "Title:- ${book.title}"
        holder.binding.tvBookAuthor.text = "Author:- ${book.author}"
        holder.binding.tvBookPrice.text = "Price:- $${book.price.toString()}"
        holder.binding.tvBookQuantity.text = "Quantity:- ${book.quantity.toString()}"

        holder.binding.btnDelete.setOnClickListener {
            clickInterface.deleteBook(position)
        }

        holder.binding.btnUpdate.setOnClickListener {
            clickInterface.updateBook(position)
        }

        holder.binding.mainRow.setOnClickListener {
            clickInterface.showBookDetails(position)
        }
    }
}