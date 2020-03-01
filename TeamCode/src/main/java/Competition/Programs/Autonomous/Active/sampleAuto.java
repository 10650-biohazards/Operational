package Competition.Programs.Autonomous.Active;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;

@Autonomous(name = "Sample Auto")
@Disabled
public class sampleAuto extends ExplosiveAuto {

    DriveSubsystem drive;

    curveProcessor3 curve;

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this);
        robot.enable();

        Zooker.track.setCurrentNode(0, 0, 90);
        ZookerMap.gyro.startAng = 5;

        drive = Zooker.drive;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void body() throws InterruptedException {
        drive.moveTurnPID(355);

        //drive.straightToPoint(new myPoint(1, -2));
        //drive.straightToPoint(new myPoint(0, 2));
        //drive.straightToPoint(new myPoint(-1, 2));
        //drive.straightToPoint(new myPoint(3, 3));
    }

    @Override
    public void exit() throws InterruptedException {

    }
}