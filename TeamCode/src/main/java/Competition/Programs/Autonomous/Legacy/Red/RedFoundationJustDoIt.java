package Competition.Programs.Autonomous.Legacy.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous(name = "Red Foundation | Just Do It", group = "red")
@Disabled
public class RedFoundationJustDoIt extends ExplosiveAuto {

    DriveSubsystem drive;
    HookSubsystem hooker;
    curveProcessor3 curve;
    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this);
        robot.enable();

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
        drive.moveStrafePow(-1, 500);
        drive.moveTurnPID(90);
        drive.moveStrafePow(-0.5, 300);
        drive.moveRangePID(14, 5000, true);
        hooker.hook();
        u.waitMS(1000);
        drive.moveStrafePow(0.7, 2000);
        drive.moveTurnFound(90);
        hooker.release();
        drive.moveStraightPID(-1000);
        drive.moveTurnPID(175);
        drive.moveStraightPID(-350);
        drive.moveTurnPID(90);
        drive.moveStraightPID(1000, 1000);
        drive.moveStraightPID(-600);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}