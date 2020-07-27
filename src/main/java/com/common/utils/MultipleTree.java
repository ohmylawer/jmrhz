package com.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 多叉树类
 */
public abstract class MultipleTree {
	
	public MultipleTree(List<?> datas) {
		super();
		this.datas = datas;
	}

	public abstract Map<Long, Node> getData() ;
	
	public String getTreeJSON() {
		
		parseData();
		
		if( CommonUtil.isEmpty(root) ) {
			
			return JSON.toJSONString("");
		}
		
		// 对多叉树进行横向排序
		root.sortChildren();
		
		// 输出有序的树形菜单的JSON字符串
		return JSON.toJSONString( root.toJSON() , new IntegerValueFilter());
		
	}
	
public String getTreeString() {
	
		parseData();
		
		// 对多叉树进行横向排序
		root.sortChildren();
		// 输出有序的树形菜单的JSON字符串
		return root.toString();
	}

	private void parseData() {
		
		final Map<Long, Node> nodeList = getData();
		
		if( CommonUtil.isEmpty(nodeList) ) {
			
			return ;
		}
		
		// 构造无序的多叉树
		Set<Map.Entry<Long, Node>> entrySet = nodeList.entrySet();
		
		for (Iterator<Map.Entry<Long, Node>> it = entrySet.iterator(); it.hasNext();) {
			
			Node node = it.next().getValue();
			
			if (node.parentId == -1l) {
				
				root = node;
			} else {
				if( nodeList.containsKey(node.parentId) ) {
					((Node) nodeList.get(node.parentId)).addChild(node);
				}				
			}
		}
		
	}
	
	
	protected List<?> datas;
	
	// 根节点
	private Node root;

}

/**
 * 节点类
 */
class Node {
	/**
	 * 节点编号
	 */
	public long id;
	/**
	 * 节点内容
	 */
	public String text;
	/**
	 * 父节点编号
	 */
	public long parentId;
	/**
	 * 孩子节点列表
	 */
	protected Children children = new Children();
	
	public Node( final long id, final long parentId, final String name) {
		
		this.id = id;
		
		this.parentId = parentId;
		
		this.text = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	// 先序遍历，拼接JSON字符串
	public String toString() {
		String result = "{" + "id : '" + id + "'" + ", text : '" + text + "'";

		if (children != null && children.getSize() != 0) {
			result += ", children : " + children.toString();
		} else {
			result += ", leaf : true";
		}

		return result + "}";
	}
	
//	public JSONObject toJSON() {
//		
//		JSONObject nodeJSON = new JSONObject();
//		nodeJSON.put("id", String.valueOf(id));
//		nodeJSON.put("name", text);
//		nodeJSON.put("children", children.toJSONArray());
//		
//		return nodeJSON;
//	}
	
	public Map<String,Object>  toJSON(){
		
		Map<String,Object>  nodeJMap = new HashMap<String,Object>();
		nodeJMap.put("id", id);
		nodeJMap.put("name", text);
		nodeJMap.put("children", children.toJSONArray());
		return nodeJMap;		
	}

// 兄弟节点横向排序
	public void sortChildren() {
		if (children != null && children.getSize() != 0) {
			children.sortChildren();
		}
	}

// 添加孩子节点
	public void addChild(Node node) {
		this.children.addChild(node);
	}
}

/**
 * 孩子列表类
 */
class Children {
	private List<Node> list = new ArrayList<Node>();

	public int getSize() {
		return list.size();
	}

	public void addChild(Node node) {
		list.add(node);
	}

// 拼接孩子节点的JSON字符串
	public String toString() {
		String result = "[";
		for (Iterator<Node> it = list.iterator(); it.hasNext();) {
			result += ((Node) it.next()).toString();
			result += ",";
		}
		result = result.substring(0, result.length() - 1);
		result += "]";
		return result;
	}
	
	public JSONArray toJSONArray() {
		
		JSONArray array = new JSONArray(list.size());
		
		for (Iterator<Node> it = list.iterator(); it.hasNext();) {
			
			array.add(it.next().toJSON());			
		}
		
		return array;
	}

// 孩子节点排序
	public void sortChildren() {
// 对本层节点进行排序
// 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
		Collections.sort(list, new NodeIDComparator());
// 对每个节点的下一层节点进行排序
		for (Iterator<Node> it = list.iterator(); it.hasNext();) {
			((Node) it.next()).sortChildren();
		}
	}
}

/**
 * 节点比较器
 */
class NodeIDComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		
		return (o1.id < o2.id ? -1 : (o1.id == o2.id ? 0 : 1));
	}

}

