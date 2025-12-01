package com.example.vivochat.presentation.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.vivochat.data.repository.UserRepository
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.viewModel.login_view_model.LoginViewModel
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupViewModel
import com.example.vivochat.presentation.viewModel.signup_view_model.SignupViewModelFac
import com.google.firebase.firestore.FirebaseFirestore

object ViewModelProviderHolder {
    val userRepo : IUserRepository = UserRepository(FirebaseFirestore.getInstance())
    fun getSignupViewModel(viewModelStoreOwner: ViewModelStoreOwner): SignupViewModel{
        val viewModelFac = SignupViewModelFac(userRepo)
        val viewModel = ViewModelProvider(viewModelStoreOwner,viewModelFac).get(SignupViewModel::class.java)
        return viewModel
    }

    fun getLoginViewModel(viewModelStoreOwner: ViewModelStoreOwner): LoginViewModel{
        val viewModel = ViewModelProvider(viewModelStoreOwner).get(LoginViewModel::class.java)
        return viewModel
    }

}