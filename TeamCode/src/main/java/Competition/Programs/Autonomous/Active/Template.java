package Competition.Programs.Autonomous.Active;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import Competition.Subsystems.VisionSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "Test Auto")
@Disabled
public class Template extends ExplosiveAuto {

    DriveSubsystem drive;
    VisionSubsystem vision;
    HookSubsystem hooker;
    curveProcessor3 curve;

    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this);
        robot.enable();

        Zooker.track.setCurrentNode(-1.5, -2.625, 0);
        ZookerMap.gyro.startAng = 0;

        drive = Zooker.drive;
        vision = Zooker.vision;
        hooker = Zooker.hooker;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void body() throws InterruptedException {
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
        hooker.hook();
        u.waitMS(1000);
        hooker.release();
        u.waitMS(1000);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}
