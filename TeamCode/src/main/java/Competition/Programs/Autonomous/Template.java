package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import Competition.Robot;
import Competition.RobotMap;
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
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        Robot.track.setCurrentNode(-1.5, -2.625, 0);
        RobotMap.gyro.startAng = 0;

        drive = Robot.drive;
        vision = Robot.vision;
        hooker = Robot.hooker;

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
