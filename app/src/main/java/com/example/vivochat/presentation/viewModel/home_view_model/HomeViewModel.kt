package com.example.vivochat.presentation.viewModel.home_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.data.dataSource.local.LocalDataSource
import com.example.vivochat.domain.repository.IUserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepo : IUserRepository,
    private val localDataSource: LocalDataSource
) : ViewModel(){


    fun getUserData(userId : String){
        viewModelScope.launch {
            userRepo.getUserData(userId)
        }
    }

    init {
      viewModelScope.launch {

      }
    }

}