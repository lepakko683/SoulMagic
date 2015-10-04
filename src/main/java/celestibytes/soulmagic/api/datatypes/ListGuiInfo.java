package celestibytes.soulmagic.api.datatypes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListGuiInfo {
	
	public final String listPath;
	
	public final List<ListGuiInfo> subLists = new LinkedList<ListGuiInfo>();
	public final List<PageGuiInfo> entries = new LinkedList<PageGuiInfo>();
	
	public ListGuiInfo(String listPath) {
		this.listPath = listPath;
	}
	
	public void add(PageGuiInfo... entries) {
		this.entries.addAll(Arrays.asList(entries));
	}
	
	public void add(ListGuiInfo... subLists) {
		this.subLists.addAll(Arrays.asList(subLists));
	}
	
	// depth: examine.monsters.ghast ; examine=0, monsters=1, ghast=2
	/** @return ListGuiInfo, PageGuiInfo or null */ 
	public Object recurseListTree(String path, int depth, PageGuiInfo create) {
//		System.out.println("recurseListTree: " + path + ";" + depth);
		if(this.listPath.equals(path)) {
//			System.out.println("return this!");
			return this;
		}
		
		if(pathNodesEqual(path, this.listPath, -2, -1)) {
			for(PageGuiInfo i : entries) {
				if(i.pagePath.equals(path)) {
					if(create != null) {
						return null;
					} else {
						return i;
					}
				}
			}
			
//			System.out.println("add! listpath=" + listPath);
			entries.add(create);
			return create;
		}
		
		for(ListGuiInfo i : subLists) {
			if(pathNodesEqual(path, i.listPath, depth + 1)) {
				return i.recurseListTree(path, depth + 1, create);
			}
		}
		
		if(create != null) {
			String newList = cutPath(path, depth + 1);
			if(newList != null) {
				ListGuiInfo toAdd = new ListGuiInfo(newList);
				subLists.add(toAdd);
//				System.out.println("add sublist=" + newList + " to: " + listPath);
				
				return toAdd.recurseListTree(path, depth + 1, create);
			} else {
//				System.out.println("cutPath: " + newList);
			}
		}
		
		System.out.println("end of method! lp: " + listPath);
		return null;
	}
	
	// index: examine.monsters.ghast ; examine=0, monsters=1, ghast = 2, ghast=-1, monsters=-2, examine=-3
	public static String getPathNode(String path, int index) {
		String[] nodes = path.split("\\.");
		int idx = 0;
		
		if(index < 0) {
			idx = nodes.length + index;
		}
		
		return idx >= 0 && idx < nodes.length ? nodes[idx] : null;
	}
	
	public static boolean nodesEqual(String a, String b) {
		if(a == null || b == null) {
			return false;
		}
		
		return a.equals(b);
	}
	
	public static boolean pathNodesEqual(String a, String b, int depth) {
		return nodesEqual(getPathNode(a, depth), getPathNode(b, depth));
	}
	
	public static boolean pathNodesEqual(String a, String b, int depth_a, int depth_b) {
		return nodesEqual(getPathNode(a, depth_a), getPathNode(b, depth_b));
	}
	
	public static String cutPath(String path, int node) {
		String[] split = path.split("\\.");
//		System.out.println("len: " + split.length + " of: " + path);
		StringBuilder ret = new StringBuilder();
		
		if(node < split.length) {
			for(int i = 0; i < split.length; i++) {
				if(i > 0) {
					ret.append('.');
				}
				
				ret.append(split[i]);
				
				if(node == i) {
					return ret.toString();
				}
			}
		}
		
		return null;
	}
}
