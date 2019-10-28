package VisionPipelines;

import android.util.Log;

import org.firstinspires.ftc.teamcode.follow;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

import Utilities.ImageUtil;
import Utilities.myRect;

public class JoulePipeline extends OpenCvPipeline {
    String TAG = "Stack Processor";
    boolean processing = true;

    private static Point r1 = new Point(0, 0);
    private static Point r2 = new Point(176, 144);
    private static myRect lookingBox = new myRect(r1, r2);

    public static Point location;

    public static double lastArea = 6400;

    private static Point lastGoodLoc;

    @Override
    public void onViewportTapped() {
        processing = !processing;
    }

    @Override
    public Mat processFrame(Mat rgbaFrame) {

        if (processing) {

            //convert to hsv
            Mat hsv = new Mat();
            Imgproc.cvtColor(rgbaFrame, hsv, Imgproc.COLOR_RGB2HSV);

            //h range is 0-179
            //s range is 0-255
            //v range is 0-255

            //values stored as list of minimum and maximum hsv values, red then green then blue
            List<Scalar> hsvMin = new ArrayList<>();
            List<Scalar> hsvMax = new ArrayList<>();

            hsvMin.add(new Scalar(150, 50, 120)); //red min
            hsvMax.add(new Scalar(179, 255, 255)); //red max

            hsvMin.add(new Scalar(0, 0, 0)); //null min
            hsvMax.add(new Scalar(0, 0, 0)); //null max

            hsvMin.add(new Scalar(0, 0, 0)); //null min
            hsvMax.add(new Scalar(0, 0, 0)); //null max


            List<Mat> rgbaChannels = new ArrayList<>();


            Mat maskedImage;

            //Core's additions
            Mat hierarchy = new Mat();
            List<MatOfPoint> contours = new ArrayList<>();
            //End

            for (int i = 0; i < 3; i++) {

                maskedImage = new Mat();

                //Applying HSV limits
                ImageUtil.hsvInRange(hsv, hsvMin.get(i), hsvMax.get(i), maskedImage);

                //Start Core's additions
                Mat contTemp = maskedImage.clone();
                Imgproc.findContours(contTemp, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
                //End Core's addition

                rgbaChannels.add(maskedImage.clone());
            }

            //add empty alpha channels
            rgbaChannels.add(Mat.zeros(hsv.size(), CvType.CV_8UC1));

            Core.merge(rgbaChannels, rgbaFrame);

            double maxSize = Double.MIN_VALUE;
            myRect maxRect = null;

            for (MatOfPoint currCont : contours) {
                myRect rect = ImageUtil.rectToMyRect(Imgproc.boundingRect(currCont));

                if (lookingBox.contains(rect.mid()) && rect.area() > 800) {
                    Imgproc.rectangle(rgbaFrame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);

                    if (rect.area() > maxSize/* && Math.abs(rect.area() - lastArea) < lastArea * 0.5*/) {
                        maxSize = rect.area();
                        maxRect = rect;
                    }
                } else {
                    Imgproc.rectangle(rgbaFrame, rect.tl(), rect.br(), new Scalar(255, 255, 0), 2);
                }
            }

            if (maxRect != null) {
                location = maxRect.mid();
                Imgproc.circle(rgbaFrame, maxRect.mid(), 5, new Scalar(255, 255, 255), 2);
            } else {
                location = null;
            }
            //End






            if (location != null) {
                lastGoodLoc = location;
                lookingBox = new myRect(new Point(location.x - 40, location.y - 40), new Point(location.x + 40, location.y + 40));
                lastArea = maxRect.area();
            } else {
                if (lastGoodLoc != null) {
                    if (lastGoodLoc.x > 120) {
                        lookingBox = new myRect(new Point(rgbaFrame.width(), rgbaFrame.height()), new Point(rgbaFrame.width() - 70, 0));
                    } else {
                        lookingBox = new myRect(new Point(0, rgbaFrame.height()), new Point(70, 0));
                    }
                }
            }

            Imgproc.rectangle(rgbaFrame, lookingBox.tl(), lookingBox.br(), new Scalar(0, 0, 255), 2);

            Log.i(TAG, lookingBox.mid().toString());
        }

        return rgbaFrame;
    }

    private enum condition {
        ONTARGET,
        OFFLEFT,
        OFFRIGHT,
        SLIGHTRIGHT,
        SLIGHTLEFT,
        UNREGISTERED
    }
}