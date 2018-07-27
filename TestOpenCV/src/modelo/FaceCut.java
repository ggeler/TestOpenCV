package modelo;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

class FaceCut {
	public static Mat cutDetectedFaces(Mat m) {
		Rect[] rect = FaceDetector.detectFaces(m);
		Rect rectCrop=null;
		Mat matOut=null;
		if (rect.length==1) {
			rectCrop = new Rect(rect[0].x, rect[0].y , rect[0].width, rect[0].height);
			matOut=m.submat(rectCrop);
		}
		else
			rectCrop = null;
		return matOut;
	}
	public static Mat resize(Mat m) {

        //Mat imageMat = new Mat();
        Mat image_res = new Mat();
        //changes bitmap to Mat   
        //Utils.bitmapToMat(image, imageMat);

        //Resize Image
        Imgproc.resize(m,image_res,new Size(150, 150),0.5,0.5,Imgproc.INTER_AREA);
        return image_res;

	}
}
