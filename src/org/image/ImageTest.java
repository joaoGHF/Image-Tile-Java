package org.image;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class ImageTest {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame;
			frame = new ImageFrame();
			frame.setTitle("ImageTest");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setVisible(true);
		});
	}
}

/**
 * A frame with the image component
 */
@SuppressWarnings("serial")
class ImageFrame extends JFrame {
	public ImageFrame() {
		add(new ImageComponent());
		pack();
	}
}

/**
 * A component that display a tiled image
 */
@SuppressWarnings("serial")
class ImageComponent extends JComponent {
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;

	private Image image;

	public ImageComponent() {		
		try {
			BufferedImage bfImage = ImageIO.read(new File("src\\images\\java.png"));
			image = bfImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		if (image == null) {
			return;
		}

		int imageWidth = image.getWidth(this);
		int imageHeight = image.getHeight(this);		

		// draw the image in the upper-left corner
		g.drawImage(image, 0, 0, null);

		// tile the image across the component
		for (int i = 0; i * imageWidth < DEFAULT_WIDTH; i++) {
			for (int j = 0; j * imageHeight < DEFAULT_WIDTH; j++) {
				if (i + j > 0) {
					g.copyArea(0, 0, imageWidth, imageHeight, i * imageWidth, j * imageHeight);
				}
			}
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}
