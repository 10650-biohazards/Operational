package Competition.Subsystems;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import Competition.ZookerMap;
import DubinsCurve.Node;
import DubinsCurve.myPoint;
import FtcExplosivesPackage.BioSubsystem;
import FtcExplosivesPackage.BiohazardNavX;
import Utilities.PID;
import Utilities.Utility;
import Utilities.driveTracker2;
import VisionPipelines.FoundationPipeline;

public class DriveSubsystem extends BioSubsystem {

    public DcMotor bright, fright, bleft, fleft;
    Utility u;
    LinearOpMode op;
    public BiohazardNavX gyro;

    ModernRoboticsI2cRangeSensor frontRange, backRange, sideRange;

    public double start;

    public driveTracker2 track;

    public DriveSubsystem(LinearOpMode op) {
        super(op);
        u = new Utility(op);
        this.op = op;
    }

    public void setTracker(driveTracker2 track) {
        this.track = track;
    }

    public void straightToPoint(myPoint point) {

        Node currentNode = track.getCurrentNode();

        double xDiff = point.x - currentNode.x;
        double yDiff = point.y - currentNode.y;

        double angle = slopeFinder(xDiff, yDiff);

        moveTurnPID(angle);

        double dist = findDist(point, currentNode.coords());
        moveStraightPID(dist);
    }

