# Bike Returning System

## Overview
The Bike Returning System is an Android application designed to assist in managing bike rentals and ensuring their return. Users can report found bikes, and the system generates random coordinates within Toronto to simulate the bike's location.
The app uses geocoding to convert coordinates into addresses and places markers on a map.
Reported bikes are listed in a RecyclerView for further actions, such as marking bikes as returned, which removes the corresponding marker from the map.

## Features
- Report a Bike: Generates a random location in Toronto, converts it to an address using geocoding, and places a marker on the map.
- View Reported Bikes: Displays all reported bikes in a RecyclerView.
- Return a Bike: Allows users to mark a bike as returned, removing its marker from the map.
- Firebase Integration: Uses Firebase Firestore for storing and managing bike data.
- Geocoding Integration: Converts latitude and longitude into human-readable addresses.

## Tech Stack
- Programming Language:** Kotlin
- Database: Firebase Firestore
- Map Integration: Google Maps SDK
- Geocoding API: Google Maps Geocoding API
- UI Frameworks: ConstraintLayout, RecyclerView, View Binding
- IDE: Android Studio

## Installation
### Prerequisites
- Android Studio installed
- Firebase project set up with Firestore enabled

### Steps
1. Clone the repository.
2. Open the project in Android Studio.
3. Add the `google-services.json` file to the `app/` directory.
4. Configure Firebase Firestore in the Firebase Console.
5. Build and run the app on an Android emulator or physical device.

## Usage
1. Open the app and navigate to the main screen. Its just a single screen app.
2. Press the Report button to simulate finding a bike. A random location will be generated, converted to an address, and marked on the map.
3. View all reported bikes in the RecyclerView.
4. Use the Return button in the RecyclerView to mark a bike as returned. This will remove the corresponding marker from the map.

## Future Enhancements
- Add user authentication for more personalized features.
- Implement notifications for reporting and returning bikes.
- Enable image upload for reported bikes.
- Improve map features, such as clustering and real-time updates.

## Contributing
Contributions are welcome! Fork the repository, make changes, and submit a pull request.
