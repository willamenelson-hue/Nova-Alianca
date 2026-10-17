package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.WarmGoldDark
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.AppViewModel

@Composable
fun MoreMenuScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val menuItems = listOf(
        MenuItem("Dízimos e Ofertas", "Contribuições seguras via Pix, chaves e recibos", Icons.Default.QrCode, AppDestination.GIVING),
        MenuItem("Planos de Leitura", "Metas com checklist e temas do cotidiano", Icons.Default.Checklist, AppDestination.PLANS),
        MenuItem("Agenda & Cultos", "Horários de cultos, ministérios e lembretes", Icons.Default.Event, AppDestination.AGENDA),
        MenuItem("Mapas Bíblicos", "Locais históricos com o Google Maps", Icons.Default.Map, AppDestination.STUDY_MAPS),
        MenuItem("Quiz Bíblico", "Perguntas e respostas por níveis", Icons.Default.Psychology, AppDestination.QUIZ),
        MenuItem("Minhas Anotações", "Versículos marcados e reflexões salvas", Icons.Default.EditNote, AppDestination.MY_NOTES)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("more_menu_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = RoyalBluePrimary.copy(alpha = 0.08f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Igreja Batista Nova Aliança",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Text(
                        text = "Recursos adicionais para o crescimento espiritual da sua família.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(menuItems.size) { index ->
            val item = menuItems[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.navigateTo(item.destination) }
                    .testTag("menu_item_${item.destination.name.lowercase()}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = WarmGoldDark,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

private data class MenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val destination: AppDestination
)
