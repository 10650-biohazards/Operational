package Competition.Programs.Autonomous.Active;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Subsystems.IntakeSubsystem;
import Competition.Subsystems.ParkSubsystem;
import Competition.Zooker;
import Competition.ZookerMap;
import Competition.Subsystems.DriveSubsystem;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "In honor of Neel Maity")
public class Neel extends ExplosiveAuto {

    DriveSubsystem drive;
    ParkSubsystem ben;
    IntakeSubsystem intake;

    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        ZookerMap robotMap = new ZookerMap(hardwareMap);
        Zooker robot = new Zooker(this);
        robot.enable();

        ZookerMap.gyro.startAng = 0;

        drive = Zooker.drive;
        ben = Zooker.park;
        intake = Zooker.intake;
    }

    @Override
    public void initAction() {

    }

    @Override
    public void body() throws InterruptedException {
        ben.extend(500, true);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}
