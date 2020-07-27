var daibanUrl = {"url":"../data/fileList.json","dataType":"text"};  //待办事项
var dicturl = {"url":"../data/dictionary.json","dataType":"text"}; /*返回的下拉框字典值*/
var initLineUrl = {"url":"../data/line.json","dataType":"text"};  //电报处理
var initPieUrl = {"url":"../data/pie.json","dataType":"text"};  //入侵检测
var actionPieUrl = {"url":"../data/actionpie.json","dataType":"text"};  //资源使用情况
var xitongUrl = {"url":"../data/xitong.json","dataType":"text"};//搜索框输入内容时弹出提示
var treeurl = {"url":"../data/tree1.json","dataType":"text"};  //tree
var liListurl = {"url":"../data/liList.json","dataType":"text"};  //服务运行状况
var ulListurl = {"url":"../data/ulList.json","dataType":"text"};  //服务运行状况
var resourcesUseUrl = {"url":"../data/resourcesUseUrl.json","dataType":"text"};  //资源使用情况
var selectBoxUrl = {"url":"../data/selectBox.json","dataType":"text"};  //资源使用情况

var daibanType = "";//待办事项
var dianbaoType = "";//电报处理
var ruqinType = "";//入侵检测
var xitongType = "";//系统状态监控
var innerText = ""
var chartlength = 0;

var treeids1="";
var treetext1="";
var treeids2="";
var treetext2="";
var benjiId = $(".benji").attr("id")
var benyuId = $(".benyu").attr("id")

var treeType = 0;
var hoverType=0;

