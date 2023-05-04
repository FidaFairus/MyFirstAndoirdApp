package com.experion.myfirstapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var userLiveData = MutableLiveData<String>()
    var emailLiveData=MutableLiveData<String>()
    var passLiveData = MutableLiveData<String>()
    var userNameValue = "Admin"
    var emailValue="admin@gmail.com"
    var passwordValue = "password@123"
    var loginStateLiveData = MutableLiveData<Boolean>()
    init {
        emailLiveData.value=""
        passLiveData.value=""
    }
    fun isValidLoginData(email : String, password: String){
        if(email.isNotEmpty() && password.isNotEmpty()){
            loginStateLiveData.postValue(emailValue==email && password == passwordValue)
        }
    }
}