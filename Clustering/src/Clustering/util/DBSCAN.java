package Clustering.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class DBSCAN {

	public static HashMap<String,LinkedList<Record>> clustering(double rSquare,int minPts,LinkedList<Record> records)
	{
		//变量定义
		HashMap<String,LinkedList<Record>> result = new HashMap<String,LinkedList<Record>>();
		ArrayList<Integer> unvisitedIndex = new ArrayList<Integer>(); 
		int curClassIndex = 1,unvisitedNum;
		
		//初始化
		int len = records.size();
		for(int i=0;i<len;i++)
		{
			records.get(i).setValue("class", "0");
			records.get(i).setValue("flag", "0");
			unvisitedIndex.add(i);
		}
		unvisitedNum = len;
		Random random = new Random();
		while(0 != unvisitedNum)
		{
			//随机选择一个unvisited的对象 ;
			int index1 = unvisitedIndex.get(random.nextInt(unvisitedNum));
			Record rec = records.get(index1);
			rec.setValue("flag", "1");
			
			Queue<Integer> n = new LinkedList<Integer>();
			
			//计算rec的r邻域内有多少个对象
			int num1 = 0;
			for(int i=0;i<len;i++)
			{
				if(index1 != i)
				{
					double disSquare = calDistance(rec,records.get(i));
					
					if(rSquare >= disSquare)
					{
						n.offer(i);
						num1++;
					}	
				}
			}
			if(num1 >= minPts)
			{
				LinkedList<Record> recList = new LinkedList<Record>();
				rec.setValue("class", ""+curClassIndex);
				recList.add(rec);
				
				Integer index2 = n.poll();
				
				while(null != index2)
				{
					if(records.get(index2).getValue("flag").equals("0"))
					{
						records.get(index2).setValue("flag", "1");
						ArrayList<Integer> tmpArray = new ArrayList<Integer>();
						for(int i=0;i<len;i++)
						{
							if(index2 != i)
							{
								double disSquare = calDistance(records.get(index2),records.get(i));
								if(rSquare >= disSquare)
								{
									tmpArray.add(i);
								}
							}
						}
						if(tmpArray.size() >= minPts)
						{
							for(int j=0;j<tmpArray.size();j++)
							{
									n.offer(tmpArray.get(j));
							}
						}
							
					}
					if(records.get(index2).getValue("class").equals("0"))
					{
						records.get(index2).setValue("class", ""+curClassIndex);
						recList.add(records.get(index2));
					}
					index2 = n.poll();
				}
				result.put(""+curClassIndex, recList);
				curClassIndex++;
			}
			//更新unvisitedIndex数组
			unvisitedIndex.clear();
			for(int i=0;i<len;i++)
			{
				if(records.get(i).getValue("flag").equals("0"))
					unvisitedIndex.add(new Integer(i));
			}
			unvisitedNum = unvisitedIndex.size();
		}	
		return result;
	}
	
	public static double calDistance(Record r1,Record r2)
	{
		double x1 = Double.parseDouble(r1.getValue("x"));
		double y1 = Double.parseDouble(r1.getValue("y"));
		
		double x2 = Double.parseDouble(r2.getValue("x"));
		double y2 = Double.parseDouble(r2.getValue("y"));
		
		double disSquare =Math.sqrt(((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
		
		return disSquare;
	}
}