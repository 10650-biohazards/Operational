package Competition.Programs.Autonomous.Active.Blue;

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

@Autonomous (name = "Blue ROAR", group = "blue")
public class BlueROAR extends ExplosiveAuto {

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

        FoundationPipeline.setRed(false);
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
        drive.moveStrafeWithFound2(1, 1500);
        drive.moveTurnFound(10);
        hooker.release();
        hooker.protect();
        drive.moveStraightPID(-500);

        ZookerMap.fleft.setPower(1);
        ZookerMap.fright.setPower(-1);
        u.waitMS(700);
        ZookerMap.bleft.setPower(0);
        ZookerMap.bright.setPower(0);

        hooker.book();
        drive.moveStraightPID(1000);
        drive.moveStrafePow(1, 300);
        u.waitMS(30000);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}