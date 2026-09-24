package com.ingrammicro.spaces.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ingrammicro.spaces.model.MockData
import com.ingrammicro.spaces.model.UserProfile
import com.ingrammicro.spaces.model.UserRole
import com.ingrammicro.spaces.ui.components.HeaderPill
import com.ingrammicro.spaces.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    var userRole by remember { mutableStateOf(UserRole.ASSOCIATE) }
    var email by remember { mutableStateOf("tania.tejada@ingrammicro.com") }
    var password by remember { mutableStateOf("••••••••••••") }
    var showPassword by remember { mutableStateOf(false) }
    var rememberDevice by remember { mutableStateOf(true) }
    var terminalPin by remember { mutableStateOf("4400") }
    var showBiometricModal by remember { mutableStateOf(false) }
    var biometricFeedback by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Pill
        Spacer(modifier = Modifier.height(12.dp))
        HeaderPill(
            floor = "Piso 14",
            location = "Sede Central Lima · Torre San Isidro"
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Branding Logo
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(NavyPrimary),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "IM",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "INGRAM SPACES",
            color = NavyPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Text(
            text = "Gestión de Espacios Corporativos · Perú",
            color = TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Role Segmented Switch
        Row(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE2E8F0))
                .padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(9.dp))
                    .background(if (userRole == UserRole.ASSOCIATE) Color.White else Color.Transparent)
                    .clickable {
                        userRole = UserRole.ASSOCIATE
                        email = "tania.tejada@ingrammicro.com"
                    }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Colaborador / Anfitrión",
                    fontSize = 11.sp,
                    fontWeight = if (userRole == UserRole.ASSOCIATE) FontWeight.Bold else FontWeight.Normal,
                    color = if (userRole == UserRole.ASSOCIATE) NavyPrimary else TextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(9.dp))
                    .background(if (userRole == UserRole.SECURITY) Color.White else Color.Transparent)
                    .clickable {
                        userRole = UserRole.SECURITY
                        email = "seguridad.lima@ingrammicro.com"
                    }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Recepción & Lobby",
                    fontSize = 11.sp,
                    fontWeight = if (userRole == UserRole.SECURITY) FontWeight.Bold else FontWeight.Normal,
                    color = if (userRole == UserRole.SECURITY) NavyPrimary else TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Main Card
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (userRole == UserRole.ASSOCIATE) "Bienvenido de vuelta" else "Consola de Seguridad TSI",
                    color = NavyPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = if (userRole == UserRole.ASSOCIATE)
                        "Inicia sesión con tus credenciales corporativas para gestionar salas, escritorios y visitas en tiempo real."
                    else
                        "Acceso de control de torniquetes, acreditación de visitas y gestión del lobby.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                // Reception PIN box
                if (userRole == UserRole.SECURITY) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFEFF6FF))
                            .border(1.dp, Color(0xFFBFDBFE), RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "PIN de Terminal Kiosco Lobby",
                                color = BlueAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = terminalPin,
                                onValueChange = { terminalPin = it },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Microsoft Entra ID Button
                Button(
                    onClick = {
                        val profile = if (userRole == UserRole.SECURITY) MockData.securityOfficer else MockData.currentAssociate
                        onLoginSuccess(profile)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = TextPrimary
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Microsoft 4 squares
                        Box(modifier = Modifier.size(16.dp)) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Box(modifier = Modifier.size(7.dp).background(Color(0xFFF25022)))
                                Spacer(modifier = Modifier.width(2.dp))
                                Box(modifier = Modifier.size(7.dp).background(Color(0xFF7FBA00)))
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Continuar con Microsoft Entra ID",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }
                }

                // Divider
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(Color(0xFFE2E8F0)))
                    Text(
                        text = "o con credenciales de red",
                        color = TextMuted,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(Color(0xFFE2E8F0)))
                }

                // Email field
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Correo Corporativo",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Password field
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Contraseña de Red",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                        },
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    )
                }

                // Remember Checkbox
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberDevice,
                            onCheckedChange = { rememberDevice = it },
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Recordar este dispositivo", fontSize = 11.sp, color = TextSecondary)
                    }
                    Text(
                        text = "¿Olvidó clave?",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BlueAccent
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Submit Button
                Button(
                    onClick = {
                        val profile = if (userRole == UserRole.SECURITY) MockData.securityOfficer else MockData.currentAssociate
                        onLoginSuccess(profile)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NavySecondary)
                ) {
                    Text(
                        text = if (userRole == UserRole.ASSOCIATE) "Iniciar Sesión en Ingram Spaces" else "Ingresar a Consola de Recepción",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Biometrics Card
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clickable {
                    showBiometricModal = true
                },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEFF6FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Biometría",
                        tint = BlueAccent,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Acceso Rápido con Biometría",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyPrimary
                    )
                    Text(
                        text = "Touch ID o Face ID corporativo verificado",
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Security Footnote
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = StatusGreenText, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "TLS 1.3 Cifrado · ISO 27001 · Sede Torre San Isidro",
                fontSize = 10.sp,
                color = TextMuted
            )
        }
    }

    // Biometric Dialog Modal
    if (showBiometricModal) {
        AlertDialog(
            onDismissRequest = { showBiometricModal = false },
            confirmButton = {
                Button(
                    onClick = {
                        val profile = if (userRole == UserRole.SECURITY) MockData.securityOfficer else MockData.currentAssociate
                        showBiometricModal = false
                        onLoginSuccess(profile)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NavySecondary)
                ) {
                    Text("Confirmar Identidad")
                }
            },
            dismissButton = {
                TextButton(onClick = { showBiometricModal = false }) {
                    Text("Cancelar")
                }
            },
            title = {
                Text(text = "Sensor Biométrico Android", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = null,
                        tint = CyanAccent,
                        modifier = Modifier.size(54.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Coloca tu huella digital en el sensor o mira la cámara para Face Unlock.",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Asociado: Tania Tejada (PE-IM-9842)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = NavyPrimary
                    )
                }
            }
        )
    }
}
