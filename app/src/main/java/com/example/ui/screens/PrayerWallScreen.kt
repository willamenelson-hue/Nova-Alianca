package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.data.model.PrayerRequest
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.WarmGoldContainer
import com.example.ui.theme.WarmGoldDark
import com.example.ui.theme.WarmGoldPrimary
import com.example.ui.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayerWallScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val prayerRequests by viewModel.churchRepository.prayerRequests.collectAsState()
    var selectedCategoryFilter by remember { mutableStateOf("Todos") }
    var showNewPrayerDialog by remember { mutableStateOf(false) }

    // Campos do formulário
    var authorNameInput by remember { mutableStateOf("") }
    var prayerTextInput by remember { mutableStateOf("") }
    var selectedCategoryInput by remember { mutableStateOf("Saúde") }
    var isPrivateToPastorInput by remember { mutableStateOf(false) }

    val categories = listOf("Todos", "Saúde", "Família", "Emprego & Futuro", "Agradecimento", "Espiritual")

    val filteredList = prayerRequests.filter { req ->
        if (selectedCategoryFilter == "Todos") true else req.category.contains(selectedCategoryFilter, ignoreCase = true)
    }

    Scaffold(
        modifier = modifier.testTag("prayer_wall_screen"),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showNewPrayerDialog = true },
                containerColor = RoyalBluePrimary,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, contentDescription = null, tint = WarmGoldPrimary) },
                text = { Text("Pedir Oração") },
                modifier = Modifier.testTag("add_prayer_fab")
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Cabeçalho de Intercessão
            Surface(
                color = RoyalBluePrimary.copy(alpha = 0.08f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Mural de Oração Comunitário",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Text(
                        text = "“Orai uns pelos outros, para que sareis. A oração feita por um justo pode muito em seus efeitos.” (Tiago 5:16)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Filtros de Categoria
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    FilterChip(
                        selected = selectedCategoryFilter == cat,
                        onClick = { selectedCategoryFilter = cat },
                        label = { Text(cat, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = RoyalBluePrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Lista de Pedidos de Oração
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp, top = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList) { req ->
                    PrayerCardItem(
                        request = req,
                        onPrayClick = {
                            viewModel.churchRepository.incrementPrayerCount(req.id)
                            viewModel.showFeedback(
                                if (!req.hasUserPrayed) "Você se uniu em oração por este motivo!" else "Oração desmarcada"
                            )
                        }
                    )
                }
            }
        }
    }

    // Modal de Novo Pedido de Oração
    if (showNewPrayerDialog) {
        AlertDialog(
            onDismissRequest = { showNewPrayerDialog = false },
            title = {
                Text(
                    text = "Apresentar Motivo de Oração",
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = authorNameInput,
                        onValueChange = { authorNameInput = it },
                        label = { Text("Seu Nome (opcional)") },
                        placeholder = { Text("Ex: Irmão João ou Deixar Anônimo") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = prayerTextInput,
                        onValueChange = { prayerTextInput = it },
                        label = { Text("Descreva seu pedido de oração *") },
                        placeholder = { Text("Compartilhe com fé sua necessidade com a igreja...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                    )

                    Text(text = "Categoria:", style = MaterialTheme.typography.labelSmall)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("Saúde", "Família", "Emprego", "Gratidão").forEach { cat ->
                            FilterChip(
                                selected = selectedCategoryInput == cat,
                                onClick = { selectedCategoryInput = cat },
                                label = { Text(cat, fontSize = 11.sp) }
                            )
                        }
                    }

                    // Opção de Confidencialidade Pastoral
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = isPrivateToPastorInput,
                            onCheckedChange = { isPrivateToPastorInput = it }
                        )
                        Text(
                            text = "Confidencial (Apenas para o Pastor Nelson)",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (prayerTextInput.isNotBlank()) {
                            viewModel.churchRepository.addPrayerRequest(
                                authorName = authorNameInput,
                                requestText = prayerTextInput,
                                category = selectedCategoryInput,
                                isPrivateToPastor = isPrivateToPastorInput
                            )
                            authorNameInput = ""
                            prayerTextInput = ""
                            isPrivateToPastorInput = false
                            showNewPrayerDialog = false
                            viewModel.showFeedback("Pedido de oração enviado com sucesso!")
                        } else {
                            viewModel.showFeedback("Por favor, descreva seu pedido de oração.")
                        }
                    }
                ) {
                    Text("Enviar Pedido")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewPrayerDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun PrayerCardItem(
    request: PrayerRequest,
    onPrayClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("prayer_item_${request.id}"),
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
                Column {
                    Text(
                        text = request.authorName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Text(
                        text = request.dateFormatted,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    color = WarmGoldContainer,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = request.category,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = WarmGoldDark,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = request.requestText,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${request.prayCount} pessoas orando",
                    style = MaterialTheme.typography.labelMedium,
                    color = WarmGoldDark,
                    fontWeight = FontWeight.SemiBold
                )

                FilledTonalButton(
                    onClick = onPrayClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = if (request.hasUserPrayed) WarmGoldPrimary else RoyalBluePrimary.copy(alpha = 0.12f),
                        contentColor = if (request.hasUserPrayed) Color.Black else RoyalBluePrimary
                    )
                ) {
                    Icon(
                        imageVector = if (request.hasUserPrayed) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (request.hasUserPrayed) "Orando! 🙏" else "Vou Orar",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
