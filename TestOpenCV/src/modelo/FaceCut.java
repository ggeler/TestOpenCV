package modelo;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

class FaceCut {
	public static Mat cutDetectedFaces(Mat m) {
		Rect[] rect = FaceDectector.detectFaces(m);
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
}
