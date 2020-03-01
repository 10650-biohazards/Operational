package Competition.Programs.Autonomous.Active.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "New Red Foundation | Bridge Park", group = "red")
public class NewRedFoundationBridge extends ExplosiveAuto {

    DriveSubsystem drive;
    HookSubsystem hooker;
    curveProcessor3 curve;
    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this);
        robot.enable();
        robot.stopVision();

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
        drive.moveStraightPID(500);
        drive.moveStrafeMod(-0.3, 5000);
        //drive.moveStrafePow(-1, 500);
        //drive.moveTurnPID(90);
        //drive.moveStrafePow(-0.4, 500);
        drive.moveRangePID(15, 5000, true);
        hooker.hook();
        u.waitMS(1000);
        drive.moveStrafeRange(6, 5000, true);
        //drive.moveStrafePow(0.7, 1100);
        drive.moveTurnFound(180);
        drive.moveStrafePow(-0.7, 1200);
        hooker.release();
        drive.moveRangePID(26, 1000, true);
        drive.moveTurnPID(90, 3000);
        drive.moveStraightPID(-1300);
        u.waitMS(30000);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}
