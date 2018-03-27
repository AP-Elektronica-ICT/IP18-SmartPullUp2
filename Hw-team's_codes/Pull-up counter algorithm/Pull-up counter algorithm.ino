#include <MedianFilter.h>
#include <MsTimer2.h>

MedianFilter median(5, 460);

const int sampling = 5; 
int oldInput = 0;
int input=0;
int sampleTime = 0;
int filteredOutput=0;
int output;
int i = 0;
float average = 0.0;
float feedback = 0.0;


void setup() {
Serial.begin(38400);
MsTimer2::set(sampling,measure);
MsTimer2::start();
pinMode(A0,INPUT);
}


void measure(){
for(i = 0; i < 30; i++)
  {
    input = analogRead(A0);
    median.in(input);
    input = median.out();
    average = average + input;
  }
average = average / 30.0;
feedback = feedback * 0.8 + 0.2 * average;

output = feedback;

Serial.print(sampleTime);
Serial.print("\t");
Serial.println(output);

sampleTime += sampling;
}


void loop() {
}
