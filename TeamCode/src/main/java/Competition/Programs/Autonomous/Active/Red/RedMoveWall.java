package Competition.Programs.Autonomous.Active.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "Red Move | Wall Park", group = "red")
public class RedMoveWall extends ExplosiveAuto {

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
        drive.moveStraightPID(2000);
        u.waitMS(23000);
        drive.moveStraightRaw(-1000);
        u.waitMS(500);
        drive.moveStrafePow(-0.3, 2000);
        u.waitMS(30000);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}