/*
 * CssOffic的永中实现
 */
function CssOffice() {
	this.divId = null;
	this.plugin = null;
	this.fileInput = null;
	/*
	 * 初始化
	 */
	this.init = function(tagID, width, height) {
		this.divId = tagID;
		var oapplet = document.getElementById(tagID);
		oapplet.innerHTML = "<APPLET archive='yozoapplet.jar' code='com.yozosoft.office.applet.YOZODTApplet.class' "
				+ " width="
				+ width
				+ " height="
				+ height
				+ " codebase='/css/js/office/plugin' MAYSCRIPT=true id ='EIOWebOffice'>";
		+"<PARAM name='java_arguments' value='-Dsun.java2d.d3d=false -Xmx512m'></APPLET>";
		this.plugin = document.getElementById("EIOWebOffice");
	};
	/*
	 * 打开文件
	 */
	this.openFile = function(url, readOnly) {
		this.plugin.openDocument(url, readOnly);
	};
	/*
	 * 打开本地文件
	 */
	this.selectLocalFile = function() {
		this.plugin.openDoc();
	};
	/*
	 * 设置菜单栏是否显示
	 */
	this.setToolbarAllVisible = function(enable) {
		this.plugin.setToolbarAllVisible(enable);
	};
	/*
	 * 远程保存
	 */
	this.saveURL = function(url, fileName) {
		if (url.indexOf("?") != -1)
			url += "&fileName=" + fileName;
		else
			url += "?fileName=" + fileName;
		this.plugin.saveURL(url, fileName);
	};
	/*
	 * 是否修改
	 */
	this.IsModified = function() {
		return this.plugin.IsModified();
	};

	/*
	 * 设置是否为修订模式
	 */
	this.enableRevision = function(enable) {
		var enabled = false;
		while (!enabled) {
			try {
				//判断文档是否是修改模式，由于打开文档时多线程，因此存在判断失败。需要使用try/catch
				if (!this.plugin.getApp().getWorkbooks().getActiveWorkbook()
						.getDocuments().getActiveDocument().isTrackRevision()) {
					this.plugin.enableRevision(enable);
					enabled = true;
				}
			} catch (e) {
			}
		}
	};

	return this;
}

/*
 * 永中加载完毕后的回调，对外统一使用onCssOfficeLoadEnd方法
 */
function afterLaunchOffice() {
	try {
		onCssOfficeLoadEnd();
	} catch (e) {
	}
}