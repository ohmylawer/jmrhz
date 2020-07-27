var tablegrid = {"url":"../data/table-daiban.json","dataType":"text"};//加载table
var dicturl = {"url":"../data/dictionary1.json","dataType":"text"}; /*返回的下拉框字典值*/
var saveUrl = {"url":"../data/saveurl.json","dataType":"text"}; 

var innerText="";
var grid = null;
var filterChecked;
var pageModule = function(){
	//加载字典项
	var initzdx = function(){
		//var arr=['security_classification','banjian_type'];
		$ajax({
			url:dicturl,
			async:false,
			success:function(data){
				initselect("securitytext",data.securitytext);
				initselect("typecont",data.typecont);
			/*	for(var i in arr){
					initselect(arr[i],data[arr[i]]);
				}*/
			}
		});
	};
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
                    columns:[   
                                {display:"状态",name:"status",width:"10%",align:"center",paixu:false,render:function(rowdata){
                                	var status = rowdata.status;
                                	if(status==0){
                                		return'<div class="statusSet" style="background:#BFBFBF;">已办</div>'
                                	}else{
                                		return '<div class="statusSet" style="background:#dc4c4c;">未办</div>';
                                	}
                                }},
                                {display:"标题",name:"textcont",width:"40%",align:"center",paixu:false,render:function(rowdata){
                                	return '<a class="'+rowdata.msg+'" href="#;"></a><div title="'+rowdata.textcont+'" onclick="viewData(\''+rowdata.id+'\');" style="width:90%;height:100%;color:#666;cursor:pointer;text-align:left;text-overflow:ellipsis;overflow:hidden;">'+(rowdata.textcont||'')+'</div>';
                                }},
                                {display:"来源",name:"laiyuan",width:"25%",align:"center",paixu:false,render:function(rowdata){
                                	return '<div title="'+rowdata.laiyuan+'" style="width:90%;height:100%;color:#666;text-align:left;text-overflow:ellipsis;overflow:hidden;">'+(rowdata.laiyuan||'')+'</div>';
                                }},
                                {display:"时间",name:"gjsj",width:"15%",align:"center",paixu:false,render:function(rowdata){
                                	var date=rowdata.gjsj||'';
                                	//date=date.substring(0,10);
                                   return date;
                                }},
                                {display:"操作",name:"caozuo",width:"10%",align:"center",paixu:false,render:function(rowdata){
                                	var status = rowdata.status;
                                	if(status==0){
                                		return'<a title="办理"><i class="fa fa-check setSign1" style="background:#ccc;cursor:default;"></i></a>';
                                	}else{
                                		return '<a title="办理" onclick="banli(\''+rowdata.id+'\')"><i class="fa fa-check setSign1"></i></a>';
                                	}
                                }}
                            ],
                    width:"100%",
                    height:"100%",
                    checkbox: true,
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
	 	
	 	$("#setMsg").click(function(){
	 		var datas = grid.getcheckrow();
	 		var ids=[];
	 		var isbanli = false;
	 		if(datas.length>0){
	 			$.each(datas,function(i){
		 			ids[i]=this.id;
		 			if(this.status==0){//已办状态
		 				isbanli = true;
		 			}
		 		})
	 			if(false == isbanli){
	 				$ajax({
		 				url:saveUrl,
		 				data:{ids:ids.toString()},
		 				success:function(data){
							if(data.result=="success"){
								newbootbox.alertInfo('办理成功！').done(function(){
									 grid.refresh();
			    				});
							}else{
								newbootbox.alertInfo("办理失败！"); 
							}
						}
		 			})
		 		}else{
		 			newbootbox.alertInfo("请选择未办事项进行办理！"); 
		 		}
	 		}else{
	 			newbootbox.alertInfo("请选择未办事项进行办理!")
	 		}
	 	})
	 	
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


function banli(dataId){
	alert(22)
	//window.parent.parent.$("#iframe1").attr("src","document/bjgl/html/view.html?id="+dataId);
}
