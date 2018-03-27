#include <SoftwareSerial.h>
#include <ArduinoJson.h>
SoftwareSerial Bluetooth (7,8); // RX and TX Pins
const size_t bufferSize = JSON_OBJECT_SIZE(3);
String Output;
int Button;
float Timerino;    
const int State = 0;
int previous,Shutter;
boolean First = false;

void FirstPackage(float Weight){

    DynamicJsonBuffer jsonBuffer(bufferSize);
    JsonObject& root = jsonBuffer.createObject();
    root["Type"] = "Initial";
    root["Weight"] = Weight;
    root.printTo(Output);
    Bluetooth.print(Output);
    Serial.println(Output);
    Output="";
}

void Pulls(float Uptime,float Downtime){

    DynamicJsonBuffer jsonBuffer(bufferSize);
    JsonObject& root = jsonBuffer.createObject();
    root["Type"] = "Measurement";
    root["Up"] = Uptime;
    root["Down"] = Downtime;
    root.printTo(Output);  
    Bluetooth.print(Output);
    Serial.println(Output); 
    Output="";                    
}

void Debounce(){
  if (Button == State){  // this function debounces the button so a single button press won't trigger multiple times 
      delay(200);
  }
}

void setup() {
  Bluetooth.begin(9600);
  Serial.begin(9600);
  pinMode(11,OUTPUT);
  digitalWrite(11,HIGH);
  pinMode(4,INPUT_PULLUP); // this enables Arduino's internal pull up resistor so you don't need to get an external resistor to your button circuit
}


void loop() {
      previous = Button;
      Debounce();
      Button = digitalRead(4);

      
      if(Button == 0) {
         if (First == false){
            FirstPackage(68.5);
            First = true;
         }
         else
          Pulls(millis()/1000,millis()/1000+2);
          
      if (Button == 0 && previous == 0)
      {
        Shutter++;
        if( Shutter ==5){
        digitalWrite(11,LOW);
        delay(8000);
        digitalWrite(11,HIGH);
        Shutter = 0;
        First = false;
        }
      }
      }
      
   
}
