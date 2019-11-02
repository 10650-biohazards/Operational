package Competition.Programs.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Soundboard;

import Competition.Robot;
import Competition.RobotMap;
import Competition.Subsystems.DriveSubsystem;
import Competition.Subsystems.HookSubsystem;
import Competition.Subsystems.IntakeSubsystem;
import Competition.Subsystems.VisionSubsystem;
import DubinsCurve.curveProcessor3;
import FtcExplosivesPackage.ExplosiveAuto;
import Utilities.Utility;

@Autonomous(name = "New Blue Skystone Only", group = "blue")
public class NewBlueSkyOnly extends ExplosiveAuto {

    DriveSubsystem drive;
    VisionSubsystem vision;
    HookSubsystem hook;
    IntakeSubsystem intake;
    curveProcessor3 curve;
    Soundboard sound;
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
        hook = Robot.hooker;
        intake = Robot.intake;
        sound = new Soundboard(this.hardwareMap);

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

        waitForStart();

        int skyPos = vision.grabSkyPos();

        //telemetry.addData("pos", skyPos);
        //telemetry.update();

        hook.release();

        drive.moveStraightPID(700);
        drive.moveTurnPID(90);

        if (skyPos == 2) {
            sound.PlaySkystoneSound(Soundboard.SkystoneSound.FIRSTSOUND);
            drive.moveRangePID(13, 3000, true);
            drive.moveStrafePow(-0.7, 500);
            drive.moveTurnPID(90);

            intake.intake();
            drive.moveStraightPID(300);

            drive.moveStrafePow(0.7, 900);
            intake.halt();
            drive.moveTurnPID(90);
            drive.moveStraightPID(-2700);
            drive.moveTurnPID(5);
            intake.outtake();
            u.waitMS(500);
            drive.moveTurnPID(90);
            drive.moveStraightPID(600);
            intake.halt();
        } else if (skyPos == 1) {
            sound.PlaySkystoneSound(Soundboard.SkystoneSound.SECONDSOUND);
            drive.moveRangePID(18, 3000, true);
            drive.moveStrafePow(-0.7, 500);
            drive.moveTurnPID(90);

            intake.intake();
            drive.moveStraightPID(300);

            drive.moveStrafePow(0.7, 900);
            intake.halt();
            drive.moveTurnPID(90);
            drive.moveStraightPID(-2300);
            drive.moveTurnPID(5);
            intake.outtake();
            u.waitMS(500);
            drive.moveTurnPID(90);
            drive.moveStraightPID(500);
            intake.halt();
        } else {
            sound.PlaySkystoneSound(Soundboard.SkystoneSound.THIRDSOUND);
            drive.moveRangePID(26, 3000, true);
            drive.moveStrafePow(-0.7, 500);
            drive.moveTurnPID(90);

            intake.intake();
            drive.moveStraightPID(300);

            drive.moveStrafePow(0.7, 900);
            intake.halt();
            drive.moveTurnPID(90);
            drive.moveStraightPID(-2100);
            drive.moveTurnPID(5);
            intake.outtake();
            u.waitMS(500);
            drive.moveTurnPID(90);
            drive.moveStraightPID(500);
            intake.halt();
        }

        /*
        waitForStart();

        int skyPos = vision.grabSkyPos();

        //telemetry.addData("pos", skyPos);
        //telemetry.update();

        hook.release();

        drive.moveStraightPID(700);
        hook.skystone();
        drive.moveTurnPID(90);

        if (skyPos == 0) {
            drive.moveRangePID(16, 3000, false);
            drive.moveStrafePow(-0.7, 300);
            hook.skystone();
            u.waitMS(1000);
            drive.swingTurnPID(135, true);
            drive.moveStraightModded(1000, 4000);
            drive.swingTurnSlow(90, false);
            drive.moveStraightModded(750, 1000);
            hook.release();
            drive.moveStraightPID(-300);
        } else if (skyPos == 1) {
            drive.moveStrafePow(-0.7, 300);
            hook.skystone();
            u.waitMS(1000);
            drive.swingTurnPID(135, true);
            drive.moveStraightModded(1000, 4000);
            drive.swingTurnSlow(90, false);
            drive.moveStraightModded(300, 1000);
            hook.release();
            drive.moveStraightPID(-300);
        } else {
            drive.moveRangePID(8.5, 3000, false);
            drive.moveStrafePow(-0.7, 300);
            hook.skystone();
            u.waitMS(1000);
            //drive.swingTurnPID(100, false);
            drive.swingTurnPID(135, true);
            drive.moveStraightModded(700, 4000);
            drive.swingTurnSlow(90, false);
            drive.moveStraightModded(1100, 4000);
            hook.release();
            drive.moveStraightPID(-300);
        }*/
    }

    @Override
    public void body() throws InterruptedException {

    }

    @Override
    public void exit() throws InterruptedException {

    }
}
