var tablegrid = {"url":"/secret/printApplication/listByCondition?flag=1","dataType":"text"};//加载tab	
var daochuUrl = {"url":"/secret/printApplication/updateApplication","dataType":"text","urls":"/secret/printApplication/updateApplication"};// 导出接口
var grid = null;
var paramobj = {};		// 搜索条件
var chartlength=0;
var pageModule = function(){
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
                     columns:[  {display:"导入包名",name:"printCompany",width:"30%",align:"center",paixu:false,render:function(rowdata){
                            	return rowdata.printCompany || '';
                            }},
                            {display:"导入时间",name:"applicant",title:true,width:"30%",align:"center",paixu:false,render:function(rowdata,n){
                               	  return rowdata.applicant || '';
                            }},
                            {display:"导入结果",name:"printFileNum",width:"25%",align:"center",paixu:false,render:function(rowdata){
                               	return rowdata.printFileNum || '';
                            }},
                            {display:"操作",name:"caozuo",title:true,width:"15%",align:"center",paixu:false,render:function(rowdata){
                            	return '<span title="查看详情" class="btnstyle" onclick="viewcont(\''+rowdata.id+'\')">查看详情</span>'
                            }},
                        ],
//                  width:$("body").width()-345,
                    height:$("body").height()-330,
                    checkbox: true,
                    paramobj: paramobj,
                    rownumberyon:true,
                    overflowx:false,
                    pagesize: 15,
                    pageyno:true,
                    url: tablegrid
               });appendcont();
		
	}

	function appendcont(){//先显示表格再追加到幻灯片内
	     chartlength++;
	     if(chartlength==2){//表格都加载完再往幻灯片里面追加，否则无法显示
	    	 $("#tab1").append($("#gridcont"));
	    	 chartlength=0;//表格刷新后重新加载
	     };
	};	
	var initother = function(){
		// 查询
		$("#search").click(function(){
			paramobj = {
				printCompany: $('#printCompany').val(),
				applicant: $('#applicant').val(),
				status: $('#sqzt').val()
			}
			
			initgrid()
		})
		
		// 重置	
		$("#reset").click(function(){
			removeInputData(['printCompany','applicant','sqzt'])
		})
		
		//时间设置
		$("#beforeTime").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "left",
		    format: "yyyy-mm-dd",
		    autoclose: true,
		}).on("change",function(){
				$("#beforeTime").datepicker("setStartDate",$("#beforeTime").val());
			}
		);
	}
	

	return{
		//加载页面处理程序
		initControl:function(){
 			initgrid();
			initother();
		}
	};
	
}();

// 查看详情
function viewcont(id){
	newbootbox.newdialog({
		id:"viewcont",
		width:1000,
		height:710,
		header:true,
		title:"查看详情",
//		url:"app/document/ywgl/html/printPageView.html"
		url:"printPageView.html?id="+id+"&flag=2" //页面单独拿出来时用此地址
	})
}



















