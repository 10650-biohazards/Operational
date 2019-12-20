package Competition.Commands;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Competition.Robot;
import Competition.RobotMap;
import FtcExplosivesPackage.BioCommand;
import FtcExplosivesPackage.BiohazardTele;
import Utilities.PID;

import org.firstinspires.ftc.teamcode.Soundboard;

import static Competition.Commands.VisionCommand.stoneStatus.NONE;
import static Competition.Commands.VisionCommand.stoneStatus.ONTARGET;
import static Competition.Commands.VisionCommand.stoneStatus.TILTLEFT;
import static Competition.Commands.VisionCommand.stoneStatus.TILTRIGHT;


public class MechCommand extends BioCommand {

    String TAG = "MechCommand";

    DcMotor intRight, intLeft, lift, rotator;

    Servo swinger, gripper, hooker, booker;

    private Gamepad manip, driver;

    BiohazardTele op;

    Soundboard sound;

    HardwareMap hw;


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
    }

    @Override
    public void init() {

        liftPID = new PID();

        intRight = RobotMap.intRight;
        intLeft = RobotMap.intLeft;

        swinger = RobotMap.swinger;
        gripper = RobotMap.gripper;
        hooker = RobotMap.hooker;
        booker = RobotMap.booker;
        lift = RobotMap.lift;
        rotator = RobotMap.rotator;

        manip = Robot.manipulator;
        driver = Robot.driver;

        sound = new Soundboard(this.hw);

        lifttarg = lift.getCurrentPosition();
        rotationTarg = rotator.getCurrentPosition();
        liftPID.setup (42,0,42,0,0,lifttarg);
        rotPID.setup(42, 0, 42, 0, 0, rotationTarg);

    }

    @Override
    public void start() {

        intLeft.setPower(0);
        intRight.setPower(0);

        startTime = System.currentTimeMillis();

    }

    @Override
    public void loop() {

        cairrage();
        //intake();
        hooker();

        booker();

        intake();

        adjTargLevel();
        moveLift();
        //updateLift();

        moveRotation();
        //updateRotation();



        //playMusic();

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

    public void intake() {

        intLeft.setPower(manip.right_stick_y );
        intRight.setPower(manip.right_stick_y);

        /*if (VisionCommand.status == NONE) {
            Log.e(TAG, "No stone in sight");
            intLeft.setPower(0);
            intRight.setPower(0);
        }
        if (VisionCommand.status == ONTARGET) {
            Log.e(TAG, "On target!");
            intLeft.setPower(1);
            intRight.setPower(1);
        }
        if (VisionCommand.status == TILTRIGHT) {
            Log.e(TAG, "Off course! Tilted to the right!");
            intLeft.setPower(1);
            intRight.setPower(0.8);
        }
        if (VisionCommand.status == TILTLEFT) {
            Log.e(TAG, "Off course! Tilted to the left!");
            intLeft.setPower(0.8);
            intRight.setPower(1);
        }*/
    }

    public void hooker() {

            hooker.setPosition(-0.5 * manip.left_trigger + 1);

            op.telemetry.addData("hooker", hooker.getPosition());
            op.telemetry.update();
    }

    public void booker() {
        booker.setPosition(0.7-(1-manip.right_trigger) * 0.4);

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

    @Override
    public void stop() {

    }
}