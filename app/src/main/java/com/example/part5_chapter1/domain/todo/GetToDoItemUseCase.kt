package com.example.part5_chapter1.domain.todo

import com.example.part5_chapter1.data.entity.ToDoEntity
import com.example.part5_chapter1.data.repository.ToDoRepository
import com.example.part5_chapter1.domain.UseCase

internal class GetToDoItemUseCase(
    private val toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(id: Long): ToDoEntity? {
        return toDoRepository.getToDoItem(id)
    }

}
