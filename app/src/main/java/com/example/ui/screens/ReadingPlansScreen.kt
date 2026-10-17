package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PreloadedData
import com.example.data.model.ReadingPlan
import com.example.data.model.ReadingTheme
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.WarmGoldContainer
import com.example.ui.theme.WarmGoldDark
import com.example.ui.theme.WarmGoldPrimary
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.AppViewModel

@Composable
fun ReadingPlansScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0 = Planos Estruturados, 1 = Temas do Cotidiano
    val completedDays by viewModel.bibleRepository.completedPlanDays.collectAsState()

    var activePlanDetail by remember { mutableStateOf<ReadingPlan?>(null) }
    var activeThemeDetail by remember { mutableStateOf<ReadingTheme?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("reading_plans_screen")
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = RoyalBluePrimary,
            contentColor = Color.White
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Planos de Leitura", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Temas do Cotidiano", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            )
        }

        if (selectedTab == 0) {
            // Planos Estruturados com Metas de Leitura e Checkbox
            if (activePlanDetail == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "Metas e Cronogramas de Leitura",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBluePrimary
                        )
                        Text(
                            text = "Acompanhe seus capítulos diários e marque cada meta concluída.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(PreloadedData.structuredReadingPlans) { plan ->
                        val completedCount = plan.items.count { item ->
                            viewModel.bibleRepository.isPlanDayCompleted(plan.id, item.dayNumber)
                        }
                        val progressPercent = (completedCount.toFloat() / plan.items.size.toFloat()).coerceIn(0f, 1f)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { activePlanDetail = plan }
                                .testTag("plan_card_${plan.id}"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = plan.title,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = RoyalBluePrimary
                                        )
                                        Text(
                                            text = plan.subtitle,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Surface(
                                        color = WarmGoldContainer,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "${plan.durationDays} dias",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = WarmGoldDark,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Barra de Progresso
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "$completedCount de ${plan.items.size} lidos",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "${(progressPercent * 100).toInt()}%",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = WarmGoldDark
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                LinearProgressIndicator(
                                    progress = { progressPercent },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp),
                                    color = WarmGoldPrimary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Text(
                                        text = "Abrir Checklist de Capítulos >",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = RoyalBluePrimary
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // Detalhe do Plano com Checkbox por Capítulo/Dia
                val plan = activePlanDetail!!
                Column(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        color = RoyalBluePrimary.copy(alpha = 0.08f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { activePlanDetail = null }) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = plan.title,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = RoyalBluePrimary
                                )
                                Text(
                                    text = "Marque cada capítulo lido no checkbox",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(plan.items) { item ->
                            val isChecked = viewModel.bibleRepository.isPlanDayCompleted(plan.id, item.dayNumber)

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("plan_item_${plan.id}_${item.dayNumber}"),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isChecked) WarmGoldContainer.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = {
                                            viewModel.bibleRepository.togglePlanDay(plan.id, item.dayNumber)
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.readingTarget,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (isChecked) WarmGoldDark else RoyalBluePrimary
                                        )
                                        Text(
                                            text = item.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Aba de Temas do Cotidiano Humano
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = "Temas do Cotidiano sob a Luz Bíblica",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Text(
                        text = "Passagens selecionadas para momentos de dúvida, ansiedade, família e decisões.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                items(PreloadedData.readingThemes) { theme ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { activeThemeDetail = theme }
                            .testTag("theme_card_${theme.id}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = when (theme.id) {
                                        "theme_peace" -> Icons.Default.Spa
                                        "theme_family" -> Icons.Default.FamilyRestroom
                                        "theme_finances" -> Icons.Default.Savings
                                        else -> Icons.Default.Favorite
                                    },
                                    contentDescription = null,
                                    tint = WarmGoldDark,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = theme.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = RoyalBluePrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = theme.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "${theme.passages.size} passagens temáticas curadas >",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = WarmGoldDark
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal com as passagens do tema do cotidiano selecionado
    if (activeThemeDetail != null) {
        val theme = activeThemeDetail!!
        AlertDialog(
            onDismissRequest = { activeThemeDetail = null },
            title = {
                Text(
                    text = theme.title,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
            },
            text = {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(theme.passages) { passage ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = passage.reference,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = WarmGoldDark
                                )
                                Text(
                                    text = "“${passage.text}”",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Reflexão: ${passage.devotionalReflection}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { activeThemeDetail = null }) {
                    Text("Concluir")
                }
            }
        )
    }
}
