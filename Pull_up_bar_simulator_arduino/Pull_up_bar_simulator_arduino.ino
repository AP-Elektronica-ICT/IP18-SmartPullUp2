#include <ArduinoJson.h> // Include this library to work with JSON data but you have to install it first. See for more info https://arduinojson.org/
#include <SoftwareSerial.h> // Include this library to work with the HC-05 Bluetooth module


// Memory pool for JSON object tree.
//
// Inside the brackets, bufferSize is the size of the pool in bytes.
// Don't forget to change this value to match your JSON document.
// Use arduinojson.org/assistant to compute the capacity.
//*StaticJsonBuffer<200> jsonBuffer;

// StaticJsonBuffer allocates memory on the stack, it can be
// replaced by DynamicJsonBuffer which allocates in the heap.
//
const size_t bufferSize = JSON_OBJECT_SIZE(3);
DynamicJsonBuffer jsonBuffer(bufferSize);

// Create the root of the object tree.
//
// It's a reference to the JsonObject, the actual bytes are inside the
// JsonBuffer with all the other nodes of the object tree.
// Memory is freed when jsonBuffer goes out of scope.
JsonObject& root = jsonBuffer.createObject();

SoftwareSerial BTserial(10, 11); // RX | TX

const int upPin = 2; //Button for the up counting
const int downPin = 3; //Button for the down counting 
const int resetPin = 4; //Reset button

int downState = 0;
int upState = 0;
int resetState = 0;

//To store the millis
//
long pullUp = 0; 
long pullDown = 0;


void setup() {

  pinMode(upPin, INPUT);
  pinMode(downPin, INPUT);
  pinMode(resetPin, INPUT);

  //Initialize Serial Bluetooth
  BTserial.begin(9600);

  // Initialize Serial port
  Serial.begin(9600);
  while (!Serial) continue;


  // Add values in the object
  //
  // Most of the time, you can rely on the implicit casts.
  // In other case, you can do root.set<long>("time", 1351824120);


  // Add a nested array.
  //
  // It's also possible to create the array separately and add it to the
  // JsonObject but it's less efficient.
  //JsonArray& data = root.createNestedArray("data");
  //data.add(48.756080);
  //data.add(2.302038);

  root.printTo(Serial);

  // This prints for example JSON in 1 line:
  // {"sensor":"gps","time":1351824120,"data":[48.756080,2.302038]}

  Serial.println();

  root.prettyPrintTo(Serial);

  // This prints for exapmle:
  // {
  //   "sensor": "gps",
  //   "time": 1351824120,
  //   "data": [
  //     48.756080,
  //     2.302038
  //   ]
  // }
}

void loop() {

  //Simulating the data with 2 buttons 

  unsigned long currentMillis = millis(); //Starting time to count the up and down in the pull up bar like in the dummy date

  //Reading buttons 
  //
  downState = digitalRead(downPin);
  upState = digitalRead(upPin);
  resetState = digitalRead(resetPin);

  if (resetState == HIGH) {
    pullUp = 0;
    pullDown = 0;
    currentMillis = 0;
  }

  if (upState == HIGH) {
    pullUp = currentMillis;
  }
  else if (downState == HIGH) {
    pullDown = currentMillis;
  }

  //Adding values to the JSON structure
  //
  if (pullUp > 0 | pullDown > 0) {
    root["Type"] = "measurement";
    root["Up"] = pullUp;
    root["Start"] = pullDown;
  }
  //Initial state before pressing any button
  //
  else {
    root["Type"] = "Initial";
    root["Machine_ID"] = 1;
    root["Weight"] = 85.5;
  }

  //Printing JSON structure in a string
  //
  String output;
  root.printTo(output);

  //Sending the JSON data in a string via BLuetooth
  BTserial.print(output);
  Serial.println(output);

  delay(20);
}
