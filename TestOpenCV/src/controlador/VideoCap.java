package controlador;

import java.awt.image.BufferedImage;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import modelo.FaceDetector;
import modelo.FaceRecognitionEigen;
import modelo.Mat2Image;

public class VideoCap {

	public VideoCap() {
		// TODO Auto-generated constructor stub
	}
		static {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		}
		private static VideoCapture camera;
		//private static Mat2Image mat2Img = new Mat2Image();
		private static FaceRecognitionEigen fr;
		
		public VideoCap (FaceRecognitionEigen f){
			if (camera==null) {
				cameraOn();
				fr=f;
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
	        camera.read(Mat2Image.getMat());
	        return Mat2Image.getImage(Mat2Image.getMat());
	    }
		public BufferedImage detectFaces() {		
	        camera.read(Mat2Image.getMat());
	        //detectFaces(mat2Img.getMat());
	        recognizeFaces(Mat2Image.getMat());
	         //FaceMatches fc= new FaceMatches(grayM1,grayM2);
	        //fc.compareMatches();
	        //FaceKnnMatches fk=new FaceKnnMatches(grayM1,grayM2);
	        //fk.compareMatches();
	        //FaceKnnBestMatches fkb=new FaceKnnBestMatches(grayM1,grayM2);
	        //fkb.compareMatches();
	        //FaceCompare.compareKnnMatches(m1, m2);
//	        FaceRecognition.recognizeFace(mat2Img.getMat());
	          return Mat2Image.getImage(Mat2Image.getMat());
	    }
		private void recognizeFaces(Mat frame) {
			Mat face=null;
			Mat grayFrame=new Mat();
			Size size = new Size(150,150);
			//System.out.println("M  Size H:"+frame.height()+" W:"+frame.width()+" Color: "+frame.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+frame.channels());
			//Imgproc.pyrDown(frame, gray);
	        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
			Rect[] rects = FaceDetector.detectFaces(grayFrame);
			for (Rect rect : rects) {
				face=new Mat(grayFrame,rect);
				Imgproc.equalizeHist(face, face);
	            Imgproc.resize(face, face, size, 1, 1, Imgproc.INTER_CUBIC);
	            //Calib3d.findHomography(pts1Mat, pts2Mat, Calib3d.RANSAC, 15, outputMask, 2000, 0.995);
				//System.out.println("M  Size H:"+face.height()+" W:"+face.width()+" Color: "+face.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+frame.channels());
		        String nombre=fr.recognize(face);
	            if (nombre==null) nombre="Unknown";
	            int x = (int) Math.max(rect.tl().x - 10, 0);
	            int y = (int) Math.max(rect.tl().y - 10, 0);
	            Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 1);
	            Imgproc.putText(frame, String.format(nombre), new Point(x, y), Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0), 1);
			}
		}
		private void detectFaces(Mat frame) {
			
			Rect[] rect = FaceDetector.detectFaces(frame);	
			for (int i=0;i<rect.length;i++) {
		        Imgproc.rectangle(frame, rect[i].tl(), rect[i].br(), new Scalar(0, 0, 255), 2);
		        System.out.println(String.format("Ubicacion %s %s %s %s",rect[i].x,rect[i].y,rect[i].width,rect[i].height));
			}
		}
		private void getDetectedEyes(Mat m) {
			
			Rect[] rect = FaceDetector.detectEyes(m);	
			for (int i=0;i<rect.length;i++) {
				Imgproc.rectangle(m, 
		        					new Point(rect[i].x, rect[i].y), 
		        					new Point(rect[i].x + rect[i].width, rect[i].y + rect[i].height), 
		        					new Scalar(0, 255, 0));	
		        System.out.println(String.format("Ubicacion %s %s %s %s",rect[i].x,rect[i].y,rect[i].width,rect[i].height));
			}
		}
}
