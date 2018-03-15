#include <SoftwareSerial.h>
#include <ArduinoJson.h>
SoftwareSerial Bluetooth (7,8); // RX and TX Pins
const size_t bufferSize = JSON_OBJECT_SIZE(3);
String output;



void BufferCreator(){
    DynamicJsonBuffer jsonBuffer(bufferSize);
    JsonObject& root = jsonBuffer.createObject();
    root["type"] = "Initial";
    root["machine_ID"] = 1;
    root["weight"] = 85.5;
    root.printTo(output);
}


void setup() {
  Bluetooth.begin(9600);
  Serial.begin(9600);
  Serial.print("Press 1 to send test package ( Bluetooth Baud rate 9600) \r\nPress 2 to select AT coding mode(You need to swich baud rate to 38400 for Bluetooth!) \r\n");
}


void loop() {
  
while(Serial.available() > 0){
   
  int mode = Serial.read()-48;
  switch (mode) {
    case 1:
    {
      BufferCreator();
      Serial.println("Sending test package");
      Bluetooth.print(output);
      Serial.println(output);
      output="";
      delay(200);
      break;
    }
    
    case 2:
    {
      Serial.println("AT mode enabled");
      while(mode == 2)
        {
          if(Serial.available() >0){
            Bluetooth.write(Serial.read());
        }
        
          if(Bluetooth.available() > 0){
            Serial.write(Bluetooth.read());
        }
    }
      
      }
    
    }
  
  }
}
