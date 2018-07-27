package modelo;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;


public class VideoCap {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private static VideoCapture camera;
	private static Mat2Image mat2Img = new Mat2Image();
	private FaceRecognitionEigen fr;
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
	public BufferedImage detectFaces(FaceRecognitionEigen f) {
		fr=f;
        camera.read(mat2Img.getMat());
        //detectFaces(mat2Img.getMat());
        recognizeFaces(mat2Img.getMat());
        //getDetectedEyes(mat2Img.getMat());
	    //Mat grayM1=Imgcodecs.imread("ggant.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    //Mat grayM2=Imgcodecs.imread("gaston3.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    /*Mat grayM1 = new Mat(m1.height(),m1.width(),0);
		Mat grayM2 = new Mat(m2.height(),m2.width(),0);
	
		Imgproc.cvtColor(m1, grayM1, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(m2, grayM2, Imgproc.COLOR_RGB2GRAY);*/
        //FaceMatches fc= new FaceMatches(grayM1,grayM2);
        //fc.compareMatches();
        //FaceKnnMatches fk=new FaceKnnMatches(grayM1,grayM2);
        //fk.compareMatches();
        //FaceKnnBestMatches fkb=new FaceKnnBestMatches(grayM1,grayM2);
        //fkb.compareMatches();
        //FaceCompare.compareKnnMatches(m1, m2);
//        FaceRecognition.recognizeFace(mat2Img.getMat());
          return mat2Img.getImage(mat2Img.getMat());
    }
	private void recognizeFaces(Mat frame) {
		/*MatOfRect rect = FaceDectector.detectFaces2(m);
		FaceRecognitionEigen fr = new FaceRecognitionEigen();
		List<Rect> rect2 = rect.toList();
		rect2.size()
		for (Iterator<Rect> i= rect2.iterator(); i.hasNext();) {
			
			String name=fr.faceRecognizer(m);	
		}
		*/
		//Mat face = new Mat();
		Mat face=null;
		Mat gray=new Mat();
		Size size = new Size(150,150);
		//mCut = FaceCut.cutDetectedFaces(m);
		//FaceRecognitionEigen fr = new FaceRecognitionEigen();
		//String name=fr.faceRecognizer(m);
		System.out.println("M  Size H:"+frame.height()+" W:"+frame.width()+" Color: "+frame.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+frame.channels());
		Imgproc.pyrDown(frame, gray);
        Imgproc.cvtColor(gray, gray, Imgproc.COLOR_BGR2GRAY);
		Rect[] rects = FaceDetector.detectFaces(gray);
		for (Rect rect : rects) {
		//if (mCut!=null) {
			//if (rect!=null) submat=face.submat(rect).clone(); else System.out.println("Se cago");
			face=new Mat(gray,rect);
					//.submat(rect).clone();
			Imgproc.equalizeHist(face, face);
            Imgproc.resize(face, face, size, 1, 1, Imgproc.INTER_CUBIC);
			System.out.println("M  Size H:"+face.height()+" W:"+face.width()+" Color: "+face.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+frame.channels());
	        String nombre=fr.recognize(face);
            int x = (int) Math.max(rect.tl().x - 10, 0);
            int y = (int) Math.max(rect.tl().y - 10, 0);
            Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 1);
            Imgproc.putText(frame, String.format(nombre), new Point(x, y), Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0), 1);

		}
		
		//System.out.println("string: "+name);
		//Imgproc.putText(m, name, new Point(10,10), Core.FONT_HERSHEY_COMPLEX, 1, new Scalar(0,0,0));
	}
	private void detectFaces(Mat frame) {
		
		Rect[] rect = FaceDetector.detectFaces(frame);	
		for (int i=0;i<rect.length;i++) {
	        /*Imgproc.rectangle(frame, 
	        					new Point(rect[i].x, rect[i].y), 
	        					new Point(rect[i].x + rect[i].width, rect[i].y + rect[i].height), 
	        					new Scalar(0, 255, 0));*/
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
