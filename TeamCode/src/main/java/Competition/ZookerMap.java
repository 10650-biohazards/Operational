package Competition;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import FtcExplosivesPackage.BiohazardNavX;

public class ZookerMap {
    public static DcMotor bright, fright, bleft, fleft, intLeft, intRight, lift, rotator, theDooker;
    public static Servo gripper, swinger, hooker, theCooker, theBooker, leftTransfer, rightTransfer, skyGrabber, theLooker, theRightLooker;
    public static BiohazardNavX gyro;
    public static WebcamName stoneCam;
    public static ModernRoboticsI2cRangeSensor frontRange, backRange, sideRange;

    public ZookerMap(HardwareMap hw) {
        //Hello, there
        //GENERAL KENOBI!
        bright  = hw.get(DcMotor.class, "bright");
        fright  = hw.get(DcMotor.class, "fright");
        bleft   = hw.get(DcMotor.class, "bleft");
        fleft   = hw.get(DcMotor.class, "fleft");
        intLeft = hw.get(DcMotor.class, "intLeft");
        intRight = hw.get(DcMotor.class, "intRight");
        theDooker = hw.get(DcMotor.class, "Uncle Ben");

        bright.setDirection(DcMotorSimple.Direction.REVERSE);
        fright.setDirection(DcMotorSimple.Direction.REVERSE);
        intRight.setDirection(DcMotorSimple.Direction.REVERSE);




        stoneCam = hw.get(WebcamName.class, "stoned cam");

        hooker = hw.get(Servo.class, "hooker");
        theCooker = hw.get(Servo.class, "cooker");
        theBooker = hw.get(Servo.class, "booker");
        leftTransfer = hw.get(Servo.class, "leftTr");
        rightTransfer = hw.get(Servo.class, "rightTr");
        skyGrabber = hw.get(Servo.class, "sky");
        theLooker = hw.get(Servo.class, "theLooker");
        theRightLooker = hw.get(Servo.class, "theRightLooker");

        gyro = new BiohazardNavX(hw, "navX", 0);
        frontRange = hw.get(ModernRoboticsI2cRangeSensor.class, "frontRange");
        backRange = hw.get(ModernRoboticsI2cRangeSensor.class, "backRange");
        sideRange = hw.get(ModernRoboticsI2cRangeSensor.class, "sideRange");
    }
}