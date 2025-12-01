package com.example.vivochat.presentation.viewModel.home_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.data.dataSource.local.LocalDataSource
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepo: IUserRepository,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    lateinit var user: User


    private var _userData = MutableStateFlow<HomeState>(HomeState.Idle)
    val userData : StateFlow<HomeState>
        get() = _userData
    fun getUserData() {
        _userData.value = HomeState.UserDataLoading
        viewModelScope.launch {
            val userId = localDataSource.getUserId()
            val res = userRepo.getUserData(userId!!)

            if(res.isSuccess){
             user = res.getOrNull()!!
                _userData.value = HomeState.UserDataSuccess
            }else{
                _userData.value = HomeState.UserDataFailed
            }
        }
    }

    init {
        getUserData()
    }

}