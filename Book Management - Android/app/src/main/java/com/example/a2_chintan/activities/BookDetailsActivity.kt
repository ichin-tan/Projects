package com.example.a2_chintan.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.a2_chintan.BookEntity
import com.example.a2_chintan.BookViewModel
import com.example.a2_chintan.databinding.ActivityBookDetailsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class BookDetailsActivity : AppCompatActivity() {

    private val bookViewModel: BookViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bookId = intent.getIntExtra("BOOK_ID", -1)
        if (bookId == -1) {
            Snackbar.make(binding.root, "Invalid Book ID!", Snackbar.LENGTH_LONG).show()
            finish()
            return
        }

        lifecycleScope.launch {
            bookViewModel.getBookById(bookId).collect { book ->
                binding.etBookTitle.setText(book.title)
                binding.etBookAuthor.setText(book.author)
                binding.etBookPrice.setText(book.price.toString())
                binding.etBookQuantity.setText(book.quantity.toString())
            }
        }


        binding.btnUpdate.setOnClickListener {
            this.updateBook()
        }
    }

    fun updateBook() {
        val title = binding.etBookTitle.text.toString()
        val author = binding.etBookAuthor.text.toString()
        val price = binding.etBookPrice.text.toString()
        val quantity = binding.etBookQuantity.text.toString()

        if (title.isEmpty() || author.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
            Snackbar.make(binding.root, "Please fill in all fields!", Snackbar.LENGTH_LONG).show()
            return
        }

        val updatedBook = BookEntity(
            id = intent.getIntExtra("BOOK_ID", -1),
            title = title,
            author = author,
            price = price.toDouble(),
            quantity = quantity.toInt()
        )

        bookViewModel.update(updatedBook)
        Snackbar.make(binding.root, "Book Updated Successfully!", Snackbar.LENGTH_LONG).show()


        var quan = intent.getIntExtra("quantity", 0)
        quan.toString()
    }
}