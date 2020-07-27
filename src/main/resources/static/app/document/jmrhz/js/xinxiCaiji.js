var tablegrid1 = {"url":"/company/list","dataType":"text"};//加载tab1
var tablegrid2 = {"url":"/product/list","dataType":"text"};//加载tab2	
var deleteUrl1 = {"url":"/company/delete","dataType":"text","urls":"/company/delete"};// 删除接口
var deleteUrl2 = {"url":"/product/delete","dataType":"text","urls":"/product/delete"};// 删除接口
var dicturl = {"url":"/dictionary/list","dataType":"text","urls":"/dictionary/list"}; /*返回的下拉框字典值*/
var daochuurl = {"url":"/company/export","dataType":"text","urls":"/company/export"}; /*导出接口*/
var daochuBmurl = {"url":"/company/exportAll","dataType":"text","urls":"/company/exportAll"}; /*导出接口*/
var daoruurl = {"url":"/company/importt","dataType":"text","urls":"/company/import"}; /*导入接口*/
var grid = null;
var grid2 = null;
var paramobj1 = {};		// tab1搜索条件
var paramobj2 = {};		// tab2搜索条件
var chartlength=0;
var checkedArr1 = [];	// 选中复选框
var checkedArr2 = [];	// 选中复选框
var fileData;	// 导入
var pageModule = function(){
	var technicalMaturityC = [], technicalMaturityJ = [];	// 技术成熟度
	var recommendedUnit = []; 								// 推荐单位
	
	
	//加载字典项
	var initzdx = function(){
		$ajax({
			url:dicturl,
			async:false,
			success:function(data){
				var unitNature = [],			// 企业（单位）性质
					joined = [], 				// 是否参见往届展览
					productType = []	// 产品类型
				data.data.forEach(function(item,i){
					if( item.dictionaryType == "unitNature" ){
						unitNature.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					if( item.dictionaryType == "joined" ){
						joined.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					if( item.dictionaryType == "recommendedUnit" ){
						recommendedUnit.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					if( item.dictionaryType == "productType" ){
						productType.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					if( item.dictionaryType == "产品" ){
						technicalMaturityC.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					if( item.dictionaryType == "技术" ){
						technicalMaturityJ.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
				})
				initselect("unitNature", unitNature);
				initselect("joined", joined);
				initselect("recommendedUnit", recommendedUnit);
				initselect("recommendedUnit1", recommendedUnit);
				initselect("productType", productType);
				initselect("technicalMaturity", technicalMaturityC);
			}
		});
	};
	
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
                     columns:[  {display:"单位名称",name:"companyName",width:"10.5%",align:"center",paixu:false,render:function(rowdata){
                                	return '<span class="btnstyle" onclick="loadQY(\''+rowdata.id+'\')">'+rowdata.companyName+'</span>';
                                }},
                                {display:"营业执照号/统一社会信用代码",name:"businessLicense",title:true,width:"8%",align:"center",paixu:false,render:function(rowdata,n){
                                   	  return rowdata.businessLicense || '';
                                }},
                                {display:"企业工商注册时间",name:"companyRegistrationTime",width:"8%",align:"center",paixu:false,render:function(rowdata){
                                   	return rowdata.companyRegistrationTime || '';
                                }},
                                {display:"企业（单位）性质",name:"unitNature",width:"11.35%",align:"center",paixu:false,render:function(rowdata){
                                	if( rowdata.unitNature != '军队单位' && rowdata.unitNature != '军工单位' && rowdata.unitNature != '高等院校' && rowdata.unitNature != '民营企业' && rowdata.unitNature != '国有企业' && rowdata.unitNature != '科研院所' ){
										var valunit = rowdata.unitNature.split('&');
										rowdata.unitNature = valunit[1];
									}
                                   	return rowdata.unitNature || '';
                                }},
                                {display:"是否参加往届展览",name:"joined",width:"12%",align:"center",paixu:false,render:function(rowdata){
                                   	return rowdata.joined || '';
                                }},
                                {display:"是否愿意参加第五届展览需求对接活动",name:"continueJoin",width:"10%",align:"center",paixu:false,render:function(rowdata){
                                	if( rowdata.continueJoin == '0' ){
		                        		return '否'
		                        	}else if(  rowdata.continueJoin == '1'  ){
		                        		return '是'
		                        	}
                                }},
                                {display:"参展负责人",name:"principal",width:"6%",align:"center",paixu:false,render:function(rowdata){
                                   	return rowdata.principal || '';
                                }},
                                {display:"手机",name:"telephone",width:"8%",align:"center",paixu:false,render:function(rowdata){
                                   	return rowdata.telephone || '';
                                }},
                                {display:"产品技术类型",name:"productType",width:"8%",align:"center",paixu:false,render:function(rowdata){
                                   	return rowdata.productType || '';
                                }},
                                 {display:"存储时间",name:"saveTime",width:"12%",align:"center",paixu:false,render:function(rowdata){
                                   	return rowdata.saveTime || '';
                                }},
                                {display:"操作",name:"caozuo",title:true,width:"10%",align:"center",paixu:false,render:function(rowdata){
                                	return '<span title="新增产品" class="btnstyle" onclick="add(\''+rowdata.id+'\',\''+rowdata.businessLicense+'\')">新增产品</span>'+
                                	'<span title="编辑企业" class="btnstyle" onclick="resiveQY(\''+rowdata.id+'\')">编辑企业</span>'
                                }},
                            ],
//                  width:$("body").width()-345,
                    height:$("body").height()-480,
                    checkbox: true,
                    paramobj: paramobj1,
                    rownumberyon:true,
                    overflowx:false,
                    pagesize: 1000,
                    pageyno:true,
                    url: tablegrid1
               });appendcont();
		
	}
	
	
	
	var initgrid2 = function(){
        grid2 = $("#gridcont2").createGrid({
             columns:[  {display:"产品名称",name:"productName",width:"10%",align:"center",paixu:false,render:function(rowdata){
                        	return '<span class="btnstyle" onclick="loadCP(\''+rowdata.id+'\')">'+rowdata.productName+'</span>';
                        }},
                        {display:"所属单位",name:"companyName",title:true,width:"10%",align:"center",paixu:false,render:function(rowdata,n){
                           	return '<span title="'+rowdata.companyName+'">'+rowdata.companyName+'</span>';
                        }},
                        {display:"往届参展情况",name:"previousExhibitions",width:"15%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.previousExhibitions+'">'+rowdata.previousExhibitions+'</span>';
                        }},
                        {display:"产品类型",name:"productType",width:"7%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.productType+'">'+rowdata.productType+'</span>';
                        }},
                        {display:"技术成熟度",name:"technicalMaturity",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.technicalMaturity+'">'+rowdata.technicalMaturity+'</span>';
                        }},
                        {display:"先进程度",name:"advancedDegree",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.advancedDegree+'">'+rowdata.advancedDegree+'</span>';
                        }},
                        {display:"参展技术领域",name:"exhibitionArea",width:"18%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.exhibitionArea+'">'+rowdata.exhibitionArea+'</span>';
                        }},
                        {display:"是否自主知识产权",name:"selfIntellectual",title:true,width:"10.3%",align:"center",paixu:false,render:function(rowdata){
                        	if( rowdata.selfIntellectual == '0' ){
                        		return '否'
                        	}else if(  rowdata.selfIntellectual == '1'  ){
                        		return '是'
                        	}
                        }},
                        {display:"操作",name:"caozuo",title:true,width:"10%",align:"center",paixu:false,render:function(rowdata){
                        	return '<span title="编辑产品" class="btnstyle" onclick="resiveCP(\''+rowdata.id+'\',\''+rowdata.businessLicense+'\')">编辑产品</span>'
                        }}
                    ],
//                  width:$("body").width()-345,
          height:$("body").height()-450,
//            height:$("body").height(),
                checkbox: true,
                paramobj: paramobj2,
                rownumberyon:true,
                overflowx:false,
                pagesize: 1000,
                pageyno:true,
                url: tablegrid2
           });appendcont();
		}

	function appendcont(){//先显示表格再追加到幻灯片内
	     chartlength++;
	     if(chartlength==2){//表格都加载完再往幻灯片里面追加，否则无法显示
	    	 $("#tab1").append($("#gridcont"));
	    	 chartlength=0;//表格刷新后重新加载
	     };
	};	
	var daoru;
	var initother = function(){
		// 查询
		$("#search").click(function(){
			paramobj1 = {
				companyName: $('#companyName').val(),
				unitNature: $('#unitNature').val(),
				joined: $('#joined').val(),
				recommendedUnitName: $('#recommendedUnit').val()
			}
			
			initgrid()
		})
		
		// 重置	
		$("#reset").click(function(){
			removeInputData(['companyName','unitNature','joined','recommendedUnit']);
			initgrid()
		})
		
		// 新增
		$('#add').click(function(){
			newbootbox.newdialog({
				id:"viewcont",
				width:1000,
				height:800,
				header:true,
				title:"新增",
		//		url:"app/document/ywgl/html/xinxiCaijiQY_add.html"
				url:"xinxiCaijiQY_add.html?flag=1" //页面单独拿出来时用此地址
			})
		})
		
		
		
		// 导出企业名单
		$('#excel').click(function(){
    		window.location.href = _ip+daochuurl.urls+'?flag=false&ids='+grid.getcheckedIds().join(',');
		})
		
		// 导出企业名单与产品信息
		$('#excelAll').click(function(){
	    	window.location.href = _ip+daochuurl.urls+'?flag=true&ids='+grid.getcheckedIds().join(',');
		})
		
		// 导出报名信息
		$('#daochu').click(function(){
	    	window.location.href = _ip+daochuBmurl.urls+'?ids='+grid.getcheckedIds().join(',');
		})
		
		
		
		// 切换产品类型   技术成熟度值
		$('#productType').change(function(){
			$('#technicalMaturity').html('');
			if( $(this).val() == '产品' ){
				initselect("technicalMaturity", technicalMaturityC);
			}else if( $(this).val() == '技术' ){
				initselect("technicalMaturity", technicalMaturityJ);
			}
		})
		
		
		// 上传文件
		$('#file').on('click', function(){
			$('#con #file_input').val('');
			$('#con #file_input').click();
		})
		

		$('#con #file_input').on('change', function(event){
			pictureShow(event);
		})

		
		var open = 1;
		daoru = function(){
			$.ajax({
				url: _ip+daoruurl.urls,
				type:'post',
				data: fileData,
				contentType:false,
				processData:false,
				success:function(data){
					if( data.company ){
						newbootbox.newdialogClose("viewcont");
						newbootbox.alert2("导入成功！");
						grid.refresh();
						grid2.refresh();
					}else{
						newbootbox.alert2("导入失败！");
					}
				}
			})
		}
		
		
		function pictureShow(e) {
			open = 1;
			var file = e.target.files[0]
			var value = e.target.value;
			fileData = new FormData();
			fileData.append('file', file);
			// 需要校验是否是推荐单位
			
			if( file.name.split('-')[0] == '中央融办' || 
				file.name.split('-')[0] == '装备发展部' || 
				file.name.split('-')[0] == '教育部' || 
				file.name.split('-')[0] == '科技部' || 
				file.name.split('-')[0] == '工信部' || 
				file.name.split('-')[0] == '财政部' || 
				file.name.split('-')[0] == '国防科工局' || 
				file.name.split('-')[0] == '全国工商联' || 
				file.name.split('-')[0] == '上海融办' || 
				file.name.split('-')[0] == '北京融办' || 
				file.name.split('-')[0] == '深圳融办' || 
				file.name.split('-')[0] == '重庆融办' ||
				file.name.split('-')[0] == '天津融办' || 
				file.name.split('-')[0] == '四川省融办' ||
				file.name.split('-')[0] == '广州融办' || 
				file.name.split('-')[0] == '青岛融办' ||
				file.name.split('-')[0] == '长沙融办' || 
				file.name.split('-')[0] == '西安融办' ||
				file.name.split('-')[0] == '南京融办' ||
				file.name.split('-')[0] == '杭州融办' ||
				file.name.split('-')[0] == '龙岩融办' ||
				file.name.split('-')[0] == '哈尔滨融办' || 
				file.name.split('-')[0] == '陆军装备部' || 
				file.name.split('-')[0] == '海军装备部' || 
				file.name.split('-')[0] == '空军装备部' || 
				file.name.split('-')[0] == '战支航天系统部' ||  
				file.name.split('-')[0] == '武汉融办' ||  
				file.name.split('-')[0] == '中国科学院' || 
				file.name.split('-')[0] == '战支网络系统部'
			){
				daoru();
			}else{
				newbootbox.newdialog({
					id:"viewcont",
					width:500,
					height:260,
					header:true,
					title:"导入",
			//		url:"app/document/ywgl/html/xinxiCaijiCP_add.html"
					url:"daorutjdw.html" //页面单独拿出来时用此地址
				})
			}
		}
		
		// 查询
		$("#search1").click(function(){
			paramobj2 = {
				productName: $('#productName').val(),
				productType: $('#productType').val(),
				recommendedUnitName: $('#recommendedUnit1').val()
			}
			
			initgrid2()
		})
		
		// 重置	
		$("#reset1").click(function(){
			removeInputData(['productName','productType','recommendedUnit1']);
			initgrid2();  
		})
		
	    
	    // 企业删除
	    $("#delete").click(function(){
	    	if( grid.getcheckedIds().length > 0 ){
	    		newbootbox.confirm({
					 title: "提示",
				     message: "是否要进行删除操作？",
				     callback1:function(){
				    	 $ajax({
							url: deleteUrl1,
							type: 'post',
							data:{
								ids: grid.getcheckedIds().join(',')
							},
							success:function(data){
								console.log(data.msg)
								if(data.data=="记录删除成功！"){
									newbootbox.alertInfo("删除成功！").done(function(){
										grid.refresh();
										grid2.refresh();
									});
								}
								else{
									newbootbox.alertInfo("删除失败！");
								}
							}
						})
		    	 	}
				});
	    	}else{
	    		newbootbox.alert2("请选择需要删除的数据！");
	    	}
	    })
	    
	    // 产品删除
	    $("#delete2").click(function(){
	    	if( grid2.getcheckedIds().length > 0 ){
	    		newbootbox.confirm({
					 title: "提示",
				     message: "是否要进行删除操作？",
				     callback1:function(){
				    	 $ajax({
							url: deleteUrl2,
							type: 'post',
							data:{
								ids: grid2.getcheckedIds().join(',')
							},
							success:function(data){
								console.log(data.msg)
								if(data.data=="记录删除成功！"){
									newbootbox.alertInfo("删除成功！").done(function(){
										grid2.refresh();
									});
								}
								else{
									newbootbox.alertInfo("删除失败！");
								}
							}
						})
					}
				});
	    	}else{
	    		newbootbox.alert2("请选择需要删除的数据！");
	    	}
	    })

		
		//时间设置
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
	}
	


	return{
		//加载页面处理程序
		initControl:function(){
			initzdx();
			initgrid();
			initgrid2();
			initother();
		},
		initgrid: function(){
			initgrid();
		},
		initgrid2: function(){
			initgrid2()
		},
		daoru: function(){
			fileData.append('recommendedUnitName', $('#recommendedUnitNames').val());
			fileData.append('recommendedUnitTel', $('#recommendedUnitTels').val());
			fileData.append('recommendedUnitContant', $('#recommendedUnitContants').val());	
			daoru();
		}
	};
}();


