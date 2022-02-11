package com.example.part5_chapter1.presentation.list

import com.example.part5_chapter1.data.entity.ToDoEntity

sealed class ToDoListState {

    object UnInitialized: ToDoListState()

    object Loading: ToDoListState()

    data class Suceess(
        val toDoList: List<ToDoEntity>
    ): ToDoListState()

    object Error: ToDoListState()

}