var pageModule = function(){
	
    //加载字典项
	var initzdx = function(){
		$ajax({
			url:dicturl,
			async:false,
			success:function(data){//注意这个initselect方法设置了默认显示第一个值，把【请选择】去掉了，即使页面有也加不上
				initselect("daiban",data.daiban);//待办事项
				initselect("dianbao",data.dianbao);//电报处理
				initselect("ruqin",data.ruqin);//入侵检测
			}
		});
	};

	var selectBox = function(){
		$ajax({
			url:selectBoxUrl,
			success:function(data){
				$("#xzfwq_list").html("");
				var html="";
				$.each(data,function(i,item){
					html+=' <li>'+
						  '  	<span class="dwmc" style="margin-left:15px;margin-right:5px;width:90px;display: inline-block;text-align:left;">'+item.dwmc+'</span>'+
			        	  '  	<span class="dwfwq"style="margin-right:0 5px;width:110px;display: inline-block;text-align:left;">'+item.dwfwq+'</span>'+
			        	  '	    <span class="dwip"style="width:110px;display: inline-block;text-align: left;">'+item.dwip+'</span>'+
						  '	</li>'
				})
				$("#xzfwq_list").append(html);
			}
		})
	}
   $("#xzfwq").click(function(){
   	   $("#xzfwq_list").slideToggle(50);//这个也可以
		/*if($("#xzfwq_list").is(":visible")){
			$("#xzfwq_list").hide();
		}else{
			$("#xzfwq_list").show();
		};*/
	})
	$("#xzfwq_list").mouseleave(function(){
		$("#xzfwq_list").hide();
	})
	$("body").delegate('#xzfwq_list>li','click',function(){
		var val1 = $("#checkIndex").text();
		var thisIndex = $(this).index(); 
		var Val2 = $(this).children(".dwmc").text();
		var Val3 = $(this).children(".dwfwq").text();
		var Val4 = $(this).children(".dwip").text();
		if(thisIndex != val1){
			$("#dwmc").text(Val2);
			$("#dwfwq").text(Val3);
			$("#dwip").text(Val4);
			$("#checkIndex").text(thisIndex);
			liList();
			ulList();
		}
	});

	var liList = function(){//服务运行状况
		$ajax({
			url:liListurl,
			type: "GET",
			data:{},
			success:function(data){
				$(".ulList1").empty();  
				var lis = "";
				$.each(data,function(){
					var idList = this.id;
					var classType = liList1(this.type);
					var textList = this.text;
					lis+='<li id="'+idList+'"><a class="'+classType+'" href="#;">'+textList+'</a></li>'
				});
				$(".ulList1").append(lis);
			}
		})
	}
	function liList1(classType){
		if(classType==1){
			return 'status1';
		}else{
			return 'status2';
		}
	}
	var ulList = function(){//服务运行状况
		$ajax({
			url:ulListurl,
			type: "GET",
			data:{},
			success:function(data){
				$(".ulListData").empty();  
				var lis = "";
				$.each(data,function(){
					var idList = this.id;
					var imgType = ulList1(this.type);
					var textList = this.text;
					lis+='<ul class="ulcont ulpadding" style="padding-top:40px;">'+
		     			 '	<li style="text-align:center;padding-left:0">'+imgType+'</li>'+
		     			 '	<li style="margin-top:5px;text-align:center">'+textList+'</li>'+
		     			 '</ul>'
				});
				$(".ulListData").append(lis);
			}
		})
	}
	function ulList1(imgType){
		if(imgType==1){
			return '<img src="../../../common/images/net1.png"/>';
		}else if(imgType==2){
			return '<img src="../../../common/images/net2.png"/>';
		}else{
			return '<img src="../../../common/images/net3.png"/>';
		}
	}
	var initChartsPie = function(val,treeids1,treetext1,treeids2,treetext2){
		$ajax({
			url:initPieUrl,
			type: "GET",
			data:{val:val,treeids1:treeids1,treetext1:treetext1,treeids2:treeids2,treetext2:treetext2},
			success:function(data){
				var data1 = data.data1;
				var data2 = data.data2;
				var data3 = data.data3;
				var data4 = data.data4;
				var data5 = data.data5;
				
				var sentOk = ((data2[0])[1])+' 台';
				var sentNo = ((data2[1])[1])+' 台';
				$(".sentOk").html(sentOk);
				$(".sentNo").html(sentNo);
				
				var sentOk1 = ((data3[0])[1])+' 台';
				var sentNo1 = ((data3[1])[1])+' 台';
				$(".sentOk1").html(sentOk1);
				$(".sentNo1").html(sentNo1);
				
				var shouwen = ((data5[0])[1])+' 台';
				var fawen = ((data5[1])[1])+' 台';
				var zzhuanwen = ((data5[2])[1])+' 台';
				$(".shouwen").html(shouwen);
				$(".fawen").html(fawen);
				$(".zzhuanwen").html(zzhuanwen);
				
				$("#piecharts_ruqin").empty();//入侵检测
				$("#piecharts1").empty();//服务器状态
				$("#piecharts2").empty();//终端状态
				
				$('#piecharts_fuwuqi').highcharts({//服务器在线
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        title: {
			            text: '120台',
			            align: 'center',
			            verticalAlign: 'middle',
			            y: 0
			        },
			        tooltip: {
			    	    /*pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'*/
			    	   pointFormat: '<b>{point.y:.f}台</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: false,
			                    format: '<b>{point.name}</b>: {point.y:.f} 台',
			                    style: {
			                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                    }
			                }
			            }
			        },
			        credits:{
			        	enabled:false
			        },
			        legend:{
			        	enabled:false
			        },
			        exporting:{
			        	enabled:false
			        },
			        colors:["#58B1DD","#FE6249"],
			        series: [{
			            type: 'pie',
			            name: '',
						size:'150%',
						innerSize:'148%',
			            data:data2
			        }]
			    });
			    $('#piecharts_zhongduan').highcharts({
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        title: {
			            text: '520台',
			            align: 'center',
			            verticalAlign: 'middle',
			            y: 0
			        },
			        tooltip: {
			    	    /*pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'*/
			    	   pointFormat: '<b>{point.y:.f}台</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: false,
			                    format: '<b>{point.name}</b>: {point.y:.f} 台',
			                    style: {
			                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                    }
			                }
			            }
			        },
			        credits:{
			        	enabled:false
			        },
			        legend:{
			        	enabled:false
			        },
			        exporting:{
			        	enabled:false
			        },
			        colors:["#58B1DD","#FE6249"],
			        series: [{
			            type: 'pie',
			            name: '',
						size:'150%',
						innerSize:'148%',
			            data:data3
			        }]
			    });
				$('#piecharts_dianbao').highcharts({//电报处理
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        title: {
			            text: '1000次',
			            align: 'center',
			            verticalAlign: 'middle',
			            y: 0
			        },
			        tooltip: {
			    	    /*pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'*/
			    	   pointFormat: '<b>{point.y:.f}次</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    format: '<b style="font-weight:normal">{point.name}</b>: {point.y:.f} 次',
			                    style: {
			                        fontSize:"14px",
			                        color:"#333"
			                    }
			                }
			            }
			        },
			        credits:{
			        	enabled:false
			        },
			        legend:{
			        	enabled:false
			        },
			        exporting:{
			        	enabled:false
			        },
			        colors:["#A9D86E","#F8A20F"],
			        series: [{
			            type: 'pie',
			            name: '',
						size:'60%',
						innerSize:'35%',
			            data:data4
			        }]
			    });
				$('#piecharts_ruqin').highcharts({
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        title: {
			            text: '1200次',
			            align: 'center',
			            verticalAlign: 'middle',
			            y: 0
			        },
			        tooltip: {
			    	    /*pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'*/
			    	   pointFormat: '<b>{point.y:.f}次</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    format: '<b style="font-weight:normal">{point.name}</b>: {point.y:.f} 次',
			                    style: {
			                        fontSize:"14px",
			                        color:"#333"
			                    }
			                }
			            }
			        },
			        credits:{
			        	enabled:false
			        },
			        legend:{
			        	enabled:false
			        },
			        exporting:{
			        	enabled:false
			        },
			        colors:["#A9D86E","#F8A20F","#E16A66","#5EAEE3"],
			        series: [{
			            type: 'pie',
			            name: '',
						size:'60%',
						innerSize:'35%',
			            data:data1
			        }]
			    });
			    $('#piecharts_wangluo').highcharts({
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false
			        },
			        title: {
			            text: '280台',
			            align: 'center',
			            verticalAlign: 'middle',
			            y: 0
			        },
			        tooltip: {
			    	    /*pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'*/
			    	   pointFormat: '<b>{point.y:.f}次</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: false,
			                    format: '<b>{point.name}</b>: {point.y:.f} 次',
			                    style: {
			                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
			                    }
			                }
			            }
			        },
			        credits:{
			        	enabled:false
			        },
			        legend:{
			        	enabled:false
			        },
			        exporting:{
			        	enabled:false
			        },
			        colors:["#58B1DD","#FE6249","#F8A20F"],
			        series: [{
			            type: 'pie',
			            name: '',
						size:'60%',
						innerSize:'35%',
			            data:data5
			        }]
			    });
			}
		});
	}
	
	//tree1
	var inittree1 = function(id,type){
		$("#tree_1").jstree("destroy");//点击tab页签刷新树（本级和本域用的是一个树）
		//$("#tree_1").jstree().destroy();  //这样的写法也正确
		$ajax({
			url:treeurl,
			data:{id:id},
			success:function(data){
					if(type == 0){
						$("#tree_1").jstree({
					    "plugins": ["wholerow", "types"],
					    "checkbox" : {
							"keep_selected_style" : false
						},
					    "core": {
					    "themes" : {
					        "responsive": false
					    },    
					    "data": data,  
					    },
					    "types" : {
					    	"default" : {
						        "icon" : "fa fa-folder icon-state-warning icon-lg"
						    },
						    "file" : {
						        "icon" : "fa fa-file icon-state-warning icon-lg"
						    },
						    "1" : {
						        "icon" : "fa fa-folder icon-state-warning icon-lg"
						    }
					    }
					});
					$("#tree_1").on("select_node.jstree", function(e,data) { 
						treeids1 = $("#" + data.selected).attr("id");  //获取id
						treetext1 = data.node.text;  //获取文字
						initChartsPie(treeids1,treetext1);
					});
				}else{
						$("#tree_1").jstree({
					    "plugins": ["wholerow", "types", "checkbox"],
					    "checkbox" : {
							"keep_selected_style" : false
						},
					    "core": {
					    "themes" : {
					        "responsive": false
					    },    
					    "data": data,  
					    },
					    "types" : {
					    	"default" : {
						        "icon" : "fa fa-folder icon-state-warning icon-lg"
						    },
						    "file" : {
						        "icon" : "fa fa-file icon-state-warning icon-lg"
						    },
						    "1" : {
						        "icon" : "fa fa-folder icon-state-warning icon-lg"
						    }
					    }
					});
					$("#tree_1").on("select_node.jstree", function(e,data) { 
						console.log(data)
						
						var firstParentId = data.node.parent;
						var firstParentText = $("#tree_1").jstree("get_text",data.node.parent);
						
						var checkArry = $("#tree_1").jstree("get_bottom_checked",true);//get_checked获得所有选中节点
						
						console.log(checkArry)
						
						treeids1="";
						treetext1="";
						$.each(checkArry,function(i,item){
							
							console.log(item.parent)
							if(item.parent){
								
							}
							
							
							
							treeids1+=item.id+",";
							treetext1+=item.text+",";
							
						});
						initChartsPie(treeids1,treetext1);
					});
					$("#tree_1").on("deselect_node.jstree", function(e,data) { 
						var checkArry = $("#tree_1").jstree("get_checked",true);
						treeids1="";
						treetext1="";
						$.each(checkArry,function(i,item){
							treeids1+=$(item).attr("id")+",";
							treetext1+=item.text+",";
						});
						initChartsPie(treeids1,treetext1);
					});
				}
			}
		})
	}
	//初始化图表--资源使用情况
	var cpuysy,cpuwsy,neicunysy,neicunwsy,cunchuysy,cunchuwsy;
	var resourcesUseDada =function(){
		$ajax({
			url:resourcesUseUrl,
			type:"get",
			data:{},
			success:function(data){
				$.each(data,function(i,item){
					if(i==0){
						cpuysy = item.ysy;
					    cpuwsy = item.wsy;
					}else if(i==1){
						neicunysy = item.ysy;
					    neicunwsy = item.wsy;
					}else{
						cunchuysy = item.ysy;
					    cunchuwsy = item.wsy;
					}
				})
			}
		})
	}
	 $('#cpuCharts1').highcharts({
	    chart: {
	        type: 'gauge',
	        //margin: [ 50, 20, 50, 20],
	        plotBackgroundColor:null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: true
	    },
	    title: {
	        text: ''
	    },
	    pane: {
	    	size:'100%',
	        startAngle: -100,
	        endAngle: 100,
	        background: [{
	            backgroundColor: '#fff',
	            borderWidth:0
	        }]
	    },
	    yAxis: {
	        min: 0,
	        max: 100,
	        minorTickInterval: 'auto',
	        minorTickWidth: 1,
	        minorTickLength: 10,
	        minorTickPosition: 'inside',
	        minorTickColor: '#666',
	        tickPixelInterval: 30,
	        tickWidth: 2,
	        tickPosition: 'inside',
	        tickLength: 10,
	        tickColor: '#666',
	        labels: {
	            step: 2,
	            rotation: 'auto',
	        },
	        title: {
	            text: 'GHz',
	        },
	        /*title: {
	            text: 'cup使用率',
	            style: {
	            	fontSize:"14px"
	            },
	            y: 25
	        },*/
	        plotBands: [{
	            from: 0,
	            to: 30,
	            color: '#55BF3B' // green
	            
	        }, {
	            from: 30,
	            to: 80,
	            color: '#DDDF0D' // yellow
	        },{
	            from: 80,
	            to: 100,
	            color: '#DF5353' // red
	        }]/*,
			labels: {
				formatter: function() {
					return this.value;//return this.value +'GHz';
				}
			}*/
	    },
		credits:{
		    enabled:false
		},
	    series: [{
	        name: 'cpu使用率',
	        data: [10],
	        tooltip: {
	            valueSuffix: ' %'
	        }
	    }]
	
	});
	$('#neicunCharts1').highcharts({
	    chart: {
	        type: 'gauge',
	        plotBackgroundColor:null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: true
	    },
	    title: {
	        text: ''
	    },
	    pane: {
	    	size:'100%',
	        startAngle: -100,
	        endAngle: 100,
	        background: [{
	            backgroundColor: '#fff',
	            borderWidth:0
	        }]
	    },
	    yAxis: {
	        min: 0,
	        max: 100,
	        minorTickInterval: 'auto',
	        minorTickWidth: 1,
	        minorTickLength: 10,
	        minorTickPosition: 'inside',
	        minorTickColor: '#666',
	        tickPixelInterval: 30,
	        tickWidth: 2,
	        tickPosition: 'inside',
	        tickLength: 10,
	        tickColor: '#666',
	        labels: {
	            step: 2,
	            rotation: 'auto'
	        },
	        title: {
	            text: 'GHz'
	        },
	        plotBands: [{
	            from: 0,
	            to: 30,
	            color: '#55BF3B' // green
	        }, {
	            from: 30,
	            to: 80,
	            color: '#DDDF0D' // yellow
	        }, {
	            from: 80,
	            to: 100,
	            color: '#DF5353' // red
	        }]
	    },
		credits:{
		    enabled:false
		},
	    series: [{
	        name: '内存使用率',
	        data: [50],
	        tooltip: {
	            valueSuffix: ' %'
	        }
	    }]
	
	});
	$('#cunchuCharts1').highcharts({
	    chart: {
	        type: 'gauge',
	        plotBackgroundColor:null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: true
	    },
	    title: {
	        text: ''
	    },
	    pane: {
	    	size:'100%',
	        startAngle: -100,
	        endAngle: 100,
	        background: [{
	            backgroundColor: '#fff',
	            borderWidth:0
	        }]
	    },
	    yAxis: {
	        min: 0,
	        max: 100,
	        minorTickInterval: 'auto',
	        minorTickWidth: 1,
	        minorTickLength: 10,
	        minorTickPosition: 'inside',
	        minorTickColor: '#666',
	        tickPixelInterval: 30,
	        tickWidth: 2,
	        tickPosition: 'inside',
	        tickLength: 10,
	        tickColor: '#666',
	        labels: {
	            step: 2,
	            rotation: 'auto'
	        },
	        title: {
	            text: 'GHz'
	        },
	        plotBands: [{
	            from: 0,
	            to: 30,
	            color: '#55BF3B' // green
	        }, {
	            from: 30,
	            to: 80,
	            color: '#DDDF0D' // yellow
	        }, {
	            from: 80,
	            to: 100,
	            color: '#DF5353' // red
	        }]
	    },
		credits:{
		    enabled:false
		},
	    series: [{
	        name: '存储使用率',
	        data: [80],
	        tooltip: {
	            valueSuffix: ' %'
	        }
	    }]
	
	});
	var initother = function(){
		
		/**幻灯片--开始**/
		$(".next a").click(function(){ nextscroll() });
		function nextscroll(){
			var vcon = $(".aui-content-box-list ");
			var offset = ($(".aui-content-box-list li").width())*-1;
			vcon.stop().animate({left:offset},"slow",function(){
				var firstItem = $(".aui-content-box-list ul li").first();
				vcon.find("ul").append(firstItem);
				$(this).css("left","0px");
				circle()
			});
		};
		function circle(){
			var currentItem = $(".aui-content-box-list ul li").first();
			var currentIndex = currentItem.attr("index");
			$(".circle li").removeClass("circle-cur");
			$(".circle li").eq(currentIndex).addClass("circle-cur");
		}
		$(".prev a").click(function(){
			var vcon = $(".aui-content-box-list ");
			var offset = ($(".aui-content-box-list li").width()*-1);
			var lastItem = $(".aui-content-box-list ul li").last();
			vcon.find("ul").prepend(lastItem);
			vcon.css("left",offset);
			vcon.animate({left:"0px"},"slow",function(){
				circle()
			})
		});/**幻灯片--结束**/
		
		$(".more1").click(function(){
			newbootbox.newdialog({
				id:"daiban",
				width:1200,
				height:600,
				header:true,
				title:"待办事项",
				url:"app/document/home/html/daibanSX.html"
			})
		})
		$(".more2").click(function(){
			newbootbox.newdialog({
				id:"xitongZT",
				width:1200,
				height:600,
				header:true,
				title:"系统状态",
				url:"app/document/home/html/xitongZT.html"
			})
		})
		$(".more3").click(function(){
			newbootbox.newdialog({
				id:"dianbao",
				width:1200,
				height:600,
				header:true,
				title:"电报处理",
				url:"app/document/home/html/dianbao.html"
			})
		})
		$(".more4").click(function(){
			newbootbox.newdialog({
				id:"ruqin",
				width:1200,
				height:600,
				header:true,
				title:"入侵检测",
				url:"app/document/home/html/ruqin.html"
			})
		})
		$(".more5").click(function(){
			newbootbox.newdialog({
				id:"wangluoCS",
				width:1200,
				height:600,
				header:true,
				title:"网络传输",
				url:"app/document/home/html/wangluoCS.html"
			})
		})
		$(".more_fuwuqi").click(function(){
			newbootbox.newdialog({
				id:"xitongJK",
				width:1200,
				height:600,
				header:true,
				title:"系统状态监控",
				url:"app/document/home/html/xitongJK.html"
			})
		})
		
		//左侧隐藏树的tab菜单切换
		$(".benji").click(function(){//本级
			treeType=0;
			var benjiId = $(".benji").attr("id")
			inittree1(benjiId,treeType);
		})
		$(".benyu").click(function(){//全局
			treeType=1;
			var benyuId = $(".benyu").attr("id")
			inittree1(benyuId,treeType);
		})
		/***鼠标移到页面最左侧淡入左侧树***/
		$(".leftborder").mouseover(function(){
			hoverType=1;
			if($(".leftTreeSet").is(":hidden")){
				$(".leftTreeSet").fadeIn(1000);
			}
		})
		$(".leftTreeSet").mouseleave(function(){
			if(hoverType == 1){
				$(".leftTreeSet").fadeOut(1000);
			}
		})
		/***点击箭头左侧树出现***/
		$(".arrowPic").css({"background":"url(../images/arrowkai_06.png) no-repeat","cursor":"pointer"});
		$("#arrowPic").click(function(){
			hoverType=0;
			if($(".leftTreeSet").is(":hidden")){
				$(".leftTreeSet").show();
				$(".arrowPic").css({"left":"490px","background":"url(../images/arrowshou_06.png) no-repeat","cursor":"pointer"});
			}else{
				$(".leftTreeSet").hide();
				$(".arrowPic").css({"left":"0","background":"url(../images/arrowkai_06.png) no-repeat","cursor":"pointer"});
			}
		})
		//tab切换--
		$(".titleTab1").delegate("div","click",function(e){
			if($(this).hasClass("active")){
				return;
			}
			$(this).addClass("active").siblings().removeClass("active")
		});
		
		//点击待办下拉框刷新
		$("#daiban").change(function(){
			daibanType = $(this).val();
		})
		//点击入侵下拉框刷新
		$("#ruqin").change(function(){
			ruqinType = $(this).val();
		})
	}
	var getimg = function(type){//待办事项里面每条信息的状态图标
		if(type==1){          
			return "msg1";
		}else if(type==2){    
			return "msg2";
		}else if(type==3){
			return "msg3";
		}else{
			return "msg4";
		}
	}
	var initdaiban = function(){//待办事项   
		$ajax({
			url:daibanUrl,
			type: "GET",
			data:{selectVal:daibanType},  
			success:function(data){
				$(".ultextset").empty();  
				//if(n==1){return;}
				var array = data.rows;
				var total = data.total;
				$(".msgtext").html(total);
				if(array.length>0){
					$.each(array,function(i,item){
						var id = item.id;
						var text = item.text;
						var type = item.type;
						var time = item.time;
						var laiyuan = item.laiyuan;
						var img = getimg(type)
						$(".ultextset").append(
							`
							<li id="${id}"><a class="${img}" href="#;">${text}</a><i style="width:25%;text-align:left;overflow:hidden;white-space: nowrap;
text-overflow: ellipsis;">&nbsp;${laiyuan}</i><i>${time}</i></li>
							`
						);
					})	
				}
			}
		});
	}
	return{
		//加载页面处理程序
		initControl:function(){
			initzdx();
			initdaiban();
			initChartsPie();
			inittree1(benyuId,treeType);
			selectBox();
			liList();
			ulList();
			resourcesUseDada();
			initother();
		}
	};
	
}();
var show = function(obj){
	$("#"+obj).modal("show");
}
var hide = function(obj){
	$("#"+obj).modal("hide");
}
