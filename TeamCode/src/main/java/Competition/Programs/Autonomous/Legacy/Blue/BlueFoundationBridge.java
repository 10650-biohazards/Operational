package Competition.Programs.Autonomous.Legacy.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous(name = "Blue Foundation Only | Bridge Park", group = "blue")
@Disabled
public class BlueFoundationBridge extends ExplosiveAuto {

    DriveSubsystem drive;
    //VisionSubsystem vision;
    HookSubsystem hooker;
    curveProcessor3 curve;

    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        Robot.track.setCurrentNode(-1, -3, 90);
        RobotMap.gyro.startAng = 90;

        drive = Robot.drive;
        //vision = Robot.vision;
        hooker = Robot.hooker;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void body() throws InterruptedException {


        drive.moveStrafePow(-1, 500);
        drive.moveTurnPID(90);
        drive.moveStrafePow(-0.5, 300);
        drive.moveRangePID(11, 5000, false);
        hooker.hook();
        u.waitMS(1000);
        drive.moveStrafePow(0.7, 2000);
        hooker.release();
        drive.moveTurnPID(87);
        drive.moveStraightPID(1000);
        drive.moveTurnPID(5);
        drive.moveStraightPID(300);
        drive.moveTurnPID(270);
        drive.moveStraightPID(1000, 1000);
        drive.moveStraightPID(-200);
        drive.moveTurnPID(180);
        drive.moveRangePID(24, 3000, true);
        drive.moveTurnPID(90);
        drive.moveStraightPID(600);
    }

    @Override
    public void exit() throws InterruptedException {

    }
    //Hi Core, I love you ;) - Casey
    /*
    Roses are red, Core is my crush, I love to drink StarBucks
    - Casey
     */
}
