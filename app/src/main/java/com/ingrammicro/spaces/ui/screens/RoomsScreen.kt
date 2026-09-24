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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomsScreen(
    onAddBooking: (Booking) -> Unit,
    modifier: Modifier = Modifier
) {
    var rooms by remember { mutableStateOf(MockData.initialRooms) }
    var selectedFilter by remember { mutableStateOf("all") }
    var selectedFloor by remember { mutableStateOf(14) }
    var selectedRoomForBooking by remember { mutableStateOf<MeetingRoom?>(null) }
    var bookingSubject by remember { mutableStateOf("") }
    var selectedSlotTime by remember { mutableStateOf("14:00 - 15:00") }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    val filteredRooms = rooms.filter { room ->
        val floorMatch = room.floor == selectedFloor
        val filterMatch = when (selectedFilter) {
            "available" -> room.status == RoomStatus.AVAILABLE
            "teams" -> room.features.any { it.contains("Teams", ignoreCase = true) }
            "large" -> room.capacity >= 10
            else -> true
        }
        floorMatch && filterMatch
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
            // Header Pill
            item {
                HeaderPill(
                    floor = "Piso $selectedFloor · Salas de Reuniones",
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
                            text = "Salas de Reuniones",
                            color = NavyPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Reserva instantánea con Microsoft 365",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    // Floor Selector Chip
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE2E8F0))
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (selectedFloor == 14) Color.White else Color.Transparent)
                                .clickable { selectedFloor = 14 }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "Piso 14",
                                fontSize = 10.sp,
                                fontWeight = if (selectedFloor == 14) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedFloor == 14) NavyPrimary else TextSecondary
                            )
                        }
                    }
                }
            }

            // Quick Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val filters = listOf(
                        "all" to "Todas",
                        "available" to "Disponibles Ahora",
                        "teams" to "M365 Teams Rooms",
                        "large" to "Grandes (10+ pax)"
                    )
                    items(filters) { (id, label) ->
                        val isSelected = selectedFilter == id
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedFilter = id },
                            label = { Text(label, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NavySecondary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // List of Meeting Rooms
            items(filteredRooms) { room ->
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
                            Column {
                                Text(
                                    text = room.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyPrimary
                                )
                                Text(
                                    text = "Capacidad: ${room.capacity} personas · Piso ${room.floor}",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }

                            // Status Tag
                            val (badgeBg, badgeText, statusLabel) = when (room.status) {
                                RoomStatus.AVAILABLE -> Triple(StatusGreenBg, StatusGreenText, "Disponible")
                                RoomStatus.OCCUPIED -> Triple(StatusRedBg, StatusRedText, "En Uso")
                                RoomStatus.UPCOMING -> Triple(StatusAmberBg, StatusAmberText, "Próxima")
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(badgeBg)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = statusLabel,
                                    color = badgeText,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Current Meeting if occupied
                        room.currentMeeting?.let { current ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFEF2F2))
                                    .padding(8.dp)
                            ) {
                                Column {
                                    Text(
                                        text = current.title,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF991B1B)
                                    )
                                    Text(
                                        text = "Organiza: ${current.organizer} · Termina: ${current.endsAt}",
                                        fontSize = 10.sp,
                                        color = Color(0xFFB91C1C)
                                    )
                                }
                            }
                        }

                        // Features
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            room.features.take(2).forEach { feat ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFFEFF6FF))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(feat, fontSize = 9.sp, color = BlueAccent, fontWeight = FontWeight.Medium)
                                }
                            }
                        }

                        // Time slots grid
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Horarios del día:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(room.slots) { slot ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (slot.isOccupied) Color(0xFFF1F5F9) else Color(0xFFEFF6FF))
                                        .border(
                                            1.dp,
                                            if (slot.isOccupied) Color(0xFFCBD5E1) else CyanAccent,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .clickable(enabled = !slot.isOccupied) {
                                            selectedRoomForBooking = room
                                            selectedSlotTime = slot.time
                                        }
                                        .padding(horizontal = 8.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = slot.time,
                                        fontSize = 10.sp,
                                        color = if (slot.isOccupied) Color(0xFF94A3B8) else NavyPrimary,
                                        fontWeight = if (slot.isOccupied) FontWeight.Normal else FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                selectedRoomForBooking = room
                                bookingSubject = "Sincronización de Equipo"
                            },
                            modifier = Modifier.fillMaxWidth().height(36.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NavySecondary)
                        ) {
                            Text("Reservar Sala en M365", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Reservation Modal
    selectedRoomForBooking?.let { room ->
        AlertDialog(
            onDismissRequest = { selectedRoomForBooking = null },
            confirmButton = {
                Button(
                    onClick = {
                        val times = selectedSlotTime.split(" - ")
                        val start = times.getOrNull(0) ?: "14:00"
                        val end = times.getOrNull(1) ?: "15:00"

                        val newBooking = Booking(
                            id = "bkg-${System.currentTimeMillis()}",
                            type = "room",
                            resourceName = "${room.name} (Piso ${room.floor})",
                            floor = "Piso ${room.floor}",
                            date = "Hoy",
                            startTime = start,
                            endTime = end,
                            title = if (bookingSubject.isBlank()) "Reunión de Negocios" else bookingSubject
                        )
                        onAddBooking(newBooking)
                        toastMessage = "✓ Sala ${room.name} reservada y sincronizada con Outlook M365"
                        selectedRoomForBooking = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavySecondary)
                ) {
                    Text("Confirmar Reserva")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedRoomForBooking = null }) {
                    Text("Cancelar")
                }
            },
            title = {
                Text("Reservar ${room.name}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Horario seleccionado: $selectedSlotTime", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = BlueAccent)
                    OutlinedTextField(
                        value = bookingSubject,
                        onValueChange = { bookingSubject = it },
                        label = { Text("Asunto de la reunión") },
                        placeholder = { Text("Ej. Revisión Trimestral Cloud") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("Sincronización directa con Exchange Online y Teams Rooms.", fontSize = 10.sp, color = TextMuted)
                }
            }
        )
    }
}
