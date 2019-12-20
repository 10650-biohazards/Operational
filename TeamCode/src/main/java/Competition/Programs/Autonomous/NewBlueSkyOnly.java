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
import Utilities.PID;
import Utilities.Utility;
import VisionPipelines.OtherLineUpPipeline;

@Autonomous(name = "New Blue Skystone Only", group = "blue")
public class NewBlueSkyOnly extends ExplosiveAuto {

    DriveSubsystem drive;
    VisionSubsystem vision;
    HookSubsystem hook;
    IntakeSubsystem intake;
    curveProcessor3 curve;
    Soundboard sound;
    Utility u = new Utility(this);

    PID turnPID = new PID();

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

        drive.moveStraightPID(880);

        if (skyPos == 2) {
            sound.PlaySkystoneSound(Soundboard.SkystoneSound.FIRSTSOUND);

            OtherLineUpPipeline.lowerX = 110;
            OtherLineUpPipeline.upperX = 130;

            u.waitMS(1000);

            DOTHEOTHERTHING(false);

            u.waitMS(1000);

            drive.moveStraightPID(200);

            intake.intake();
            drive.swingTurnPID(280, false);
            drive.moveStraightPID(-350);
            intake.halt();

            drive.moveStrafePow(-1, 450);

            drive.moveTurnPID(273);
            drive.moveStraightPID(1920);
            intake.outtake();
            drive.moveStraightPID(-300);
            intake.halt();
        } else if (skyPos == 1) {
            sound.PlaySkystoneSound(Soundboard.SkystoneSound.SECONDSOUND);
            DOTHEOTHERTHING(true);
            drive.moveStrafePow(0.4, 450);

            intake.intake();
            drive.swingTurnPID(280, false);
            intake.halt();
            drive.moveTurnPID(270);
            //drive.moveStraightPID(-250);

            drive.moveStrafePow(-1, 450);

            drive.moveTurnPID(270);
            drive.moveStraightPID(1500);
            intake.outtake();
            drive.moveStraightPID(-300);
            intake.halt();
        } else {
            sound.PlaySkystoneSound(Soundboard.SkystoneSound.THIRDSOUND);
            //DOTHEOTHERTHING(true);
            //drive.moveStrafePow(-0.4, 100);

            intake.intake();
            drive.swingTurnPID(280, false);
            intake.halt();
            drive.moveTurnPID(270);
            drive.moveStraightPID(-150);

            drive.moveStrafePow(-1, 480);

            drive.moveTurnPID(275);
            drive.moveStraightPID(1370);
            intake.outtake();
            drive.moveStraightPID(-300);
            intake.halt();
        }
    }

    public void DOTHETHING() {
        boolean done = false;
        while (!done) {
            double stoneY = vision.getStoneY();
            double brp, frp, blp, flp;

            if (stoneY == -1) {

                //OFF SCREEN

                brp = 0.5;
                frp = -0.5;
                blp = -0.5;
                flp = 0.5;
            } else if (stoneY > 110) {

                //OFF RIGHT

                brp = -0.5;
                frp = 0.5;
                blp = 0.5;
                flp = -0.5;
            } else if (stoneY < 90) {

                //OFF LEFT

                brp = 0.5;
                frp = -0.5;
                blp = -0.5;
                flp = 0.5;
            } else {
                brp = 0;
                frp = 0;
                blp = 0;
                flp = 0;
            }

            double mod = 0;
            if (true) {
                mod = turnPID.status(drive.gyro.getYaw());
            }

            setPows(brp + mod, frp + mod, blp - mod, flp - mod);
        }
    }

    public void DOTHEOTHERTHING(boolean moveRight) {
        double brp, frp, blp, flp;

        while (opModeIsActive() && !vision.linedUp()) {
            telemetry.addData("Lendd up", vision.linedUp());
            telemetry.update();

            if (moveRight) {
                brp = -0.4;
                frp = 0.4;
                blp = 0.4;
                flp = -0.4;
            } else {
                brp = 0.4;
                frp = -0.4;
                blp = -0.4;
                flp = 0.4;
            }

            double mod = 0;
            if (true) {
                mod = turnPID.status(refine(drive.gyro.getYaw() + 90));
            }

            setPows(brp - mod, frp - mod, blp + mod, flp + mod);
        }

        setPows(0, 0, 0, 0);
    }

    public double refine(double input) {
        input %= 360;
        if (input < 0) {
            input += 360;
        }
        return input;
    }

    private void setPows(double brp, double frp, double blp, double flp) {

        drive.bright.setPower(brp);
        drive.fright.setPower(frp);
        drive.bleft.setPower(blp);
        drive.fleft.setPower(flp);

    }

    @Override
    public void body() throws InterruptedException {

    }

    @Override
    public void exit() throws InterruptedException {

    }
}
