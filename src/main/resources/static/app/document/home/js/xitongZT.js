var TreeDadaUrl1 = {"url":"../data/flare.json","dataType":"text"}; 
var TreeDadaUrl2 = {"url":"../data/flare.json","dataType":"text"}; 
var TreeDadaUrl3 = {"url":"../data/cloumn1.json","dataType":"text"}; 

var chartlength = 0;
var dom = document.getElementById("echartsTree1");
var dom2 = document.getElementById("echartsTree2");
var closeType=0;

var pageModule = function(){
	
	console.log(window.location)
	console.log(window.navigator.userAgent)
	
	function moveHtml(id){
			var scroll_offset = $("#" + id).offset().top-200;
			$("#divBody").animate({
	    		scrollTop:scroll_offset
	    	},0);
	}
	var initTreeDada2 =function(){
		$ajax({
			url:TreeDadaUrl2,
			success:function(data){
				var n1 = 0;
				var fn1 = function(obj){
					var children = obj.children;
					if(!!children){
						if(children.length>0){
							$.each(children,function(i,o){
								fn1(o)
							})
						}else if(children.length==0){
							n1+=1;
						}
					}else{
						n1+=1;
					}
				}
				fn1(data);
				$(dom2).css("height",(n1*70)+"px");
				
				var myChart = echarts.init(dom2);
				option = null;
							
			    myChart.setOption(option = {
			        tooltip: {
			            trigger: 'item',
			            triggerOn: 'mousemove'
			        },
			        series: [
			            {
			                type: 'tree',
			                symbol:'emptyCircle',
			                symbolSize:7,
			                label: {
				                    normal: {
				                        position: 'bottom',
				                        verticalAlign:'middle',
				                        align:'center',
				                        fontSize: 14,
				                        color:'#333',
				                        distance:30,//文字与图间距
				                        formatter: function (param) {
							             }
				                    }
				              },
			                top: '10%',
			                bottom: '15%',
			                layout:'radial',
			                data:[data],
			                expandAndCollapse: true,
			                animationDuration: 550,
			                animationDurationUpdate: 750,
			                initialTreeDepth:4,//默认：2，树图初始展开的层级（深度）。根节点是第 0 层
			            }
			        ]
			    });appendcont();
			    
			    
			}
		})
	}
	var initTreeDada3 =function(){
		$ajax({
			url:TreeDadaUrl3,
			success:function(data){
				$('#echartsTree3').highcharts({
		            chart: {
		                type: 'column'
		            },
		            title: {
		                text: ''
		            },
		            xAxis: {
		                categories: data.x1,
		                labels: {
				            style: {
				            	fontSize: '14px',
				            }
				        }    
		            },
		            yAxis: {
		            	gridLineDashStyle:'Dash',
		                min: 0,
		                title: {
		                    text: ''
		                },
		                stackLabels: {
		                    enabled: true,
		                    style: {
		                        //fontWeight: 'bold',
		                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray',
		                        fontSize:'14px'
		                    }
		                }
		            },
		            legend: {
		               enabled:false
		            },
		            credits: {
		               enabled:false
		            },
		            tooltip: {
		                formatter: function() {
		                    return '<b>'+ this.x +'</b><br/>'+this.series.name +': '+ this.y +'台<br/>总数: '+ this.point.stackTotal+'台';
		                }
		            },
		            plotOptions: {
		                column: {
		                    stacking: 'normal',
		                    dataLabels: {
		                        enabled: true,
		                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
		                        style: {
		                            fontSize:'14px'
		                        }
		                    }
		                }
		            },
		            colors:["#58B1DD","#FE6249"],
		            series: data.data1
		        });appendcont();
				
			}
		})
	}
	var initTreeDada1 =function(closeType){
		$ajax({
			url:TreeDadaUrl1,
			async:false,
			success:function(data){
				if(closeType == 1){
					data.mcHereShow=false;
				}
				var n1 = 0;
				var fn1 = function(obj){
					var children = obj.children;
					if(!!children){
						if(children.length>0){
							$.each(children,function(i,o){
								if(closeType == 1){
									o.mcHereShow=false;
									fn1(o)
								}else{
									fn1(o)
								}
							})
						}else if(children.length==0){
							n1+=1;
						}
					}else{
						n1+=1;
					}
				}
				fn1(data);
				//console.log(n1)//得到级别中最多的节点个数
				$(dom).css("width",(n1*90)+"px");
				
				var myChart = echarts.init(dom);
				option = null;
			    myChart.setOption(option = {
			        tooltip: {
			            trigger: 'item',
			            triggerOn: 'mousemove',
			            formatter:function(param){
			            	var data1 = param.data;
			            	var nameArr = [];
			            	var countArr = [];
							var children = data1.children;
							var fwqLen=0;
							var zdLen=0;
							var fwq_zc=0;//服务器正常
							var fwq_yc=0;//服务器异常
							var zd_zc=0;//终端正常
							var zd_yc=0;//终端异常
							var type,status;
							if(!!children){
								if(children.length>0){
									$.each(children,function(){
									     type = this.type;//服务器或终端type
										 status = this.status;//正常或异常type
										 if(typeof(type)!="undefined"){//因为赋值了不可用（！！type）
											if(type == 0){
												fwqLen++
												if(typeof(status)!="undefined"){
													if(status == 0){
														fwq_zc++
													}else{
														fwq_yc++
													}
												}

											}else{
												zdLen++
												if(typeof(status)!="undefined"){
													if(status == 0){
														zd_zc++
													}else{
														zd_yc++
													}
												}

											}
										 }
										
										var name = this.name;
										if(name.indexOf("军委服务器") != -1){
											fwqLen++
										}else if(name.indexOf("军委终端") != -1){
											zdLen++
										};
										
										nameArr.push(this.name); 
									})
									
									if(typeof(type)!="undefined" && typeof(status)!="undefined"){//放在children.length>0的条件里否则末端节点有hover事件
										var html = `
											<div>服务器:${fwqLen}台&nbsp;&nbsp;(正常:${fwq_zc}台&nbsp;&nbsp;异常:${fwq_yc}台) </div>
										    <div>终 &nbsp;&nbsp;端:${zdLen}台&nbsp;&nbsp;(正常:${zd_zc}台&nbsp;&nbsp;异常:${zd_yc}台 )</div>
										`
										return html;
									}else{
										var nameStr = nameArr.join(",")
										
										if(nameStr.indexOf("军委服务器") != -1 || nameStr.indexOf("军委终端") != -1){
											var html = `
												<div>军委服务器:${fwqLen}台</div>
											    <div>军委终端:${zdLen}台</div>
											`
											return html;
										}else{
											
											return nameStr;
										}
									}								
									
								}


							}

							       
							       
			            }
			        },
			        series: [
			            {
			                type: 'tree',
			                label: {
				                    normal: {
				                        position: 'bottom',
				                        verticalAlign:'middle',
				                        align:'center',
				                        fontSize: 14,
				                        color:'#333',
				                        distance:20,//文字与图间距
				                        formatter: function (param) {
							             }
				                    }
				              },
			                top: '10%',
			                left: '3%',
			                bottom: '15%',
			                right: '3%',
			                orient: 'vertical',
			                leaves: {
			                    label: {
			                        normal: {
			                            position: 'bottom',
			                            verticalAlign: 'middle',
			                            align: 'center',
			                            fontSize: 14
			                        }
			                    }
			                },
			                data:[data],
			                expandAndCollapse: true,
			                animationDuration: 550,
			                animationDurationUpdate: 750,
			                initialTreeDepth:4,//默认：2，树图初始展开的层级（深度）。根节点是第 0 层
			            }
			        ]
			    });appendcont();
			    
			    
			}
		})
		
	};
	function appendcont(){//先显示图表再追加到幻灯片内
	     chartlength++;
	     if(chartlength==3){//图表都加载完再往幻灯片里面追加，否则无法显示图表
	    	 $("#tab1").append($("#echartsTree1"));
	    	 $("#tab2").append($("#echartsTree2"));
	    	 $("#tab3").append($("#echartsTree3"));
	    	 chartlength=0;//图表刷新后重新加载
	     };
	};	
	var initother = function(){
	 	//tab切换
		$(".titleTab1").delegate("div","click",function(e){
			$(".titleTab1").find("div").removeClass("active");
			$(this).addClass("active");
			if($(".tab1").hasClass("active")){
				$(".ctrlAchartBtn").show();
				$(".statusDiv").show();
			}else{
				$(".ctrlAchartBtn").hide();
				$(".statusDiv").hide();
			}
		});
		$("#closeBtn").click(function(){
			closeType = 1;
			initTreeDada1(closeType);
		})
		$("#openBtn").click(function(){
			closeType = 0;
			initTreeDada1(closeType);
		})
	};
	
	return{
		//加载页面处理程序
		initControl:function(){
			initTreeDada1();
			initTreeDada2();
			initTreeDada3();
			initother();
			
		}
	};
	
}();

