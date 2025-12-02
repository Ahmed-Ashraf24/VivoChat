package com.example.vivochat.presentation.viewModel.home_view_model

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vivochat.domain.entity.Contact
import com.example.vivochat.domain.entity.User
import com.example.vivochat.domain.repository.IUserRepository
import com.example.vivochat.presentation.ui.screens.login.components.LoginForm
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepo: IUserRepository,
    private val firebaseAuth: FirebaseAuth,
    private val context: Context
) : ViewModel() {

    lateinit var user: User
    lateinit var availableContacts: List<User>
    lateinit var unAvailableContacts: List<Contact>

    private var _userData = MutableStateFlow<HomeState>(HomeState.Idle)
    val userData: StateFlow<HomeState>
        get() = _userData


    fun getUserData() {
        try {
            _userData.value = HomeState.DataLoading
            val phoneContacts = getContacts(context)
            viewModelScope.launch {
                val userId = firebaseAuth.currentUser!!.uid
                val res = userRepo.getUserData(userId)

                if (res.isSuccess) {
                    user = res.getOrNull()!!

                    val pair = userRepo.filterContacts(phoneContacts)
                    availableContacts = pair.first
                    unAvailableContacts = pair.second
                    _userData.value = HomeState.DataSuccess


                } else {
                    _userData.value = HomeState.DataFailed
                }

            }
        } catch (e: Exception) {
            Log.d("catch the erro", e.toString())
        }
    }


    init {
        getUserData()
    }

    fun getContacts(context: Context): List<Contact> {
        val contactsList = mutableListOf<Contact>()
        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (it.moveToNext()) {
                val name = it.getString(nameIndex)
                val number = it.getString(numberIndex)
                contactsList.add(Contact(name, number))
            }
        }

        return contactsList
    }

}