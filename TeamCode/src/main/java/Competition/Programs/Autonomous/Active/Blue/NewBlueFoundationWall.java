package Competition.Programs.Autonomous.Active.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "New Blue Foundation | Wall Park", group = "blue")
public class NewBlueFoundationWall extends ExplosiveAuto {

    DriveSubsystem drive;
    HookSubsystem hooker;
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
        drive.moveRangePID(10, 5000, false);
        hooker.hook();
        u.waitMS(1000);
        drive.moveStrafeRange(6, 5500, true);
        drive.moveTurnFound(5);
        drive.moveStrafePow(-0.7, 700);
        hooker.release();
        drive.moveStraightPID(-1000, 1000);
        drive.moveStrafePow(0.5, 300);
        drive.moveTurnPID(100);
        drive.moveStraightPID(1200);
        u.waitMS(30000);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}