var tablegrid = {"url":"/secret/printApplication/listByCondition?flag=1","dataType":"text"};//加载tab	
var daochuUrl = {"url":"/secret/printApplication/updateApplication","dataType":"text","urls":"/secret/printApplication/updateApplication"};// 导出接口
var grid = null;
var paramobj = {};		// 搜索条件
var chartlength=0;
var pageModule = function(){
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
                     columns:[  {display:"导出包名",name:"printCompany",width:"30%",align:"center",paixu:false,render:function(rowdata){
                            	return rowdata.printCompany || '';
                            }},
                            {display:"导出时间",name:"applicant",title:true,width:"30%",align:"center",paixu:false,render:function(rowdata,n){
                               	  return rowdata.applicant || '';
                            }},
                            {display:"导出结果",name:"printFileNum",width:"25%",align:"center",paixu:false,render:function(rowdata){
                               	return rowdata.printFileNum || '';
                            }},
                            {display:"操作",name:"caozuo",title:true,width:"15%",align:"center",paixu:false,render:function(rowdata){
                            	return '<span title="查看详情" class="btnstyle" onclick="viewcont(\''+rowdata.id+'\')">查看详情</span>'+
                            		'<span title="导出" class="btnstyle" onclick="daochu(\''+rowdata.id+'\')">导出</span>'
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
		
	    $("#daochu").click(function(){
	    	if( grid.getcheckedIds().length > 0 ){
		    	newbootbox.confirm({
					 title: "提示",
				     message: "是否要进行导出操作？",
				     callback1:function(){
				    	 $ajax({
							url:daochuUrl,
							data:{
								ids: grid.getcheckedIds().join(',')
							},
							success:function(data){
								console.log(data.msg)
								if(data.msg=="success"){
									newbootbox.alertInfo("导出成功！").done(function(){
										grid.refresh();
									});
								}
								else{
									newbootbox.alertInfo("导出失败！");
								}
							}
						})
				     }
				});
	    	}else{
	    		newbootbox.alertInfo("请选择需要导出的数据！");
	    	}
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

function daochu(id){
	$ajax({
		url:daochuUrl,
		data:{
			ids: id
		},
		success:function(data){
			console.log(data.msg)
			if(data.msg=="success"){
				newbootbox.alertInfo("导出成功！").done(function(){
					grid.refresh();
				});
			}
			else{
				newbootbox.alertInfo("导出失败！");
			}
		}
	})
}


















