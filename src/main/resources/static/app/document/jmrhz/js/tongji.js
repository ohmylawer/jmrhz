var tablegrid1 = {"url":"/company/list","dataType":"text"};//加载tab1
var tablegrid2 = {"url":"/product/list","dataType":"text"};//加载tab2	
var dicturl = {"url":"/dictionary/list","dataType":"text","urls":"/dictionary/list"}; /*返回的下拉框字典值*/
var deleteUrl = {"url":"/secret/printRecord/deleteRecords","dataType":"text","urls":"/secret/printRecord/deleteRecords"};	// 删除列表数据接口
var grid = null;
var grid2 = null;
var paramobj1 = {};		// 搜索条件
var paramobj2 = {};		// 产品技术搜索条件
var chartlength=0;	
var pageModule = function(){ 
	var technicalMaturityC = [], technicalMaturityJ = [];	// 技术成熟度
	
	//加载字典项
	var initzdx = function(){
		$ajax({
			url:dicturl,
			async:false,
			success:function(data){
				var unitNature = [],			// 企业（单位）性质
					joined = [], 				// 是否参见往届展览
					recommendedUnit = [], 		// 推荐单位
					companyProductType = [],	// 产品技术类型
					
					// 产品技术中的字典
					productType = [],			// 类型
					advancedDegree = []			// 先进程度
					
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
					
					if( item.dictionaryType == "companyProductType" ){
						companyProductType.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					// 产品技术中的字典渲染
					if( item.dictionaryType == "productType" ){
						productType.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					if( item.dictionaryType == "advancedDegree" ){
						advancedDegree.push({
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
				initselect("companyProductType", companyProductType);
				
				// 产品技术中的字典渲染
				initselect("productType", productType);
				initselect("advancedDegree", advancedDegree);
				initselect("technicalMaturity", technicalMaturityC);
				
			}
		});
	};
	var initgrid = function(){
        grid = $("#gridcont").createGrid({
             columns:[  {display:"单位名称",name:"companyName",width:"10.5%",align:"center",paixu:false,render:function(rowdata){
                        	return '<span title="'+rowdata.companyName+'">'+rowdata.companyName+'</span>';
                        }},
                        {display:"营业执照号/同意社会信用代码",name:"businessLicense",title:true,width:"10%",align:"center",paixu:false,render:function(rowdata,n){
                           	  return '<span title="'+rowdata.businessLicense+'">'+rowdata.businessLicense+'</span>';
                        }},
                        {display:"企业工商注册时间",name:"companyRegistrationTime",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.companyRegistrationTime+'">'+rowdata.companyRegistrationTime+'</span>';
                        }},
                        {display:"企业（单位）性质",name:"unitNature",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.unitNature+'">'+rowdata.unitNature+'</span>';
                        }},
                        {display:"是否参加往届展览",name:"joined",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.joined+'">'+rowdata.joined+'</span>';
                        }},
                        {display:"是否愿意参加第五届展览需求对接活动",name:"continueJoin",width:"10%",align:"center",paixu:false,render:function(rowdata){
                        	if( rowdata.continueJoin == '0' ){
                        		return '否'
                        	}else if(  rowdata.continueJoin == '1'  ){
                        		return '是'
                        	}
                        }},
                        {display:"参展负责人",name:"principal",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.principal+'">'+rowdata.principal+'</span>';
                        }},
                        {display:"手机",name:"telephone",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.telephone+'">'+rowdata.telephone+'</span>';
                        }},
                        {display:"产品技术类型",name:"productType",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.productType+'">'+rowdata.productType+'</span>';
                        }},
                        {display:"技术领域",name:"technologyField",title:true,width:"30%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.technologyField+'">'+rowdata.technologyField+'</span>';
                        }},
                    ],
//                  width:$("body").width()-345,
            height:$("body").height()-330,
            checkbox: true,
            paramobj: paramobj1,
            rownumberyon:true,
            overflowx:false,
            pagesize: 15,
            pageyno:true,
            url: tablegrid1,
            loadafter: function(data){
            	$('#num').html($('#gridcont_totol').html())
            }
       });appendcont();
		
	}
	
	var initgrid2 = function(){
        grid = $("#gridcont2").createGrid({
             columns:[ {display:"产品名称",name:"productName",width:"10%",align:"center",paixu:false,render:function(rowdata){
                        	return '<span title="'+rowdata.productName+'">'+rowdata.productName+'</span>';
                        }},
                        {display:"产品技术编号",name:"productTechnicalNumber",title:true,width:"15%",align:"center",paixu:false,render:function(rowdata,n){
                            return '<span title="'+rowdata.productTechnicalNumber+'">'+rowdata.productTechnicalNumber+'</span>';
                        }},
                        {display:"所属单位",name:"companyName",title:true,width:"9%",align:"center",paixu:false,render:function(rowdata,n){
                           	return '<span title="'+rowdata.companyName+'">'+rowdata.companyName+'</span>';
                        }},
                        {display:"往届参展情况",name:"previousExhibitions",width:"15%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.previousExhibitions+'">'+rowdata.previousExhibitions+'</span>';
                        }},
                        {display:"产品类型",name:"productType",width:"6%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.productType+'">'+rowdata.productType+'</span>';
                        }},
                        {display:"技术成熟度",name:"technicalMaturity",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.technicalMaturity+'">'+rowdata.technicalMaturity+'</span>';
                        }},
                        {display:"先进程度",name:"advancedDegree",width:"10%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.advancedDegree+'">'+rowdata.advancedDegree+'</span>';
                        }},
                        {display:"参展技术领域",name:"exhibitionArea",width:"17%",align:"center",paixu:false,render:function(rowdata){
                           	return '<span title="'+rowdata.exhibitionArea+'">'+rowdata.exhibitionArea+'</span>';
                        }},
                        {display:"是否自主知识产权",name:"selfIntellectual",title:true,width:"8.2%",align:"center",paixu:false,render:function(rowdata){
                        	if( rowdata.selfIntellectual == '0' ){
                        		return '否'
                        	}else if(  rowdata.selfIntellectual == '1'  ){
                        		return '是'
                        	}
                        }},
                    ],
                width:$("body").width()-120,
            	height:$("body").height()-400,
                checkbox: true,
                paramobj: paramobj2,
                rownumberyon:true,
                overflowx:false,
                pagesize: 15,
                pageyno:true,
                url: tablegrid2,
                loadafter: function(data){
            		$('#num2').html($('#gridcont2_totol').html());
                }
           });appendcont();
		}

	function appendcont(){//先显示表格再追加到幻灯片内
	     chartlength++;
	     if(chartlength==2){//表格都加载完再往幻灯片里面追加，否则无法显示
	    	 $("#tab1").append($("#gridcont"));
	    	 $("#tab2").append($("#gridcont2"));
	    	 chartlength=0;//表格刷新后重新加载
	     };
	};	
	var initother = function(){
		// 查询
		$("#search").click(function(){
			paramobj1 = {
				recommendedUnitName: $('#recommendedUnit').val(),
				unitNature: $('#unitNature').val(),
				productType: $('#companyProductType').val(),
				joined: $('#joined').val(),
				continueJoin: $('#continueJoin').val(),
				appearOnMarket: $('#appearOnMarket').val(),
				techCompany: $('#techCompany').val(),
				technologyFinancialServicesDemand: $('#technologyFinancialServicesDemand').val()
			}
			
			initgrid()
		})
		
		// 重置	
		$("#reset").click(function(){
			removeInputData(['recommendedUnit','unitNature','companyProductType','joined','continueJoin','appearOnMarket','techCompany','technologyFinancialServicesDemand']);
			initgrid()
		})
		
		// 产品技术查询
		$("#search1").click(function(){
			paramobj2 = {
				productType: $('#productType').val(),
				technicalMaturity: $('#technicalMaturity').val(),
				advancedDegree: $('#advancedDegree').val(),
				selfIntellectual: $('#selfIntellectual').val()
			}
			initgrid2()
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
		
		// 产品技术重置	
		$("#reset1").click(function(){
			removeInputData(['productType','technicalMaturity','advancedDegree','selfIntellectual']);
			initgrid2()
		})
		
		//tab切换
		$(".titleTab1").delegate("div","click",function(e){
			$(".titleTab1").find("div").removeClass("active");
			$(this).addClass("active");
			if($(".tab1").hasClass("active")){
				$(".btm_cont1").show();
				initgrid();
				$(".btm_cont2").hide();
			}else{
				initgrid2();
				$(".btm_cont2").show();
				$(".btm_cont1").hide();
			}
		});
	}
	
	
	return{
		//加载页面处理程序
		initControl:function(){
			initzdx();
			initgrid();
			initgrid2();
			initother();
		}
	};
	
}();


function viewcont(id){
	newbootbox.newdialog({
		id:"viewcont",
		width:1000,
		height:720,
		header:true,
		title:"查看详情",
//		url:"app/document/ywgl/html/printPageView.html"
		url:"printPageView.html?id="+id+"&flag=3" // 页面单独拿出来时用此地址
	})
}
