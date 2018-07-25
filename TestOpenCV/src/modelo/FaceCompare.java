package modelo;

import java.util.List;

import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FastFeatureDetector;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;

abstract class FaceCompare {
    private static FastFeatureDetector detector = FastFeatureDetector.create(4,true,1);//.create(FeatureDetector.ORB);// .ORB); //FastFeatureDetector.FAST_N
    private static DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);//BRISK);//.ORB);
    protected static DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);//.BRUTEFORCE_HAMMING);//DescriptorMatcher.BRUTEFORCE_HAMMING);
    protected Mat m1=new Mat();
    protected Mat m2=new Mat();
    protected Mat descriptors1=new Mat();
	protected Mat descriptors2=new Mat();
	protected MatOfKeyPoint keypoints1=new MatOfKeyPoint();
	protected MatOfKeyPoint keypoints2=new MatOfKeyPoint();
	
	public FaceCompare(Mat m1, Mat m2){
		System.out.println("-----------FaceCompare-------------");
		this.m1=m1;
		this.m2=m2;
	}
	protected void faceCompareDescriptors () { 
			//Mat m1, Mat m2, Mat descriptors1, Mat descriptors2, MatOfKeyPoint keypoints1, MatOfKeyPoint keypoints2) {

	    detector.detect(m1, keypoints1);
		detector.detect(m2, keypoints2);
		System.out.println("Detected M1: "+m1.size()+" - M2: "+m2.size()+" Blobs" );

		List<KeyPoint> m1KeyPoints=keypoints1.toList();
		List<KeyPoint> m2KeyPoints=keypoints2.toList();
		System.out.println("M1 Keypoints: "+m1KeyPoints.size() );
		System.out.println("M2 Keypoints: "+m2KeyPoints.size() );

		extractor.compute(m1, keypoints1, descriptors1);
		extractor.compute(m2, keypoints2, descriptors2);
		System.out.println("M1 Descriptor Size: "+descriptors1.size());
		System.out.println("M2 Descriptor Size: "+descriptors2.size());
	}
	protected void getFaces() {//Mat mat1, Mat mat2) {
   	 
		/*Mat grayM1 = new Mat(m1.height(),m1.width(),0);
		Mat grayM2 = new Mat(m2.height(),m2.width(),0);
		grayM1=m1;
		grayM2=m2;*/
		/*Imgproc.cvtColor(m1, grayM1, Imgproc.COLOR_RGB2GRAY);
		Imgproc.cvtColor(m2, grayM2, Imgproc.COLOR_RGB2GRAY);*/
   
		Mat faceM1=FaceCut.cutDetectedFaces(m1);
		Mat faceM2=FaceCut.cutDetectedFaces(m2);
		if (faceM1==null || faceM2==null) {
			System.out.println("Alguna imagen no detecta cara - exit");
			return;
		}
		this.m1=faceM1;
		this.m2=faceM2;
	}
	protected Mat faceDrawMatches(MatOfDMatch matches) {//Mat m1, Mat m2, MatOfKeyPoint k1, MatOfKeyPoint k2, MatOfDMatch matches) {
	    Mat outputMat = new Mat();
	    Features2d.drawMatches(m1, keypoints1, m2, keypoints2, matches, outputMat);
	    return outputMat;
//	    Imgcodecs.imwrite("knnMatchesList.jpg",outputMat);
	}
	public abstract void compareMatches() ;
}
