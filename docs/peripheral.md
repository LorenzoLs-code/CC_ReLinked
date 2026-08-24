# Peripherals
The best way to get the peripheral is via peripheral.wrap. **peripheral.find dose not work**. 
> Example: CableHub = peripheral.warp("top")
---
## Cable Hub - functions
Works **like** the Binary- and Analogue input/output from the **redstone library** 
from **CC:tweaked**.

Binary input/output:
- `getChannel(int channel_ID)`
- `setChannel(int channel_ID, boolean value)`

Analogue input/output:
- `getAnalogChannel(int channel_ID)`
- `setAnalogChannel(int channel_ID, int value)`