package Competition.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Competition.ZookerMap;
import FtcExplosivesPackage.Subsystem;
import Utilities.Utility;

public class ParkSubsystem extends Subsystem {

    Utility u;
    DcMotor theDooker;

    public ParkSubsystem(LinearOpMode op) {
        super(op);
        u = new Utility(op);
    }

    @Override
    public void enable() {
        theDooker = ZookerMap.theDooker;
    }

    @Override
    public void disable() {

    }

    public void extend(int t, boolean forward) {
        theDooker.setPower(forward ? 1 : -1);
        u.waitMS(t);
        theDooker.setPower(0);
    }

    @Override
    public void stop() {

    }
}
