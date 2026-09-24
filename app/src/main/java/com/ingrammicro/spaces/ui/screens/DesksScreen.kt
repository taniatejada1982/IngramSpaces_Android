package com.ingrammicro.spaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
fun DesksScreen(
    onAddBooking: (Booking) -> Unit,
    modifier: Modifier = Modifier
) {
    var desks by remember { mutableStateOf(MockData.initialDesks) }
    var selectedZone by remember { mutableStateOf("all") }
    var selectedDeskForBooking by remember { mutableStateOf<Desk?>(null) }
    var durationMode by remember { mutableStateOf("full") }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    val filteredDesks = desks.filter { desk ->
        when (selectedZone) {
            "tech" -> desk.zone.contains("Tech", ignoreCase = true)
            "focus" -> desk.zone.contains("Focus", ignoreCase = true)
            "cloud" -> desk.zone.contains("Cloud", ignoreCase = true)
            else -> true
        }
    }

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
                    floor = "Piso 14 · Hot-Desking",
                    location = "Torre San Isidro"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Puestos de Trabajo Híbridos",
                    color = NavyPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Reserva tu escritorio antes de asistir a la oficina",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }

            // Interactive Floor Map Schematic Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Plano Esquemático · Piso 14",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary
                            )
                            Text(
                                "4 Disponibles",
                                fontSize = 10.sp,
                                color = StatusGreenText,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Grid representing physical desks
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0xFFF1F5F9))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            desks.forEach { d ->
                                val isAvail = d.status == DeskStatus.AVAILABLE
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable {
                                        if (isAvail) selectedDeskForBooking = d
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isAvail) Color(0xFFD1FAE5) else Color(0xFFFEE2E2))
                                            .border(
                                                1.dp,
                                                if (isAvail) Color(0xFF10B981) else Color(0xFFEF4444),
                                                RoundedCornerShape(8.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Desk,
                                            contentDescription = null,
                                            tint = if (isAvail) Color(0xFF047857) else Color(0xFFB91C1C),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        d.code.substringAfterLast("-"),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Zone Filter Row
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val zones = listOf(
                        "all" to "Todas las Zonas",
                        "tech" to "Coworking Tech",
                        "focus" to "Zona Silenciosa Focus",
                        "cloud" to "Soluciones Cloud"
                    )
                    items(zones) { (id, label) ->
                        val isSelected = selectedZone == id
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedZone = id },
                            label = { Text(label, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NavySecondary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // List of Desks
            items(filteredDesks) { desk ->
                val isAvailable = desk.status == DeskStatus.AVAILABLE

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = desk.code,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyPrimary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = desk.zone,
                                    fontSize = 10.sp,
                                    color = BlueAccent,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = desk.amenities.joinToString(" · "),
                                fontSize = 10.sp,
                                color = TextMuted
                            )
                            if (desk.occupiedBy != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Ocupado por ${desk.occupiedBy.name}",
                                    fontSize = 10.sp,
                                    color = Color(0xFFDC2626),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Button(
                            onClick = { selectedDeskForBooking = desk },
                            enabled = isAvailable,
                            modifier = Modifier.height(34.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isAvailable) CyanAccent else Color(0xFFCBD5E1)
                            )
                        ) {
                            Text(
                                text = if (isAvailable) "Reservar" else "Ocupado",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isAvailable) Color.White else Color(0xFF64748B)
                            )
                        }
                    }
                }
            }
        }
    }

    // Desk Booking Modal
    selectedDeskForBooking?.let { desk ->
        AlertDialog(
            onDismissRequest = { selectedDeskForBooking = null },
            confirmButton = {
                Button(
                    onClick = {
                        val newBooking = Booking(
                            id = "bkg-${System.currentTimeMillis()}",
                            type = "desk",
                            resourceName = "Puesto ${desk.code} (${desk.zone})",
                            floor = "Piso 14",
                            date = "Hoy",
                            startTime = if (durationMode == "morning") "08:30" else "14:00",
                            endTime = if (durationMode == "afternoon") "18:30" else "18:00",
                            title = "Puesto Hot-Desking Asignado"
                        )
                        onAddBooking(newBooking)
                        toastMessage = "✓ Puesto ${desk.code} reservado exitosamente"
                        selectedDeskForBooking = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavySecondary)
                ) {
                    Text("Confirmar Puesto")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedDeskForBooking = null }) {
                    Text("Cancelar")
                }
            },
            title = {
                Text("Reservar Puesto ${desk.code}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Zona: ${desk.zone}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = BlueAccent)
                    Text("Equipamiento incluido: ${desk.amenities.joinToString(", ")}", fontSize = 11.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Turno de reserva:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FilterChip(
                            selected = durationMode == "full",
                            onClick = { durationMode = "full" },
                            label = { Text("Día Completo (08:30 - 18:00)", fontSize = 10.sp) }
                        )
                    }
                }
            }
        )
    }
}
