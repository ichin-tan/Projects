package com.example.a2_chintan.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a2_chintan.BookEntity
import com.example.a2_chintan.BookViewModel
import com.example.a2_chintan.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val bookViewModel: BookViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            this.addBook()
        }

        binding.btnViewAll.setOnClickListener {
            val intent = Intent(this, BookListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addBook() {
        val title = binding.etBookTitle.text.toString().trim()
        val author = binding.etBookAuthor.text.toString().trim()
        val price = binding.etBookPrice.text.toString().trim()
        val quantity = binding.etBookQuantity.text.toString().trim()

        if (title.isEmpty() || author.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
            Snackbar.make(binding.root, "Please fill in all fields!", Snackbar.LENGTH_LONG).show()
            return
        }

        val book = BookEntity(
            title = title,
            author = author,
            price = price.toDouble(),
            quantity = quantity.toInt()
        )

        bookViewModel.insert(book)
        Snackbar.make(binding.root, "Book Added Successfully!!", Snackbar.LENGTH_LONG).show()

        clearFields()
    }

    private fun clearFields() {
        binding.etBookTitle.text.clear()
        binding.etBookAuthor.text.clear()
        binding.etBookPrice.text.clear()
        binding.etBookQuantity.text.clear()
    }
}
