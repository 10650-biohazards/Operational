package Competition.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import Competition.Robot;
import Competition.RobotMap;
import FtcExplosivesPackage.Subsystem;

public class HookSubsystem extends Subsystem {

    private Servo hooker;
    private Servo skyGrabber;

    public HookSubsystem(OpMode op) {
        super(op);
    }

    @Override
    public void enable() {
        hooker = RobotMap.hooker;
        skyGrabber = RobotMap.skyGrabber;
        RobotMap.theBooker.setPosition(0.3472);
    }

    @Override
    public void disable() {

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
