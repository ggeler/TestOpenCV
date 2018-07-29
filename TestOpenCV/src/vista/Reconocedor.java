package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.VideoCap;
import modelo.FaceRecognitionEigen;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;

public class Reconocedor extends JFrame {
    private VideoCap videoCap; 
    private FaceRecognitionEigen fr;
	private JPanel contentPane;
	private JPanel panel;
	private JButton btnSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reconocedor frame = new Reconocedor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private class MyThread extends Thread{
        @Override
        public void run() {
            for (;;){
                repaint();
                try { Thread.sleep(30);
                } catch (InterruptedException e) {    }
            }
        }
    }
	/**
	 * Create the frame.
	 */
	public Reconocedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(30, 12, 640, 480);
		contentPane.add(panel);

		JButton btnComenzar = new JButton("Comenzar");
		btnComenzar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				fr=new FaceRecognitionEigen();
				fr.inicializar();
				videoCap = new VideoCap(fr);
		        new MyThread().start();
			}
		});
		btnComenzar.setBounds(753, 35, 114, 25);
		contentPane.add(btnComenzar);

		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (videoCap!=null) {
					videoCap.finalize();
					videoCap=null;
				}
				if (fr!=null) fr=null;
			}
		});
		btnFinalizar.setBounds(753, 90, 114, 25);
		contentPane.add(btnFinalizar);

		btnSalir = new JButton("Salir");
		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(1);
			}
		});
		btnSalir.setBounds(753, 144, 114, 25);
		contentPane.add(btnSalir);
		
	}
    public void paint(Graphics g){
        g = panel.getGraphics();
        //videoCap.getDetectedFaceOneFrame();
        //videoCap.
        if (videoCap!=null) g.drawImage(videoCap.detectFaces(), 0, 0, this);
        //if (videoCap!=null) g.drawImage(videoCap.getOneFrame(),0,0,this);
    }
}
