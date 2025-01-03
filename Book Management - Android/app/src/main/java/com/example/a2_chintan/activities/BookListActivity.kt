package com.example.a2_chintan.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2_chintan.BookEntity
import com.example.a2_chintan.BookViewModel
import com.example.a2_chintan.adapters.BookAdapter
import com.example.a2_chintan.databinding.ActivityBookListBinding
import com.example.a2_chintan.interfaces.ClickDetectorInterface
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class BookListActivity : AppCompatActivity(), ClickDetectorInterface {

    private lateinit var binding: ActivityBookListBinding
    var arrBooks: MutableList<BookEntity> = mutableListOf()
    lateinit var adapter: BookAdapter
    private val bookViewModel: BookViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.adapter = BookAdapter(this.arrBooks, this)
        this.binding.recyclerViewBooks.adapter = adapter
        this.binding.recyclerViewBooks.layoutManager = LinearLayoutManager(this)
        this.binding.recyclerViewBooks.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        this.getAndShowAllBooks()
    }

    fun getAndShowAllBooks() {
        lifecycleScope.launch {
            bookViewModel.allBooks.collect { books ->
                books.let {
                    arrBooks.clear()
                    arrBooks.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun deleteBook(position: Int) {
        val bookToDelete = arrBooks[position]
        bookViewModel.delete(bookToDelete)
        Snackbar.make(binding.root, "Book Deleted Successfully", Snackbar.LENGTH_LONG).show()
    }

    override fun updateBook(position: Int) {
        val bookToUpdate = arrBooks[position]
        val intent = Intent(this, BookDetailsActivity::class.java)
        intent.putExtra("BOOK_ID", bookToUpdate.id)
        startActivity(intent)
    }

    override fun showBookDetails(position: Int) {
        val bookToUpdate = arrBooks[position]
        val intent = Intent(this, BookDetailsActivity::class.java)
        intent.putExtra("BOOK_ID", bookToUpdate.id)
        startActivity(intent)
    }
}