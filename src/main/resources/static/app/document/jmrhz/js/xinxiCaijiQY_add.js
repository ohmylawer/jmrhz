var saveUrl = {"url":"/company/save","dataType":"text","urls":"/company/save"};/*保存*/
var saveBUrl = {"url":"/company/update","dataType":"text","urls":"/company/update"};/*编辑保存*/
var dicturl = {"url":"/dictionary/list","dataType":"text","urls":"/dictionary/list"}; /*返回的下拉框字典值*/ 
var loadurl = {"url":"/company/load","dataType":"text","urls":"/company/load"}; /* 查看详情url */ 
var picurl = {"url":"/company/getPic","dataType":"text","urls":"/company/getPic"}; /* 获取图片url */
var guifanurl = {"url":"/company/export","dataType":"text","urls":"/company/export"};	/* 点击规范下载 */
var id = getUrlParam("id");
var flag = getUrlParam("flag");		// 1 新增    2 查看     3 编辑
var pageModule = function(){
	
	//加载字典项
	var initzdx = function(){
		$ajax({
			url:dicturl,
			async:false,
			success:function(data){
				var joined = [], 				// 是否参见往届展览
					companyProductType = [],	// 产品技术类型
					technologyField = [],		// 技术领域
					unitNature = [],			// 企业（单位）性质
					sacle = [],					// 企业规模
					techCompanyLevel = [],		// 高新技术企业等级
					technologyFinancialServicesDemandType = []	// 科技金融服务需求类型
					
				data.data.forEach(function(item,i){
					// 是否参见往届展览
					if( item.dictionaryType == "joined" ){
						joined.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					// 产品技术类型
					if( item.dictionaryType == "companyProductType" ){
						companyProductType.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					// 技术领域
					if( item.dictionaryType == "technologyField" ){
						technologyField.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					// 企业（单位）性质
					if( item.dictionaryType == "unitNature" ){
						unitNature.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					// 企业规模
					if( item.dictionaryType == "sacle" ){
						sacle.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					// 高新技术企业等级
					if( item.dictionaryType == "techCompanyLevel" ){
						techCompanyLevel.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					// 科技金融服务需求类型
					if( item.dictionaryType == "technologyFinancialServicesDemandType" ){
						technologyFinancialServicesDemandType.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
					
					checkVal("joined", joined);
					checkVal("productType", companyProductType);
					checkVal("technologyField", technologyField);
					initselect("unitNature", unitNature);
					initselect("sacle", sacle);
					initselect("techCompanyLevel", techCompanyLevel);
					checkVal("technologyFinancialServicesDemandType", technologyFinancialServicesDemandType);
				})
			}
		});
	};
	
	
	var initother = function(){
		// 选企业(单位)性质时候    选择其他   输入详细信息
		$('#unitNature').change(function(){
			if( $(this).val() == '其他' ){
				$(this).css({'width': '30%','float': 'left'});
				$('#qita').show().css({'width': '67%','float': 'right'})
			}else{
				$(this).css({'width': '100%'});
				$('#qita').hide()
			}
		})
		
		// 高新技术企业等级
		$('input[name="techCompany"]').change(function(){
			if( $(this).val() == 'true' ){
				$('.techs').show()
				$('#techCompanyLevel').addClass('must').prop('disabled', false);
			}else{
				$('.techs').hide()
				$('#techCompanyLevel').removeClass('must').val('').prop('disabled', true);
			}
		})
		
		// 上市时间校验
		$('input[name="appearOnMarket"]').change(function(){
			if( $(this).val() == 'true' ){
				$('.sstime').show()
				$('#appearOnMarketTime').addClass('must').prop('disabled', false);
			}else{
				$('.sstime').hide()
				$('#appearOnMarketTime').removeClass('must').val('').prop('disabled', true);
			}
		})
		
		// 科技金融服务需求校验
		$('input[name="technologyFinancialServicesDemand"]').change(function(){
			if( $(this).val() == 'true' ){
				$('.kjjr').show()
				$('.technologyFinancialServicesDemandType').prop('disabled', false);
				$('#technologyFinancialServicesDemandDescription').addClass('must').prop('disabled', false);
				$('#technologyFinancialServicesDemandType').addClass('must').prop('disabled', false);
			}else{
				$('.kjjr').hide()
				$('.technologyFinancialServicesDemandType').attr('checked', false).prop('disabled', true);
				$('#technologyFinancialServicesDemandDescription').removeClass('must').val('').prop('disabled', true);
				$('#technologyFinancialServicesDemandType').removeClass('must').val('').prop('disabled', true);
			}
		})
		
		
		// 图片展示
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
					id: id
				},
				success:function(data){
					var formdata = data.data;
					var datapic = formdata.enterpriseImage.split(',');
					// 单选框
					for(key in formdata){
						console.log(formdata[key])
						if( formdata[key] === true ){
							formdata[key] = 'true';
						}else if( formdata[key] === false ){
							formdata[key] = 'false';
						}
					}
					
					// 地址
					var addressdetail = formdata.address.split('&');
					$('#addressdetail').val(addressdetail[0]);
					$('#detailAddress').val(addressdetail[1]);
					
					// 附件
					if( formdata.accessory ){
						$('#file').hide();
						$('.filemessage').html(formdata.accessory);
						if( flag == 3 ){
							$('#shanchu').show();
						}
					}
					
					// 上市时间
					if( formdata.appearOnMarket == 'true' ){
						$('.sstime').show()
						$('#appearOnMarketTime').addClass('must').prop('disabled', false);
					}else{
						$('.sstime').hide()
						$('#appearOnMarketTime').removeClass('must').prop('disabled', true).val('');
					}
					
					// 高新技术企业等级
					if( formdata.techCompany == 'true' ){
						$('.techs').show()
						$('#techCompanyLevel').addClass('must').prop('disabled', false);
					}else{
						$('.techs').hide()
						$('#techCompanyLevel').removeClass('must').val('').prop('disabled', true);
					}
					
					// 科技金融服务需求校验
					if( formdata.technologyFinancialServicesDemand == 'true' ){
						$('.kjjr').show()
						$('.technologyFinancialServicesDemandType').prop('disabled', false);
						$('#technologyFinancialServicesDemandDescription').addClass('must').prop('disabled', false);
						$('#technologyFinancialServicesDemandType').addClass('must').prop('disabled', false);
					}else{
						$('.kjjr').hide()
						$('.technologyFinancialServicesDemandType').attr('checked', false).prop('disabled', true);
						$('#technologyFinancialServicesDemandDescription').removeClass('must').val('').prop('disabled', true);
						$('#technologyFinancialServicesDemandType').removeClass('must').val('').prop('disabled', true);
					}
					
					// 企业(单位)性质  是否其他
					if( formdata.unitNature != '军队单位' && formdata.unitNature != '军工单位' && formdata.unitNature != '高等院校' && formdata.unitNature != '民营企业' && formdata.unitNature != '国有企业' && formdata.unitNature != '科研院所' ){
						var valunit = formdata.unitNature.split('&');
						formdata.unitNature = valunit[0];
						$('#unitNature').css({'width': '30%','float': 'left'});
						$('#qita').show().css({'width': '67%','float': 'right'});
						$('#qita').val(valunit[1]);
					}
					
					setformdata(formdata)
					getpic(datapic[0],1);
					getpic(datapic[1],2);
					getpic(datapic[2],3);
					
					// 查看禁用					
					if(flag == 2){
						$('input').attr('disabled', true);
						$('select').attr('disabled', true);
						$('textarea').attr('disabled', true);
					}
				}
			})
		}
		
		// 取消
		$("#closedBtn").click(function(){
			newbootbox.newdialogClose("smChangeMsgAdd");
		});
		
		// 保存
		$("#save").click(function() {
			var urlsave;
			if( flag == 1 ){
				urlsave = saveUrl;
			}else if( flag == 3 ){
				urlsave = saveBUrl;
			}
			$('#address').val($('#addressdetail').val()+'&'+$('#detailAddress').val())
			
			var saveArr = [
				"companyName",
				"businessLicense",
				"legalPerson",
				"companyRegistrationTime",
				"joined",
				"continueJoin",
				"principal",
				"telephone",
				"fixedlineTelephone",
				"postcode",
				"address",
				"productType",
				"technologyField",
				"unitNature",
				"sacle",
				"techCompany",
				"techCompanyLevel",
				"appearOnMarket",
				"appearOnMarketTime",
				"registeredCaptital",
				"totalEnterpriseAssets",
				"militaryTurnover",
				"avgMilitaryTurnover",
				"merchandiseTurnover",
				"avgMerchandiseTurnover",
				"enterpriseProfile",
				"personnelStructure",
				"sharesConstitute",
				"patentSituation",
				"prizeSituation",
				"joinActivity",
				"paticipationInLargeProject",
				"technologyFinancialServicesDemand",
				"technologyFinancialServicesDemandType",
				"technologyFinancialServicesDemandDescription"
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
			formdata.enterpriseImage = $('#src1').val()+','+$('#src2').val()+','+$('#src3').val();
			formdata.accessory = $('.filemessage').html();
			
			// 企业(单位)性质  是否其他
			if( $('#unitNature').val() == '其他' ){
				formdata.unitNature = '其他&'+$('#qita').val();
			}
			
			// 编辑的时候传 id
			if( flag == 3 ){
				formdata.id = id;
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
							window.top.frmInner1.pageModule.initgrid();
						}else{
							newbootbox.alert2("保存失败！");
						}
					},
				});
			}
		})
		
		// 点击     参照大中小为企业划分标准（国统字[2011]75号）
		$('#guifan').click(function(){
			window.open('adfadf.html');
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
