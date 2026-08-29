# Peripherals
The best way to get the peripheral is via peripheral.find(`name`)
> Example: CableHub = peripheral.find("ccrl_cablehub")
---
## Cable Hub - functions
Works **like** the Binary- and Analogue input/output from the **redstone library** 
from **CC:tweaked**.

Name: `"ccrl_cablehub"`

Binary input/output:
- `getChannel(int channel_ID)`
- `setChannel(int channel_ID, boolean value)`

Analogue input/output:
- `getAnalogChannel(int channel_ID)`
- `setAnalogChannel(int channel_ID, int value)`


## Data Sender - functions
This is for controlling an overlay that apprises when the data glasses are equipment.
Thees functions are basically **manipulating a buffer**. In the buffer you can create/delete text, 
and clear it. When you send it you basically **create a copy** of the buffer that is than given to the data glasses.
>The most important function is `send()`

Name: `"ccrl_data_sender"`

### Util
- `send()` sends the data to the glasses
- `clear()` clears all the data

### Text
- `createText(String text, int x, int y)`| returns: **text_index**
- `deleteText(text_index)`

### Fill
This a rectangle with a specific color
- `createFill(int x1, int y1, int x2, int y2, int color)`| returns: **fill_index**
- `deleteFill(int fill_index)`