package action;

import com.opensymphony.xwork2.ActionSupport;

import util.ZipUtil;

public class UploadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7446253305123669271L;
	private String path;
	private ZipUtil zipUtil = new ZipUtil();

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String execute() {
		System.out.println(path);
		String test = "e:/test/";
		zipUtil.unZip(path, test);
		return SUCCESS;
	}

}
