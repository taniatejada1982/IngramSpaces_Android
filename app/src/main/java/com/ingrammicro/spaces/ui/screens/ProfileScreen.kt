package com.ingrammicro.spaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.ingrammicro.spaces.model.Booking
import com.ingrammicro.spaces.model.UserProfile
import com.ingrammicro.spaces.ui.components.HeaderPill
import com.ingrammicro.spaces.ui.theme.*

@Composable
fun ProfileScreen(
    user: UserProfile,
    bookings: List<Booking>,
    onCancelBooking: (String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(16.dp)
            .padding(bottom = 72.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            HeaderPill(
                floor = "Piso 14 · Credencial Digital",
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
                        text = "Mi Credencial & Reservas",
                        color = NavyPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Identidad corporativa y recursos asignados",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }

                TextButton(
                    onClick = onLogout,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFDC2626))
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Salir", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Digital Corporate Associate Badge Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavyPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CyanAccent),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("IM", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("INGRAM MICRO PERÚ", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("Sede Central Lima", color = Color(0xFF93C5FD), fontSize = 10.sp)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9999.dp))
                                .background(Color(0xFF065F46))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text("ACTIVO · NIVEL 3", color = Color(0xFFA7F3D0), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(user.name.take(2).uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(user.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(user.title, color = Color(0xFF93C5FD), fontSize = 11.sp)
                            Text(user.department, color = Color(0xFFBFDBFE), fontSize = 10.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.White.copy(alpha = 0.15f)))
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Fotocheck ID", color = Color(0xFF93C5FD), fontSize = 10.sp)
                            Text(user.corporateId, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Ubicación", color = Color(0xFF93C5FD), fontSize = 10.sp)
                            Text(user.floor, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Active Bookings List
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Mis Reservas Activas para Hoy",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NavyPrimary
                )
                Text(
                    "${bookings.size} asignadas",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        if (bookings.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.EventBusy, contentDescription = null, tint = TextMuted, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("No tienes reservas activas en este momento", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
        } else {
            items(bookings) { bkg ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (bkg.type == "room") Icons.Default.MeetingRoom else Icons.Default.Desk,
                                    contentDescription = null,
                                    tint = CyanAccent,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(bkg.resourceName, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NavyPrimary)
                            }
                            Text("${bkg.startTime} - ${bkg.endTime}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = BlueAccent)
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(bkg.title, fontSize = 11.sp, color = TextSecondary)

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Sincronizado M365", fontSize = 10.sp, color = StatusGreenText, fontWeight = FontWeight.Medium)
                            TextButton(
                                onClick = { onCancelBooking(bkg.id) },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFDC2626))
                            ) {
                                Text("Liberar Espacio", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
