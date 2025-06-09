# SolarEase: Android App for IoT Solar Monitoring

SolarEase brings together Android, IoT and Cloud services to enable real-time monitoring of solar installations in home setups.


## Key Highlights
- **Multi-Brand Support**: Custom ESP32 board with switchable MAX232 level-shifting. Works with RS-232 or TTL-232 inverter/charge controller and a WiFi network. Currently, 2 brands have been tested out. Incorporation of more brands is a work in progress.
- **Live Metrics & Historical Reports**: Voltage, current, power output, temperature updated every 10 seconds. Daily, weekly and monthly energy displayed through charts.
- **Weather Integration**: Local cloud-cover, temperature and solar irradiance data. 3-day forecast to help plan energy usage.
- **Offline support**: In-memory TTL caching keeps data info available for up to an hour without intenet connectivity, and auto-syncs when internet connection is restored.

## Simplified System Architecture

```mermaid
graph TD
A[Residential Solar Hardware<br>Inverters, Charge Controllers, Panels] -->|Data| B[ESP32 Adapter<br>Serial-to-Wi-Fi Conversion]
B -->|Wi-Fi| C[Cloud Services<br>Firebase & Azure IoT Hub<br>Data Storage & Processing]
C -->|API| D[Android App<br>Live & Historical Metrics]
```

## Tech Stack
-   **Android**: Kotlin + Jetpack Compose
-   **Firmware**: C (ESP32, MAX232)
-   **Cloud**: Firebase Auth & Firestore, Azure IoT Hub & Functions
-   **APIs**: Open-Meteo for weather
-   **Tools**: Retrofit, Dagger Hilt, Coroutines

## Screenshots

<table>
  <tr>
    <td><img src="screenshots/splash.png" alt="Splash Screen" width="250"/></td>
    <td><img src="screenshots/onboarding.jpg" alt="Onboarding Screen" width="250"/></td>
    <td><img src="screenshots/location.png" alt="Location Screen" width="250"/></td>
  </tr>
  <tr>
    <td><img src="screenshots/home.jpg" alt="Home Screen" width="250"/></td>
    <td><img src="screenshots/battery.jpg" alt="Battery Screen" width="250"/></td>
    <td><img src="screenshots/weather.jpg" alt="Weather Screen" width="250"/></td>
  </tr>
  <tr>
    <td><img src="screenshots/devices.jpg" alt="Devices Screen" width="250"/></td>
    <td><img src="screenshots/reports.jpg" alt="Reports Screen" width="250"/></td>
    <td><img src="screenshots/system-details.jpg" alt="System Details Screen" width="250"/></td>
  </tr>
</table>
