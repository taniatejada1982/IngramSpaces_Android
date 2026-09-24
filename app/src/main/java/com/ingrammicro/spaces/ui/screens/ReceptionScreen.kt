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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ingrammicro.spaces.model.*
import com.ingrammicro.spaces.ui.components.HeaderPill
import com.ingrammicro.spaces.ui.theme.*

@Composable
fun ReceptionScreen(
    modifier: Modifier = Modifier
) {
    var visitors by remember { mutableStateOf(MockData.initialVisitors) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedVisitor by remember { mutableStateOf<Visitor?>(null) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var showBadgeDialog by remember { mutableStateOf(false) }

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
                    floor = "Lobby Principal · Seguridad",
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
                            text = "Consola de Recepción & Lobby",
                            color = NavyPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Control de torniquetes y validación de visitas",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFECFDF5))
                            .border(1.dp, Color(0xFFA7F3D0), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("3 Torniquetes OK", fontSize = 10.sp, color = StatusGreenText, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Quick Search Form
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            "Consultar DNI, Apellido o Token QR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Ej. 44892105 o Benavides", fontSize = 12.sp) },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    val found = visitors.find {
                                        it.documentNumber.contains(searchQuery) ||
                                                it.name.contains(searchQuery, ignoreCase = true)
                                    }
                                    if (found != null) {
                                        selectedVisitor = found
                                        toastMessage = "✓ Registro encontrado: ${found.name}"
                                    } else {
                                        toastMessage = "⚠ No se encontró registro para \"$searchQuery\""
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = NavySecondary)
                            ) {
                                Text("Buscar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Selected Visitor Action Box
            selectedVisitor?.let { vis ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                        border = androidx.compose.foundation.BorderStroke(2.dp, CyanAccent)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                "Visitante Seleccionado",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BlueAccent
                            )
                            Text(
                                vis.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary
                            )
                            Text(
                                "${vis.company} · ${vis.documentType}: ${vis.documentNumber}",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            Text(
                                "Anfitrión: ${vis.hostName} · Destino: Piso 14",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        toastMessage = "✓ Torniquete 01 desbloqueado para ${vis.name}"
                                    },
                                    modifier = Modifier.weight(1f).height(38.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669))
                                ) {
                                    Text("Habilitar Torniquete", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = {
                                        toastMessage = "🔔 Mensaje Teams enviado a ${vis.hostName}"
                                    },
                                    modifier = Modifier.weight(1f).height(38.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = BlueAccent)
                                ) {
                                    Text("Avisar por Teams", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = { showBadgeDialog = true },
                                modifier = Modifier.fillMaxWidth().height(36.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Generar Fotocheck Físico Temporal", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            // Visitor Roster
            item {
                Text(
                    "Control de Ingresos Diario (Lobby TSI)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
            }

            items(visitors) { v ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(v.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyPrimary)
                            Text("${v.company} · ${v.scheduledTime}", fontSize = 10.sp, color = TextSecondary)
                        }

                        Button(
                            onClick = { selectedVisitor = v },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("Gestionar", fontSize = 10.sp, color = NavyPrimary, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }

    // Physical Badge Dialog
    if (showBadgeDialog && selectedVisitor != null) {
        val vis = selectedVisitor!!
        AlertDialog(
            onDismissRequest = { showBadgeDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        toastMessage = "✓ Enviado a Impresora Zebra Kiosk TSI-01"
                        showBadgeDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavySecondary)
                ) {
                    Text("Imprimir Credencial")
                }
            },
            dismissButton = {
                TextButton(onClick = { showBadgeDialog = false }) {
                    Text("Cerrar")
                }
            },
            title = {
                Text("Fotocheck Temporal", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF8FAFC))
                        .border(1.dp, NavySecondary, RoundedCornerShape(10.dp))
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("INGRAM MICRO PERÚ", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NavyPrimary)
                    Text("VISITANTE", fontWeight = FontWeight.Bold, fontSize = 9.sp, color = BlueAccent)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(vis.name, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyPrimary)
                    Text(vis.company, fontSize = 10.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Destino: Piso 14 · Autoriza: ${vis.hostName}", fontSize = 9.sp, color = TextMuted)
                }
            }
        )
    }
}
