package ucl.cs.camera;

public class Camera implements WriteListener{
    private final MemoryCard memorycard;
    private final Sensor sensor;
    private boolean power = false;


    public Camera(MemoryCard m, Sensor s){
        memorycard = m;
        sensor = s;
    }


  public void pressShutter() {
        if (power) {
            byte[] x = sensor.readData();
            memorycard.write(x);
        }
  }

  public void powerOn() {
    power = true;
    sensor.powerUp();
  }

  public void powerOff() {
      power = false;
      sensor.powerDown();
  }

  public void writeComplete(){
    return;
  }

}

