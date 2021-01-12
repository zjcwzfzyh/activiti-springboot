//加载应该所有的科室，供选择
layui.use(['layer','jquery','table','laytpl'], function(){
    var $ = layui.$,
        layer = layui.layer,
    	table = layui.table,
        laytpl = layui.laytpl;

	var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		/*check: {
			enable: true
		},*/
		data: {
			simpleData: {
				enable: true,
                rootPId: 0
			}
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			removeTitle: '删除知识点',
			renameTitle: '编辑知识点'
		},
		callback: {
			//  beforeRename : beforeRename, //编辑结束时触发，用来验证输入的数据是否符合要求(也是根据返回值 true|false 确定是否可以编辑完成)	
			beforeRemove: beforeRemove, //点击删除时触发，用来提示用户是否确定删除（可以根据返回值 true|false 确定是否可以删除
			beforeEditName: beforeEditName,
            // 单击事件
		}
	};	
	//添加节点,交互后台
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if(treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId +
			"' title='添加知识点' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if(btn) btn.bind("click", function() {
			layer.open({
				type: 2,
				content: 'view?url=knowledge/add-knowledge',
				area: ['30%', '50%'],
				btn: ['确定', '取消'],
				yes: function(index, layero) {
					var submitID = 'LAY-knowledge-add-submit',
						submit = layero.find('iframe').contents().find('#' + submitID);
					submit.trigger('click');
				},
				success: function(layero, index) {
					var body = layer.getChildFrame('body', index);
					body.find("#pid").val(treeNode.id);
				}
			});
			return false;
		});
	};
	//添加节点,视觉上添加，在添加节点交互后台成功后调用
	window.addHoverDomVision = function(id, pid, name) {
		var zTree = $.fn.zTree.getZTreeObj("knowledge");
		var treeNode = zTree.getNodeByParam("id", pid, null);
		zTree.addNodes(treeNode, {
			id: id,
			pId: pid,
			name: name
		});
	};

	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};

	// 删除树节点
	function beforeRemove(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var idList = treeNode.id;
		idList = getAllChildrenNodes(treeNode,idList);
		layer.confirm("确认删除 " + treeNode.name + " 吗？", {
			btn: ['是', '否']
		}, function(index) {
			$.ajax({
				url: 'deleteKnowledgeInfo',
				type: 'POST',
				dataType: 'json',
				data : {
                    ids : idList
				},
				success: function(data) {
					if(data.success) {
						zTree.removeNode(treeNode);
						layer.close(index);
						alertMessage("删除成功");
					} else {
						layer.msg(data.msg, {
							icon: 5
						});
					}
				},
				error: function() {
					layer.msg('系统错误', {
						icon: 5
					});
				}
			});
		}, function(index) {
			layer.close(index);
		});
		return false;
	}
	
	function getAllChildrenNodes(treeNode,result){
	      if (treeNode.isParent) {
	        var childrenNodes = treeNode.children;
	        if (childrenNodes) {
	            for (var i = 0; i < childrenNodes.length; i++) {
	                result += ',' + childrenNodes[i].id;
	                result = getAllChildrenNodes(childrenNodes[i], result);
	            }
	        }
	    }
	    return result;
	}
	
	//编辑树节点
	function beforeEditName(treeId, treeNode) {
		layer.open({
			type: 2,
			content: 'view?url=knowledge/edit-knowledge',
			area: ['30%', '30%'],
			btn: ['确定', '取消'],
			yes: function(index, layero) {
				var submitID = 'LAY-knowledge-edit-submit',
					submit = layero.find('iframe').contents().find('#' + submitID);
				submit.trigger('click');
			},
			success: function(layero, index) {
				//回填赋值
				var body = layer.getChildFrame('body', index);
				body.find("#knowledgeId").val(treeNode.id);
				body.find("#knowledgeName").val(treeNode.name);
			}
		});
		return false;
	}
	
	//编辑节点,视觉上编辑，在节点交互后台成功后调用
	window.editNameVision = function(id, name) {
		var zTree = $.fn.zTree.getZTreeObj("knowledge");
		var nodes = zTree.getNodesByParam("id", id, null);
		nodes.name = name;
		console.log(nodes);
		console.log(zTree);
		if (nodes.length>0) {
			nodes[0].name = name;
			zTree.updateNode(nodes[0]);
		}
		//zTree.updateNode(nodes);
	};

	//加载菜单树
    $.ajax({
		url: 'getKnowledgeInfoList',
		type: 'POST',
		dataType: 'json',
		success: function(data) {
			if(data.success) {
				ztree = $.fn.zTree.init($("#knowledge"), setting, data.data);
             	ztree.expandAll(true);
			} else {
				layer.msg('加载知识点数据节点失败', {
					icon: 5
				});
			}
		},
		error: function(data) {
			layer.msg(data.msg, {
				icon: 5
			});
		}
	 });

});