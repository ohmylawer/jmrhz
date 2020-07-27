var saveUrl = {"url":"/product/save","dataType":"text","urls":"/product/save"};/*保存*/
var saveBUrl = {"url":"/product/update","dataType":"text","urls":"/product/update"};/*编辑保存*/
var dicturl = {"url":"/dictionary/list","dataType":"text","urls":"/dictionary/list"}; /*返回的下拉框字典值*/ 
var loadurl = {"url":"/product/load","dataType":"text","urls":"/product/load"}; /* 查看详情url */ 
var danweiurl = {"url":"/company/load","dataType":"text","urls":"/company/load"}; /* 查看详情url */ 
var picurl = {"url":"/company/getPic","dataType":"text","urls":"/company/getPic"}; /* 获取图片url */
var id = getUrlParam("id");	// 	企业id
var businessLicense = getUrlParam("businessLicense");	// 	单位营业执照号

var idself  = getUrlParam("idself");	// 编辑时自己id 
var flag = getUrlParam("flag");		// 1 新增    2 查看	    3编辑
var pageModule = function(){
	if( flag == 1 ){
		// 获取单位名称
		$ajax({
			url: danweiurl,
			async:false,
			data:{
				id: id
			},
			success:function(data){
				var formdata = data.data;
				$('#companyName').val(formdata.companyName).attr('disabled', true)
			}
		})
	}
	
	var technicalMaturityC = [], technicalMaturityJ = [];	// 技术成熟度
	//加载字典项
	var initzdx = function(){
		$ajax({
			url:dicturl,
			async:false,
			success:function(data){
				var joined = [], 				// 往届参加情况
					productType = [],			// 产品类型
					technicalMaturity = [],		// 技术成熟度
					advancedDegree = [],		// 先进程度
					technologyField = [],		// 参加技术领域
					displayExhibits = []		// 展品形式
					
				data.data.forEach(function(item,i){
					// 往届参加情况
					if( item.dictionaryType == "joined" ){
						joined.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
						console.log(joined)
					}
					
					// 产品类型
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
					
					// 参加技术领域
					if( item.dictionaryType == "technologyField" ){
						technologyField.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					// 展品形式
					if( item.dictionaryType == "displayExhibits" ){
						displayExhibits.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
				})
				checkVal('previousExhibitions', joined);
				initselect("productType", productType);
				initselect("technicalMaturity", technicalMaturityC);
				initselect("advancedDegree", advancedDegree);	// 先进程度
				checkVal("exhibitionArea", technologyField);	// 参加技术领域
				checkVal("displayExhibits", displayExhibits);	// 展品形式
			}
		});
	};
	
	
	var initother = function(){
		
		// 展品形式
		$('input.displayExhibits').eq(0).change(function(){
			if( $(this)[0].checked == true ){
				$('.zpxs').show()
				
				$('#displayShape1').addClass('must').prop('disabled', false);
				$('#displayShape2').addClass('must').prop('disabled', false);
				$('#displayShape3').addClass('must').prop('disabled', false);
				$('#displayWeight').addClass('must').prop('disabled', false);
				$('#bearingRequirements').addClass('must').prop('disabled', false);
			}else{
				$('.zpxs').hide()
				
				$('#displayShape1').removeClass('must').val('').prop('disabled', true);
				$('#displayShape2').removeClass('must').val('').prop('disabled', true);
				$('#displayShape3').removeClass('must').val('').prop('disabled', true);
				$('#displayWeight').removeClass('must').val('').prop('disabled', true);
				$('#bearingRequirements').removeClass('must').val('').prop('disabled', true);
			}
		})
		
		var getpic = function(datas,sum){
			// 获取图片路径
			$.ajax({
				url: _ip+picurl.urls,
				async:false,
				type: 'post',
				data:{
					path: datas
				},
				success: function(data){
					var img = data;
					$('#src'+sum).val(datas);
					$('#img'+sum).attr('src', "data:image/jpg;base64,"+img);
				}
			})
		}
		
		// 查看 回显数据
		if( flag == 2 || flag == 3 ){
			if( flag == 2 ){
				$('#save').hide();
				$('#return').html('关闭');
			}
			$ajax({
				url: loadurl,
				async:false,
				data:{
					id: idself
				},
				success:function(data){
					var formdata = data.data;
					var datapic = formdata.productPicture.split(',');
					var displayShape = formdata.displayShape ? formdata.displayShape.split(',') : ['','','']; 
					
					// 技术成熟度
					if( formdata.productType == '产品' ){
						initselect("technicalMaturity", technicalMaturityC);
					}else{
						initselect("technicalMaturity", technicalMaturityJ);
					}
					
					// 展品形式
					if( formdata.displayExhibits.split(',')[0] == '实物' ){
						$('.zpxs').show()
						
						$('#displayShape1').addClass('must').prop('disabled', false);
						$('#displayShape2').addClass('must').prop('disabled', false);
						$('#displayShape3').addClass('must').prop('disabled', false);
						$('#displayWeight').addClass('must').prop('disabled', false);
						$('#bearingRequirements').addClass('must').prop('disabled', false);
					}else{
						$('.zpxs').hide()
						
						$('#displayShape1').removeClass('must').val('').prop('disabled', true);
						$('#displayShape2').removeClass('must').val('').prop('disabled', true);
						$('#displayShape3').removeClass('must').val('').prop('disabled', true);
						$('#displayWeight').removeClass('must').val('').prop('disabled', true);
						$('#bearingRequirements').removeClass('must').val('').prop('disabled', true);
					}
					
					
					// 实物展品外形尺寸
					$('#displayShape1').val(displayShape[0]);
					$('#displayShape2').val(displayShape[1]);
					$('#displayShape3').val(displayShape[2]);
					
					// 单选框
					for(key in formdata){
						console.log(formdata[key])
						if( formdata[key] === true ){
							formdata[key] = 'true';
						}else if( formdata[key] === false ){
							formdata[key] = 'false';
						}
					}
					
					// 附件
					if( formdata.accessory ){
						$('#file').hide();
						$('.filemessage').html(formdata.accessory);
						if( flag == 3 ){
							$('#shanchu').show();
						}
					}
					
					setformdata(formdata);
					$('#companyName').attr('disabled', true);
					if(flag == 2){
						$('input').attr('disabled', true);
						$('select').attr('disabled', true);
						$('textarea').attr('disabled', true);
					}
					
					getpic(datapic[0],1);
					getpic(datapic[1],2);
					getpic(datapic[2],3);
				}
			})
		}
		
		
		// 切换产品类型   技术成熟度值
		$('#productType').change(function(){
			$('#technicalMaturity').html('');
			if( $(this).val() == '产品' ){
				initselect("technicalMaturity", technicalMaturityC);
			}else if( $(this).val() == '技术' ){
				initselect("technicalMaturity", technicalMaturityJ);
			}
		})
		
		// 保存
		$("#save").click(function() {
			var urlsave;
			if( flag == 1 ){
				urlsave = saveUrl;
			}else if( flag == 3 ){
				urlsave = saveBUrl;
			}
			
			var saveArr = [
			"companyName",
			"productName",
			"previousExhibitions",
			"productType",
			"technicalMaturity",
			"advancedDegree",
			"exhibitionArea",
			"selfIntellectual",
			"selfIntellectualExplain",
			"productInstruction",
			"mainProductIndicators",
			"situationOfApplication",
			"inputsndReturns",
			"patentSituation",
			"prizeSituation",
			"displayExhibits",
			"displayWeight",
			"displayEffect",
			"bearingRequirements",
			"siteRequirements"
			];
			
			// 必填校验
			var must = 1;
			saveArr.forEach(function(item, i){
				if($("[name="+item+"]").hasClass('must') && $("[name="+item+"]").val() == '' ){
					must = 2;
				}
			})
			
			
//			saveArr.forEach(function(item, i){
//				$("input[name="+item+"]").val('true');
//				$("textarea[name="+item+"]").val('true');
//				$(".fl[name="+item+"]").val('123');
//				$('#companyRegistrationTime').val('2018-09-05');
//				$('#appearOnMarketTime').val('2018-09-05');
//			})
			var formdata = getformdata(saveArr);
			formdata.productPicture = $('#src1').val()+','+$('#src2').val()+','+$('#src3').val();
			formdata.displayShape = $('#displayShape1').val()+','+$('#displayShape2').val()+','+$('#displayShape3').val();
			formdata.accessory = $('.filemessage').html();
			formdata.businessLicense = businessLicense;		// 公司营业执照号
			
			if( flag == 3 ){
				formdata.id = idself;
			}
			
			if( must == 2 ){
				newbootbox.alert2("请完整信息！");
				must = 1;
			}else if( $('#img1').attr('src') == "" || $('#img2').attr('src') == "" || $('#img2').attr('src') == "" ){
				newbootbox.alert2("请上传图片！");
			}else{
				$ajax({
					url: urlsave,
					async: false,
					type: 'post',
					contentType: 'application/json',
					data: JSON.stringify(formdata),
					success: function(data){
						if( data.message == '操作完成！' ){
							newbootbox.alert2("保存成功！");
							newbootbox.newdialogClose("viewcont");
							window.top.frmInner1.pageModule.initgrid2();
						}else{
							newbootbox.alert2("保存失败！");
						}
					},
				});
			}
			
		})
		
		// 取消
		$('#return').click(function(){
			newbootbox.newdialogClose("viewcont");
		})
		
		//时间设置
		$("#companyRegistrationTime").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "left",
		    format: "yyyy-mm-dd",
		    autoclose: true,
		})
		
		$("#appearOnMarketTime").datepicker({
		    language:"zh-CN",
		    rtl: Metronic.isRTL(),
		    orientation: "left",
		    format: "yyyy-mm-dd",
		    autoclose: true,
		})
	}
	

	return{
		//加载页面处理程序
		initControl:function(){
			initzdx();
			initother();
		}
	};
	
}();
