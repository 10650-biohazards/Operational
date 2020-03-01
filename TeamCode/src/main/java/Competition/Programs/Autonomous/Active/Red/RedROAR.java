package Competition.Programs.Autonomous.Active.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import Competition.Subsystems.ParkSubsystem;
import Competition.Subsystems.VisionSubsystem;
import Competition.Zooker;
import Competition.ZookerMap;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;
import VisionPipelines.FoundationPipeline;

@Autonomous (name = "Red ROAR", group = "red")
public class RedROAR extends ExplosiveAuto {

    DriveSubsystem drive;
    HookSubsystem hooker;
    ParkSubsystem ben;
    VisionSubsystem vision;
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
        vision = Zooker.vision;

        curve = new curveProcessor3(drive, telemetry, this);

        vision.enableFoundation();

        FoundationPipeline.setRed(true);
    }

    @Override
    public void initAction() {
        hooker.book();
    }

    @Override
    public void body() throws InterruptedException {
        hooker.prepare2();
        hooker.protect();
        drive.moveStrafeFound(5000);
        hooker.hook();
        u.waitMS(200);
        drive.moveStrafeWithFound(1, 1700);
        drive.moveTurnFound(180);
        hooker.release();
        hooker.protect();
        drive.moveStraightPID(500);

        ZookerMap.bleft.setPower(-1);
        ZookerMap.bright.setPower(1);
        u.waitMS(700);
        ZookerMap.bleft.setPower(-1);
        ZookerMap.bright.setPower(1);

        drive.moveStraightPID(-800);
        drive.moveStrafePow(1, 300);
        u.waitMS(30000);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}