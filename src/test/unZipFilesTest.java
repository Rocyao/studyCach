package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import util.ZipUtil;

public class unZipFilesTest {

	@Test
	public void test() {
		String ZipUrl = "e:/test.zip";
		String path = "e:/test/";
		ZipUtil zipUtil = new ZipUtil();
		zipUtil.unZip(ZipUrl, path);
	}

}
