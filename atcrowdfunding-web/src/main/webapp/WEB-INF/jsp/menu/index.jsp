<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
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
							<i class="glyphicon glyphicon-th"></i> 权限菜单列表
						</h3>
					</div>
					<div class="panel-body">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">菜单新增</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<input type="hidden" name="pid">
							<label>菜单名称</label> <input id="name" name="name" class="form-control"
								placeholder="输入菜单名称">
						</div>
						<div class="form-group">
							<label>菜单url</label> <input id="url" name="url" class="form-control"
								placeholder="菜单url">
						</div>
						<div class="form-group">
							<label>菜单图标</label> <input id="icon" name="icon" class="form-control"
								placeholder="输入菜单图标">
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
	
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">菜单修改</h4>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<input type="hidden" name="id">
							<label>菜单名称</label> <input id="name" name="name" class="form-control"
								placeholder="输入菜单名称">
						</div>
						<div class="form-group">
							<label>菜单url</label> <input id="url" name="url" class="form-control"
								placeholder="菜单url">
						</div>
						<div class="form-group">
							<label>菜单图标</label> <input id="icon" name="icon" class="form-control"
								placeholder="输入菜单图标">
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
			initZtree();
		});
		
		function initZtree() {
			var setting = {
					callback:{
	                    onClick:function(event,treeId,treeNode){
	                    	return false;
	                    }
	                },
					data: {
						simpleData: {
							enable: true,
							pIdKey: "pid"
						},
						key: {
							url: ""
						}
					},
					view:{
						//selectedMulti: false,//设置是否允许同时选中多个节点。默认值: true
						addDiyDom: function(treeId, treeNode){
							$("#" + treeNode.tId + "_ico").removeClass();
							$("#" + treeNode.tId + "_span").before("<span class='"+treeNode.icon+"'></span>");
						},
						addHoverDom: function(treeId, treeNode){  
							var aObj = $("#" + treeNode.tId + "_a"); 
							if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
							var s = '<span id="btnGroup'+treeNode.tId+'">';
							if ( treeNode.level == 0 ) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn('+treeNode.id+')" title="添加菜单" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							} else if ( treeNode.level == 1 ) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateBtn('+treeNode.id+')" title="修改菜单">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
								if (treeNode.children.length == 0) {
									s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteBtn('+treeNode.id+')" title="删除菜单" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
								}
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn('+treeNode.id+')" title="添加菜单" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
							} else if ( treeNode.level == 2 ) {
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateBtn('+treeNode.id+')" title="修改菜单">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteBtn('+treeNode.id+')" title="删除菜单" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
							}
			
							s += '</span>';
							aObj.after(s);
						},
						removeHoverDom: function(treeId, treeNode){
							$("#btnGroup"+treeNode.tId).remove();
						}
					}

				};
			
			var url = "${PATH}/menu/loadTree";
			var json = {};
			$.get(url,json,function(result){
				var zNodes = result;
				zNodes.push({id:0,name:"系统菜单",icon:"fa fa-sitemap"});
				$.fn.zTree.init($("#treeDemo"), setting, zNodes); //异步访问数据
				
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				treeObj.expandAll(true);
			});
		}
		
		//************添加菜单开始****************************************************************
		function addBtn(id) {
			$("#addModal").modal({
				show:true,
				backdrop:'static',
				keyboard:false
			});
			$("#addModal input[name='pid']").val(id);
		}
		
		$("#saveBtn").click(function(){
			var pid = $("#addModal input[name='pid']").val();
			var name = $("#addModal input[name='name']").val();
			var url = $("#addModal input[name='url']").val();
			var icon = $("#addModal input[name='icon']").val();
			$.ajax({
				type:"post",
				url:"${PATH}/menu/addMenu",
				data:{
					pid:pid,
					name:name,
					url:url,
					icon:icon
				},
				beforeSend:function(){
					return true;
				},
				success:function(result){
					if(result == "ok"){
						layer.msg("保存成功",{time:1000},function(){
							$("#addModal").modal('hide');
							$("#addModal input[name='pid']").val();
							$("#addModal input[name='name']").val();
							$("#addModal input[name='url']").val();
							$("#addModal input[name='icon']").val();
							initZtree();
						});
					}else{
						layer.msg("保存失败");
					}
				}
			});
		});
		//************添加菜单结束****************************************************************
		
		//************修改菜单开始****************************************************************
		function updateBtn(id) {
			$.get("${PATH}/menu/getMenuById",{id:id},function(result){
				$("#updateModal").modal({
					show:true,
					backdrop:'static',
					keyboard:false
				});
				$("#updateModal input[name='id']").val(result.id);
				$("#updateModal input[name='name']").val(result.name);
				$("#updateModal input[name='url']").val(result.url);
				$("#updateModal input[name='icon']").val(result.icon);
			});
			
		}
		
		$("#updateBtn").click(function(){
			var id = $("#updateModal input[name='id']").val();
			var name = $("#updateModal input[name='name']").val();
			var url = $("#updateModal input[name='url']").val();
			var icon = $("#updateModal input[name='icon']").val();
			$.ajax({
				type:"post",
				url:"${PATH}/menu/updateMenu",
				data:{
					id:id,
					name:name,
					url:url,
					icon:icon
				},
				beforeSend:function(){
					return true;
				},
				success:function(result){
					if(result == "ok"){
						layer.msg("修改成功",{time:1000},function(){
							$("#updateModal").modal('hide');
							initZtree();
						});
					}else{
						layer.msg("修改失败");
					}
				}
				
			});
		});
		
		//************修改菜单结束****************************************************************
		
		//************删除菜单开始****************************************************************
		function deleteBtn(id) {
			layer.confirm('您是否确定删除该条数据？',{btn:['确定','取消']},function(index){
				layer.close(index);
				$.get("${PATH}/menu/deleteMenu",{id:id},function(result){
					if(result=="ok"){
						layer.msg("删除成功",{time:1000},function(){
							initZtree();
						});
					}else{
						layer.msg("删除失败");
					}
				});
			},function(index){
				
				layer.close(index);
			});
		}
		//************删除菜单结束****************************************************************

	</script>
</body>
</html>
