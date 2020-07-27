/*
 * CssOffic的实现
 */
function CssOffice() {
	this.divId = null;
	this.plugin = null;
	this.fileInput = null;
	var office;	
	/**
	 * 初始化xls文件
	 */
	this.initEt = function InitEt(tagID, width, height)
	{
		
		if (office != undefined){
			office.Application.Quit();
		}
	
		var iframe;
		iframe = document.getElementById(tagID);
		var codes=[];
		codes.push('<object name="rpcet" id="rpcet_id" type="application/x-et" wpsshieldbutton="false" data="../../../common/js/office/template/newfile.et" width="'+width+'" height="'+height+'">');
		codes.push('<param name="quality" value="high" />');
		codes.push('<param name="bgcolor" value="#ffffff" />');
		codes.push('<param name="Enabled" value="1" />');
		codes.push('<param name="allowFullScreen" value="true" />');
		codes.push('</object>');
		iframe.innerHTML = codes.join("");
		office = document.getElementById("rpcet_id");
		window.onbeforeunload = function() {
	    	office.Application.Quit(); 
	    }; 
	}

	//打开远程文件
	this.OpenRemoteEt = function OpenRemoteEt(url)
	{
		office = document.getElementById("rpcet_id");
		office.Application.Workbooks.OpenRemote(url);
	}	
	
	
	
	this.OpenDocumentEt = function OpenDocumentEt(url)
	{
		office = document.getElementById("rpcet_id");
		office.Application.openDocument(url, true)
	}	
	/*
	 * 保存到本地
	 */
	this.saveAs = function() {
		office = document.getElementById("rpcet_id");
		office.Application.saveAs();
	};
	
	/*
	 * 初始化
	 */
	this.init = function(tagID, width, height) {
		var obj;
		this.divId = tagID;
		var iframe = document.getElementById(tagID);
		var codes = [];   
		codes.push("<object name='webwps' id='webwps_id' type='application/x-wps'  " +
				"data='../../../css/js/office/template/normal.wpt'  width='100%'   wmode='opaque'  wmode='transparent'  " +
				"height='100%'> <param name='quality' value='high' /> <param name='Enabled' value='1' /> " +
				"<param name='allowFullScreen' value='true' /> <param name='bgcolor' value='#ffffff' /> " +
				"<param name='wmode' value='opaque' />  <param name='wmode' value='transparent' /> </object>");
		iframe.innerHTML = codes.join("");
		obj = document.getElementById("webwps_id");
		
		//wps加载完毕后的回调，对外统一使用onCssOfficeLoadEnd方法
		var Interval_control = setInterval(
			function(){
				var app = obj.Application;
				if(app && app.IsLoad()){
					clearInterval(Interval_control);
					this.plugin = app;
					
					/*
					 * 打开文件
					 */
					this.openFile = function(url, readOnly) {
						this.plugin.openDocumentRemote(url, readOnly);
					};
					/*
					 * 打开本地文件
					 */
					this.selectLocalFile = function() {
						this.plugin.openDocument();
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
						//开启或者停止修订,true:开启 ,false:停止
						this.plugin.enableRevision(enable);
					};
					/**
					 * 打印预览
					 */
					this.printPreview = function(){
						//设置为普通打印模式
						var aa = this.plugin.Options.put_PrintHiddenText(true);
						var bb = this.plugin.Options.put_PrintDrawingObjects(true);
						this.plugin.Documents.GetDocument().PrintPreview();
					};
						
					/**
					 * 套打预览
					 */
					this.printSetPreview = function(){
						//设置为套打模式
						var aa = this.plugin.Options.put_PrintHiddenTextMode(0);
						var bb = this.plugin.Options.put_PrintDrawingObjects(false);
						this.plugin.Documents.GetDocument().PrintPreview();
					};
					
					onCssOfficeLoadEnd(this);
				}
		},200);
		
	};

	return this;
}
