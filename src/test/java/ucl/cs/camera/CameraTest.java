package ucl.cs.camera;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class CameraTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    MemoryCard mem = context.mock(MemoryCard.class);

    Sensor sensor = context.mock(Sensor.class);

    Camera testCam = new Camera(mem, sensor);

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        // this is what you expect to see happen after your camera command
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerUp();
        }});
        // camera command to
        testCam.powerOn();
    }

    @Test
    public void switchingTheCameraOffPowersDownTheSensor() {
        // this is what you expect to see happen after your camera command
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerDown();
        }});
        testCam.powerOff();
    }

    @Test
    public void pressingShutterWhilstCameraOffDoesNothing() {

        // this is what you expect to see happen after your camera command
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerDown();
            exactly(0).of(sensor).readData();
        }});
        testCam.powerOff();
        testCam.pressShutter();
    }

    @Test
    public void pressingShutterWhilstCameraOnCopiesData() {

        //byte[] IMAGE = new byte[15];

        // this is what you expect to see happen after your camera command
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerUp();
            exactly(1).of(sensor).readData();
            // exactly(1).of(sensor).readData(); will(returnValue(IMAGE));
            exactly(1).of(mem).write(with(any(byte[].class)));
            // or exactly(1).of(mem).write(IMAGE);

        }});
        testCam.powerOn();
        testCam.pressShutter();
    }

    @Test
    public void cameraDoesNotTurnOffSensorIfDataWritingNotComplete() {

        // this is what you expect to see happen after your camera command
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerUp();
            exactly(1).of(sensor).readData();
            exactly(1).of(mem).write(with(any(byte[].class)));
            exactly(0).of(sensor).powerDown();

        }});
        testCam.powerOn();
        testCam.pressShutter();
        testCam.powerOff();
    }

    @Test
    public void cameraPowersDownAfterWritingComplete() {

        // this is what you expect to see happen after your camera command
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerUp();
            exactly(1).of(sensor).readData();
            exactly(1).of(mem).write(with(any(byte[].class)));
            exactly(0).of(sensor).powerDown();

        }});
        testCam.powerOn();
        testCam.pressShutter();
        testCam.powerOff();
    }




}
