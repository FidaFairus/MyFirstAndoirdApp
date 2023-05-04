package com.experion.myfirstapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.experion.myfirstapp.ui.theme.MyfirstappTheme
import com.experion.myfirstapp.viewmodel.LoginViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyfirstappTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LoginPage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(modifier: Modifier = Modifier, loginViewModel: LoginViewModel = LoginViewModel()) {


    Column( modifier.padding(40.dp),
        verticalArrangement = Arrangement.Center,
    )

    {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        val loginState by loginViewModel.loginStateLiveData.observeAsState(false)
        var isEmailInvalid by remember { mutableStateOf(false) }
        var isLoginSuccess by remember { mutableStateOf(false)}
        var isLoginFailed by remember { mutableStateOf(false)}
        Image(painter = painterResource(id = R.drawable.person), contentDescription = "person",
        modifier= Modifier
            .size(100.dp)
            .clip(CircleShape)
            .align(CenterHorizontally)
        )
        Text(
            text="Login",
            style= TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.W800,
                fontFamily = FontFamily.Serif

            ),
            modifier=Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Email",
            modifier = modifier,
            style= TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                letterSpacing = 0.1.em,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            singleLine = true,
            onValueChange = { newText ->
                email = newText
                isEmailInvalid=false
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            label = {Text(text="Enter valid Email")},
            modifier=Modifier.border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
            shape = RoundedCornerShape(4.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent, // Add this line to remove underline
                errorIndicatorColor = Color.Red // Optional: set error indicator color
            ),

            )
        if (isEmailInvalid) {
            Text(
                text = "Invalid email format",
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Password",
            modifier = modifier,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            letterSpacing = 0.1.em
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
            },
            shape = RoundedCornerShape(4.dp),
            label = {Text(text="Enter your Password")},
            modifier=Modifier.border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent, // Add this line to remove underline
                errorIndicatorColor = Color.Red // Optional: set error indicator color
            ),

            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(painter = if(passwordVisible) painterResource(id = R.drawable.visibility) else painterResource(
                        id = R.drawable.visibility_off),
                        contentDescription =if (passwordVisible)"Show Password" else "Hide Password",
                        modifier= Modifier.size(24.dp)
                    )
                }
            }

        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = {
            if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches()){

                    isEmailInvalid = true // Set the error flag when the email is invalid
                    return@Button

            }
            loginViewModel.isValidLoginData(email, password)
            if (loginState) {
                isLoginSuccess = true
            } else {
                isLoginFailed = true
            }
        },

            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        )
        {
            Text(text = "Login",
                style= TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W800,
                    letterSpacing = 0.3.em
                )
            )

        }
        val context = LocalContext.current
        if(isLoginSuccess){
            Toast.makeText(context,"Login Successfull", Toast.LENGTH_SHORT).show()
        }
//        else{
//            Text(text = "Invalid username or password")
//        }




    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyfirstappTheme {
        LoginPage()
    }
}