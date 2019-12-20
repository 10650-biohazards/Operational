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
import VisionPipelines.LineUpPipeline;
import VisionPipelines.OtherLineUpPipeline;

@Autonomous(name = "Red Double Skystone", group = "red")
@Disabled
public class RedDoubleSky extends ExplosiveAuto {

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

        turnPID.setup(0.03, 0, 0, 0, 0.5, 270);
        movePID.setup(0.002, 0, 0, 0.2, 0, 110);

        curve = new curveProcessor3(drive, telemetry, this);
    }

    @Override
    public void initAction() {

        waitForStart();


        hook.grabSkystone();
        u.waitMS(5000);
        hook.releaseSkystone();
        u.waitMS(5000);



        int skyPos = vision.grabSkyPos();

        //telemetry.addData("pos", skyPos);
        //telemetry.update();

        hook.release();

        drive.moveStraightPID(800);

        if (skyPos == 0) {

            //THIS ONE IS CODED. PROBABLY WON'T WORK

            drive.moveTurnPID(270);
            drive.moveStraightPID(-500);

            vision.phoneCam.setPipeline(new LineUpPipeline());

            DOTHETHING(true, 270);
            intake.intake();
            drive.moveStraightPID(400);
            intake.halt();

            /*
            OtherLineUpPipeline.lowerX = 30;
            OtherLineUpPipeline.upperX = 50;
            u.waitMS(1000);

            sound.PlaySkystoneSound(Soundboard.SkystoneSound.FIRSTSOUND);
            DOTHEOTHERTHING(true, 0);
            u.waitMS(1000);
            intake.intake();
            drive.swingTurnPID(100, true);
            drive.moveTurnPID(90);
            drive.moveStraightPID(-250);
            intake.halt();
            */

            drive.moveStrafePow(-1, 480);

            drive.moveStraightPID(-2000);

            drive.moveTurnPID(90);

            intake.outtake();
            u.waitMS(300);
            intake.halt();

            drive.moveRangePID(10, 5000, false);
            drive.moveTurnPID(270);



            DOTHETHING(true, 270);
            intake.intake();
            drive.moveStraightPID(300);
            intake.halt();
        } else if (skyPos == 1) {
            sound.PlaySkystoneSound(Soundboard.SkystoneSound.SECONDSOUND);
            DOTHEOTHERTHING(false, 0);
            drive.moveStrafePow(-0.5, 80);
            u.waitMS(1000);

            intake.intake();
            drive.swingTurnPID(100, true);
            intake.halt();

            drive.moveStrafePow(1, 480);

            drive.moveTurnPID(90);
            drive.moveStraightPID(1550);
            intake.outtake();
            drive.moveStraightPID(-300);
            intake.halt();
        } else {
            sound.PlaySkystoneSound(Soundboard.SkystoneSound.THIRDSOUND);
            //DOTHEOTHERTHING(false);
            drive.moveStrafePow(0.5, 300);
            u.waitMS(1000);

            intake.intake();
            drive.swingTurnPID(100, true);
            intake.halt();

            drive.moveTurnPID(100);

            drive.moveStrafePow(1, 500);

            drive.moveTurnPID(90);
            drive.moveStraightPID(1550);
            intake.outtake();
            drive.moveStraightPID(-300);
            intake.halt();
        }
    }

    public void DOTHETHING(boolean moveRight, double targAng) {
        boolean done = false;
        u.startTimer(3000);

        turnPID.setTarget(targAng);

        while (!done && opModeIsActive() && !u.timerDone()) {
            double stoneY = vision.getStoneX();

            telemetry.addData("Y-pos", stoneY);
            telemetry.addData("area", LineUpPipeline.area);
            telemetry.addData("Targ", turnPID.tarVal);
            telemetry.addData("Actual", refine(drive.gyro.getYaw()));

            double brp, frp, blp, flp;

            double movePow = -movePID.status(stoneY);

            if (stoneY == -1) {

                //OFF SCREEN

                telemetry.addData("OFF SCREEN", "");

                if (moveRight) {
                    brp = 0.4;
                    frp = -0.4;
                    blp = -0.4;
                    flp = 0.4;
                } else {
                    brp = -0.4;
                    frp = 0.4;
                    blp = 0.4;
                    flp = -0.4;
                }
            } else if (stoneY < 108) {

                //OFF RIGHT

                telemetry.addData("RIGHT?", "");

                brp = -movePow;
                frp = movePow;
                blp = movePow;
                flp = -movePow;
            } else if (stoneY > 112) {

                //OFF LEFT

                telemetry.addData("LEFT?", "");

                brp = -movePow;
                frp = movePow;
                blp = movePow;
                flp = -movePow;
            } else {

                done = true;

                telemetry.addData("WE're HERE", "");

                brp = 0;
                frp = 0;
                blp = 0;
                flp = 0;
            }

            telemetry.update();

            double mod = 0;
            if (true) {
                mod = turnPID.status(refine(drive.gyro.getYaw()));
            }

            setPows(brp - mod, frp - mod, blp + mod, flp + mod);
        }
    }

    public void DOTHEOTHERTHING(boolean moveRight, double targAng) {
        double brp, frp, blp, flp;

        turnPID.setTarget(targAng + 90);

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
