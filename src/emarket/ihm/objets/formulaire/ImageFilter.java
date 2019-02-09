package emarket.ihm.objets.formulaire;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Arrays;

public class ImageFilter extends FileFilter {

	private static final String[] ALLOWED_EXTS = new String[]
			{"tiff", "tif", "gif", "jpeg", "jpg", "png"};

	@Override
	public boolean accept(File f) {
		return f.isDirectory() ||
				Arrays.asList(ALLOWED_EXTS).contains(this.getExtension(f));
	}

	@Override
	public String getDescription() {
		return "Image";
	}


	private String getExtension(File f) {
		String ext = null;
		String s   = f.getName();
		int    i   = s.lastIndexOf(".");

		if (i > 0 && i < s.length() - 1)
			ext = s.substring(i + 1).toLowerCase();

		return ext;
	}
}
