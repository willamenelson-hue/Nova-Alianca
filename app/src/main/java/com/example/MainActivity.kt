package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppBottomNavigation
import com.example.ui.components.AppTopBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppRoot()
            }
        }
    }
}

@Composable
fun MainAppRoot(viewModel: AppViewModel = viewModel()) {
    val currentDestination by viewModel.currentDestination.collectAsState()
    val userFeedback by viewModel.userFeedbackMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Notificações / Toasts pelo Snackbar
    LaunchedEffect(userFeedback) {
        if (userFeedback != null) {
            snackbarHostState.showSnackbar(userFeedback!!)
            viewModel.clearFeedback()
        }
    }

    // Gerenciamento de botão "Voltar" (BackHandler)
    BackHandler(
        enabled = currentDestination !in listOf(
            AppDestination.HOME,
            AppDestination.BIBLE,
            AppDestination.HYMNS,
            AppDestination.PRAYER,
            AppDestination.MORE
        )
    ) {
        viewModel.navigateTo(AppDestination.MORE)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                onNavigateBack = {
                    when (currentDestination) {
                        AppDestination.GIVING,
                        AppDestination.PLANS,
                        AppDestination.AGENDA,
                        AppDestination.STUDY_MAPS,
                        AppDestination.QUIZ,
                        AppDestination.MY_NOTES -> viewModel.navigateTo(AppDestination.MORE)
                        else -> viewModel.navigateTo(AppDestination.HOME)
                    }
                }
            )
        },
        bottomBar = {
            AppBottomNavigation(
                currentDestination = currentDestination,
                onNavigate = { dest -> viewModel.navigateTo(dest) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Crossfade(
            targetState = currentDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            label = "ScreenTransition"
        ) { destination ->
            when (destination) {
                AppDestination.HOME -> HomeScreen(viewModel = viewModel)
                AppDestination.BIBLE -> BibleReaderScreen(viewModel = viewModel)
                AppDestination.HYMNS -> HymnsScreen(viewModel = viewModel)
                AppDestination.PRAYER -> PrayerWallScreen(viewModel = viewModel)
                AppDestination.MORE -> MoreMenuScreen(viewModel = viewModel)
                AppDestination.GIVING -> GivingScreen(viewModel = viewModel)
                AppDestination.PLANS -> ReadingPlansScreen(viewModel = viewModel)
                AppDestination.AGENDA -> ChurchAgendaScreen(viewModel = viewModel)
                AppDestination.STUDY_MAPS -> BibleStudyScreen(viewModel = viewModel)
                AppDestination.QUIZ -> BibleStudyScreen(viewModel = viewModel)
                AppDestination.MY_NOTES -> MyNotesScreen(viewModel = viewModel)
            }
        }
    }
}
