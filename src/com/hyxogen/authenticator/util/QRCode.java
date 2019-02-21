package com.hyxogen.authenticator.util;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 * @author Daan Meijer
 *
 */
public class QRCode {

	public final String value;

	public QRCode(String value) {
		this.value = value;
	}

	public Image build() {
		try {
			URL url = new URL("https://chart.googleapis.com/chart?cht=qr&chs=200x200&chld=M&chl=" + value);
			Image image = ImageIO.read(url);

			return image;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Image build(int width, int height) {
		try {
			URL url = new URL(
					"https://chart.googleapis.com/chart?cht=qr&chs=" + width + "x" + height + "&chld=M&chl=" + value);
			Image image = ImageIO.read(url);

			return image;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}