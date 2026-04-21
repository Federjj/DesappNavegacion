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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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
            val sfFontFamily = FontFamily.SansSerif
            val typography = Typography(
                displayLarge = TextStyle(fontFamily = sfFontFamily),
                displayMedium = TextStyle(fontFamily = sfFontFamily),
                displaySmall = TextStyle(fontFamily = sfFontFamily),
                headlineLarge = TextStyle(fontFamily = sfFontFamily),
                headlineMedium = TextStyle(fontFamily = sfFontFamily),
                headlineSmall = TextStyle(fontFamily = sfFontFamily),
                titleLarge = TextStyle(fontFamily = sfFontFamily),
                titleMedium = TextStyle(fontFamily = sfFontFamily),
                titleSmall = TextStyle(fontFamily = sfFontFamily),
                bodyLarge = TextStyle(fontFamily = sfFontFamily),
                bodyMedium = TextStyle(fontFamily = sfFontFamily),
                bodySmall = TextStyle(fontFamily = sfFontFamily),
                labelLarge = TextStyle(fontFamily = sfFontFamily),
                labelMedium = TextStyle(fontFamily = sfFontFamily),
                labelSmall = TextStyle(fontFamily = sfFontFamily)
            )
            MaterialTheme(
                colorScheme = darkColorScheme(),
                typography = typography
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationWrapper()
                }
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
            TabRow(
                selectedTabIndex = selectedTab,
                contentColor = Color(0xFF87CEEB)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) },
                        selectedContentColor = Color(0xFF87CEEB),
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
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
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onBack,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF87CEEB))
                    ) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { if (nameInput.isNotBlank()) onSend(nameInput) },
                        enabled = nameInput.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF87CEEB),
                            contentColor = Color.Black,
                            disabledContainerColor = Color(0xFF87CEEB).copy(alpha = 0.5f),
                            disabledContentColor = Color.Black.copy(alpha = 0.5f)
                        )
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
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF87CEEB),
                    focusedLabelColor = Color(0xFF87CEEB),
                    cursorColor = Color(0xFF87CEEB)
                )
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
