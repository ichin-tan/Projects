# Property Rental System

## Overview
The Property Rental System is an Android application designed to streamline property rental management. 
The project consists of two apps: 
  - Renter App for users searching for rental properties 
  - Landlord App for landlords listing their properties. 
Both apps share a Firebase database for seamless integration.

## Features
### Renter App
- Login and Logout: Firebase Authentication for secure user access.
- Map View Listings: Displays available properties on a map with markers.
- Filter Properties: Search properties by maximum monthly rental price.
- Add to Watchlist: Save properties to a watchlist (login required).
- Manage Watchlist: View and remove properties from the watchlist.

### Landlord App
- Login and Logout: Firebase Authentication for secure user access.
- Create Listings: Add properties with details such as address, image URL, rental price, and number of bedrooms.
- View Listings: View all listed properties in a RecyclerView.
- Update Listings: Modify property details, mark properties as rented, or delete listings.
- Geocoding Integration: Convert addresses to latitude and longitude or use the device's current location.

## Tech Stack
- Programming Language: Kotlin
- Database: Firebase Firestore
- Authentication: Firebase Authentication
- Geocoding API: Google Maps Geocoding API
- Map Integration: Google Maps SDK
- UI Frameworks: ConstraintLayout, RecyclerView, View Binding
- IDE: Android Studio

## Installation
### Prerequisites
- Android Studio installed
- Firebase project set up with Firestore and Authentication enabled

### Steps
1. Clone the repository:
2. Open the project in Android Studio.
3. Add the `google-services.json` file to the `app/` directory.
4. Configure Firebase Authentication and Firestore in the Firebase Console.
5. Build and run the apps on an Android emulator or physical device.

## Usage
### Renter App
1. Login to access full features.
2. Browse properties on the map.
3. Filter properties by price to narrow down your search.
4. Add desired properties to your watchlist.
5. Manage your watchlist by removing unwanted properties.

### Landlord App
1. Login to create and manage property listings.
2. Add new properties with accurate details.
3. View all listed properties in a RecyclerView.
4. Update property details or delete listings as needed.

## Future Enhancements
- Implement notifications for renters and landlords.
- Add property image upload functionality.
- Improve search filters with additional criteria.

## Contributing
Contributions are welcome! Fork the repository, make changes, and submit a pull request.
