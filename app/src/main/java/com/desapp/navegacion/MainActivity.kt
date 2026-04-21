package com.desapp.navegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                NavigationWrapper()
            }
        }
    }
}

/**
 * 1. Configuración de Navegación
 * Define un grafo de navegación desacoplado.
 */
@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { 
            MainScreen(
                onNavigateToHome = { navController.navigate("home") },
                onNavigateToForm = { navController.navigate("form") }
            ) 
        }
        composable("home") { 
            HomeScreen(onBack = { navController.popBackStack() }) 
        }
        composable("form") { 
            FormScreen(
                onBack = { navController.popBackStack() },
                onSend = { name -> navController.navigate("result/$name") }
            ) 
        }
        composable(
            route = "result/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            ResultScreen(name, onBack = { navController.popBackStack() })
        }
    }
}

/**
 * 2. Pantalla de Inicio (MainScreen)
 * Presenta dos botones centrales: "Home" y "Form".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigateToHome: () -> Unit, onNavigateToForm: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Main Screen") }) }
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
                modifier = Modifier.width(200.dp)
            ) {
                Text("Home")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToForm,
                modifier = Modifier.width(200.dp)
            ) {
                Text("Form")
            }
        }
    }
}

/**
 * 3. Flujo Home (Tabs & Back Navigation)
 * Sistema de 2 Tabs con contenido dinámico y flecha de retorno.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onBack: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Tab 1", "Tab 2")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home Flow") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedTab) {
                    0 -> Text("Hola mundo", style = MaterialTheme.typography.headlineMedium)
                    1 -> Text("Hola Perú", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}

/**
 * 4. Flujo Form (Scaffolding & Data Transfer)
 * Utiliza Scaffold con BottomAppBar como ButtonBar para la acción "Enviar".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(onBack: () -> Unit, onSend: (String) -> Unit) {
    var nameInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulario") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        },
        bottomBar = {
            // Scaffolding - ButtonBar: Área de bottom bar para acción "Enviar"
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { if (nameInput.isNotBlank()) onSend(nameInput) },
                        enabled = nameInput.isNotBlank()
                    ) {
                        Text("Enviar")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                label = { Text("Ingresa tu nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
    }
}

/**
 * Pantalla de Resultado
 * Recupera el argumento y despliega el mensaje final.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(name: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resultado") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hola $name",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
