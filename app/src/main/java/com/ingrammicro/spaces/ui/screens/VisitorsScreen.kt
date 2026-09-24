package com.ingrammicro.spaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ingrammicro.spaces.model.*
import com.ingrammicro.spaces.ui.components.HeaderPill
import com.ingrammicro.spaces.ui.components.QrCodeView
import com.ingrammicro.spaces.ui.theme.*

@Composable
fun VisitorsScreen(
    modifier: Modifier = Modifier
) {
    var visitors by remember { mutableStateOf(MockData.initialVisitors) }
    var selectedVisitor by remember { mutableStateOf(visitors.first()) }
    var showNewVisitorDialog by remember { mutableStateOf(false) }
    var newVisitorName by remember { mutableStateOf("") }
    var newVisitorCompany by remember { mutableStateOf("") }
    var newVisitorDoc by remember { mutableStateOf("") }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var turnstileSimulated by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            toastMessage?.let { msg ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    containerColor = NavyPrimary,
                    contentColor = Color.White
                ) {
                    Text(msg, fontSize = 12.sp)
                }
            }
        },
        containerColor = SurfaceBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 72.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                HeaderPill(
                    floor = "Pase QR Kiosk · Torniquetes",
                    location = "Torre San Isidro"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Acreditación de Visitas",
                            color = NavyPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Pase digital QR para torniquetes del Lobby",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = { showNewVisitorDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavySecondary),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Pre-Registrar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // QR Pass Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Badge Header
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(NavyPrimary)
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "INGRAM MICRO PERÚ · PASE DIGITAL",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = selectedVisitor.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                        Text(
                            text = "${selectedVisitor.company} · ${selectedVisitor.documentType}: ${selectedVisitor.documentNumber}",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // QR Code
                        QrCodeView(dataToken = selectedVisitor.qrCodeToken, size = 170.dp)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = selectedVisitor.qrCodeToken,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueAccent
                        )
                        Text(
                            text = "Validez: Hasta las ${selectedVisitor.validUntil} · Piso 14",
                            fontSize = 10.sp,
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Turnstile Simulator Button
                        Button(
                            onClick = {
                                turnstileSimulated = true
                                toastMessage = "✓ Torniquete Óptico 01 Abierto. ¡Bienvenido ${selectedVisitor.name}!"
                            },
                            modifier = Modifier.fillMaxWidth().height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (turnstileSimulated) Color(0xFF059669) else CyanAccent
                            )
                        ) {
                            Icon(Icons.Default.SensorDoor, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (turnstileSimulated) "Torniquete Desbloqueado" else "Simular Escaneo en Torniquete",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Quick Visitor Selector
            item {
                Text(
                    text = "Visitantes Programados para Hoy",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
            }

            items(visitors) { visitor ->
                val isSelected = visitor.id == selectedVisitor.id

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedVisitor = visitor
                            turnstileSimulated = false
                        },
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFEFF6FF) else Color.White),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) CyanAccent else Color(0xFFE2E8F0)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = visitor.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary
                            )
                            Text(
                                text = "${visitor.company} · Anfitrión: ${visitor.hostName}",
                                fontSize = 10.sp,
                                color = TextSecondary
                            )
                        }

                        Text(
                            text = visitor.scheduledTime,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlueAccent
                        )
                    }
                }
            }
        }
    }

    // New Visitor Pre-Registration Dialog
    if (showNewVisitorDialog) {
        AlertDialog(
            onDismissRequest = { showNewVisitorDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        if (newVisitorName.isNotBlank()) {
                            val newVis = Visitor(
                                id = "vis-${System.currentTimeMillis()}",
                                name = newVisitorName,
                                documentType = "DNI",
                                documentNumber = if (newVisitorDoc.isBlank()) "72819203" else newVisitorDoc,
                                company = if (newVisitorCompany.isBlank()) "Empresa Externa" else newVisitorCompany,
                                hostName = "Tania Tejada",
                                hostEmail = "tania.tejada@ingrammicro.com",
                                scheduledTime = "15:00 PM",
                                status = VisitorStatus.PENDING,
                                qrCodeToken = "INGRAM-PASS-TSI14-${newVisitorName.take(3).uppercase()}",
                                validUntil = "19:00 PM"
                            )
                            visitors = listOf(newVis) + visitors
                            selectedVisitor = newVis
                            toastMessage = "✓ Pase QR generado y enviado por correo a $newVisitorName"
                            showNewVisitorDialog = false
                            newVisitorName = ""
                            newVisitorCompany = ""
                            newVisitorDoc = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavySecondary)
                ) {
                    Text("Generar Pase QR")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewVisitorDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = {
                Text("Pre-Registrar Visitante", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newVisitorName,
                        onValueChange = { newVisitorName = it },
                        label = { Text("Nombre Completo") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newVisitorCompany,
                        onValueChange = { newVisitorCompany = it },
                        label = { Text("Empresa o Entidad") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newVisitorDoc,
                        onValueChange = { newVisitorDoc = it },
                        label = { Text("Número de DNI / Pasaporte") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}
