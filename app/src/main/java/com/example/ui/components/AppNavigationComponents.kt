package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.WarmGoldPrimary
import com.example.ui.viewmodel.AppDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentDestination: AppDestination,
    onNavigateBack: () -> Unit,
    onActionClick: (() -> Unit)? = null
) {
    val title = when (currentDestination) {
        AppDestination.HOME -> stringResource(R.string.church_full_name)
        AppDestination.BIBLE -> "Bíblia Sagrada"
        AppDestination.HYMNS -> "Hinários & Louvores"
        AppDestination.PRAYER -> "Mural de Oração"
        AppDestination.MORE -> "Mais Opções"
        AppDestination.GIVING -> "Dízimos e Ofertas"
        AppDestination.PLANS -> "Planos de Leitura"
        AppDestination.AGENDA -> "Agenda & Cultos"
        AppDestination.STUDY_MAPS -> "Mapas Bíblicos"
        AppDestination.QUIZ -> "Quiz Bíblico"
        AppDestination.MY_NOTES -> "Minhas Anotações"
    }

    val isSubScreen = currentDestination !in listOf(
        AppDestination.HOME,
        AppDestination.BIBLE,
        AppDestination.HYMNS,
        AppDestination.PRAYER,
        AppDestination.MORE
    )

    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.3.sp
                    ),
                    maxLines = 1
                )
                if (currentDestination == AppDestination.HOME) {
                    Text(
                        text = "Igreja Batista Nova Aliança",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = WarmGoldPrimary
                        )
                    )
                }
            }
        },
        navigationIcon = {
            if (isSubScreen) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Church,
                    contentDescription = "Logo IBNA",
                    tint = WarmGoldPrimary,
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                )
            }
        },
        actions = {
            if (onActionClick != null) {
                IconButton(
                    onClick = onActionClick,
                    modifier = Modifier.testTag("top_bar_action_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Informações"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = RoyalBluePrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = WarmGoldPrimary
        ),
        modifier = Modifier.fillMaxWidth().testTag("app_top_bar")
    )
}

@Composable
fun AppBottomNavigation(
    currentDestination: AppDestination,
    onNavigate: (AppDestination) -> Unit
) {
    val mainTabs = listOf(
        Triple(AppDestination.HOME, "Início", Icons.Default.Home),
        Triple(AppDestination.BIBLE, "Bíblia", Icons.Default.MenuBook),
        Triple(AppDestination.HYMNS, "Hinários", Icons.Default.MusicNote),
        Triple(AppDestination.PRAYER, "Orações", Icons.Default.VolunteerActivism),
        Triple(AppDestination.MORE, "Mais", Icons.Default.GridView)
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("app_bottom_nav"),
        tonalElevation = 8.dp
    ) {
        mainTabs.forEach { (dest, label, icon) ->
            val isSelected = when (dest) {
                AppDestination.HOME -> currentDestination == AppDestination.HOME
                AppDestination.BIBLE -> currentDestination == AppDestination.BIBLE
                AppDestination.HYMNS -> currentDestination == AppDestination.HYMNS
                AppDestination.PRAYER -> currentDestination == AppDestination.PRAYER
                AppDestination.MORE -> currentDestination in listOf(
                    AppDestination.MORE,
                    AppDestination.GIVING,
                    AppDestination.PLANS,
                    AppDestination.AGENDA,
                    AppDestination.STUDY_MAPS,
                    AppDestination.QUIZ,
                    AppDestination.MY_NOTES
                )
                else -> false
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(dest) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label
                    )
                },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = RoyalBluePrimary,
                    selectedTextColor = RoyalBluePrimary,
                    indicatorColor = WarmGoldPrimary.copy(alpha = 0.35f)
                ),
                modifier = Modifier.testTag("nav_tab_${dest.name.lowercase()}")
            )
        }
    }
}
