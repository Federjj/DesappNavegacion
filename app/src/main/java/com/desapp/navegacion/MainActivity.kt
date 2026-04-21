package com.desapp.navegacion

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        onNavigateToHome = {
                            startActivity(Intent(this, HomeActivity::class.java))
                        },
                        onNavigateToForm = {
                            startActivity(Intent(this, FormActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigateToHome: () -> Unit, onNavigateToForm: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Pantalla principal") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onNavigateToHome,
                modifier = Modifier.width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF87CEEB),
                    contentColor = Color.Black
                )
            ) {
                Text("Home")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToForm,
                modifier = Modifier.width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF87CEEB),
                    contentColor = Color.Black
                )
            ) {
                Text("Form")
            }
        }
    }
}
