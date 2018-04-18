#include <SoftwareSerial.h>
#include <ArduinoJson.h>
SoftwareSerial Bluetooth (7,8); // RX and TX Pins
const size_t bufferSize = JSON_OBJECT_SIZE(3);
String output;
int button;
float Timerino;
const int state = 0;
boolean first = false;

void BufferCreator(){
  if (first == false){          // this is the initial package which is only sent once
    DynamicJsonBuffer jsonBuffer(bufferSize);
    JsonObject& root = jsonBuffer.createObject();
    root["type"] = "Initial";
    root["machine_ID"] = 1;
    root["weight"] = 85.5;
    root.printTo(output);
  } 
  
  else{                         // after the initial package button press will send pull-up data
    DynamicJsonBuffer jsonBuffer(bufferSize);
    JsonObject& root = jsonBuffer.createObject();
    root["type"] = "Measurement";
    root["up"] = Timerino/1000;
    root["down"] = Timerino/1000 + random(1,2); // this just randomises the down time so there is slight variation. don't press the button too fast or otherwize
    root.printTo(output);                       // the downtime might be later than the beginning of the next pull up 
    
  }
}
void debounce(){
  if (button == state){  // this function debounces the button so a single button press won't trigger multiple times 
      delay(200);
  }
}

void setup() {
  Bluetooth.begin(9600);
  Serial.begin(9600);
  pinMode(4,INPUT_PULLUP); // this enables Arduino's internal pull up resistor so you don't need to get an external resistor to your button circuit
}


void loop() {
   
      debounce();
      Timerino = millis();
      button = digitalRead(4);
      
      if(button == 0) {
       BufferCreator();
       first = true;
       Bluetooth.print(output);
      Serial.println(output);
      output="";
      }
      
   
}
