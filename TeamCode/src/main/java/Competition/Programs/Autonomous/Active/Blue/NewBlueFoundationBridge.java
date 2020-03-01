package Competition.Programs.Autonomous.Active.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Subsystems.ParkSubsystem;
import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "New Blue Foundation | Bridge Park", group = "blue")
public class NewBlueFoundationBridge extends ExplosiveAuto {

    DriveSubsystem drive;
    HookSubsystem hooker;
    ParkSubsystem ben;
    curveProcessor3 curve;
    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this);
        robot.enable();
        //robot.stopVision();

        Zooker.track.setCurrentNode(1, -3, 90);
        ZookerMap.gyro.startAng = 90;

        drive = Zooker.drive;
        hooker = Zooker.hooker;
        ben = Zooker.park;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void body() throws InterruptedException {
        hooker.book();
        drive.moveStraightPID(-500);
        drive.moveStrafeMod(-0.3, 5000);
        //drive.moveStrafePow(-1, 500);
        //drive.moveTurnPID(90);
        //drive.moveStrafePow(-0.3, 700);
        drive.moveRangePID(10, 5000, false);
        hooker.hook();
        u.waitMS(1000);
        drive.moveStrafeRange(6, 5500, true);
        //drive.moveStrafePow(0.7, 1400);
        drive.moveTurnFound(5);
        drive.moveStrafePow(-0.7, 700);
        hooker.release();
        drive.moveRangePID(30, 1000, false);
        hooker.protect();
        drive.moveTurnPID(90, 3000);
        drive.moveStraightPID(1300, 1000);
        ben.extend(500, true);
        u.waitMS(30000);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}