package com.ingrammicro.spaces.model

object MockData {
    val currentAssociate = UserProfile(
        id = "usr-1982",
        name = "Tania Tejada",
        email = "tania.tejada@ingrammicro.com",
        role = UserRole.ASSOCIATE,
        title = "Senior Cloud Solutions Architect",
        department = "Advanced Solutions & Cloud Perú",
        avatarUrl = "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=256&q=80",
        corporateId = "PE-IM-9842",
        floor = "Piso 14 · Torre San Isidro"
    )

    val securityOfficer = UserProfile(
        id = "sec-004",
        name = "Oficial Víctor Ramírez",
        email = "seguridad.lima@ingrammicro.com",
        role = UserRole.SECURITY,
        title = "Supervisor de Seguridad & Recepción",
        department = "Facilities & Corporate Physical Security",
        avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=256&q=80",
        corporateId = "SEC-LOBBY-A",
        floor = "Lobby Principal · Piso 1"
    )

    val initialRooms = listOf(
        MeetingRoom(
            id = "room-huascaran",
            name = "Sala Huascarán",
            floor = 14,
            capacity = 14,
            status = RoomStatus.AVAILABLE,
            features = listOf("Microsoft Teams Rooms", "Poly Studio X50", "2 Pantallas 75\" 4K", "Pizarra Interactiva"),
            slots = listOf(
                MeetingSlot("08:30 - 09:30", false),
                MeetingSlot("09:30 - 10:30", false),
                MeetingSlot("10:30 - 11:30", false),
                MeetingSlot("11:30 - 12:30", true, "Directorio Comercial"),
                MeetingSlot("14:00 - 15:00", false),
                MeetingSlot("15:00 - 16:30", false),
                MeetingSlot("16:30 - 17:30", false)
            )
        ),
        MeetingRoom(
            id = "room-machupicchu",
            name = "Sala Machu Picchu",
            floor = 14,
            capacity = 8,
            status = RoomStatus.OCCUPIED,
            currentMeeting = CurrentMeeting(
                title = "Q3 Cloud Partner Review (AWS & Azure)",
                organizer = "Carlos Mendoza",
                endsAt = "11:45 AM"
            ),
            features = listOf("Sistema ClickShare Barco", "TV OLED 65\"", "Cámara Logitech Rally", "Mesa Ergonómica"),
            slots = listOf(
                MeetingSlot("09:00 - 10:30", true, "Carlos Mendoza"),
                MeetingSlot("10:30 - 11:45", true, "Carlos Mendoza"),
                MeetingSlot("12:00 - 13:00", false),
                MeetingSlot("14:30 - 15:30", false),
                MeetingSlot("16:00 - 17:00", true, "Marketing Latam")
            )
        ),
        MeetingRoom(
            id = "room-colca",
            name = "Sala Cañón del Colca",
            floor = 14,
            capacity = 6,
            status = RoomStatus.UPCOMING,
            currentMeeting = CurrentMeeting(
                title = "Demo Ciberseguridad Fortinet / Palo Alto",
                organizer = "Renato Silva",
                endsAt = "Inicia en 12 min"
            ),
            features = listOf("Jabra PanaCast 50 180°", "Display 55\" UHD", "Conexión USB-C 100W"),
            slots = listOf(
                MeetingSlot("09:00 - 10:00", false),
                MeetingSlot("10:15 - 11:30", true, "Renato Silva"),
                MeetingSlot("11:30 - 12:30", false),
                MeetingSlot("15:00 - 16:00", false)
            )
        ),
        MeetingRoom(
            id = "room-misti",
            name = "Sala Volcán Misti (Executive)",
            floor = 14,
            capacity = 18,
            status = RoomStatus.AVAILABLE,
            features = listOf("Crestron Mercury UC", "Proyector Láser 4K", "Micrófonos de Techo Shure", "Servicio Catering"),
            slots = listOf(
                MeetingSlot("09:00 - 11:00", false),
                MeetingSlot("11:00 - 13:00", false),
                MeetingSlot("14:00 - 16:00", false),
                MeetingSlot("16:00 - 18:00", true, "All-Hands Perú")
            )
        ),
        MeetingRoom(
            id = "room-kuelap",
            name = "Sala Fortaleza Kuélap",
            floor = 14,
            capacity = 4,
            status = RoomStatus.AVAILABLE,
            features = listOf("Huddle Pod Acústico", "Monitor 43\"", "Barra Video Poly Studio"),
            slots = listOf(
                MeetingSlot("08:00 - 09:30", false),
                MeetingSlot("09:30 - 11:00", false),
                MeetingSlot("11:00 - 12:30", false),
                MeetingSlot("13:30 - 15:00", false)
            )
        )
    )

