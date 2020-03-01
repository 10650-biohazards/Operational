package Competition;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import FtcExplosivesPackage.BiohazardNavX;

public class FinalMap {
    public static DcMotor bright, fright, bleft, fleft, intLeft, intRight, lift, rotator;
    public static Servo gripper, swinger, hooker;
    public static BiohazardNavX gyro;
    public static WebcamName stoneCam;
    public static ModernRoboticsI2cRangeSensor frontRange, backRange, sideRange;

    public FinalMap(HardwareMap hw) {
        //Hello, there
        //GENERAL KENOBI!
        bright  = hw.get(DcMotor.class, "bright");
        fright  = hw.get(DcMotor.class, "fright");
        bleft   = hw.get(DcMotor.class, "bleft");
        fleft   = hw.get(DcMotor.class, "fleft");
        lift    = hw.get(DcMotor.class, "lift");
        rotator = hw.get(DcMotor.class, "rotator");
        intLeft = hw.get(DcMotor.class, "intLeft");
        intRight = hw.get(DcMotor.class, "intRight");

        bright.setDirection(DcMotorSimple.Direction.REVERSE);
        fright.setDirection(DcMotorSimple.Direction.REVERSE);
        intRight.setDirection(DcMotorSimple.Direction.REVERSE);

        stoneCam = hw.get(WebcamName.class, "stoned cam");

        gripper = hw.get(Servo.class, "firm grasp");
        swinger = hw.get(Servo.class, "ragtime");
        hooker = hw.get(Servo.class, "hooker");

        gyro = new BiohazardNavX(hw, "navX", 0);
        frontRange = hw.get(ModernRoboticsI2cRangeSensor.class, "frontRange");
        backRange = hw.get(ModernRoboticsI2cRangeSensor.class, "backRange");
        sideRange = hw.get(ModernRoboticsI2cRangeSensor.class, "sideRange");
    }
}