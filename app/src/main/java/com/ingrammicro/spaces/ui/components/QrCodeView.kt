package com.ingrammicro.spaces.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun QrCodeView(
    dataToken: String,
    size: Dp = 180.dp,
    modifier: Modifier = Modifier
) {
    val matrixSize = 25
    val seed = dataToken.hashCode()

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val cellSize = this.size.width / matrixSize

            fun isCornerFinder(r: Int, c: Int): Boolean {
                if (r < 7 && c < 7) return true
                if (r < 7 && c >= matrixSize - 7) return true
                if (r >= matrixSize - 7 && c < 7) return true
                return false
            }

            fun isFinderDark(r: Int, c: Int, startR: Int, startC: Int): Boolean {
                val relR = r - startR
                val relC = c - startC
                if (relR == 0 || relR == 6 || relC == 0 || relC == 6) return true
                if (relR in 2..4 && relC in 2..4) return true
                return false
            }

            for (r in 0 until matrixSize) {
                for (c in 0 until matrixSize) {
                    val isDark = when {
                        r < 7 && c < 7 -> isFinderDark(r, c, 0, 0)
                        r < 7 && c >= matrixSize - 7 -> isFinderDark(r, c, 0, matrixSize - 7)
                        r >= matrixSize - 7 && c < 7 -> isFinderDark(r, c, matrixSize - 7, 0)
                        r == 6 || c == 6 -> (r + c) % 2 == 0
                        else -> {
                            val pseudo = abs((seed xor (r * 31 + c * 17))) % 100
                            pseudo > 45
                        }
                    }

                    if (isDark) {
                        drawRect(
                            color = Color(0xFF002358),
                            topLeft = Offset(c * cellSize, r * cellSize),
                            size = Size(cellSize, cellSize)
                        )
                    }
                }
            }
        }
    }
}
