package modelo;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.imgcodecs.Imgcodecs;

class FaceMatches extends FaceCompare {

	public FaceMatches(Mat m1, Mat m2) {
		super(m1, m2);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void compareMatches() {
		getFaces();//m1,m2);
		faceCompareDescriptors();
		MatOfDMatch matches=faceMatch();//d1,d2);
		Mat outMat=faceDrawMatches(matches);//m1,m2,k1,k2,matches);
		Imgcodecs.imwrite("matchesList.jpg",outMat);
	}
	private MatOfDMatch faceMatch() {// Mat descriptors1, Mat descriptors2) {
		MatOfDMatch matches = new MatOfDMatch();
	    List<DMatch> matchesList; //= matches.toList();
	    List<DMatch> bestMatchesList = new ArrayList<DMatch>();
	    MatOfDMatch matchesFiltered = new MatOfDMatch();
	    System.out.println("------------FacesMatch----------");
		Double max_dist = 0.0;
        Double min_dist = 100.0;
	    Double threshold = 3 * min_dist;
        Double threshold2 = 2 * min_dist;
	    
		matcher.match(descriptors1, descriptors2, matches);
		System.out.println("matches.size() : " + matches.size());
	    //System.out.println("matches : " + matches);
	        
		for (DMatch d: matches.toArray()) {
		    	Double dist=(double) d.distance;
		    	//if (dist>0) System.out.print("Dist:"+dist+" - ");
		    	if (dist<min_dist && dist!=0) min_dist=dist;
		    	if (dist>max_dist) max_dist=dist;
		    }
		System.out.println("\nmax_dist : " + max_dist);
		System.out.println("min_dist : " + min_dist);

		threshold = 3 * min_dist;
	    threshold2 = 2 * min_dist;
	    if (threshold > 75) {
	    	threshold = 75.0;
	    } else if (threshold2 >= max_dist) {
	    	threshold = min_dist * 1.1;
	    } else if (threshold >= max_dist) {
	    	threshold = threshold2 * 1.4;
	    }
	    System.out.println("Threshold : " + threshold);
	 
	    matchesList = matches.toList();
	    for (int i = 0; i < matchesList.size(); i++) {//DMatch d: matches.toArray()) {
	    	Double dist = (double) matchesList.get(i).distance;//d.distance;
	        if (dist < threshold) {
	        	bestMatchesList.add(matches.toList().get(i));
	                //System.out.println(String.format(i + " best match added : %s", dist));
	        }
	    }
	        
	    matchesFiltered.fromList(bestMatchesList);
	    System.out.println("matchesFiltered.size() : " + matchesFiltered.size());
	    if (matchesFiltered.rows() >= 1) {
	    	System.out.println("match found");
	    } else {
	    	System.out.println("match not found");
	    }
	    return matchesFiltered;
	}
}
