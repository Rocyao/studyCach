package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class UnZipFiles {
	/**
	 * ��ѹ��ָ��Ŀ¼
	 * 
	 * @param zipPath
	 * @param descDir
	 */
	public static void unZipFiles(String zipPath, String descDir) {
		unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * ��ѹ�ļ���ָ��Ŀ¼
	 * 
	 * @param zipFile
	 * @param descDir
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, String descDir) {
		File pathFile = new File(descDir);// ������ѹ�ļ�Ŀ¼
		// �ж��Ƿ����
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip;
		InputStream in = null;
		OutputStream out = null;
		try {
			zip = new ZipFile(zipFile);// ��Ҫ��ѹ���ļ�
			for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {

				System.out.println(entries);//?????????
				ZipEntry entry = (ZipEntry) entries.nextElement();
				
				String zipEntryName = entry.getName();
				in = zip.getInputStream(entry);
				String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");

				// �ж�·���Ƿ����,�������򴴽��ļ�·��
				File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				// �ж��ļ�ȫ·���Ƿ�Ϊ�ļ���,����������Ѿ��ϴ�,����Ҫ��ѹ
				if (new File(outPath).isDirectory()) {
					continue;
				}

				// ����ļ�·����Ϣ
				System.out.println(outPath);

				out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
			}
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("******************��ѹ���********************");
	}
}
