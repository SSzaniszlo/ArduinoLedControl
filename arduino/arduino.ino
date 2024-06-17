int R = 9;
int G = 10;
int B = 11;

void setup() {
  Serial.begin(9600);
  pinMode(R, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(B, OUTPUT);
}

void loop() {
  if (Serial.available() > 0) {
  

    byte colour[3];

    Serial.readBytes(colour, 3);
    //int g = Serial.readBytesUntil()

    analogWrite(R, colour[0]);

    analogWrite(G, colour[1]);

    analogWrite(B, colour[2]);

    

  }
}
