package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Soundboard;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import Competition.Subsystems.IntakeSubsystem;
import Competition.Subsystems.VisionSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.PID;
import Utilities.Utility;

@Autonomous(name = "Skystone tester", group = "blue")
@Disabled
public class FancyTester extends ExplosiveAuto {

    DriveSubsystem drive;
    VisionSubsystem vision;
    HookSubsystem hook;
    IntakeSubsystem intake;
    curveProcessor3 curve;
    Soundboard sound;
    Utility u = new Utility(this);

    PID turnPID = new PID();
    PID movePID = new PID();

    @Override
    public void initHardware() {
        RobotMap robotMap = new RobotMap(hardwareMap);
        Robot robot = new Robot(this);
        robot.enable();

        Robot.track.setCurrentNode(-1.5, -2.625, 0);
        RobotMap.gyro.startAng = 0;

        drive = Robot.drive;
        vision = Robot.vision;
        hook = Robot.hooker;
        intake = Robot.intake;
        sound = new Soundboard(this.hardwareMap);

        turnPID.setup(0.03, 0, 0, 0, 0.5, 90);
        movePID.setup(0.005, 0, 0, 0.0, 0, 150);

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

        waitForStart();
        DOTHETHING();
        intake.intake();
        drive.swingTurnPID(115, true);
    }

    public void DOTHETHING() {

        double brp, frp, blp, flp;

        while (opModeIsActive() && !vision.linedUp()) {
            telemetry.addData("Lendd up", vision.linedUp());
            telemetry.update();

            brp = -0.4;
            frp = 0.4;
            blp = 0.4;
            flp = -0.4;

            double mod = 0;
            if (true) {
                mod = turnPID.status(refine(drive.gyro.getYaw() + 90));
            }

            setPows(brp - mod, frp - mod, blp + mod, flp + mod);
        }
    }

    private void setPows(double brp, double frp, double blp, double flp) {

        drive.bright.setPower(brp);
        drive.fright.setPower(frp);
        drive.bleft.setPower(blp);
        drive.fleft.setPower(flp);

    }

    public double refine(double input) {
        input %= 360;
        if (input < 0) {
            input += 360;
        }
        return input;
    }

    @Override
    public void body() throws InterruptedException {

    }

    @Override
    public void exit() throws InterruptedException {

    }
}
