var tablegrid = {"url":"../data/table-daiban.json","dataType":"text"};//加载tab
var dicturl = {"url":"../data/dictionary1.json","dataType":"text"}; /*返回的下拉框字典值*/

var innerText="";
var grid = null;
var filterChecked;
var pageModule = function(){
	//加载字典项
	var initzdx = function(){
		$ajax({
			url:dicturl,
			async:false,
			success:function(data){
				initselect("securitytext",data.securitytext);
				initselect("typecont",data.sbmc);
			}
		});
	};
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
                    columns:[   {display:"设备名称",name:"textcont",width:"30%",align:"center",paixu:false,render:function(rowdata){
                                	return '<div title="'+rowdata.textcont+'" style="width:100%;height:100%;color:#666;text-align:left;text-overflow:ellipsis;overflow:hidden;">'+(rowdata.textcont||'')+'</div>';
                                }},
                                {display:"cpu使用率",name:"cpuval",width:"15%",align:"center",paixu:false,render:function(rowdata){
                                	return '<div title="'+rowdata.cpuval+'" onclick="viewData(\''+rowdata.id+'\',\''+rowdata.cpuval+'\');" style="width:100%;color:#2E78C3;height:100%;cursor:pointer;">'+(rowdata.cpuval||'')+'</div>';
                                }},
                                {display:"内存使用率",name:"neicunval",width:"15%",align:"center",paixu:false,render:function(rowdata){
                                	return '<div title="'+rowdata.neicunval+'" onclick="viewData(\''+rowdata.id+'\',\''+rowdata.neicunval+'\');" style="width:100%;color:#2E78C3;height:100%;cursor:pointer;">'+(rowdata.neicunval||'')+'</div>';
                                }},
                                {display:"存储使用率",name:"cunchuval",width:"15%",align:"center",paixu:false,render:function(rowdata){
                                	return '<div title="'+rowdata.cunchuval+'" onclick="viewData(\''+rowdata.id+'\',\''+rowdata.cunchuval+'\');" style="width:100%;color:#2E78C3;height:100%;cursor:pointer;">'+(rowdata.cunchuval||'')+'</div>';
                                }},
                                {display:"时间",name:"gjsj",width:"25%",align:"center",paixu:false,render:function(rowdata){
                                	var date=rowdata.gjsj||'';
                                	//date=date.substring(0,10);
                                   return date;
                                }}
                            ],
                    width:"100%",
                    height:"100%",
                    checkbox: false,
                    rownumberyon:true,
                    overflowx:false,
                    pagesize: 15,
                    //pageyno:true,
                    url: tablegrid
               });
		
	}
     $('#linecharts').highcharts({
            title: {
                text: ''
            },
            xAxis: {
                categories: ['6：00', '7：00', '8：00', '9：00', '10：00', '11：00',
                    '12：00', '13：00', '14：00', '15：00', '16：00', '17：00']
            },
            yAxis: {
                title: {
                    text: '单位：次'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '次'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                borderWidth: 0
            },
            credits:{
        	     enabled:false
	        },
	        exporting:{
	        	enabled:false
	        },
            series: [{
                name: '收文数',
                data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
            }, {
                name: '发文数',
                data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
            }]
        });	
	var initother = function(){
	 	
	 	//tab切换
		$(".titleTab1").delegate("div","click",function(e){
//			if($("#gridtab").hasClass("active")){
//				$("#searchType").show();
//			}else{
//				$("#searchType").hide();
//			}
			if($(this).hasClass("active")){
				return;
			}
			$(this).addClass("active").siblings().removeClass("active");
			
		});
		
//		$(".date-picker").datepicker({
//		    language:"zh-CN",
//		    rtl: Metronic.isRTL(),
//		    orientation: "right",
//		    autoclose: true
//		});
		$("#fromdate").datepicker({
	    language:"zh-CN",
	    rtl: Metronic.isRTL(),
	    orientation: "left",
	    format: "yyyy-mm-dd",
	    autoclose: true,
	}).on("change",function(){
			$("#todate").datepicker("setStartDate",$("#fromdate").val());
		}
	);
	$("#todate").datepicker({
	    language:"zh-CN",
	    rtl: Metronic.isRTL(),
	    orientation: "left",
	    format: "yyyy-mm-dd",
	    autoclose: true,
	}).on("change",function(){
			$("#fromdate").datepicker("setEndDate",$("#todate").val())
		}
	);
		$(".form_datetime").datetimepicker({
		    language:"zh-CN",
		    autoclose: true,
		    isRTL: Metronic.isRTL(),
		    format: "yyyy-mm-dd hh:ii",
		    pickerPosition: (Metronic.isRTL() ? "bottom-right" : "bottom-left")
		});
		
		$(".input-group-btn").click(function(){
			$(this).parent().find("input").eq(0).focus();
		})
		
		
	};
	
	return{
		//加载页面处理程序
		initControl:function(){
			initzdx();
			initgrid();
			initother();
			
		}
	};
	
}();


function viewData(dataId,datacont){
	//window.location.href = "xitongView.html?id="+dataId;
	newbootbox.newdialog({
				id:"daiban",
				width:1200,
				height:600,
				header:true,
				title:"运维服务器cup使用率",
				url:"app/document/home/html/xitongView.html"
			})
	//window.parent.parent.$("#iframe1").attr("src","document/bjgl/html/view.html?id="+dataId);
}
