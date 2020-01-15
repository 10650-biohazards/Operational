package Competition.Programs.Autonomous.Active;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "In honor of Neel Maity")
public class Neel extends ExplosiveAuto {

    DriveSubsystem drive;

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        RobotMap.gyro.startAng = 0;

        drive = Robot.drive;
    }

    @Override
    public void initAction() {

    }

    @Override
    public void body() throws InterruptedException {
        drive.moveStrafePow(0.5, 200);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}
