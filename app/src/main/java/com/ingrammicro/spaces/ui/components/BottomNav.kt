package com.ingrammicro.spaces.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ingrammicro.spaces.ui.theme.*

enum class AppDestination(val label: String, val icon: ImageVector) {
    ROOMS("Salas", Icons.Default.MeetingRoom),
    DESKS("Puestos", Icons.Default.Desk),
    VISITORS("Visitas", Icons.Default.Badge),
    RECEPTION("Recepción", Icons.Default.Security),
    PROFILE("Mi Perfil", Icons.Default.AccountCircle)
}

@Composable
fun IngramBottomNav(
    currentDestination: AppDestination,
    onDestinationSelected: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(SurfaceCard)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppDestination.values().forEach { destination ->
            val isSelected = currentDestination == destination
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onDestinationSelected(destination) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = destination.icon,
                    contentDescription = destination.label,
                    tint = if (isSelected) CyanAccent else TextMuted,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = destination.label,
                    color = if (isSelected) CyanAccent else TextMuted,
                    fontSize = 10.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                if (isSelected) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(3.dp)
                            .clip(RoundedCornerShape(9999.dp))
                            .background(CyanAccent)
                    )
                }
            }
        }
    }
}
