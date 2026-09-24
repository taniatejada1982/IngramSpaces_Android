package com.ingrammicro.spaces.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ingrammicro.spaces.ui.theme.*

@Composable
fun HeaderPill(
    floor: String = "Piso 14",
    location: String = "Sede Central Lima · Torre San Isidro",
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(9999.dp))
            .background(SurfaceCard)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(9999.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Color(0xFF00452D))
        )
        Text(
            text = "🏢 $location",
            color = TextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = floor,
            color = BlueAccent,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
