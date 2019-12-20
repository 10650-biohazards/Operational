package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "New Red Foundation | Wall Park", group = "red")
public class NewRedFoundationWall extends ExplosiveAuto {

    DriveSubsystem drive;
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
        drive.moveStrafePow(-0.4, 500);
        drive.moveRangePID(15, 5000, true);
        hooker.hook();
        u.waitMS(1000);
        drive.moveStrafePow(0.7, 1100);
        drive.moveTurnFound(180);
        drive.moveStrafePow(-0.7, 1200);
        hooker.release();
        drive.moveStraightPID(1000, 1000);
        drive.moveTurnPID(190);
        drive.moveStrafePow(1, 700);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}
