package com.ingrammicro.spaces.model

enum class UserRole {
    ASSOCIATE,
    SECURITY
}

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val title: String,
    val department: String,
    val avatarUrl: String,
    val corporateId: String,
    val floor: String
)

enum class RoomStatus {
    AVAILABLE,
    OCCUPIED,
    UPCOMING
}

data class MeetingSlot(
    val time: String,
    val isOccupied: Boolean,
    val bookedBy: String? = null
)

data class CurrentMeeting(
    val title: String,
    val organizer: String,
    val endsAt: String
)

data class MeetingRoom(
    val id: String,
    val name: String,
    val floor: Int,
    val capacity: Int,
    val status: RoomStatus,
    val currentMeeting: CurrentMeeting? = null,
    val features: List<String>,
    val slots: List<MeetingSlot>
)

enum class DeskStatus {
    AVAILABLE,
    OCCUPIED,
    RESERVED
}

data class DeskOccupant(
    val name: String,
    val avatar: String,
    val role: String
)

data class Desk(
    val id: String,
    val code: String,
    val zone: String,
    val status: DeskStatus,
    val occupiedBy: DeskOccupant? = null,
    val amenities: List<String>
)

enum class VisitorStatus {
    PENDING,
    IN_LOBBY,
    CHECKED_IN,
    DEPARTED
}

data class Visitor(
    val id: String,
    val name: String,
    val documentType: String,
    val documentNumber: String,
    val company: String,
    val hostName: String,
    val hostEmail: String,
    val scheduledTime: String,
    val status: VisitorStatus,
    val qrCodeToken: String,
    val validUntil: String,
    val accessGrantedTurnstile: String? = null
)

data class Booking(
    val id: String,
    val type: String, // "room" or "desk"
    val resourceName: String,
    val floor: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val title: String,
    val status: String = "active"
)
