package modelo;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import org.opencv.core.Core;
import org.opencv.core.Mat;

class Mat2Image {
	static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private Mat mat = new Mat();
    private BufferedImage img;

    public Mat2Image() {
    }

    public Mat2Image(Mat mat) {
        getSpace(mat);
    }

    public Mat getMat() {
    	return mat;
    }
    
     public void getSpace(Mat mat) {
        int type = 0;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        this.mat = mat;
        int w = mat.cols();
        int h = mat.rows();
        if (img == null || img.getWidth() != w || img.getHeight() != h || img.getType() != type)
            img = new BufferedImage(w, h, type);
    }

    BufferedImage getImage(Mat mat){
        getSpace(mat);
        WritableRaster raster = img.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return img;
    }

}
