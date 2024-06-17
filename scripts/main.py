import ledlib
import time

counter = 0
direction = 0

while True:
    if direction == 0:
        counter += 1
    elif direction == 1:
        counter -= 1

    ledlib.writeRGB(counter, counter, counter, 10)
    

    if counter >= 255:
        direction = 1
    elif counter <= 0:
        direction = 0
    