**Project Overview**

SmartPark is a Java-based REST API application for managing urban parking lots and vehicles.
It allows users to:

-Register parking lots
-Register vehicles
-Check in/out vehicles
-View current parking lot status
-View all vehicles currently parked

**Prerequisites**
-Java 17 installed and configured in your system PATH
-Maven 3.11.0 installed and configured in your system PATH
-Git (for cloning the repository)

**Verify installation:**
java -version
mvn -version
git --version

**Build & Run**
1.mvn clean install
2.mvn spring-boot:run

**API Endpoints**
Parking Lot
POST /api/parking-lots → Register a parking lot
GET /api/parking-lots/{lotId} → Get lot status
GET /api/parking-lots/{lotId}/vehicles → Get all vehicles in a lot
POST /api/parking-lots/{lotId}/checkin?licensePlate=XYZ → Check in a vehicle
POST /api/parking-lots/{lotId}/checkout?licensePlate=XYZ → Check out a vehicle

Vehicle
POST /api/vehicles → Register a vehicle
GET /api/vehicles/parked → Get all currently parked vehicles

**API Testing**
Postman collection included: postman/SmartPark-APIs.json
Postman environment included: postman/SmartPark-Env.postman_environment.json

**Notes on Environment**
The environment contains a variable:{{lotId}} which holds a preloaded parking lot ID.

This allows you to reuse the same lot ID across requests without manually editing URLs for check-in, check-out, and status endpoints.

To use it:
1.Import SmartPark-Env.postman_environment.json into Postman.
2.Select the environment from the dropdown.
3.All requests that use {{lotId}} will automatically use the preloaded lot ID.

**Notes**
Dummy data is preloaded in the H2 database at startup.
Unit tests are included under src/test/java.

