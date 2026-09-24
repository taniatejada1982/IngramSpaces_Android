package com.ingrammicro.spaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.ingrammicro.spaces.model.Booking
import com.ingrammicro.spaces.model.MockData
import com.ingrammicro.spaces.model.UserProfile
import com.ingrammicro.spaces.model.UserRole
import com.ingrammicro.spaces.ui.components.AppDestination
import com.ingrammicro.spaces.ui.components.IngramBottomNav
import com.ingrammicro.spaces.ui.screens.*
import com.ingrammicro.spaces.ui.theme.IngramSpacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IngramSpacesTheme {
                MainAppContent()
            }
        }
    }
}

@Composable
fun MainAppContent() {
    var currentUser by remember { mutableStateOf<UserProfile?>(null) }
    var currentDestination by remember { mutableStateOf(AppDestination.ROOMS) }
    var bookings by remember { mutableStateOf(MockData.initialBookings) }

    if (currentUser == null) {
        LoginScreen(
            onLoginSuccess = { user ->
                currentUser = user
                currentDestination = if (user.role == UserRole.SECURITY) {
                    AppDestination.RECEPTION
                } else {
                    AppDestination.ROOMS
                }
            }
        )
    } else {
        Scaffold(
            bottomBar = {
                IngramBottomNav(
                    currentDestination = currentDestination,
                    onDestinationSelected = { destination ->
                        currentDestination = destination
                    }
                )
            }
        ) { paddingValues ->
            val screenModifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

            when (currentDestination) {
                AppDestination.ROOMS -> {
                    RoomsScreen(
                        onAddBooking = { newBooking ->
                            bookings = listOf(newBooking) + bookings
                        },
                        modifier = screenModifier
                    )
                }
                AppDestination.DESKS -> {
                    DesksScreen(
                        onAddBooking = { newBooking ->
                            bookings = listOf(newBooking) + bookings
                        },
                        modifier = screenModifier
                    )
                }
                AppDestination.VISITORS -> {
                    VisitorsScreen(
                        modifier = screenModifier
                    )
                }
                AppDestination.RECEPTION -> {
                    ReceptionScreen(
                        modifier = screenModifier
                    )
                }
                AppDestination.PROFILE -> {
                    ProfileScreen(
                        user = currentUser!!,
                        bookings = bookings,
                        onCancelBooking = { bookingId ->
                            bookings = bookings.filter { it.id != bookingId }
                        },
                        onLogout = {
                            currentUser = null
                        },
                        modifier = screenModifier
                    )
                }
            }
        }
    }
}
