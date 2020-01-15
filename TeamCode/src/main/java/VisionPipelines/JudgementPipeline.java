package VisionPipelines;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

import Utilities.ImageUtil;
import Utilities.myRect;

public class JudgementPipeline extends OpenCvPipeline {
    String TAG = "INTAKE PIPELINE";
    boolean processing = true;

    public static Point position;
    public static boolean found;

    @Override
    public void onViewportTapped() {
        processing = !processing;
    }

    @Override
    public Mat processFrame(Mat rgbaFrame) {

        if (processing) {

            Imgproc.line(rgbaFrame, new Point(), new Point(), new Scalar(255, 255, 255), 5);

            //convert to hsv
            Mat hsv = new Mat();
            Imgproc.cvtColor(rgbaFrame, hsv, Imgproc.COLOR_RGB2HSV);

            //h range is 0-179
            //s range is 0-255
            //v range is 0-255

            //values stored as list of minimum and maximum hsv values, red then green then blue
            List<Scalar> hsvMin = new ArrayList<>();
            List<Scalar> hsvMax = new ArrayList<>();

            hsvMin.add(new Scalar(150, 50, 100)); //yellow min
            hsvMax.add(new Scalar(179, 255, 255)); //yellow max

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


            maskedImage = new Mat();

            //Applying HSV limits
            ImageUtil.hsvInRange(hsv, hsvMin.get(0), hsvMax.get(0), maskedImage);

            //Start Core's additions
            Mat contTemp = maskedImage.clone();
            Imgproc.findContours(contTemp, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
            //End Core's addition

            rgbaChannels.add(maskedImage.clone());
            rgbaChannels.add(maskedImage.clone());
            rgbaChannels.add(maskedImage.clone());


            myRect maxRect = null;
            double maxArea = 0;
            found = false;
            for (MatOfPoint currCont : contours) {
                myRect rect = ImageUtil.rectToMyRect(Imgproc.boundingRect(currCont));

                if (rect.area() > maxArea && rect.area() > 100) {
                    found = true;
                    maxRect = rect;
                    maxArea = rect.area();
                }
            }

            if (maxRect != null) {
                position = new Point(maxRect.mid().x, maxRect.mid().y);
            }


            //add empty alpha channels
            rgbaChannels.add(Mat.zeros(hsv.size(), CvType.CV_8UC1));

            Core.merge(rgbaChannels, rgbaFrame);

            if (maxRect != null) {
                Imgproc.rectangle(rgbaFrame, maxRect.bl(), maxRect.tr(), new Scalar(0, 255, 0));
            }
        }


        return rgbaFrame;
    }
}