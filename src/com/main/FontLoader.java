package com.main;

import java.awt.Font;

public class FontLoader {
	public static Font loadFont(String path) {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.load("/" + path));
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return font;
	}
}
