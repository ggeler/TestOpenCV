package modelo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.face.BasicFaceRecognizer;
import org.opencv.face.EigenFaceRecognizer;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FaceRecognitionEigen {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	private Mat im0,im1,im2,im3,im4,im5,im6;
	//private Mat labels;
	//;
	private List<Mat> images= new ArrayList<Mat>();
	private FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();//10,95);//LBPHFaceRecognizer.create();
	
	//Nuevas
	List<Integer> labelList = new ArrayList<>();
    MatOfInt labels = new MatOfInt();

	
	public FaceRecognitionEigen() {
		// TODO Auto-generated constructor stub
	}
	public String faceRecognizer(Mat m) {
		Mat mTmp=new Mat();//m.rows(),m.cols(),1);
		Imgproc.cvtColor(m,mTmp,Imgproc.COLOR_BGR2GRAY);
		//Imgproc.chan
		System.out.println("MTmp  Size H:"+mTmp.height()+" W:"+mTmp.width()+" Color: "+mTmp.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
		double[] confidence = null;
		int[] intLabels=null;
		faceRecognizer.predict(mTmp, intLabels, confidence);
		int predictedLabel = faceRecognizer.predict_label(mTmp);
		System.out.println("predicted: "+predictedLabel);
		return faceRecognizer.getLabelInfo(predictedLabel);
	}
	public String recognize(Mat frame) {
	    assert frame!=null;
	    if (frame==null) System.out.println("m null");
		//Mat mGray = new Mat();
        //Mat mProc = new Mat();
	    //Mat mDstn = new Mat();//(gray.rows(), gray.cols(), gray.type());
	    int[] labelsInt = new int[1];
	    double[] confidences = new double[1];
		    //Size size = new Size(92, 112);
	        //Imgproc.pyrDown(frame, frame);
	    //Mat mTmp=FaceCut.resize(m);
	    //Imgproc.cvtColor(mTmp, mGray, Imgproc.COLOR_BGR2GRAY);
        //Imgproc.equalizeHist(mGray, mDstn);
        //mProc=FaceCut.resize(FaceCut.cutDetectedFaces(mDstn));
	            //Imgproc.resize(submat, submat, size, 1, 1, Imgproc.INTER_CUBIC);
	    //System.out.println("gray  Size H:"+destination.height()+" W:"+destination.width()+" Color: "+destination.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
	    //System.out.println("M  Size H:"+m.height()+" W:"+m.width()+" Color: "+m.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
	    //System.out.println("MGray  Size H:"+mGray.height()+" W:"+mGray.width()+" Color: "+mGray.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+m.channels());
			
	    faceRecognizer.predict(frame, labelsInt, confidences);
	    double confidence = confidences[0];
	    int label = labelsInt[0];

	    String labelInfo = faceRecognizer.getLabelInfo(label);
	            System.out.println("labelInfo = " + labelInfo);
	            System.out.println("label = " + label);
	            System.out.println("confidence = " + confidence);
	            System.out.println("threshold = "+((BasicFaceRecognizer)faceRecognizer).getThreshold());
	    label=faceRecognizer.predict_label(frame);
	    labelInfo = faceRecognizer.getLabelInfo(label);
	            System.out.println("**");
	            System.out.println("labelInfo = " + labelInfo);
	            System.out.println("label = " + label);
	            System.out.println("confidence = " + confidence);
	            System.out.println("threshold = "+((BasicFaceRecognizer)faceRecognizer).getThreshold());
	            
	            //int x = (int) Math.max(rect.tl().x - 10, 0);
	            //int y = (int) Math.max(rect.tl().y - 10, 0);
	            //Imgproc.putText(frame, String.format("Predict = %s", labelInfo), new Point(x, y), Core.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 255, 0), 2);
	            return labelInfo;
	}
	public void inicializar(FaceRecognitionEigen f) {
		loadImages();
		//trainImages();
		train();

	}
	private void trainImages() {
        
        // FaceRecognizer faceRecognizer = createEigenFaceRecognizer(); 
        // FaceRecognizer faceRecognizer = createLBPHFaceRecognizer() 
        faceRecognizer.train(images, labels);
	}
	private void train() {
	    System.out.println(labels.dump());

	    faceRecognizer.train(images, labels);
	    //faceRecognizer.save("eigen-faces_trained_data.xml");
	    //recognizer.save("C:\\face-data\\fisher-faces_trained_data.xml");
	    //recognizer.save("C:\\face-data\\lbph-faces_trained_data.xml");
	}
	private void loadImages() {
		int personCount=1;
		im0=FaceCut.resize(FaceCut.cutDetectedFaces(Imgcodecs.imread("1.jpg",Imgcodecs.IMREAD_GRAYSCALE)));//Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)));
/*	     im1=FaceCutImgcodecs.imread("gaston.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	     im2=Imgcodecs.imread("gaston1.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	     im3=Imgcodecs.imread("gaston4.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
*///FaceCut.resize(FaceCut.cutDetectedFaces(
	    
	     im4=FaceCut.resize(FaceCut.cutDetectedFaces(Imgcodecs.imread("agus1.jpg",Imgcodecs.IMREAD_GRAYSCALE)));
	     im5=FaceCut.resize(FaceCut.cutDetectedFaces(Imgcodecs.imread("agus2.jpg",Imgcodecs.IMREAD_GRAYSCALE)));
/*	     im6=Imgcodecs.imread("agus3.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		   System.out.println("M0 Size H:"+im0.height()+" W:"+im0.width()+" Col Row "+im0.cols()+" "+im0.rows()+" Color "+im0.channels());
		   System.out.println("M1 Size H:"+im1.height()+" W:"+im1.width()+im1.channels());
		   System.out.println("M2 Size H:"+im2.height()+" W:"+im2.width());
		   System.out.println("M3 Size H:"+im3.height()+" W:"+im3.width());
		   System.out.println("M4 Size H:"+im4.height()+" W:"+im4.width());
		   System.out.println("M5 Size H:"+im5.height()+" W:"+im5.width());
		   System.out.println("M6 Size H:"+im6.height()+" W:"+im6.width());
*/		    if (im0!=null) {	            
		    	Imgproc.equalizeHist(im0, im0);
		    	labelList.add(personCount++);
		    	images.add(im0); }
            else System.out.println("null en im0");
/*		    if (im1!=null) {	            
		    	Imgproc.equalizeHist(im1, im1);
		    	label2List.add(personCount++);
		    	images.add(im1); }
		    else System.out.println("null en im1");
		    if (im2!=null) {	            
		    	Imgproc.equalizeHist(im2, im2);
		    	label2List.add(personCount++);
		    	images.add(im2); }
		    else System.out.println("null en im2");
		    if (im3!=null) {	            
		    	Imgproc.equalizeHist(im3, im3);
		    	label2List.add(personCount++);
		    	images.add(im3); }
		    else System.out.println("null en im3");
*/	     if (im4!=null) {	            
		    	Imgproc.equalizeHist(im4, im4);
		    	labelList.add(personCount++);
		    	images.add(im4); }
	     else System.out.println("null en im4");
	     if (im5!=null) {	            
		    	Imgproc.equalizeHist(im5, im5);
		    	labelList.add(personCount++);
		    	images.add(im5); }
	     else System.out.println("null en im5");
/*	     if (im6!=null) {	            
		    	Imgproc.equalizeHist(im6, im6);
		    	labelList.add(personCount++);
		    	images.add(im6); }
	     else System.out.println("null en im6");
*/
	     System.out.println("M0  Size H:"+im0.height()+" W:"+im0.width()+" Color: "+im0.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+im0.channels());
	     System.out.println("M4  Size H:"+im4.height()+" W:"+im4.width()+" Color: "+im4.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+im4.channels());
	     System.out.println("M5  Size H:"+im5.height()+" W:"+im5.width()+" Color: "+im5.channels()+" CVT "+Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE+" "+im5.channels());
				
	     labels.fromList(labelList);
		    //String[] names = {"W-S", "B-A", "T-Y"};
//		    for (Integer integer : label2List) {
//		        faceRecognizer.setLabelInfo(integer, integer.toString());
//		    }
/*	    intLabels[0]=0;
        intLabels[1]=1;
        
	    labels = new Mat(7, 1, CvType.CV_32SC1);
        labels.put(0,0,0);
        labels.put(1,0,0);
       labels.put(2, 0, 0);
        labels.put(3, 0, 0);
        labels.put(4,0,1);
        labels.put(5,0,1);
        labels.put(6, 0, 1);*/
        faceRecognizer.setLabelInfo(1, "Gaston0");
       faceRecognizer.setLabelInfo(2, "Agus1");
        faceRecognizer.setLabelInfo(3, "Agus2");
        /*faceRecognizer.setLabelInfo(3, "Gaston3");
        faceRecognizer.setLabelInfo(4, "Agus1");
        faceRecognizer.setLabelInfo(5, "Agus2");
        faceRecognizer.setLabelInfo(6, "Agus3");*/
/*        Imgproc.cvtColor(m,m,Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat newM=FaceCut.resize(m);
 	   System.out.println("newM Size H:"+newM.height()+" W:"+newM.width());
        images.add(newM);
        labels.put(7, 0, 7);
        faceRecognizer.setLabelInfo(7, "new");*/
        //labels.put(7,0,7);
	}
}
