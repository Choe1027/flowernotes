package com.flowernotes.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/***
 * javaBean排序器器
 * @author shanpengcheng
 */
public class BeanSort<T> implements Comparator<T> {
	private String sortAttrName;
	
	public BeanSort() {}
	/***
	 * javaBean比较器
	 * @param sortAttrName	对象中的大小参考值的属性名
	 */
	public BeanSort(String sortAttrName) {
		this.sortAttrName = sortAttrName;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int compare(T o1, T o2) {
		try {
			if(o1 instanceof Integer) {
				int v1 = (Integer)o1;
				int v2 = (Integer)o2;
				return v1>v2?1:v1==v2?0:-1;
			}
			
			if((o1 instanceof Comparable) && (o2 instanceof Comparable) 
					&& o1.getClass().getName().equals(o2.getClass().getName())) {
				return ((Comparable<T>)o1).compareTo(o2);
			}
			
			Object value1 = ObjectUtil.getAttributeValue(o1, sortAttrName);
			Object value2 = ObjectUtil.getAttributeValue(o2, sortAttrName);
			if(value1 instanceof Integer) {
				if((Integer)value1 < (Integer)value2) {
					return -1;
				} else if ((Integer)value1 > (Integer)value2) {
					return 1;
				} 
			} else if(value1 instanceof Long) {
				if((Long)value1 < (Long)value2) {
					return -1;
				} else if ((Long)value1 > (Long)value2) {
					return 1;
				} 
			} else if(value1 instanceof Double) {
				if((Double)value1 < (Double)value2) {
					return -1;
				} else if ((Double)value1 > (Double)value2) {
					return 1;
				} 
			} else {
				return value1.toString().compareTo(value2.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return 0;
	}
	
	/**
	 * 排序(升序)
	 * @param list	排序对象
	 */
	public void sortList(List<T> list) {
		Collections.sort(list, this);
	}
	
	/**
	 * 降序
	 * @param list	排序对象
	 */
	public void unSortList(List<T> list) {
		sortList(list);
		Collections.reverse(list);
	}
	
	public static void main(String[] args) {
//		List<WarStoryRank> wsList = new ArrayList<WarStoryRank>();
//		Random r = new Random();
//		for(int i=0; i<10; i++) {
//			WarStoryRank wsr = new WarStoryRank();
//			int s = r.nextInt(100);
//			wsr.setScore(s);
//			wsr.setStoryLevel(1);
//			wsList.add(wsr);
//			System.out.print(s+" ");
//		}
//		BeanSort<WarStoryRank> bs = new BeanSort<WarStoryRank>("score");
//		bs.unSortList(wsList);
//		System.out.println();
//		for(WarStoryRank wsr : wsList) {
//			System.out.print(wsr.getScore()+" ");
//		}
		List<Integer> valList = new ArrayList<Integer>();
		Random r = new Random();
		for(int i=0; i<10; i++) {
			int temp = r.nextInt(100);
			System.out.print("\t" + temp);
			valList.add(temp);
		}
		BeanSort<Integer> bs = new BeanSort<Integer>();
		bs.unSortList(valList);
		System.out.println();
		for(Integer val : valList) {
			System.out.print("\t" + val);
		}
	}
}
