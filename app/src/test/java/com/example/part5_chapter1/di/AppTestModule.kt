package com.example.part5_chapter1.di

import com.example.part5_chapter1.repository.TestToDoRepository
import com.example.part5_chapter1.domain.todo.GetToDoItemUseCase
import com.example.part5_chapter1.data.repository.ToDoRepository
import com.example.part5_chapter1.domain.todo.*
import com.example.part5_chapter1.presentation.detail.DetailMode
import com.example.part5_chapter1.presentation.detail.DetailViewModel
import com.example.part5_chapter1.presentation.list.ListViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

internal val appTestModule = module {

    factory { GetToDoListUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }

    single<ToDoRepository> { TestToDoRepository() }

    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }

}
