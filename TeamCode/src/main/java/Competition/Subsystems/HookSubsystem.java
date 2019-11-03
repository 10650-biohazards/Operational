package Competition.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import Competition.RobotMap;
import FtcExplosivesPackage.Subsystem;

public class HookSubsystem extends Subsystem {

    private Servo hooker;

    public HookSubsystem(OpMode op) {
        super(op);
    }

    @Override
    public void enable() {
        hooker = RobotMap.hooker;
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

    //HEY, THIS ONE ALSO WORK FOR PUSHING UPPER EDGE OF SKYSTONE
    public void skystone() {hooker.setPosition(0.7);}

    public void grabSkystone() {
        hooker.setPosition(0.59);
    }

    @Override
    public void stop() {
        release();
    }
}
