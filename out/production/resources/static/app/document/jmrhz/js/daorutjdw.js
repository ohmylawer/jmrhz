var daoruurl = {"url":"/company/import","dataType":"text","urls":"/company/import"}; /*导入接口*/
var dicturl = {"url":"/dictionary/list","dataType":"text","urls":"/dictionary/list"}; /*返回的下拉框字典值*/ 
var pageModules = function(){
	//加载字典项
	var initzdx = function(){
		$ajax({
			url:dicturl,
			async:false,
			success:function(data){
				var recommendedUnit = [];				// 推荐单位
				data.data.forEach(function(item,i){
					if( item.dictionaryType == "recommendedUnit" ){
						recommendedUnit.push({
							"value": data.data[i].dictionaryValue,
							"text": item.dictionaryValue
						})
					}
				})
				initselect("recommendedUnitName", recommendedUnit);
			}
		});
	};
	
	
	var initother = function(){
		// 保存
		$('#save').one('click', function(){
			var saveArr = ["recommendedUnitName", "recommendedUnitTel", "recommendedUnitContant"];
			var formdata = getformdata(saveArr);
			window.top.frmInner1.$('#recommendedUnitNames').val(formdata.recommendedUnitName);			
			window.top.frmInner1.$('#recommendedUnitTels').val(formdata.recommendedUnitTel);		
			window.top.frmInner1.$('#recommendedUnitContants').val(formdata.recommendedUnitContant);			
			
			window.top.frmInner1.pageModule.daoru();
		})
		
		// 取消
		$('#return').click(function(){
			newbootbox.newdialogClose("viewcont");
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