    val initialDesks = listOf(
        Desk(
            id = "d-101",
            code = "14-TECH-01",
            zone = "Coworking Tech",
            status = DeskStatus.AVAILABLE,
            amenities = listOf("Monitor 34\" Ultrawide", "Dock Thunderbolt 4", "Silla Herman Miller Aeron")
        ),
        Desk(
            id = "d-102",
            code = "14-TECH-02",
            zone = "Coworking Tech",
            status = DeskStatus.OCCUPIED,
            occupiedBy = DeskOccupant(
                name = "Gabriel Zúñiga",
                avatar = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=128&q=80",
                role = "Cybersecurity Engineer"
            ),
            amenities = listOf("Doble Monitor 27\"", "Dock USB-C", "Soporte Laptop")
        ),
        Desk(
            id = "d-103",
            code = "14-TECH-03",
            zone = "Coworking Tech",
            status = DeskStatus.AVAILABLE,
            amenities = listOf("Monitor 34\" Ultrawide", "Dock Thunderbolt 4", "Cargador Inalámbrico Qi")
        ),
        Desk(
            id = "d-201",
            code = "14-FOCUS-01",
            zone = "Focus Silenciosa",
            status = DeskStatus.AVAILABLE,
            amenities = listOf("Cabina acústica individual", "Luz regulable", "Teclado ergonómico")
        ),
        Desk(
            id = "d-301",
            code = "14-CLOUD-01",
            zone = "Soluciones Cloud",
            status = DeskStatus.AVAILABLE,
            amenities = listOf("Doble Monitor 27\"", "Mac/PC Dock Dual", "Auriculares ANC Jabra")
        )
    )

    val initialVisitors = listOf(
        Visitor(
            id = "vis-01",
            name = "Ing. Rodrigo Benavides",
            documentType = "DNI",
            documentNumber = "44892105",
            company = "Microsoft Perú Corp",
            hostName = "Tania Tejada",
            hostEmail = "tania.tejada@ingrammicro.com",
            scheduledTime = "10:30 AM",
            status = VisitorStatus.IN_LOBBY,
            qrCodeToken = "INGRAM-PASS-TSI14-RB4489",
            validUntil = "14:30 PM"
        ),
        Visitor(
            id = "vis-02",
            name = "Dra. Carolina Alarcón",
            documentType = "CE",
            documentNumber = "002938174",
            company = "Cisco Systems Latam",
            hostName = "Carlos Mendoza",
            hostEmail = "carlos.mendoza@ingrammicro.com",
            scheduledTime = "11:15 AM",
            status = VisitorStatus.CHECKED_IN,
            qrCodeToken = "INGRAM-PASS-TSI14-CA0029",
            validUntil = "16:00 PM",
            accessGrantedTurnstile = "Torniquete 02 (Piso 14)"
        ),
        Visitor(
            id = "vis-03",
            name = "Lic. Fernando Quevedo",
            documentType = "DNI",
            documentNumber = "71029341",
            company = "Telefónica Tech Perú",
            hostName = "Tania Tejada",
            hostEmail = "tania.tejada@ingrammicro.com",
            scheduledTime = "14:00 PM",
            status = VisitorStatus.PENDING,
            qrCodeToken = "INGRAM-PASS-TSI14-FQ7102",
            validUntil = "18:00 PM"
        )
    )

    val initialBookings = listOf(
        Booking(
            id = "bkg-01",
            type = "room",
            resourceName = "Sala Huascarán (Piso 14)",
            floor = "Piso 14",
            date = "Hoy, 24 de Septiembre",
            startTime = "14:00",
            endTime = "15:30",
            title = "Sincronización Estratégica Ingram Cloud & Partners"
        ),
        Booking(
            id = "bkg-02",
            type = "desk",
            resourceName = "Puesto 14-TECH-03 (Coworking Tech)",
            floor = "Piso 14",
            date = "Hoy, 24 de Septiembre",
            startTime = "08:30",
            endTime = "18:00",
            title = "Reserva Diaria - Jornada Híbrida"
        )
    )
}
