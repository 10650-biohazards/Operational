package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import Competition.Subsystems.VisionSubsystem;
import DubinsCurve.Node;
import DubinsCurve.curveProcessor3;
import DubinsCurve.myPoint;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous(name = "Red Skystone Only", group = "red")
public class RedSkyOnly extends ExplosiveAuto {

    DriveSubsystem drive;
    VisionSubsystem vision;
    HookSubsystem hook;
    curveProcessor3 curve;
    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        Robot.track.setCurrentNode(-1.5, -2.625, 0);
        RobotMap.gyro.startAng = 0;

        drive = Robot.drive;
        vision = Robot.vision;
        hook = Robot.hooker;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {


        waitForStart();

        int skyPos = vision.grabSkyPos();

        telemetry.addData("pos", skyPos);
        telemetry.update();

        u.waitMS(2000);

        drive.moveStraightPID(700);
        drive.moveTurnPID(90);

        if (skyPos == 0) {
            drive.moveRangePID(16, 3000, false);
            drive.moveStrafePow(-0.7, 300);
            hook.skystone();
            u.waitMS(1000);
            drive.swingTurnPID(135, true);
            drive.moveStraightModded(1000, 4000);
            drive.swingTurnSlow(90, false);
            drive.moveStraightModded(750, 1000);
            hook.release();
            drive.moveStraightPID(-300);
        } else if (skyPos == 1) {
            drive.moveStrafePow(-0.7, 300);
            hook.skystone();
            u.waitMS(1000);
            drive.swingTurnPID(135, true);
            drive.moveStraightModded(1000, 4000);
            drive.swingTurnSlow(90, false);
            drive.moveStraightModded(300, 1000);
            hook.release();
            drive.moveStraightPID(-300);
        } else {
            drive.moveRangePID(8.5, 3000, false);
            drive.moveStrafePow(-0.7, 300);
            hook.skystone();
            u.waitMS(1000);
            //drive.swingTurnPID(100, false);
            drive.swingTurnPID(135, true);
            drive.moveStraightModded(700, 4000);
            drive.swingTurnSlow(90, false);
            drive.moveStraightModded(1100, 4000);
            hook.release();
            drive.moveStraightPID(-300);
        }
    }

    @Override
    public void body() throws InterruptedException {

    }

    @Override
    public void exit() throws InterruptedException {

    }
}
