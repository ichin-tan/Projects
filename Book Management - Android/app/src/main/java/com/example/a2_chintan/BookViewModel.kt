package com.example.a2_chintan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private var bookDao : BookDao = BookDatabase.getDatabase(application).bookDao()
    val allBooks: Flow<List<BookEntity>> = bookDao.getAllBooks()

    fun insert(book: BookEntity) {
        viewModelScope.launch {
            bookDao.insertBook(book)
        }
    }

    fun update(book: BookEntity) {
        viewModelScope.launch {
            bookDao.updateBook(book)
        }
    }

    fun delete(book: BookEntity) {
        viewModelScope.launch {
            bookDao.deleteBook(book)
        }
    }

    fun getBookById(id: Int): Flow<BookEntity> {
        return bookDao.getBookById(id)
    }
}
