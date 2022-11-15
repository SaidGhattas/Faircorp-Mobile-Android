# Faircorp-Mobile-Android

This project is an Android application that allows to control the temperature of the rooms of a building.


#
# Features

## Data
- This application is connected to the Faircorp-spring database which allows it to use an online API.
- This application implements a database updated from the online API which allows for offline access to the required data.
#
## Activities:

### Main activity: 
- list of all the buildings

### Rooms activity: 

- list of rooms of a building (filtered by the ID of a building) using a RecyclerView
- Each Room can be clicked on to go to the room activity be edited
- Each Room has two buttons to go to the window activity or the heater activity
- Each Room shows its current and target temperature of a room

### Windows activity:

- list of windows of a room (filtered by the ID of a room) using a RecyclerView
- Each Window can be clicked on to go to the window activity to be edited
- Each Window has a switch to open or close the window
- Each Window shows its current status

### Heaters activity:

- list of heaters of a room (filtered by the ID of a room) using a RecyclerView
- Each Heater can be clicked on to go to the heater activity to be edited
- Each Heater has a switch to turn on or off the heater
- Each Heater shows its current status

### Room, Window, Heater activities:

- These activities can be reached from the Rooms, Windows, Heaters activities.
- Used to create or edit a room, window or heater.

#
### Author:  *Said Ghattas*
