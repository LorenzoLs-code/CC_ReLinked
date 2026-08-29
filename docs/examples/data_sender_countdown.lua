-- get the peripheral
p = peripheral.find("ccrl_data_sender")

for i=60, 1, -1 do
    -- clear the buffer
    p.clear()

    -- create text in the buffer
    p.createText("Countdown: " .. i, 10, 10);

    -- send the buffer to the glasses
    p.send()

    -- wait
    os.sleep(1)
end

p.clear()
p.send()
