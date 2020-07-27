var pageModule = function(){

	var fileStyle = 0;	// 1 图片        2附件
	var company = _ip+"/company/upload";	// 企业url
	var companyImg = _ip+"/company/getPic";	// 企业图片url
	var num;	// 第几个图片
	function pictureShow(e, company, i) {
		var file = e.target.files[0]
		var value = e.target.value
		var fileData = new FormData()
		fileData.append('file', file)
		var filesize = e.target.files[0].size;
		//console.log((filesize/1024/1024).toFixed(2))
		var fileSize = (filesize/1024/1024).toFixed(2);	// 文件大小  单位 M
		if( fileStyle == 1 && fileSize > 3){
			$('#file_input').val('');
			$('.filemessage').html("您上传的文件超过3M，请重新上传！");
			$(".filemessage").show();
			return;
		}else if( fileStyle == 2 && fileSize > 10){
			$('#file_input').val('');
			$('.filemessage').html("您上传的文件超过10M，请重新上传！");
			$(".filemessage").show();
			return;
		}else{
			$.ajax({
				url: company,
				type:'post',
				data: fileData,
				contentType:false,
				dataType:'json',
				processData:false,
				success:function(data){
					if( data.message == '上传成功' ){
						if( fileStyle == 1 ){
							$('#img'+num).attr("src", window.URL.createObjectURL(file))
							$('#src'+num).val(data.path)
						}else if( fileStyle == 2 ){
							newbootbox.alert2("上传成功！");
							$('#file').hide();
							$('#shanchu').show();
							$('.filemessage').html(data.name);
							$('#file_input').val(data.path);
						}
					}else{
						newbootbox.alert2("上传失败！");
					}
				}
			})
		}
	}
		// 图片1 上传
		$('.jpgFile li').eq(0).on('click', function(){
			$('#addForm #file_input').val('');
			$('#addForm #file_input').click();
			fileStyle = 1;
			num = 1;
		})
		$('.jpgFile li').eq(1).on('click', function(){
			$('#addForm #file_input').val('');
			$('#addForm #file_input').click();
			fileStyle = 1;
			num = 2;
		})
		$('.jpgFile li').eq(2).on('click', function(){
			$('#addForm #file_input').val('');
			$('#addForm #file_input').click();
			fileStyle = 1;
			num = 3;
		})
		
	
	
		// 上传文件
		$('#file').on('click', function(){
			$('#addForm #file_input').val('');
			$(".filemessage").html('').show();
			fileStyle = 2;
			$('#addForm #file_input').click();
		})
		
		// 附件删除按钮隐藏
		$('#shanchu').hide();
		$('#addForm #file_input').on('change', function(event){
			pictureShow(event, company);
		})

		// 附件删除按钮
		$('#shanchu').click(function(){
			$('#file').show();
			$('#shanchu').hide();
			$('.filemessage').html('');
			$('#file_input').val('');
		})

	// 验证表单框值
	var	phone = /^[1][3,4,5,7,8][0-9]{9}$/,
		number= /^[0-9]+(.[0-9]{1,6})?$/,
		positalCode = /^(0[1-7]|1[0-356]|2[0-7]|3[0-6]|4[0-7]|5[1-7]|6[1-7]|7[0-5]|8[013-6])\d{4}$/,
		fixphone = /\d{3}-\d{8}|\d{4}-\d{7}/;
	
	$(document).on("blur","[validateType='phone']",function(){   //验证手机号码
		if( $(this).val() !== '' ){
			if(!phone.test($(this).val())){
				newbootbox.alert2("请输入正确的手机号码！");
			}
		}
	}).on("blur","[validateType='number']",function(){   //验证数字
		if( $(this).val() !== '' ){
			if(!number.test($(this).val())){
				newbootbox.alert2("请输入数字！");
			}
		}
	}).on("blur","[validateType='num']",function(){   //验证保留两位小数
		if( $(this).val() !== '' ){
			if( !number.test($(this).val()) ){
				newbootbox.alert2("请输入数字！");
			}else{
				var val = $(this).val();
				$(this).val(Number(val).toFixed(2))
			}
		}
	}).on("blur","[validateType='positalCode']",function(){   //验证邮政编码
		if( $(this).val() !== '' ){
			if(!positalCode.test($(this).val())){
				newbootbox.alert2("请输入正确的邮政编码！");
			}
		}
	}).on("blur","[validateType='fixphone']",function(){   //验证固定电话
		if( $(this).val() !== '' ){
			if(!fixphone.test($(this).val())){
				newbootbox.alert2("请输入正确的固定电话！");
			}
		}
	})

}();
















