package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.VisionSubsystem;
import DubinsCurve.Node;
import DubinsCurve.curveProcessor3;
import DubinsCurve.myPoint;
import FtcExplosivesPackage.ExplosiveAuto;

@Autonomous(name = "Drive Subsystem Test")
public class DriveSubsystemTesting extends ExplosiveAuto {

    public DcMotor bright, fright, bleft, fleft;

    DriveSubsystem drive;
    VisionSubsystem vision;
    curveProcessor3 curve;

    LinearOpMode op;



    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        Robot.track.setCurrentNode(1.5, -2.625, 0);
        RobotMap.gyro.startAng = 90;

        drive = Robot.drive;
        vision = Robot.vision;
        bleft = robotMap.bleft;
        fleft = robotMap.fleft;
        bright = robotMap.bright;
        fright = robotMap.fright;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

    }
    @Override
    public void body() throws InterruptedException {
            double blInitPos = Math.abs(bleft.getCurrentPosition());
            while (true) {
                bleft.setPower(-0.3);
                fleft.setPower(0.3);
                bright.setPower(-0.3);
                fright.setPower(0.3);

                if (blInitPos > 1000 + bleft.getCurrentPosition() || !op.opModeIsActive()) break;
            }
            bleft.setPower(0.0);
            fleft.setPower(0.0);
            bright.setPower(0.0);
            fright.setPower(0.0);

    }

    @Override
    public void exit() throws InterruptedException {
        bleft.setPower(0.0);
        fleft.setPower(0.0);
        bright.setPower(0.0);
        fright.setPower(0.0);
    }
}
