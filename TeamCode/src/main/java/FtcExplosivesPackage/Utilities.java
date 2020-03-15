package FtcExplosivesPackage;

public class Utilities {

    public static class Point{
        public double x;
        public double y;
        public Point(double X, double Y){
            x = X;
            y = Y;
        }
        public Point(){
            x = 0;
            y = 0;
        }

    }

    public static double getSign (double val){
        double rval;
        rval = val/Math.abs(val);
        return rval;
    }

    public static float ScaleAdjustment(float maxValue, float... val){

        float largestValue = 0;
        float previousLargestValue = 0;
        float adjustment;

        for(int i = 0; i < val.length; i++){
            largestValue = Math.max(val[i],previousLargestValue);
            previousLargestValue = largestValue;
        }
        if(largestValue > maxValue){
            adjustment = maxValue/largestValue;
        } else {
            adjustment = 1;
        }
        return adjustment;
    }

    public static double Distance(double X1, double Y1, double X2, double Y2){
        return Math.sqrt(Math.pow(X1-X2,2)+Math.pow(Y1-Y2,2));
    }


    public static Utilities.Point Rotate2D(Utilities.Point point, float angle){
        double angleRad = Math.toRadians(angle);
        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);
        return new Utilities.Point(point.x * cos - point.y * sin,point.x * sin + point.y * cos);
    }

}
