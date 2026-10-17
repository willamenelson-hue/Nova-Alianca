package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PreloadedData
import com.example.data.model.GivingReceipt
import com.example.data.model.GivingType
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.WarmGoldContainer
import com.example.ui.theme.WarmGoldDark
import com.example.ui.theme.WarmGoldPrimary
import com.example.ui.viewmodel.AppViewModel

@Composable
fun GivingScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val receipts by viewModel.churchRepository.donationReceipts.collectAsState()

    var selectedType by remember { mutableStateOf(GivingType.DIZIMO) }
    var selectedAmountValue by remember { mutableStateOf("50") }
    var donorNameInput by remember { mutableStateOf("") }
    var showSuccessReceiptDialog by remember { mutableStateOf<GivingReceipt?>(null) }

    val presetAmounts = listOf("20", "50", "100", "200", "500")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("giving_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cabeçalho de Fidelidade e Gratidão
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = RoyalBluePrimary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.VolunteerActivism,
                            contentDescription = null,
                            tint = WarmGoldPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Dízimos e Ofertas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "“Cada um contribua segundo propôs no seu coração; não com tristeza, ou por necessidade; porque Deus ama ao que dá com alegria.” (2 Coríntios 9:7)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Escolha da Categoria de Contribuição
        item {
            Text(
                text = "Finalidade da Contribuição:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = RoyalBluePrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GivingType.values().forEach { type ->
                    val isSelected = selectedType == type
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) WarmGoldPrimary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedType = type }
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = type.label,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Seleção de Valor Sugerido
        item {
            Text(
                text = "Valor da Contribuição (R$):",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = RoyalBluePrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                presetAmounts.forEach { preset ->
                    FilterChip(
                        selected = selectedAmountValue == preset,
                        onClick = { selectedAmountValue = preset },
                        label = { Text("R$ $preset", fontSize = 12.sp) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = selectedAmountValue,
                onValueChange = { selectedAmountValue = it },
                label = { Text("Ou digite outro valor (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = donorNameInput,
                onValueChange = { donorNameInput = it },
                label = { Text("Seu Nome (para o recibo/controle)") },
                placeholder = { Text("Ex: Carlos Silva ou Em Anônimo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Cartão do Pix Oficial e QR Code Seguro
        item {
            val amountDouble = selectedAmountValue.toDoubleOrNull() ?: 50.0
            val pixCode = viewModel.churchRepository.generatePixPayload(selectedType, amountDouble)

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(3.dp),
                modifier = Modifier.fillMaxWidth().testTag("pix_card")
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pagamento Instantâneo via PIX",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalBluePrimary
                    )
                    Text(
                        text = "Igreja Batista Nova Aliança • CNPJ ${PreloadedData.PIX_CNPJ}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // QR Code Desenhado Graficamente
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(2.dp, WarmGoldPrimary, RoundedCornerShape(12.dp))
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SimulatedQrCodeCanvas()
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Chave Pix: ${PreloadedData.PIX_KEY}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = WarmGoldDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Botão 1: Copiar Chave Pix
                    OutlinedButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(PreloadedData.PIX_KEY))
                            viewModel.showFeedback("Chave Pix copiada com sucesso!")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Copiar Chave Pix (E-mail)")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botão 2: Copiar Código Pix Copia e Cola
                    Button(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(pixCode))
                            viewModel.showFeedback("Código Pix Copia e Cola copiado! Abra o app do seu banco.")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalBluePrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.QrCode, contentDescription = null, tint = WarmGoldPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Copiar Código Pix Copia e Cola")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Botão 3: Gerar Comprovante / Registrar
                    FilledTonalButton(
                        onClick = {
                            val receipt = viewModel.churchRepository.recordDonation(
                                type = selectedType,
                                amount = amountDouble,
                                donorName = donorNameInput
                            )
                            showSuccessReceiptDialog = receipt
                            viewModel.showFeedback("Comprovante gerado com sucesso!")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.ReceiptLong, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Registrar e Gerar Comprovante")
                    }
                }
            }
        }

        // Histórico de Comprovantes de Doação
        if (receipts.isNotEmpty()) {
            item {
                Text(
                    text = "Meus Recibos de Contribuição",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
            }
            items(receipts) { receipt ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${receipt.type.label} • ${receipt.donorName}",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Ref: ${receipt.transactionRef}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = receipt.amountFormatted,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = WarmGoldDark
                        )
                    }
                }
            }
        }
    }

    // Modal de Comprovante de Contribuição
    if (showSuccessReceiptDialog != null) {
        val r = showSuccessReceiptDialog!!
        AlertDialog(
            onDismissRequest = { showSuccessReceiptDialog = null },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = WarmGoldDark,
                    modifier = Modifier.size(44.dp)
                )
            },
            title = {
                Text(
                    text = "Comprovante Registrado",
                    fontWeight = FontWeight.Bold,
                    color = RoyalBluePrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Igreja Batista Nova Aliança", fontWeight = FontWeight.Bold)
                    Text(text = "Finalidade: ${r.type.label}")
                    Text(text = "Contribuinte: ${r.donorName}")
                    Text(text = "Valor: ${r.amountFormatted}", fontWeight = FontWeight.Bold, color = RoyalBluePrimary)
                    Text(text = "Referência: ${r.transactionRef}", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Que o Senhor multiplique com ricas bênçãos a sua fidelidade e generosidade na casa de Deus!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showSuccessReceiptDialog = null }) {
                    Text("Concluir")
                }
            }
        )
    }
}

@Composable
private fun SimulatedQrCodeCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        val step = w / 9f

        // Desenha padrões estilizados de cantos do QR code
        drawRect(Color.Black, Offset(0f, 0f), Size(step * 3, step * 3))
        drawRect(Color.White, Offset(step, step), Size(step, step))

        drawRect(Color.Black, Offset(w - step * 3, 0f), Size(step * 3, step * 3))
        drawRect(Color.White, Offset(w - step * 2, step), Size(step, step))

        drawRect(Color.Black, Offset(0f, h - step * 3), Size(step * 3, step * 3))
        drawRect(Color.White, Offset(step, h - step * 2), Size(step, step))

        // Padrões internos do código
        drawRect(Color.Black, Offset(step * 4, step * 4), Size(step, step))
        drawRect(Color.Black, Offset(step * 2, step * 4), Size(step, step))
        drawRect(Color.Black, Offset(step * 6, step * 4), Size(step, step))
        drawRect(Color.Black, Offset(step * 4, step * 2), Size(step, step))
        drawRect(Color.Black, Offset(step * 4, step * 6), Size(step, step))
        drawRect(Color.Black, Offset(step * 5, step * 7), Size(step, step))
    }
}
