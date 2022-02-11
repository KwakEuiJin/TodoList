package com.example.part5_chapter1.domain.todo

import com.example.part5_chapter1.data.repository.ToDoRepository
import com.example.part5_chapter1.domain.UseCase

internal class DeleteToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(id: Long) {
        return toDoRepository.deleteToDoItem(id)
    }

}
