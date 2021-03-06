package Competition.Commands;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Competition.ZookerMap;
import Competition.Zooker;
import FtcExplosivesPackage.BioCommand;
import FtcExplosivesPackage.BiohazardTele;
import Utilities.PID;
import Utilities.Utility;
import VisionPipelines.IntakePipeline;

import org.firstinspires.ftc.teamcode.Soundboard;


public class MechCommand extends BioCommand {

    String TAG = "MechCommand";

    DcMotor intRight, intLeft, lift, rotator, theDooker;

    Servo swinger, gripper, hooker, theCooker, theBookie, leftTransfer, rightTransfer, theRightLooker, theLeftLooker;

    private Gamepad manip, driver;

    BiohazardTele op;

    Soundboard sound;

    HardwareMap hw;

    Utility u;

    double mod = 0.3238 - 0.3072;

    boolean shuttleFirst, foundFirst;

    double lastTriggered = System.currentTimeMillis();

    boolean endGame = false;
    double lastTime = System.currentTimeMillis();

    //ROTATION STUFF
    private final int VERTICAL = 42;
    private final int RELOAD = 42;
    private int rotationTarg;
    private PID rotPID = new PID();

    //LIFT STUFF
    int nextLevel = 0;
    private final int BASE_HEIGHT = 42;
    private final int TICKS_PER_LEVEL = 42;
    private final int RELOAD_HEIGHT = 42;
    PID liftPID;
    int lifttarg;
    double startTime;


    public MechCommand(BiohazardTele op) {
        super(op, "mech");
        this.op = op;
        u = new Utility(op);
    }

    @Override
    public void init() {

        liftPID = new PID();

        intRight = ZookerMap.intRight;
        intLeft = ZookerMap.intLeft;
        theDooker = ZookerMap.theDooker;

        swinger = ZookerMap.swinger;
        gripper = ZookerMap.gripper;
        hooker = ZookerMap.hooker;
        theCooker = ZookerMap.theCooker;
        theBookie = ZookerMap.theBooker;
        leftTransfer = ZookerMap.leftTransfer;
        rightTransfer = ZookerMap.rightTransfer;
        theRightLooker = ZookerMap.theRightLooker;
        theLeftLooker = ZookerMap.theLooker;

        ZookerMap.skyGrabber.setPosition(0.2);
        ZookerMap.theBooker.setPosition(0.42 + mod);

        //lift = ZookerMap.lift;
        rotator = ZookerMap.rotator;

        manip = Zooker.manipulator;
        driver = Zooker.driver;

        sound = new Soundboard(this.hw);

        //lifttarg = lift.getCurrentPosition();
        //rotationTarg = rotator.getCurrentPosition();
        //liftPID.setup (42,0,42,0,0,lifttarg);
        //rotPID.setup(42, 0, 42, 0, 0, rotationTarg);

    }

    @Override
    public void start() {
        intLeft.setPower(0);
        intRight.setPower(0);

        startTime = System.currentTimeMillis();
    }

    @Override
    public void loop() {
        hooker();
        dooker();
        rigInEm();
        intake();
        cookInEm();
    }

    private void cookInEm() {
        if (manip.right_trigger > 0.05) {
            theCooker.setPosition(0);
        } else {
            theCooker.setPosition(0.6);
        }
    }

    private void dooker() {
        if (manip.left_stick_y > 0.05 && theDooker.getCurrentPosition() > 50) {
            theDooker.setPower(-manip.left_stick_y);
        } else if (manip.left_stick_y < 0.05 && theDooker.getCurrentPosition() < 11000) {
            theDooker.setPower(-manip.left_stick_y);
        } else {
            theDooker.setPower(0);
        }
        op.telemetry.addData("Yes", theDooker.getCurrentPosition());
        op.telemetry.update();
    }

    private void rigInEm() {

        if (manip.b) {
            theBookie.setPosition(0.42 + mod);
        } else {
            if (manip.x && lastTime + 1000 < System.currentTimeMillis()) {
                endGame = !endGame;
                lastTime = System.currentTimeMillis();
            }

            if (!endGame) {
                if ((manip.right_stick_y < -0.05 || VisionCommand.stoneY > IntakePipeline.slowThresh) && VisionCommand.stoneY > 90) {
                    theBookie.setPosition(0.2027 + mod);
                } else if (manip.right_stick_y < -0.05) {
                    theBookie.setPosition(0.3472 + mod);
                } else {
                    theBookie.setPosition(0.2972 + mod);
                }
            } else {
                theBookie.setPosition(0.42 + mod);
            }
        }
    }

