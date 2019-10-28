package Competition.Programs.TeleOp;

import android.media.MediaPlayer;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.R;

import Utilities.PID;

@TeleOp(name = "TEST")
public class test extends OpMode {

    DcMotor lift, yes;
    ModernRoboticsI2cRangeSensor ultra;
    PID movePID = new PID();

    boolean first = true;

    @Override
    public void init() {
        lift = hardwareMap.get(DcMotor.class, "lift");
        yes  = hardwareMap.get(DcMotor.class, "fright");
        ultra = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "SONIC THE HEDGEHOG");
        movePID.setup(0.00625, 0, 0, 0.2, 20, 10);
    }

    @Override
    public void loop() {
        lift.setPower(gamepad1.left_stick_y);
        yes.setPower(-gamepad1.left_stick_y);

        telemetry.addData("Yes", ultra.getDistance(DistanceUnit.INCH));
        telemetry.addData("PID", movePID.status(ultra.getDistance(DistanceUnit.INCH)));
        telemetry.addData("A LOT OF DAMAGE", this.hardwareMap.voltageSensor.iterator().next().getVoltage());
        telemetry.update();

        /*if (this.hardwareMap.voltageSensor.iterator().next().getVoltage() < 7.0 && first) {
            MediaPlayer player = MediaPlayer.create(hardwareMap.appContext, R.raw.tunaktunaktun);
            player.setLooping(false);
            player.seekTo(21000);
            player.start();
            first = false;
        }*/
    }
}