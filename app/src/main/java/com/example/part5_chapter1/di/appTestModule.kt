package com.example.part5_chapter1.di

import android.content.Context
import androidx.room.Room
import com.example.part5_chapter1.data.local.db.ToDoDatabase
import com.example.part5_chapter1.data.repository.ToDoRepository
import com.example.part5_chapter1.data.repository.DefaultToDoRepository
import com.example.part5_chapter1.domain.todo.*
import com.example.part5_chapter1.domain.todo.DeleteAllToDoItemUseCase
import com.example.part5_chapter1.domain.todo.DeleteToDoItemUseCase
import com.example.part5_chapter1.domain.todo.GetToDoItemUseCase
import com.example.part5_chapter1.domain.todo.GetToDoListUseCase
import com.example.part5_chapter1.domain.todo.InsertToDoListUseCase
import com.example.part5_chapter1.domain.todo.InsertToDoUseCase
import com.example.part5_chapter1.domain.todo.UpdateToDoUseCase

import com.example.part5_chapter1.presentation.detail.DetailMode
import com.example.part5_chapter1.presentation.list.ListViewModel
import com.example.part5_chapter1.presentation.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel


internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    factory { GetToDoListUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }

    single<ToDoRepository> { DefaultToDoRepository(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }

    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }

}

internal fun provideDB(context: Context): ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()
