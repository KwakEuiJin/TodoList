package com.example.part5_chapter1.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.part5_chapter1.data.entity.ToDoEntity
import com.example.part5_chapter1.domain.todo.DeleteToDoItemUseCase
import com.example.part5_chapter1.domain.todo.GetToDoItemUseCase
import com.example.part5_chapter1.domain.todo.InsertToDoUseCase
import com.example.part5_chapter1.domain.todo.UpdateToDoUseCase
import com.example.part5_chapter1.presentation.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.Exception

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id: Long = -1,
    private val getToDoItemUseCase: GetToDoItemUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val updateToDoUseCase: UpdateToDoUseCase,
    private val insertToDoUseCase: InsertToDoUseCase
) : BaseViewModel() {

    private var _toDoDetailLiveData =
        MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialized)
    val toDoDetailLiveData: LiveData<ToDoDetailState> = _toDoDetailLiveData

    override fun fetchData() = viewModelScope.launch {
        when (detailMode) {
            DetailMode.WRITE -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Write)
            }
            DetailMode.DETAIL -> {
                _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
                try {
                    getToDoItemUseCase(id)?.let {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(it))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }

    fun deleteToDo() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        try {
            deleteToDoItemUseCase(id)
            _toDoDetailLiveData.postValue(ToDoDetailState.Delete)
        } catch (e: Exception) {
            e.printStackTrace()
            _toDoDetailLiveData.postValue(ToDoDetailState.Error)
        }
    }

    fun setModifyMode() = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Modify)
    }

    fun writeToDo(title: String, description: String) = viewModelScope.launch {
        _toDoDetailLiveData.postValue(ToDoDetailState.Loading)
        when (detailMode) {
            DetailMode.WRITE -> {
                try {
                    val toDoEntity = ToDoEntity(title = title, description = description)
                    id = insertToDoUseCase(toDoEntity)
                    _toDoDetailLiveData.postValue(ToDoDetailState.Success(toDoEntity))
                    detailMode = DetailMode.DETAIL
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
            DetailMode.DETAIL -> {
                try {
                    getToDoItemUseCase(id)?.let {
                        val updateToDoEntity = it.copy(title = title, description = description)
                        updateToDoUseCase(updateToDoEntity)
                        _toDoDetailLiveData.postValue(ToDoDetailState.Success(updateToDoEntity))
                    } ?: kotlin.run {
                        _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _toDoDetailLiveData.postValue(ToDoDetailState.Error)
                }
            }
        }
    }

}
