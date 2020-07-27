var tablegrid = {"url":"../data/tablegrid1.json","dataType":"text"};//加载tab
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
				initselect("typecont",data.gjxw);
			}
		});
	};
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
                    columns:[   {display:"设备",name:"textcont",width:"60%",align:"center",paixu:false,render:function(rowdata){
                                	return '<div title="'+rowdata.textcont+'" onclick="viewData(\''+rowdata.id+'\');" style="width:100%;height:100%;color:#666;cursor:pointer;text-align:left;text-overflow:ellipsis;overflow:hidden;">'+(rowdata.textcont||'')+'</div>';
                                }},
                                {display:"攻击行为",name:"lx",title:true,width:"15%",align:"center",paixu:false,render:function(rowdata,n){
                                   return rowdata.lx||'';
                                }},
                                {display:"时间",name:"gjsj",width:"25%",align:"center",paixu:false,render:function(rowdata){
                                	var date=rowdata.gjsj||'';
                                	//date=date.substring(0,10);
                                   return date;
                                }}
                            ],
                    width:$("#gridcont").parents(".tab-content").width()+"px",
                    height:$("#gridcont").parents(".tab-content").height()+"px",
                    checkbox: false,
                    rownumberyon:true,
                    overflowx:false,
                    pagesize: 15,
                    pageyno:false,
                    url: tablegrid
               });
		
	}
	setTimeout(function(){
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
	        colors:["#F8A20F","#32D2C9"],
            series: [{
                name: '服务器',
                data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
            }, {
                name: '终端',
                data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
            }]
        });	
      },0)
	var initother = function(){
	 	
	 	//tab切换
		$(".titleTab1").delegate("div","click",function(e){
			$(".titleTab1").find("div").removeClass("active");
			$(this).addClass("active");
			if($("#gridtab").hasClass("active")){
				$(".form-horizontal").show();
			}else{
				$(".form-horizontal").hide();
			}
			
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


function viewData(dataId){
	//window.parent.parent.$("#iframe1").attr("src","document/bjgl/html/view.html?id="+dataId);
}