    public void moveStraightPID(double targDist, int stopTime) {
        PID movePID = new PID();
        PID modPID = new PID();

        double target = bright.getCurrentPosition() + targDist;
        double targAng = refine(gyro.getYaw());

        movePID.setup(0.00023, 0, 0, 0.10, 5, target);
        //modPID.setup(0.02, 0, 0, 0, 0, targAng);

        u.startTimer(stopTime);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {

            //double mod = modPID.status(refine(gyro.getYaw()));
            double mod = 0;
            double power = movePID.status(bright.getCurrentPosition());

            op.telemetry.addData("POWER", power);
            op.telemetry.addData("br", bright.getCurrentPosition());
            op.telemetry.addData("fr", fright.getCurrentPosition());
            op.telemetry.addData("bl", bleft.getCurrentPosition());
            op.telemetry.addData("fl", fleft.getCurrentPosition());
            op.telemetry.addData("Target", target);
            op.telemetry.addData("TargAng", targAng);
            op.telemetry.update();

            setPows(power - mod, power - mod, power + mod, power + mod);

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }
    public void moveStraightPID(double targDist) {moveStraightPID(targDist, 3000);}

    public void moveStraightModded(double targDist, int stopTime) {
        PID movePID = new PID();
        PID modPID = new PID();

        double target = bright.getCurrentPosition() + targDist;
        double targAng = refine(gyro.getYaw());

        movePID.setup(0.00015, 0, 0, 0.2, 20, target);
        modPID.setup(0.02, 0, 0, 0, 0, targAng);

        u.startTimer(stopTime);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {

            double mod = modPID.status(refine(gyro.getYaw()));
            double power = movePID.status(bright.getCurrentPosition());

            op.telemetry.addData("POWER", power);
            op.telemetry.addData("br", bright.getCurrentPosition());
            op.telemetry.addData("fr", fright.getCurrentPosition());
            op.telemetry.addData("bl", bleft.getCurrentPosition());
            op.telemetry.addData("fl", fleft.getCurrentPosition());
            op.telemetry.addData("Target", target);
            op.telemetry.addData("TargAng", targAng);
            op.telemetry.update();

            setPows(power - mod, power - mod, power + mod, power + mod);

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }

    public void moveRangePID(double targDist, int stopTime, boolean front) {
        PID movePID = new PID();
        PID modPID = new PID();

        double target = targDist;

        ModernRoboticsI2cRangeSensor range;

        if (front) {
            range = frontRange;
        } else {
            range = backRange;
        }

        movePID.setup(0.016, 0, 0, 0.14, 0.16, target);

        u.startTimer(stopTime);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {

            if (range.getDistance(DistanceUnit.INCH) == 0.0) {
                setPows(0, 0, 0, 0);
            } else {
                double power = -movePID.status(range.getDistance(DistanceUnit.INCH));

                if (!front) {
                    power *= -1;
                }

                op.telemetry.addData("POWER", power);
                op.telemetry.addData("Dist", range.getDistance(DistanceUnit.INCH));
                op.telemetry.addData("Target", target);
                op.telemetry.update();

                setPows(power, power, power, power);

                track.refresh();
            }

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }

    public void moveStrafeRange(double targDist, int stopTime, boolean foundation) {
        PID movePID = new PID();
        PID modPID = new PID();

        double target = targDist;

        if (foundation) movePID.setup(0.04, 0, 0, 0.1, 0.16, target);
        else movePID.setup(0.012, 0, 0, 0.1, 0.16, target);

        modPID.setup(0.02, 0, 0, 0, 0, 90);

        u.startTimer(stopTime);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {

            if (sideRange.getDistance(DistanceUnit.INCH) == 0.0) {
                setPows(0, 0, 0, 0);
            } else {
                double power = -movePID.status(sideRange.getDistance(DistanceUnit.INCH));
                double mod = modPID.status(refine(gyro.getYaw()));

                op.telemetry.addData("POWER", power);
                op.telemetry.addData("Dist", sideRange.getDistance(DistanceUnit.INCH));
                op.telemetry.addData("Target", target);
                op.telemetry.update();

                setPows(power - mod, -power - mod, -power + mod, power + mod);

                track.refresh();
            }

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }

    public void moveStrafeMod(double pow, int stopTime) {
        PID modPID = new PID();
        modPID.setup(0.02, 0, 0, 0, 0, 90);

        u.startTimer(stopTime);

        while (!u.timerDone() && op.opModeIsActive()) {
            double power = pow;
            double mod = modPID.status(refine(gyro.getYaw()));

            setPows(power - mod, -power - mod, -power + mod, power + mod);

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(500);
    }

    public void moveStrafePID(double targDist, int stopTime) {
        PID movePID = new PID();
        movePID.setup(0.0002, 0, 0, 0.2, 20,bright.getCurrentPosition() + targDist);

        u.startTimer(stopTime);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {
            double power = movePID.status(bright.getCurrentPosition());
            setPows(power, -power, -power, power);

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(500);
    }

    public void moveStrafePow(double pow, int stopTime) {

        u.startTimer(stopTime);

        while (!u.timerDone() && op.opModeIsActive()) {
            double power = pow;
            setPows(power, -power, -power, power);

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        //u.waitMS(500);
    }

    public void moveStrafeWithFound(double pow, int stopTime) {
        PID modPID = new PID();
        modPID.setup(0.02, 0, 0, 0, 0, start + 10);

        u.startTimer(stopTime);

        while (!u.timerDone() && op.opModeIsActive()) {
            double power = pow;
            double mod = modPID.status(refine(gyro.getYaw()));

            setPows(power - mod, -power - mod, -power + mod, power + mod);

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
    }

    public void moveStrafeWithFound2(double pow, int stopTime) {
        PID modPID = new PID();
        modPID.setup(0.02, 0, 0, 0, 0, refine(start - 10));

        u.startTimer(stopTime);

        while (!u.timerDone() && op.opModeIsActive()) {
            double power = pow;
            double mod = modPID.status(refine(gyro.getYaw()));

            setPows(power - mod, -power - mod, -power + mod, power + mod);

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
    }

    public void moveStrafeFound(int stopTime) {

        PID modPID = new PID();
        start = refine(gyro.getYaw());
        modPID.setup(0.02, 0, 0, 0, 0, start);

        u.startTimer(600);

        setPows(-1, 1, 1, -1);

        while (!u.timerDone()) {

            double mod = modPID.status(refine(gyro.getYaw()));

            setPows(-1 - mod, 1 - mod, 1 + mod, -1 + mod);

            if (!op.opModeIsActive()) return;
        }

        u.startTimer(stopTime - 600);

        while (!u.timerDone() && !FoundationPipeline.present) {

            double mod = modPID.status(refine(gyro.getYaw()));

            setPows(-1 - mod, 1 - mod, 1 + mod, -1 + mod);


            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
    }

    public void moveStraightRaw(double targDist) {

        double target = bright.getCurrentPosition() + targDist;
        double initDiff = targDist - bright.getCurrentPosition();

        boolean forward = !(initDiff < 0);

        boolean done = false;
        while (!done && op.opModeIsActive()) {
            if (forward) {
                setPows(1, 1, 1, 1);
                done = bright.getCurrentPosition() > target;
            } else {
                setPows(-1, -1, -1, -1);
                done = bright.getCurrentPosition() < target;
            }

            op.telemetry.addData("ENCODER", bright.getCurrentPosition());
            op.telemetry.addData("TARGET", target);
            op.telemetry.addData("DONE", done);
            op.telemetry.addData("FORWARD", forward);
            op.telemetry.update();

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }


    public void moveTurnPID(double targetAng, int stopTime) {
        double mod = 0;

        double curr = refine(gyro.getYaw());
        if (Math.abs(targetAng - curr) > 180) {
            if (curr > 180) {
                mod = 360 - curr + 15;
            } else {
                mod = 360 - targetAng + 15;
            }
        }

        PID movePID = new PID();
        //movePID.setup(0.002, 0, 0, 0.14, 0.25,refine(targetAng + mod));
        movePID.setup(0.0025, 0, 0, 0.2, 0.25,refine(targetAng + mod));

        op.telemetry.addData("mod", mod);
        op.telemetry.addData("raw ang", gyro.getYaw());
        op.telemetry.addData("refined", refine(gyro.getYaw()));
        op.telemetry.addData("Raw Target", targetAng);
        op.telemetry.addData("modded ang", refine(gyro.getYaw() + mod));
        op.telemetry.addData("Modded Target", refine(targetAng + mod));
        op.telemetry.update();

        //u.waitMS(10000);

        u.startTimer(stopTime);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {
            double currAng = refine(gyro.getYaw() + mod);

            double power = movePID.status(currAng);
            setPows(-power, -power, power, power);

            op.telemetry.addData("mod", mod);
            op.telemetry.addData("Power", power);
            op.telemetry.addData("working", currAng);
            op.telemetry.addData("refined", refine(gyro.getYaw()));
            op.telemetry.addData("Raw Target", targetAng);
            op.telemetry.addData("Modded Target", refine(targetAng + mod));
            op.telemetry.update();

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }
    public void moveTurnPID(double targetAng) {
        moveTurnPID(targetAng, 2000);
    }

    public void moveTurnFound(double targetAng) {
        double mod = 0;

        double curr = refine(gyro.getYaw());
        if (Math.abs(targetAng - curr) > 180) {
            if (curr > 180) {
                mod = 360 - curr + 15;
            } else {
                mod = 360 - targetAng + 15;
            }
        }

        PID movePID = new PID();
        movePID.setup(0.006, 0, 0, 0.2, 0.25,refine(targetAng + mod));

        op.telemetry.addData("mod", mod);
        op.telemetry.addData("raw ang", gyro.getYaw());
        op.telemetry.addData("refined", refine(gyro.getYaw()));
        op.telemetry.addData("Raw Target", targetAng);
        op.telemetry.addData("modded ang", refine(gyro.getYaw() + mod));
        op.telemetry.addData("Modded Target", refine(targetAng + mod));
        op.telemetry.update();

        //u.waitMS(10000);

        u.startTimer(3000);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {
            double currAng = refine(gyro.getYaw() + mod);

            double power = movePID.status(currAng);
            setPows(-power, -power, power, power);

            op.telemetry.addData("mod", mod);
            op.telemetry.addData("Power", power);
            op.telemetry.addData("working", currAng);
            op.telemetry.addData("refined", refine(gyro.getYaw()));
            op.telemetry.addData("Raw Target", targetAng);
            op.telemetry.addData("Modded Target", refine(targetAng + mod));
            op.telemetry.update();

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }

    public void swingTurnPID(double targetAng, boolean right) {
        double mod = 0;

        PID breakPIDF = new PID();
        PID breakPIDB = new PID();
        breakPIDF.setup(0.00, 0, 0, 0, 0, 0);
        breakPIDB.setup(0.00, 0, 0, 0, 0, 0);

        if (right) {
            breakPIDB.setTarget(bright.getCurrentPosition());
            breakPIDF.setTarget(fright.getCurrentPosition());
        } else {
            breakPIDB.setTarget(bleft.getCurrentPosition());
            breakPIDF.setTarget(fleft.getCurrentPosition());
        }

        double curr = refine(gyro.getYaw());
        if (targetAng - curr < 0 && right) {
            mod = 360 - curr + 15;
        }
        if (refine(gyro.getYaw()) - targetAng < 0 && !right) {
            mod = 360 - targetAng + 15;
        }

        PID movePID = new PID();
        movePID.setup(0.008, 0, 0, 0.1, 0.2, refine(targetAng + mod));

        u.startTimer(2500);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {
            double currAng = refine(gyro.getYaw() + mod);

            double power = movePID.status(currAng);

            op.telemetry.addData("mod", mod);
            op.telemetry.addData("Power", power);
            op.telemetry.addData("working", currAng);
            op.telemetry.addData("refined", refine(gyro.getYaw()));
            op.telemetry.addData("Raw Target", targetAng);
            op.telemetry.addData("Modded Target", refine(targetAng + mod));
            op.telemetry.update();

            if (right) {
                setPows(0, 0, power, power);
            } else {
                setPows(-power, -power, 0, 0);
            }

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }

    public void swingTurnSlow(double targetAng, boolean right) {
        double mod = 0;

        PID breakPIDF = new PID();
        PID breakPIDB = new PID();
        breakPIDF.setup(0.00, 0, 0, 0, 0, 0);
        breakPIDB.setup(0.00, 0, 0, 0, 0, 0);

        if (right) {
            breakPIDB.setTarget(bright.getCurrentPosition());
            breakPIDF.setTarget(fright.getCurrentPosition());
        } else {
            breakPIDB.setTarget(bleft.getCurrentPosition());
            breakPIDF.setTarget(fleft.getCurrentPosition());
        }

        double curr = refine(gyro.getYaw());
        if (targetAng - curr < 0 && right) {
            mod = 360 - curr + 15;
        }
        if (refine(gyro.getYaw()) - targetAng < 0 && !right) {
            mod = 360 - targetAng + 15;
        }

        PID movePID = new PID();
        movePID.setup(0.01, 0, 0, 0.15, 0.2, refine(targetAng + mod));

        u.startTimer(30000);

        while (!u.timerDone() && !movePID.done() && op.opModeIsActive()) {
            double currAng = refine(gyro.getYaw() + mod);

            double power = movePID.status(currAng);

            op.telemetry.addData("mod", mod);
            op.telemetry.addData("Power", power);
            op.telemetry.addData("working", currAng);
            op.telemetry.addData("refined", refine(gyro.getYaw()));
            op.telemetry.addData("Raw Target", targetAng);
            op.telemetry.addData("Modded Target", refine(targetAng + mod));
            op.telemetry.update();

            if (right) {
                setPows(0, 0, power, power);
            } else {
                setPows(-power, -power, 0, 0);
            }

            track.refresh();

            if (!op.opModeIsActive()) return;
        }
        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }

    public double refine(double input) {
        input %= 360;
        if (input < 0) {
            input += 360;
        }
        return input;
    }

    private static double refine_ang(double ang) {
        ang %= 360;
        ang += ang < 0 ? 360 : 0;
        return ang;
    }

    private static double torads(double ang) {
        return ang * (Math.PI / 180);
    }

    public void auto_omni(double targetAng, double power, int time) {
        u.startTimer(time);

        while (!u.timerDone()) {
            omni_drive(targetAng, power);
        }

        setPows(0, 0, 0, 0);
        u.waitMS(200);
    }

    public void omni_drive(double targetang, double power) {
        double refinedAng;
        double aAng;
        double bAng;

        double aPowrr;
        double bPowrr;

        targetang = refine_ang(targetang);

        refinedAng = refine_ang(gyro.getYaw());
        refinedAng = targetang - refinedAng;
        refinedAng = refine_ang(refinedAng);


        System.out.println(refinedAng);


        aAng = refinedAng + 45;
        bAng = refinedAng - 45;

        if ((refinedAng < 90 && refinedAng > 0) || (refinedAng < 270 && refinedAng > 180)) {
            aPowrr = refinedAng < 90 ? 1 : -1;
            bPowrr = Math.cos(torads(aAng)) / Math.cos(torads(bAng));
            bPowrr *= refinedAng > 180 ? -1 : 1;
        } else {
            aPowrr = -Math.cos(torads(bAng)) / Math.cos(torads(aAng));
            aPowrr *= refinedAng > 270 ? -1 : 1;
            bPowrr = refinedAng < 180 ? -1 : 1;
        }

        aPowrr *= power;
        bPowrr *= power;
        setPows(bPowrr, aPowrr, aPowrr, bPowrr);
        op.telemetry.addData("aAngle", aAng);
        op.telemetry.addData("bAngle", bAng);
    }

    public void setPows(double brp, double frp, double blp, double flp) {
        bright.setPower(brp);
        fright.setPower(frp);
        bleft.setPower(blp);
        fleft.setPower(flp);
    }

    @Override
    public void enable() {
        bright = ZookerMap.bright;
        fright = ZookerMap.fright;
        bleft = ZookerMap.bleft;
        fleft = ZookerMap.fleft;

        gyro = ZookerMap.gyro;

        frontRange = ZookerMap.frontRange;
        backRange = ZookerMap.backRange;
        sideRange = ZookerMap.sideRange;
    }

    @Override
    public void disable() {

    }

    @Override
    public void stop() {

    }

    private double findDist(myPoint point1, myPoint point2) {
        return Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
    }

    private double slopeFinder(double xDiff, double yDiff) {
        double raw = Math.atan(yDiff/xDiff);
        if (xDiff >= 0 && yDiff >= 0) {
            raw *= -1;
            raw += Math.PI / 2;
            return Math.toDegrees(raw);
        } else if (xDiff >= 0 && yDiff <= 0) {
            raw *= -1;
            raw += Math.PI / 2;
            return Math.toDegrees(raw);
        } else if (xDiff <= 0 && yDiff <= 0) {
            raw *= -1;
            raw += 3 * (Math.PI / 2);
            return Math.toDegrees(raw);
        } else if (xDiff <= 0 && yDiff >= 0) {
            raw *= -1;
            raw += 3 * (Math.PI / 2);
            return Math.toDegrees(raw);
        }
        return 42;
    }
}
