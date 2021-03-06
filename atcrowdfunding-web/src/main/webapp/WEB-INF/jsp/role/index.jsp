<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<%@ include file="/WEB-INF/jsp/common/css.jsp"%>
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row">
			<jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"></jsp:include>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="condition" class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button id="queryBtn" type="button" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button id="deleteBatchBtn" type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button id="addBtn" type="button" class="btn btn-primary"
							style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">序号</th>
										<th width="30"><input id="allCheckBtn" type="checkbox"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul class="pagination">
												
											</ul>
										</td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="roleAddModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">角色新增</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label>角色名称</label> <input id="name" name="name" class="form-control"
								placeholder="输入角色名">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="roleUpdateModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">角色修改</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label>角色名称</label>
							<input type="hidden" name="id">
							<input id="name" name="name" class="form-control"placeholder="输入角色名">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="updateBtn" type="button" class="btn btn-primary">修改</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="assignPermissionModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">角色分配</h4>
				</div>
				<div class="modal-body">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="assignBtn" type="button" class="btn btn-primary">分配</button>
				</div>
			</div>
		</div>
	</div>


	<%@ include file="/WEB-INF/jsp/common/js.jsp"%>
	<script type="text/javascript">
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});
			
			initData(1);
		});
		
		//1.获取数据
		var json = {
				pageNum:1,
				pageSize:2
		};
		
		function initData(pageNum) {
			json.pageNum = pageNum;
			var index = -1;
			$.ajax({
				type:'post',
				url:'${PATH}/role/loadData',
				data:json,
				beforeSend:function(){
					index = layer.load(0,{time:10*1000});
					return true;
				},
				success:function(result){
					console.log(result);
					layer.close(index);
					initShow(result);
					initNavg(result);
				}
			});
		}
		
		//2.展示数据
		function initShow(result) {
			console.log(result);
			$('tbody').empty();
			var list = result.list;
			var content = '';
			$.each(list,function(i,e){
				content+='<tr>';
				content+='  <td>'+(i+1)+'</td>';
				content+='  <td><input class="itemCheckBtn" type="checkbox" roleId="'+e.id+'"></td>';
				content+='  <td>'+e.name+'</td>';
				content+='  <td>';
				content+='	  <button type="button" roleId="'+e.id+'" class="assignPermissionClass btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
				content+='	  <button type="button" roleId="'+e.id+'" class="updateClass btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
				content+='	  <button type="button" class="btn btn-danger btn-xs deleteButton" roleId="'+e.id+'"><i class=" glyphicon glyphicon-remove"></i></button>';
				content+='  </td>';
				content+='</tr>';
			});
			
			$("tbody").html(content);
		}
		//3.展示分页条
		function initNavg(result) {
			$('.pagination').empty();
			var navigatepageNums = result.navigatepageNums;
			
			if(result.isFirstPage){
				$('.pagination').append($('<li class="disabled"><a href="#">上一页</a></li>'));
			}else{
				$('.pagination').append($('<li><a onclick="initData('+(result.pageNum-1)+')">上一页</a></li>'));
			}
			
			
			$.each(navigatepageNums,function(i,num){
				if(num == result.pageNum){
					$('.pagination').append($('<li class="active"><a href="#">'+num+'<span class="sr-only">(current)</span></a></li>'));
				}else{
					$('.pagination').append($('<li><a onclick="initData('+num+')">'+num+'</a></li>'));
				}
			});
			
			
			if(result.isLastPage){
				$('.pagination').append($('<li class="disabled"><a href="#">下一页</a></li>'));
			}else{
				$('.pagination').append($('<li><a onclick="initData('+(result.pageNum+1)+')">下一页</a></li>'));
			}
			
		}
		
		//=================添加角色开始========================================
		
		$("#queryBtn").click(function(){
			var condition = $("#condition").val();
			json.condition = condition;
			initData(1);
		});

		$("#addBtn").click(function(){
			$("#roleAddModal").modal({
				show:true, //打开
				backdrop:'static'
			});
		});
		
		$("#saveBtn").click(function(){
			var name = $("#roleAddModal input[name='name']").val();
			var index = -1;
			$.ajax({
				type:'post',
				url:'${PATH}/role/addRole',
				data:{name:name},
				beforeSend:function(){
					index = layer.load(0,{time:10*1000});
					return true;
				},
				success:function(result){
					if(result == 'ok'){
						layer.close(index);
						layer.msg("保存成功",{time:1000},function(){
							//1、关闭模态框
							$('#roleAddModal').modal('hide');
							$("#roleAddModal input[name='name']").val("");
							initData(1);
						});
					}else{
						layer.close(index);
						layer.msg("保存失败");
					}
				}
			});
		});
		
		$('tbody').on('click','.updateClass',function(){
			var roleId = $(this).attr("roleId");
			$.get("${PATH}/role/getRoleById",{id:roleId},function(result){
				console.log(result);
				
				$("#roleUpdateModal").modal({
					show:true, //打开
					backdrop:'static',
					keybodar:false
				});
				
				$("#roleUpdateModal input[name='name']").val(result.name);
				$("#roleUpdateModal input[name='id']").val(result.id);
			});
		});
		
		$("#updateBtn").click(function(){
			var name = $("#roleUpdateModal input[name='name']").val();
			var id = $("#roleUpdateModal input[name='id']").val();
			$.post('${PATH}/role/updateRole',{id:id,name:name},function(result){
				if(result=="ok"){
					layer.msg("修改成功",{time:1000},function(){
						$('#roleUpdateModal').modal('hide');
						initData(json.pageNum);
					});
				}else{
					layer.msg("修改失败");
				}
			});
		});
		
		$('tbody').on('click','.deleteButton',function(){
			var roleId = $(this).attr("roleId");
			layer.confirm('您是否确定删除该条数据？',{btn:['确定','取消']},function(index){
				layer.close(index);
				$.get("${PATH}/role/doDelete",{id:roleId},function(result){
					if(result=="ok"){
						layer.msg("删除成功",{time:1000},function(){
							initData(json.pageNum);
						});
					}else{
						layer.msg("删除失败");
					}
				});
			},function(index){
				
				layer.close(index);
			});
		});
		
		$("#allCheckBtn").click(function(){
			$(".itemCheckBtn").prop("checked",$(this).prop("checked"));
		});
		
		$(".itemCheckBtn").click(function() {
			$("#allCheckBtn").prop("checked", $(".itemCheckBtn:checked").length == $(".itemCheckBtn").length);
		});
		
		//删除多个
		$("#deleteBatchBtn").click(function(){
			//1、获取到当前被选中要删除的用户
			var eles = $(".itemCheckBtn:checked");
			if(eles.length == 0){
				layer.msg("请选中要删除的数据");
				return false;
			}
			var ids = new Array();
			eles.each(function(){
				ids.push($(this).attr("roleId"));
			});
			var str = ids.join(",")
			layer.confirm("确认删除这些【"+str+"】用户吗？", {btn:["确认","取消"]}, function(){
				$.get("${PATH}/role/deleteBatch",{ids:str},function(result){
					if(result == "ok"){
						layer.msg("删除成功",{time:1000},function(){
							initData(json.pageNum);
						});
					}else{
						layer.msg("删除失败");
					}
				});
			}, function(){
				
			});
		});
		
		//************给角色分配权限 开始*********************************************************************
		var roleId = '';
		$("tbody").on("click",".assignPermissionClass",function(){
			$("#assignPermissionModal").modal({
				show:true, //打开
				backdrop:'static',
				keybodar:false
			});
			
			roleId = $(this).attr("roleId");
			
			initZtree();
		});
		
		function initZtree() {
			var setting = {
					callback:{
	                    onClick:function(event,treeId,treeNode){
	                    	return false;
	                    }
	                },
	                check: {
	    				enable: true
	    			},
					data: {
						simpleData: {
							enable: true,
							pIdKey: "pid"
						},
						key: {
							name: "title",
							url: ""
						}
					},
					view:{
						addDiyDom: function(treeId, treeNode){
							$("#" + treeNode.tId + "_ico").removeClass();
							$("#" + treeNode.tId + "_span").before("<span class='"+treeNode.icon+"'></span>");
						},
					}

				};
			
			//1.加载数据
			$.get("${PATH}/permission/loadTree",function(result){
				var zNodes = result;
				$.fn.zTree.init($("#treeDemo"), setting, zNodes); //异步访问数据
				
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				treeObj.expandAll(true);
				
				//2.回显已分配许可
				$.get("${PATH}/role/listPermissionIdByRoleId",{roleId:roleId},function(result){
					$.each(result,function(i,e){
						var permissionId = e;
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var node = treeObj.getNodeByParam("id", permissionId, null);
						treeObj.checkNode(node, true, false, false);
					});
				});
			});
		}
		
		$("#assignBtn").click(function(){
			
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = treeObj.getCheckedNodes(true);
			
			if(nodes.length == 0){
        		layer.msg("请选择要分配的权限",{time:2000,icon:6});
        		return false;
			}else{
				var json = {
						roleId:roleId
				};
				
				$.each(nodes,function(i,e){
					var permissionId = e.id;
					json['ids['+i+']']=permissionId;
						
				});
				
				$.ajax({
					type:"post",
					url:"${PATH}/role/doAssignPermissionToRole",
					data:json,
					success:function(result){
						if(result =="ok"){
							layer.msg("分配成功",{time:1000},function(){
								$('#assignPermissionModal').modal('hide');
							});
						}else{
							layer.msg("分配失败");
						}
					}
				});
			}
		});
	</script>
</body>
</html>
