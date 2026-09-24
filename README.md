# Ingram Spaces - Aplicación Android Nativa (Jetpack Compose & Kotlin)

Esta es la implementación **100% Nativa en Kotlin y Jetpack Compose** para **Ingram Spaces Perú (Torre San Isidro · Piso 14)**, compatible con Android Studio.

---

## 📁 Estructura del Proyecto Android

```
android/
├── build.gradle.kts                   # Configuración Gradle raíz (plugins AGP 8.5.2, Kotlin 2.0.0)
├── settings.gradle.kts                # Configuración de repositorios y módulos (:app)
├── gradle.properties                  # Parámetros JVM y soporte AndroidX
├── app/
│   ├── build.gradle.kts               # Dependencias de Compose, Material3, Navigation, Coil, Biometrics
│   ├── proguard-rules.pro             # Reglas de optimización ProGuard/R8
│   └── src/main/
│       ├── AndroidManifest.xml        # Manifiesto con permisos de INTERNET y BIOMETRIC
│       ├── java/com/ingrammicro/spaces/
│       │   ├── MainActivity.kt        # Actividad principal y orquestador de navegación
│       │   ├── model/
│       │   │   ├── Models.kt          # Modelos de datos (UserProfile, MeetingRoom, Desk, Visitor, Booking)
│       │   │   └── MockData.kt        # Datos iniciales para salas, puestos y visitas de Torre San Isidro
│       │   └── ui/
│       │       ├── theme/
│       │       │   ├── Color.kt       # Paleta corporativa (NavyPrimary #002358, CyanAccent #0091FF, etc.)
│       │       │   ├── Type.kt        # Tipografía adaptada a Material 3
│       │       │   └── Theme.kt       # Tema corporativo IngramSpacesTheme
│       │       ├── components/
│       │       │   ├── HeaderPill.kt  # Indicador superior dinámico de piso y sede
│       │       │   ├── BottomNav.kt   # Barra de navegación inferior nativa con 5 pestañas
│       │       │   └── QrCodeView.kt  # Renderizador gráfico nativo de código QR para torniquetes
│       │       └── screens/
│       │           ├── LoginScreen.kt     # Inicio de sesión con selector de roles, Entra ID, PIN y biometría
│       │           ├── RoomsScreen.kt     # Reserva de salas M365 (Huascarán, Machu Picchu, etc.)
│       │           ├── DesksScreen.kt     # Puestos Hot-Desking con mapa esquemático interactivo
│       │           ├── VisitorsScreen.kt  # Acreditación de visitas y pase QR dinámico para torniquetes
│       │           ├── ReceptionScreen.kt # Consola de lobby, apertura de torniquetes y fotocheck temporal
│       │           └── ProfileScreen.kt   # Fotocheck digital de colaborador y gestión de reservas
│       └── res/
│           ├── values/
│           │   ├── strings.xml
│           │   ├── colors.xml
│           │   └── themes.xml
│           └── xml/
│               ├── backup_rules.xml
│               └── data_extraction_rules.xml
```

---

## 🛠️ Cómo Abrir el Proyecto y Compilar el APK en Android Studio

1. **Abrir en Android Studio**:
   - Descarga o descomprime la carpeta `android`.
   - Abre Android Studio (Ladybug, Koala, Iguana o Flamingo con JDK 17+).
   - Selecciona **File > Open...** y elige la carpeta `android`.
   - Espera a que Gradle sincronice las dependencias automáticas (`Sync Project with Gradle Files`).

2. **Compilar el APK de Depuración (Debug APK)**:
   - En Android Studio: Menú superior **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
   - O desde la terminal en la carpeta `android`:
     ```bash
     ./gradlew assembleDebug
     ```
   - El archivo generado se encontrará en:
     `android/app/build/outputs/apk/debug/app-debug.apk`

3. **Ejecutar en Emulador o Dispositivo Físico**:
   - Conecta un teléfono Android con depuración USB habilitada o inicia un Emulador (Pixel 8 / API 34).
   - Presiona el botón verde de **Run (Shift + F10)**.

---

## 🔒 Configuración Segura de APIs y Gemini en Android

Si integras llamadas a APIs de backend o al SDK de Gemini en Android, sigue estas directrices de seguridad recomendadas por Google:

### 1. Nunca incluyas claves de API en el código fuente ni en el repositorio
Guarda tus claves en el archivo `local.properties` (que está ignorado en Git):
```properties
# local.properties
GEMINI_API_KEY=AIzaSyD_tu_clave_secreta_aqui
BACKEND_BASE_URL=https://api.ingrammicro.pe/v1
```

### 2. Expón las claves a través de `BuildConfig` en `app/build.gradle.kts`:
```kotlin
import java.util.Properties

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    buildFeatures {
        compose = true
        buildConfig = true
    }

    defaultConfig {
        val geminiKey = localProperties.getProperty("GEMINI_API_KEY") ?: ""
        buildConfigField("String", "GEMINI_API_KEY", "\"$geminiKey\"")
    }
}
```

### 3. Uso del SDK Oficial de Google Generative AI en Kotlin:
Agrega la dependencia en `app/build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
}
```
Y crea el cliente de forma segura:
```kotlin
val generativeModel = GenerativeModel(
    modelName = "gemini-1.5-flash",
    apiKey = BuildConfig.GEMINI_API_KEY
)
```

### 4. Recomendación de Arquitectura Empresarial (Proxy Backend):
Para entornos de producción corporativos (como Ingram Micro), se recomienda que las solicitudes a IA o a Microsoft Graph API pasen a través de un **servidor backend proxy** seguro con autenticación de tokens JWT o Microsoft Entra ID (Bearer token), evitando almacenar credenciales de infraestructura en los dispositivos móviles de los usuarios.
