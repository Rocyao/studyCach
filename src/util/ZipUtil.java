package util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {
	private ZipFile zipFile;
	private ZipOutputStream zipOut;
	private int bufSize;
	private byte[] buf;

	public ZipUtil() {
		this.bufSize = 4096;
		this.buf = new byte[this.bufSize];
	}

	public void doZip(String srcFile, String destFile) {
		File zipFile = new File(srcFile);
		try {
			this.zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)));

			this.zipOut.setComment("comment");

			this.zipOut.setEncoding("GBK");

			this.zipOut.setMethod(8);

			this.zipOut.setLevel(9);

			handleFile(zipFile, this.zipOut, "");

			this.zipOut.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void handleFile(File zipFile, ZipOutputStream zipOut, String dirName) throws IOException {
		System.out.println("±éÀúÎÄ¼þ£º" + zipFile.getName());

		if (zipFile.isDirectory()) {
			File[] files = zipFile.listFiles();

			if (files.length == 0) {
				this.zipOut.putNextEntry(new ZipEntry(dirName + zipFile.getName() + File.separator));
				this.zipOut.closeEntry();
			} else {
				for (File file : files) {
					handleFile(file, zipOut, dirName + zipFile.getName() + File.separator);
				}
			}
		} else {
			FileInputStream fileIn = new FileInputStream(zipFile);

			this.zipOut.putNextEntry(new ZipEntry(dirName + zipFile.getName()));
			int length = 0;

			while ((length = fileIn.read(this.buf)) > 0) {
				this.zipOut.write(this.buf, 0, length);
			}

			fileIn.close();
			this.zipOut.closeEntry();
		}
	}

	public void unZip(String unZipfile, String destFile) {
		try {
			this.zipFile = new ZipFile(unZipfile);

			Enumeration entries = this.zipFile.getEntries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();

				File file = new File(destFile + File.separator + entry.getName());

				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}

					InputStream inputStream = this.zipFile.getInputStream(entry);

					FileOutputStream fileOut = new FileOutputStream(file);
					int length = 0;

					while ((length = inputStream.read(this.buf)) > 0) {
						fileOut.write(this.buf, 0, length);
					}
					fileOut.close();
					inputStream.close();
				}
			}
			this.zipFile.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}