    public void intake() {

        if (manip.y) {
            theRightLooker.setPosition(0.25);
            theLeftLooker.setPosition(0);
        }

        if (manip.right_stick_y > 0.05 || driver.right_trigger > 0.05) {
            IntakePipeline.minY = 140;
            //STOOPID ONE
            if (VisionCommand.intakeStatus == VisionCommand.stoneStatus.ONTARGET) {
                theRightLooker.setPosition(0.0);
                theLeftLooker.setPosition(0.25);
            } else if (VisionCommand.intakeStatus == VisionCommand.stoneStatus.TILTLEFT) {
                theLeftLooker.setPosition(0.0);
            } else if (VisionCommand.intakeStatus == VisionCommand.stoneStatus.TILTRIGHT) {
                theRightLooker.setPosition(0.25);
            } else if (VisionCommand.intakeStatus == VisionCommand.stoneStatus.FARLEFT) {
                theRightLooker.setPosition(0.0);
                theLeftLooker.setPosition(0.25);
            } else if (VisionCommand.intakeStatus == VisionCommand.stoneStatus.FARRIGHT) {
                theRightLooker.setPosition(0.0);
                theLeftLooker.setPosition(0.25);
            } else {
                theRightLooker.setPosition(0.0);
                theLeftLooker.setPosition(0.25);
            }
            intLeft.setPower(1);
            intRight.setPower(1);
        } else if (manip.right_stick_y < -0.05) {
            intLeft.setPower(-1);
            intRight.setPower(-1);
        } else if (manip.a || driver.right_bumper) {
            if (VisionCommand.stoneY > IntakePipeline.slowThresh) {
                intLeft.setPower(-0.6);
                intRight.setPower(-0.6);
            } else {
                intLeft.setPower(-0.6);
                intRight.setPower(-0.6);
            }
        } else {
            shuttleFirst = true;
            intLeft.setPower(0);
            intRight.setPower(0);
            theRightLooker.setPosition(0.0);
            theLeftLooker.setPosition(0.25);

        }
    }

    public void autoStack() {

        boolean done = false;
        lifttarg = getTargHeight();

        /*while (!done && !driver.b) {
            if (VisionCommand.stackStatus == VisionCommand.stackStatus.DONE) {
                gripper.setPosition(0.7);
                done = true;
            } else {
                gripper.setPosition(0.3);
            }
            updateLift();
            updateRotation();
        }*/
    }

    public void cairrage() {

        if (manip.x) {
            gripper.setPosition(0.3);
        } else {
            gripper.setPosition(0.7);
        }

        if (manip.b) {
            swinger.setPosition(0.35);
        } else {
            swinger.setPosition(0.05);
        }
    }

    public void hooker() {

        if (manip.left_trigger > 0.55) {
            hooker.setPosition(0.45);
        } else if (manip.left_trigger > 0.05) {
            hooker.setPosition(0.58);
        } else {
            hooker.setPosition(1.0);
        }


        op.telemetry.addData("YOOV", hooker.getPosition());

        //op.telemetry.addData("hooker", hooker.getPosition());
        //op.telemetry.update();
    }

    public void adjTargLevel() {

        boolean buffer = startTime + 500 < System.currentTimeMillis();

        if (manip.left_bumper && buffer) {

            nextLevel++;
            startTime = System.currentTimeMillis();

        }

        if (manip.left_trigger > 0.05 && buffer && nextLevel > 0) {

            nextLevel--;
            startTime = System.currentTimeMillis();

        }

        Log.e(TAG, "Position: " + nextLevel);
        Log.e(TAG, "Buffer: " + buffer);

    }

    public int getTargHeight() {
        return BASE_HEIGHT + (TICKS_PER_LEVEL * nextLevel);
    }

    public void moveLift() {

        if (manip.dpad_up || manip.right_stick_y < 0.05) {

            lifttarg = getTargHeight();

        }
        if (manip.dpad_up || manip.right_stick_y > 0.05) {

            lifttarg = RELOAD_HEIGHT;

        }

    }

    public void updateLift() {

        liftPID.adjTarg(lifttarg);

        lift.setPower(liftPID.status(lift.getCurrentPosition()));

    }

    public void moveRotation() {

        if (manip.right_trigger > 0.05 || manip.dpad_down) {

            rotationTarg = RELOAD;

        }
        if (manip.right_bumper || manip.dpad_up) {

            rotationTarg = VERTICAL;

        }

    }

    public void updateRotation() {

        rotPID.adjTarg(rotationTarg);

        rotator.setPower(rotPID.status(rotator.getCurrentPosition()));

    }

    public void playMusic () {
        if (!driver.dpad_down) {
            if (manip.y) {
                sound.playSound(1, 0, true);
            } else if (manip.b) {
                sound.playSound(2, 0, true);
            } else if (manip.a) {
                sound.playSound(3, 0, true);
            } else if (manip.x) {
                sound.playSound(4, 0, true);
            }
        } else {
            sound.playSound(1,0,false);
            sound.playSound(2,0,false);
            sound.playSound(3,0,false);
            sound.playSound(4,0,false);
        }
    }

    private void manualTransfer() {
        leftTransfer.setPosition(0.5 + (manip.left_stick_y / 2));
        rightTransfer.setPosition(0.5 - (manip.left_stick_y / 2));
    }

    @Override
    public void stop() {
        theDooker.setPower(0);
    }
}