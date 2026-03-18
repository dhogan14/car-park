<a id="readme-top"></a>
<div align="center">
<h3 align="center">Car Park</h3>
  <p align="center">
    A microservice to manage a car park.
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This microservice is made to manage your car park locally.

Assumptions:
* Used data structures over in-memory DB for time purposes, should that be required you could use hsqldb.
* BillId should be unique as looking forward to potential DB integration it's the easiest to use as a unique id.
* No security/auuthorisation was required. Should that have been needed the @Secured tag could be used to specify roles the user requires to access the APIs.
* Currently the car park size is set to 3 for quick and easy testing. If you would like to change that number look in the application.properties file and change the size there.
* There are no current check on the car park size (negative number, etc). Assumed that it would always take the form of 0 < size < max int value.

Going Forward:
* Should this be used with a DB the BillId could be used as the Id, but would require an extra check to ensure no duplicate vehicle reg numbers are entered.
* Add input validation for SQL injection.
* Increase unit test coverage.
* Add the cost per minute values to the projects properties so changing them is easier in the future.
* Add proper logging with something like @Slf4j for easier debugging/tracing.

Details of how to run and use this project are described below.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- GETTING STARTED -->
## Getting Started

Below is an example of how you can instruct your audience on installing and setting up this service.

1. Clone the repo
   ```sh
   git clone https://github.com/dhogan14/car-park
   ```
2. Run the command
    ```sh
    mvn clean install
    ```
   This installs the required dependencies. It should only need to be run before starting the project locally for the first time and may take a few minutes.
   If mvn isn't recognised in your terminal you should check if your editor has maven enabled and if working with IntelliJ you can use the maven window found on the right-hand side of the editor to Execute Maven Goal with the same command as above.
3. Run the command
   ```sh
    mvn spring-boot:run
   ```
4. Once you see "Application Started" appear in the terminal it's ready to receive requests.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

There are 3 API endpoints in this project: getCapacity, enterCar, removeCar.

### 1. getCapacity
URL: localhost:8080/parking

REST method: GET

Request parameters: N/A

Request Body: N/A

Response Codes:
* 200 OK - A JSON object describing the car parks occupancy.

Response Body:
* availableSpaces
* occupiedSpaces

### 2. enterCar
URL: localhost:8080/parking

REST method: POST

Request parameters: N/A

Request Body: A JSON object of the vehicle entering the car park described with the fields:
* vehicleReg - String - The vehicles registration number.
* vehicleType - int - The vehicles type - 1: small, 2: medium, 3: large.

Responses:
* 200 OK - A JSON object of vehicles parking details.
* 400 BAD_REQUEST - The input provided wasn't allowed to be added.
* 507 INSUFFICIENT_STORAGE - there's no space in the car park to park the vehicle.

Response Body:
* vehicleReg - String - The vehicles registration number;
* spaceNumber - int - The car park space assigned to the vehicle.
* timeIn - LocalDateTime - The date-time at which the vehicle entered the car park.

### 3. removeCar
URL: localhost:8080/parking/bill

REST method: POST

Request parameters: N/A

Request Body: A String vehicle registration of the car to leave the car park;

Response(s):
* 200 OK - A JSON object describing the vehicle leaving and the charge for the stay.
* 400 BAD_REQUEST - The vehicles registration couldn't be found in the car park.

Response Body:
* vehicleReg - String - The vehicles registration number;
* spaceNumber - int - The car park space assigned to the vehicle.
* timeIn - LocalDateTime - The date-time at which the vehicle entered the car park.
* timeOut - LocalDateTime - The date-time at which the vehicle leaves the car park.
* billId - String - A unique representation of this vehicles stay at the car park. Of the form vehicleReg + timeIn.
* vehicleCharge - double - The cost for the vehicle staying at the car park.

<!-- CONTACT -->
## Contact

Donal Hogan - Software Engineer

Project Link: [https://github.com/dhogan14/car-park](https://github.com/dhogan14/car-park)

<p align="right">(<a href="#readme-top">back to top</a>)</p>