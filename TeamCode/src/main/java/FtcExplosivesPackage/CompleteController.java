package FtcExplosivesPackage;

/**
 * Created by Varun on 11/16/2017.
 * Refactored and Edited by Aidan on 3/14/2020.
 */


import com.qualcomm.robotcore.hardware.Gamepad;

import FtcExplosivesPackage.Utilities;

public class CompleteController {
    Gamepad gamepad;
    JoystickDeadzoneShape ds;
    JoystickShape js;
    private double TD;
    private boolean RT;
    private boolean reverseX;
    private boolean reverseY;
    public double JoystickDeadzoneMag;

    public enum JoystickDeadzoneShape{
        CIRCULAR,
        SQUARE
    }

    public enum JoystickShape{
        CIRCULAR,
        SQUARE
    }

    public void SetControllerJoystick (Gamepad gamepad, JoystickShape js, JoystickDeadzoneShape ds, double JDM, boolean reverseX, boolean reverseY){
        this.gamepad = gamepad;
        this.ds = ds;
        this.js = js;
        this.JoystickDeadzoneMag = JDM;
        this.reverseX = reverseX;
        this.reverseY = reverseY;
    }

    public void SetControllerTrigger (Gamepad gamepad, double TriggerDeadzone, boolean ReverseTrigger){
        this.gamepad = gamepad;
        this.TD = TriggerDeadzone;
        this.RT = ReverseTrigger;

    }

    public double lx(){
        return GetControllerJoystick(gamepad.left_stick_x,gamepad.left_stick_y)[0];
    }

    public double ly(){
        return GetControllerJoystick(gamepad.left_stick_x,gamepad.left_stick_y)[1];

    }

    public double rx(){
        return GetControllerJoystick(gamepad.right_stick_x,gamepad.right_stick_y)[0];
    }

    public double ry(){
        return GetControllerJoystick(gamepad.right_stick_x,gamepad.right_stick_y)[1];
    }

    public double leftTrigger(){
        return GetControllerTrigger(gamepad.left_trigger);
    }

    public double rightTrigger(){
        return GetControllerTrigger(gamepad.right_trigger);
    }

    public boolean leftStickButton(){
        return gamepad.left_stick_button;
    }

    public boolean rightStickButton(){
        return gamepad.right_stick_button;
    }

    public boolean leftBumper(){
        return gamepad.left_bumper;
    }

    public boolean rightBumper(){
        return gamepad.right_bumper;
    }

    public boolean dpadUp(){
        return gamepad.dpad_up;
    }

    public boolean dpadDown(){
        return gamepad.dpad_down;
    }

    public boolean dpadLeft(){
        return gamepad.dpad_left;
    }

    public boolean dpadRight(){
        return gamepad.dpad_right;
    }

    public boolean backButton() {
        return gamepad.back;
    }

    public boolean a(){
        return gamepad.a;
    }

    public boolean b(){
        return gamepad.b;
    }

    public boolean x(){
        return gamepad.x;
    }

    public boolean y(){
        return gamepad.y;
    }

    private double[] GetControllerJoystick (double X, double Y){
        double XY[] = {0,0};
        boolean OutsideJoystickDeadzone = false;
        double JDM = this.JoystickDeadzoneMag;
        if(reverseX) X=-X;
        if(reverseY) Y=-Y;
        switch(this.ds){
            case CIRCULAR:  if (JDM > Utilities.Distance(X,Y,0,0)){
                                OutsideJoystickDeadzone = false;
                            } else {
                                OutsideJoystickDeadzone = true;
                            }
                            break;

            case SQUARE:    if (X < JDM || Y < JDM){
                                OutsideJoystickDeadzone = false;
                            } else{
                                OutsideJoystickDeadzone = true;
                            }
                            break;
        }
        if (OutsideJoystickDeadzone == true) {
            switch(this.js){

                case CIRCULAR:  XY[0] = X;
                                XY[1] = Y;
                                break;

                //Square joystick shape means that the maximum output of a joystick lies on a square that fits inside the circle
                //This allows for strafing diagonally at maximum speed
                case SQUARE:    XY[0] = X * Math.sqrt(2);
                                XY[1] = Y * Math.sqrt(2);

                                if (XY[0] > 1 || XY[0] < -1) {
                                    XY[0] = Utilities.getSign(XY[0]);
                                } else if (XY[1] > 1 || XY[1] < -1) {
                                    XY[1] = Utilities.getSign(XY[1]);
                                }
                                break;
            }
        } else {
            XY[0] = 0;
            XY[1] = 0;
        }
        return XY;
    }
    private double GetControllerTrigger (double TriggerPos){
        double rval;
        if(TriggerPos >= TD){
            rval = TriggerPos-TD;
        } else {
            rval = 0;
        }
        rval = rval/(1-TD);
        if(RT) {
            rval = 1 - rval;
        }

        return rval;
    }
}