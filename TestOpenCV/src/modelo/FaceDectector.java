package modelo;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

class FaceDectector {
	private static  CascadeClassifier faceDetector = new CascadeClassifier("/home/gaston/Downloads/opencv-3.4.2/data/lbpcascades/lbpcascade_frontalface_improved.xml");
			//"/home/gaston/Downloads/opencv-3.4.1/data/lbpcascades/lbpcascade_frontalface_improved.xml");
	//private static  CascadeClassifier eyesDetector = new CascadeClassifier("/home/gaston/Downloads/xml/haarcascade_mcs_nose.xml");
	private static  CascadeClassifier eyesDetector = new CascadeClassifier("/home/gaston/Downloads/opencv-3.4.1/data/haarcascades_cuda/haarcascade_eye.xml");
	
	public static  Rect[] detectFaces(Mat m) {
	    MatOfRect detectedFaces = new MatOfRect();
	    faceDetector.detectMultiScale(m, detectedFaces);
	    //System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	    return detectedFaces.toArray();
	}
	public static  MatOfRect detectFaces2(Mat m) {
	    MatOfRect detectedFaces = new MatOfRect();
	    faceDetector.detectMultiScale(m, detectedFaces);
	    //System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	    return detectedFaces;
	}
	public static  Rect[] detectEyes(Mat m) {
		MatOfRect eyesDetection = new MatOfRect();
		eyesDetector.detectMultiScale(m, eyesDetection);
	    //System.out.println(String.format("Detected %s eyes", eyesDetection.toArray().length));
	    return eyesDetection.toArray();
	    //faceRecognizer
	}
/*	public static void recognizeFace(Mat m) {
		FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
		faceRecognizer.predict_label(m);
		//Face face = new Face();
		Face.drawFacemarks(m, m);
	}
	*/
}
