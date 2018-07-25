package modelo;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;


public class VideoCap {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private static VideoCapture camera;
	private static Mat2Image mat2Img = new Mat2Image();
	//private static FaceRecognition fr = new FaceRecognition();
	
	public VideoCap (){
		if (camera==null) {
			cameraOn();
		}
	}
	public void finalize() {
		cameraOff();
		camera=null;
	}
	public void cameraOn() {
		camera = new VideoCapture(0);
		camera.open(0);
       if(!camera.isOpened()){
            System.out.println("Error");
        }
        else {                  

        	System.out.println("Camara On");
         }
	}
	public void cameraOff() {
		if (camera.isOpened()) {
			camera.release();
			System.out.println("Camara Off");
		}
	}
	public BufferedImage getOneFrame() {
        camera.read(mat2Img.getMat());
        return mat2Img.getImage(mat2Img.getMat());
    }
	public BufferedImage getFacesOneFrame() {
        camera.read(mat2Img.getMat());
        //getDetectedFaces(mat2Img.getMat());
        //getDetectedEyes(mat2Img.getMat());
	    Mat grayM1=Imgcodecs.imread("ggant.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    Mat grayM2=Imgcodecs.imread("gaston3.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    /*Mat grayM1 = new Mat(m1.height(),m1.width(),0);
		Mat grayM2 = new Mat(m2.height(),m2.width(),0);
	
		Imgproc.cvtColor(m1, grayM1, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(m2, grayM2, Imgproc.COLOR_RGB2GRAY);*/
        FaceMatches fc= new FaceMatches(grayM1,grayM2);
        fc.compareMatches();
        FaceKnnMatches fk=new FaceKnnMatches(grayM1,grayM2);
        fk.compareMatches();
        FaceKnnBestMatches fkb=new FaceKnnBestMatches(grayM1,grayM2);
        fkb.compareMatches();
        //FaceCompare.compareKnnMatches(m1, m2);
//        FaceRecognition.recognizeFace(mat2Img.getMat());
          return mat2Img.getImage(mat2Img.getMat());
    }

	private void getDetectedFaces(Mat m) {
		
		Rect[] rect = FaceRecognition.detectFaces(m);	
		for (int i=0;i<rect.length;i++) {
	        Imgproc.rectangle(m, 
	        					new Point(rect[i].x, rect[i].y), 
	        					new Point(rect[i].x + rect[i].width, rect[i].y + rect[i].height), 
	        					new Scalar(0, 255, 0));
			System.out.println(String.format("Ubicacion %s %s %s %s",rect[i].x,rect[i].y,rect[i].width,rect[i].height));
		}
	}
	private void getDetectedEyes(Mat m) {
		
		Rect[] rect = FaceRecognition.detectEyes(m);	
		for (int i=0;i<rect.length;i++) {
			Imgproc.rectangle(m, 
	        					new Point(rect[i].x, rect[i].y), 
	        					new Point(rect[i].x + rect[i].width, rect[i].y + rect[i].height), 
	        					new Scalar(0, 255, 0));	
	        System.out.println(String.format("Ubicacion %s %s %s %s",rect[i].x,rect[i].y,rect[i].width,rect[i].height));
		}
	}
}
