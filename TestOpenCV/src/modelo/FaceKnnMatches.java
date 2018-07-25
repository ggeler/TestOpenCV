package modelo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;

class FaceKnnMatches extends FaceCompare {
	public FaceKnnMatches(Mat m1, Mat m2) {
		super(m1, m2);
		// TODO Auto-generated constructor stub
	}
	private List<MatOfDMatch> faceMatch (){//Mat descriptors1, Mat descriptors2, MatOfKeyPoint keypoints1, MatOfKeyPoint keypoints2) {
		List<MatOfDMatch> knnMatchesList = new ArrayList<MatOfDMatch>();
		System.out.println("-----------KNNMatches----------");
        matcher.knnMatch(descriptors1, descriptors2, knnMatchesList, 2);
        System.out.println("KNN Matches Size:"+knnMatchesList.size());
		return knnMatchesList;
	}
	private Mat faceDrawMatches(List<MatOfDMatch> matches) {//Mat m1, Mat m2, MatOfKeyPoint k1, MatOfKeyPoint k2, List<MatOfDMatch> matches) {
	    Mat outputMat = new Mat();
	    Features2d.drawMatches2(m1, keypoints1, m2, keypoints2, matches, outputMat);
	    return outputMat;
//	    Imgcodecs.imwrite("knnMatchesList.jpg",outputMat);
	}
	@Override
	public void compareMatches() {//Mat m1, Mat m2) {
		getFaces();
		faceCompareDescriptors();//m1,m2,d1,d2,k1,k2);
		List<MatOfDMatch> matches=faceMatch();//m1,m2,k1,k2);
		Mat outMat=faceDrawMatches(matches);//m1,m2,k1,k2,matches);
		Imgcodecs.imwrite("knnMatchesList.jpg",outMat);
	}
}
