package Competition.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import Competition.ZookerMap;
import FtcExplosivesPackage.Subsystem;

public class HookSubsystem extends Subsystem {

    private Servo hooker;
    private Servo skyGrabber;

    public HookSubsystem(OpMode op) {
        super(op);
    }

    @Override
    public void enable() {
        hooker = ZookerMap.hooker;
        skyGrabber = ZookerMap.skyGrabber;
    }

    @Override
    public void disable() {

    }

    public void book() {
        ZookerMap.theBooker.setPosition(0.2972);
    }

    public void protect() {
        ZookerMap.theBooker.setPosition(0.42);
    }

    public void prepare() {
        ZookerMap.hooker.setPosition(0.58);
    }

    public void prepare2() {
        ZookerMap.hooker.setPosition(0.62);
    }

    public void hook() {
        hooker.setPosition(0.45);
    }

    public void release() {
        hooker.setPosition(1);
    }

    public void releaseSkystone() {
        skyGrabber.setPosition(0.2);
    }

    public void grabSkystone() {
        skyGrabber.setPosition(0.7);
    }

    @Override
    public void stop() {
        release();
    }
}
