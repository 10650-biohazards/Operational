package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous(name = "Red Foundation | Wall Park", group = "red")
public class RedFoundationWall extends ExplosiveAuto {

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

        Robot.track.setCurrentNode(1, -3, 90);
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
        drive.moveStrafePow(-1, 470);
        drive.moveTurnPID(90);
        drive.moveStrafePow(-0.5, 300);
        drive.moveRangePID(15, 5000, true);
        hooker.hook();
        u.waitMS(1000);
        drive.moveStrafePow(0.7, 2000);
        drive.moveTurnFound(93);
        hooker.release();
        drive.moveStraightPID(-1000);
        drive.moveTurnPID(175);
        drive.moveStraightPID(-350);
        drive.moveTurnPID(90);
        drive.moveStraightPID(2000, 1000);
        drive.moveStraightPID(-200);
        drive.moveTurnPID(180);
        drive.moveStraightPID(1000, 2000);
        drive.moveStraightPID(-100);
        drive.moveTurnPID(270);
        drive.moveStraightPID(600);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}