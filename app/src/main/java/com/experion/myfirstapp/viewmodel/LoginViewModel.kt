package com.experion.myfirstapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var userLiveData = MutableLiveData<String>()
    var passLiveData = MutableLiveData<String>()
    var userNameValue = "Admin"
    var passwordValue = "password@123"
    var loginStateLiveData = MutableLiveData<Boolean>()
    init {
        userLiveData.value=""
        passLiveData.value=""
    }
    fun isValidLoginData(username : String, password: String){
        if(username.isNotEmpty() && password.isNotEmpty()){
            loginStateLiveData.postValue(userNameValue==username && password == passwordValue)
        }
    }
}