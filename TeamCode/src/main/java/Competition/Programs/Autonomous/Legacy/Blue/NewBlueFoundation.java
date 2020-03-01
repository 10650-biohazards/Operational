package Competition.Programs.Autonomous.Legacy.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "New Blue Foundation")
@Disabled
public class NewBlueFoundation extends ExplosiveAuto {

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
        drive.moveRangePID(12, 5000, false);
        hooker.hook();
        u.waitMS(1000);
        drive.moveStrafePow(0.7, 1000);
        drive.moveTurnFound(5);
        drive.moveStrafePow(-0.7, 700);
        hooker.release();
        drive.moveStrafePow(1, 800);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}
