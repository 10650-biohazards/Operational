package Competition.Programs.Autonomous.Active.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "Red Move | Bridge Park", group = "red")
public class RedMoveBridge extends ExplosiveAuto {

    DriveSubsystem drive;
    HookSubsystem hooker;
    curveProcessor3 curve;
    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this);
        robot.enable();

        Zooker.track.setCurrentNode(1, -3, 0);
        ZookerMap.gyro.startAng = 0;

        drive = Zooker.drive;
        hooker = Zooker.hooker;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void body() throws InterruptedException {
        drive.moveStraightPID(2000);
        u.waitMS(22000);
        drive.moveRangePID(27, 2000, false);
        drive.moveTurnPID(5, 1000);
        drive.moveStrafePow(-0.4, 2000);
        u.waitMS(30000);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}