// 新增产品按钮
function add(id, businessLicense){
	newbootbox.newdialog({
		id:"viewcont",
		width:1000,
		height:710,
		header:true,
		title:"新增",
//		url:"app/document/ywgl/html/xinxiCaijiCP_add.html"
		url:"xinxiCaijiCP_add.html?id="+id+"&flag=1&businessLicense="+businessLicense //页面单独拿出来时用此地址
	})
}

// 查看企业详情
function loadQY(id){
	newbootbox.newdialog({
		id:"viewcont",
		width:1000,
		height:710,
		header:true,
		title:"查看",
//		url:"app/document/ywgl/html/xinxiCaijiCP_add.html"
		url:"xinxiCaijiQY_add.html?id="+id+"&flag=2" //页面单独拿出来时用此地址
	})
}

// 编辑企业
function resiveQY(id){
	newbootbox.newdialog({
		id:"viewcont",
		width:1000,
		height:710,
		header:true,
		title:"编辑",
//		url:"app/document/ywgl/html/xinxiCaijiCP_add.html"
		url:"xinxiCaijiQY_add.html?id="+id+"&flag=3" //页面单独拿出来时用此地址
	})
}


// 查看产品详情
function loadCP(id){
	newbootbox.newdialog({
		id:"viewcont",
		width:1000,
		height:710,
		header:true,
		title:"查看",
//		url:"app/document/ywgl/html/xinxiCaijiCP_add.html"
		url:"xinxiCaijiCP_add.html?idself="+id+"&flag=2" //页面单独拿出来时用此地址
	})
}

// 编辑产品详情
function resiveCP(id,companyId){
	newbootbox.newdialog({
		id:"viewcont",
		width:1000,
		height:710,
		header:true,
		title:"编辑",
//		url:"app/document/ywgl/html/xinxiCaijiCP_add.html"
		url:"xinxiCaijiCP_add.html?idself="+id+"&id="+companyId+"&flag=3" //页面单独拿出来时用此地址
	})
}










