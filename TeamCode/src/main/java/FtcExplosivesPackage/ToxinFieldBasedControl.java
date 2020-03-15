package FtcExplosivesPackage;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.slf4j.helpers.Util;

import FtcExplosivesPackage.Utilities;

/**
 *   Created by Aidan 9/14/19
 */

public class ToxinFieldBasedControl{





    public static Utilities.Point getLeftJoystick(Gamepad driver, BiohazardNavX gyro){
        Utilities.Point stick  = new Utilities.Point(driver.left_stick_x, driver.left_stick_y);

        float gyroAngle = (float)gyro.getYaw();

        return Utilities.Rotate2D(stick, -gyroAngle);

    }

    public static Utilities.Point getRightJoystick(Gamepad driver, BiohazardNavX gyro){
        Utilities.Point stick = new Utilities.Point(driver.right_stick_x, driver.right_stick_y);

        float gyroAngle = (float)gyro.getYaw();

        return Utilities.Rotate2D(stick, -gyroAngle);



    }
}
