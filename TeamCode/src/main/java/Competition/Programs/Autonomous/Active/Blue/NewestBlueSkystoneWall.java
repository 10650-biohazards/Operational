package Competition.Programs.Autonomous.Active.Blue;

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
import VisionPipelines.LineUpPipeline;

@Autonomous(name = "Blue Skystone | Bridge Park", group = "blue")
public class NewestBlueSkystoneWall extends ExplosiveAuto {

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



        int skyPos = vision.grabSkyPos();

        hook.release();

        drive.moveStraightPID(830);

        if (skyPos == 0) {

            drive.moveTurnPID(90);
            drive.moveRangePID(30, 2000, true);
            drive.moveRangePID(30, 2000, true);
            //drive.moveStraightPID(100);
            drive.moveStrafePow(-0.5, 500);
            hook.grabSkystone();
            u.waitMS(500);
            drive.moveStrafePow(0.5, 1000);
            drive.moveTurnPID(90);
            drive.moveStraightModded(-1600, 5000);
            hook.releaseSkystone();

            //SINGLE
            drive.moveStraightPID(600);

            /* DOUBLE SKYSTONE
            drive.moveTurnPID(90);
            drive.moveStraightPID(-3000);
            drive.moveTurnPID(90);
            drive.moveStrafePow(-0.5, 500);
            hook.grabSkystone();
            u.waitMS(500);
             */

        } else if (skyPos == 1) {
            drive.moveTurnPID(90);
            drive.moveRangePID(22.5, 2000, true);
            drive.moveRangePID(22.5, 2000, true);
            //drive.moveStraightPID(100);
            drive.moveStrafePow(-0.5, 500);
            hook.grabSkystone();
            u.waitMS(500);
            drive.moveStrafePow(0.5, 1000);
            drive.moveTurnPID(90);
            drive.moveStraightModded(-1850, 5000);
            hook.releaseSkystone();

            //SINGLE
            drive.moveStraightPID(600);

        } else {
            drive.moveTurnPID(90);
            drive.moveRangePID(16.5, 2000, true);
            drive.moveRangePID(16.5, 2000, true);
            //drive.moveStraightPID(100);
            drive.moveStrafePow(-0.5, 500);
            hook.grabSkystone();
            telemetry.addData("YEET", RobotMap.skyGrabber.getPosition());
            telemetry.update();
            u.waitMS(500);
            drive.moveStrafePow(0.5, 1000);
            drive.moveTurnPID(90);
            drive.moveStraightModded(-2000, 5000);
            hook.releaseSkystone();

            //SINGLE
            drive.moveStraightPID(600);
        }

        drive.moveStrafePow(0.4, 1000);
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
