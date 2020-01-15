package Competition.Programs.Autonomous.Active.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous (name = "Blue Move | Bridge Park", group = "blue")
public class BlueMoveBridge extends ExplosiveAuto {

    DriveSubsystem drive;
    HookSubsystem hooker;
    curveProcessor3 curve;
    Utility u = new Utility(this);

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        Robot.track.setCurrentNode(1, -3, 0);
        RobotMap.gyro.startAng = 0;

        drive = Robot.drive;
        hooker = Robot.hooker;

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

    }

    @Override
    public void body() throws InterruptedException {
        drive.moveStraightPID(2000);
        u.waitMS(22000);
        drive.moveRangePID(29, 2000, false);
        drive.moveTurnPID(5, 1000);
        drive.moveStrafePow(0.4, 1500);
    }

    @Override
    public void exit() throws InterruptedException {

    }
}