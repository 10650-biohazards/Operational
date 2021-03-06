package Competition.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Competition.ZookerMap;
import FtcExplosivesPackage.Subsystem;

public class IntakeSubsystem extends Subsystem {

    DcMotor intLeft, intRight;

    public IntakeSubsystem(OpMode op) {
        super(op);
    }

    @Override
    public void enable() {
        intLeft = ZookerMap.intLeft;
        intRight = ZookerMap.intRight;

        intLeft.setPower(0);
        intRight.setPower(0);
    }

    @Override
    public void disable() {

    }

    public void intake() {
        intLeft.setPower(1);
        intRight.setPower(1);
    }

    public void outtake() {
        intLeft.setPower(-1);
        intRight.setPower(-1);
    }

    public void halt() {
        intLeft.setPower(0);
        intRight.setPower(0);
    }

    @Override
    public void stop() {
        intRight.setPower(0);
        intLeft.setPower(0);

    }
}
