package FtcExplosivesPackage;

public class Utilities {

    private enum sign{
        POSITIVE,
        NEGATIVE
    }

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

    public static class multiReturn{

        public double return1;
        public double return2;
        public double return3;
        public double return4;
        public double return5;
        public double return6;

        public multiReturn (double... Return){
            int i;

            for (i = 0; i < Return.length; i++){
                if (i == 0){
                    return1 = Return[i];
                } else if (i == 1){
                    return2 = Return[i];
                } else if (i == 2){
                    return3 = Return[i];
                } else if (i == 3){
                    return4 = Return[i];
                } else if (i == 4){
                    return5 = Return[i];
                } else if (i == 5) {
                    return6 = Return[i];
                }
            }
        }
    }

    public static double getSign (double val){
        return val/Math.abs(val);
    }

    public static float ScaleAdjustment(float maxValue, float... val){

        float largestValue = 0;
        float adjustment;

        for(int i = 0; i <= val.length; i++){
            largestValue = Math.max(Math.abs(val[i]),largestValue);
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

    public static multiReturn quadSolve(double coefficientA, double coefficientB, double coefficientC){
        double A = coefficientA;
        double B = coefficientB;
        double C = coefficientC;
        double answer1 = 0;
        double answer2 = 0;
        double imaginary = Math.sqrt(-1);

        if (Math.pow(B,2)-4*A*C < 0){
            return new Utilities.multiReturn(imaginary,imaginary);
        } else {
            answer1 = (-1*B+Math.sqrt(Math.pow(B,2)-4*A*C))/2*A;
            answer2 = (-1*B-Math.sqrt(Math.pow(B,2)-4*A*C))/2*A;
            return new Utilities.multiReturn(answer1,answer2);
        }
    }

    public static double quadEval(double x, double coefficientA, double coefficientB, double coefficientC){
        return coefficientA*(Math.pow(x,2))+coefficientB*x+coefficientC;
    }

    public static double cubicEval(double x, double coefficientA, double coefficientB, double coefficientC, double coefficientD){
        return coefficientA*(Math.pow(x,3))+coefficientB*(Math.pow(x,2))+coefficientC*x+coefficientD;
    }

    public static double ellipseEval(double x, double h, double k, double a, double b, Utilities.sign sign){
        double y = 0;
        switch (sign) {
            case POSITIVE:
                y = (Math.sqrt((1 - Math.pow((x - h), 2) / Math.pow(a, 2)) * Math.pow(b, 2)) + k);
                break;
            case NEGATIVE:
                y = ((-1 * Math.sqrt((1 - Math.pow((x - h), 2) / Math.pow(a, 2)) * Math.pow(b, 2))) + k);
                break;
        }
        return y;
    }

    public static double circleEval(double x, double h, double k, double radius, Utilities.sign sign){
        double y = 0;
        switch(sign){
            case POSITIVE:
                y = (Math.sqrt(Math.pow(radius,2)-(Math.pow((x-h),2))))+k;
                break;
            case NEGATIVE:
                y = -1*(Math.sqrt(Math.pow(radius,2)-(Math.pow((x-h),2))))+k;
                break;
        }
        return y;
    }

    public static double exponentialEval(double x, double h, double k, double a, double b){
        double y = a*Math.pow(b,(x-h))+k;
        return y;
    }
}
