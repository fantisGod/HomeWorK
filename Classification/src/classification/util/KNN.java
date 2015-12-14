package classification.util;

import java.util.ArrayList;
import java.util.LinkedList;

public class KNN {

	//变量定义
	static private int maxAge = 0;
	static private int minAge = 200;
	static private int maxDuration = 0;
	static private int minDuration = 60000;
	static private int maxCampaign = 0;
	static private int minCampaign = 500;
	static private int maxPdays = 0;
	static private int minPdays = 999;
	static private int maxPrevious = 0;
	static private int minPrevious = 200;
	static private double maxEmpVarRate = -100;
	static private double minEmpVarRate = 100;
	static private double maxConsPriceIdx = 0;
	static private double minConsPriceIdx = 100;
	static private double maxConsConfIdx = -100;
	static private double minConsConfIdx = 100;
	static private double maxEuribor3m = 0;
	static private double minEuribor3m = 100;
	static private double maxNrEmployed = 0;
	static private double minNrEmployed = 10000;
	
	//预处理
	public static void dataProcessing(ArrayList<Record> records)
	{
		int len = records.size();
		int maxAgeTmp = 0;
		int minAgeTmp = 200;
		int maxDurationTmp = 0;
		int minDurationTmp = 60000;
		int maxCampaignTmp = 0;
		int minCampaignTmp = 500;
		int maxPdaysTmp = 0;
		int minPdaysTmp = 999;
		int maxPreviousTmp = 0;
		int minPreviousTmp = 200;
		double maxEmpVarRateTmp = -100;
		double minEmpVarRateTmp = 100;
		double maxConsPriceIdxTmp = 0;
		double minConsPriceIdxTmp = 100;
		double maxConsConfIdxTmp = -100;
		double minConsConfIdxTmp = 100;
		double maxEuribor3mTmp = 0;
		double minEuribor3mTmp = 100;
		double maxNrEmployedTmp = 0;
		double minNrEmployedTmp = 10000;
		
		for(int i=0;i<len;i++)
		{
			int tmp = Integer.parseInt(records.get(i).getValue("age"));
			int tmp2 = Integer.parseInt(records.get(i).getValue("duration"));
			int tmp3 = Integer.parseInt(records.get(i).getValue("campaign"));
			int tmp4 = Integer.parseInt(records.get(i).getValue("pdays"));
			int tmp5 = Integer.parseInt(records.get(i).getValue("previous"));
			double tmp6 = Double.parseDouble(records.get(i).getValue("emp.var.rate"));
			double tmp7 = Double.parseDouble(records.get(i).getValue("cons.price.idx"));
			double tmp8 = Double.parseDouble(records.get(i).getValue("cons.conf.idx"));
			double tmp9 = Double.parseDouble(records.get(i).getValue("euribor3m"));
			double tmp10 = Double.parseDouble(records.get(i).getValue("nr.employed"));
			if(tmp > maxAgeTmp)
				maxAgeTmp = tmp;
			if(tmp < minAgeTmp)
				minAgeTmp = tmp;
			if(tmp2 > maxDurationTmp)
				maxDurationTmp = tmp2;
			if(tmp2 < minDurationTmp)
				minDurationTmp = tmp2;
			if(tmp3 > maxCampaignTmp)
				maxCampaignTmp = tmp3;
			if(tmp3 < minCampaignTmp)
				minCampaignTmp = tmp3;
			if(tmp4 != 999)
			{
				if(tmp4 > maxPdaysTmp)
					maxPdaysTmp = tmp4;
				if(tmp4 < minPdaysTmp)
					minPdaysTmp = tmp4;
			}
			if(tmp5 > maxPreviousTmp)
				maxPreviousTmp = tmp5;
			if(tmp5 < minPreviousTmp)
				minPreviousTmp = tmp5;
			if(tmp6 > maxEmpVarRateTmp)
				maxEmpVarRateTmp = tmp6;
			if(tmp6 < minEmpVarRateTmp)
				minEmpVarRateTmp = tmp6;
			if(tmp7 > maxConsPriceIdxTmp)
				maxConsPriceIdxTmp = tmp7;
			if(tmp7 < minConsPriceIdxTmp)
				minConsPriceIdxTmp = tmp7;
			if(tmp8 > maxConsConfIdxTmp)
				maxConsConfIdxTmp = tmp8;
			if(tmp8 < minConsConfIdxTmp)
				minConsConfIdxTmp = tmp8;
			if(tmp9 > maxEuribor3mTmp)
				maxEuribor3mTmp = tmp9;
			if(tmp9 < minEuribor3mTmp)
				minEuribor3mTmp = tmp9;
			if(tmp10 > maxNrEmployedTmp)
				maxNrEmployedTmp = tmp10;
			if(tmp10 < minNrEmployedTmp)
				minNrEmployedTmp = tmp10;
		}
		
		maxAge = maxAgeTmp;
		minAge = minAgeTmp;
		maxDuration = maxDurationTmp;
		minDuration = minDurationTmp;
		maxCampaign = maxCampaignTmp;
		minCampaign = minCampaignTmp;
		maxPdays = maxPdaysTmp;
		minPdays = minPdaysTmp;
		maxPrevious = maxPreviousTmp;
		minPrevious = minPreviousTmp;
		maxEmpVarRate = maxEmpVarRateTmp;
		minEmpVarRate = minEmpVarRateTmp;
		maxConsPriceIdx = maxConsPriceIdxTmp;
		minConsPriceIdx = minConsPriceIdxTmp;
		maxConsConfIdx = maxConsConfIdxTmp;
		minConsConfIdx = minConsConfIdxTmp;
		maxEuribor3m = maxEuribor3mTmp;
		minEuribor3m = minEuribor3mTmp;
		maxNrEmployed = maxNrEmployedTmp;
		minNrEmployed = minNrEmployedTmp;
	}
	
	public static ArrayList<String> classifier(LinkedList<Record> recordList,ArrayList<Record> trainRecords,ArrayList<Record> testRecords,ArrayList<String> attrList)
	{
		//变量定义
		ArrayList<String> resList = new ArrayList<String>();
		ArrayList<Record> records = new ArrayList<Record>(recordList);
		int k = 1;
		double[] knnDistance = new double[k];
		String[] knnY = new String[k];
		int curNum = 0;
		double maxDis = 0;
		
		//预处理，即计算数值型属性的最大值和最小值
		dataProcessing(records);
		
		//以随机的4120条记录作为检测集，其他记录作为训练集
		int len1 = trainRecords.size();
		int len2 = testRecords.size();
	    for(int i=0;i<len2;i++)
	    {
	    	for(int j=0;j<len1;j++)
	    	{
	    		double distance = calDistance(records.get(j),records.get(i),attrList);
	    		String s = records.get(j).getValue("y");
	    		if(curNum < k)
	    		{
	    		    knnDistance[curNum] = distance;
	    		    knnY[curNum] = s;
	    		    curNum++;
	    		    int tmp = curNum - 2;
	    		    while((tmp >= 0) && (distance < knnDistance[tmp]))
	    		    {
	    		    	knnDistance[tmp+1] = knnDistance[tmp];
	    		    	knnY[tmp+1] = knnY[tmp];
	    		    	tmp--;
	    		    }
	    		    knnDistance[tmp+1] = distance;
	    		    knnY[tmp+1] = s;
	    		    
	    		    maxDis = knnDistance[curNum-1];
	    		}
	    		else if((k == curNum) && (distance < knnDistance[curNum-1]))
	    		{
	    			knnDistance[curNum-1] = distance;
	    		    knnY[curNum-1] = s;
	    		    int tmp = curNum - 2;
	    		    while((tmp >= 0) && (distance < knnDistance[tmp]))
	    		    {
	    		    	knnDistance[tmp+1] = knnDistance[tmp];
	    		    	knnY[tmp+1] = knnY[tmp];
	    		    	tmp--;
	    		    }
	    		    knnDistance[tmp+1] = distance;
	    		    knnY[tmp+1] = s;
	    		    
	    		    maxDis = knnDistance[curNum-1];
	    		}
	    	}
	    	
	    	int yNum = 0;
	    	int nNum = 0;
	    	for(int t=0;t<k;t++)
	    	{
	    		if(knnY[t].equals("yes"))
	    			yNum++;
	    		else
	    			nNum++;
	    	}
	    	if(yNum >= nNum)
	    		resList.add("yes");
	    	else
	    		resList.add("no");
	    }
	    		
		return resList;
	}
	
	//计算两个记录之间的距离
	public static double calDistance(Record r1,Record r2,ArrayList<String> attrList)
	{
		double distance = 0;
		int diataSum = 0;
		
		int len = attrList.size();
		int curIndex = 0;
		if((curIndex<len) && (true == attrList.get(curIndex).equals("age")))
		{
			double tmp1 = Math.abs(Integer.parseInt(r1.getValue("age")) - Integer.parseInt(r2.getValue("age")));
			double tmp2 = tmp1/(maxAge-minAge);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("job")))
		{
			if(!(r1.getValue("job").equals("unknown") || r2.getValue("job").equals("unknown")))
			{
				if(false == r1.getValue("job").equals(r2.getValue("job")))
				{
					distance += 1;
					diataSum++;
				}
				else
				{
					diataSum++;
				}
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("marital")))
		{
			if(!(r1.getValue("marital").equals("unknown") || r2.getValue("marital").equals("unknown")))
			{
				if(false == r1.getValue("marital").equals(r2.getValue("marital")))
				{
					distance += 1;
					diataSum++;
				}
				else
				{
					diataSum++;
				}
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("education")))
		{
			if(!(r1.getValue("education").equals("unknown") || r2.getValue("education").equals("unknown")))
			{
				if(false == r1.getValue("education").equals(r2.getValue("education")))
				{
					distance += 1;
					diataSum++;
				}
				else
				{
					diataSum++;
				}
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("default")))
		{
			if(!(r1.getValue("default").equals("unknown") || r2.getValue("default").equals("unknown")))
			{
				if(false == r1.getValue("default").equals(r2.getValue("default")))
				{
					distance += 1;
					diataSum++;
				}
				else
				{
					diataSum++;
				}
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("housing")))
		{
			if(!(r1.getValue("housing").equals("unknown") || r2.getValue("housing").equals("unknown")))
			{
				if(false == r1.getValue("housing").equals(r2.getValue("housing")))
				{
					distance += 1;
					diataSum++;
				}
				else
				{
					diataSum++;
				}
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("loan")))
		{
			if(!(r1.getValue("loan").equals("unknown") || r2.getValue("loan").equals("unknown")))
			{
				if(false == r1.getValue("loan").equals(r2.getValue("loan")))
				{
					distance += 1;
					diataSum++;
				}
				else
				{
					diataSum++;
				}
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("contact")))
		{
			if(false == r1.getValue("contact").equals(r2.getValue("contact")))
			{
				distance += 1;
				diataSum++;
			}
			else
			{
				diataSum++;
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("month")))
		{
			if(false == r1.getValue("month").equals(r2.getValue("month")))
			{
				distance += 1;
				diataSum++;
			}
			else
			{
				diataSum++;
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("day_of_week")))
		{
			if(false == r1.getValue("day_of_week").equals(r2.getValue("day_of_week")))
			{
				distance += 1;
				diataSum++;
			}
			else
			{
				diataSum++;
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("duration")))
		{
			double tmp1 = Math.abs(Integer.parseInt(r1.getValue("duration")) - Integer.parseInt(r2.getValue("duration")));
			double tmp2 = tmp1/(maxDuration-minDuration);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("campaign")))
		{
			double tmp1 = Math.abs(Integer.parseInt(r1.getValue("campaign")) - Integer.parseInt(r2.getValue("campaign")));
			double tmp2 = tmp1/(maxCampaign-minCampaign);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("pdays")))
		{
			double tmp1 = Integer.parseInt(r1.getValue("pdays"));
			double tmp2 = Integer.parseInt(r2.getValue("pdays"));
			if(tmp1 == tmp2)
			{
				diataSum++;
			}
			else if((999 == tmp1) || (999 == tmp2))
			{
				distance += 1;
				diataSum++;
			}
			else
			{
				double tmp3 = Math.abs(tmp1-tmp2)/(maxPdays-minPdays);
				distance += tmp3;
				diataSum++;
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("previous")))
		{
			double tmp1 = Math.abs(Integer.parseInt(r1.getValue("previous")) - Integer.parseInt(r2.getValue("previous")));
			double tmp2 = tmp1/(maxPrevious-minPrevious);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("poutcome")))
		{
			if(false == r1.getValue("poutcome").equals(r2.getValue("poutcome")))
			{
				distance += 1;
				diataSum++;
			}
			else
			{
				diataSum++;
			}
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("emp.var.rate")))
		{
			double tmp1 = Math.abs(Double.parseDouble(r1.getValue("emp.var.rate")) - Double.parseDouble(r2.getValue("emp.var.rate")));
			double tmp2 = tmp1/(maxEmpVarRate-minEmpVarRate);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("cons.price.idx")))
		{
			double tmp1 = Math.abs(Double.parseDouble(r1.getValue("cons.price.idx")) - Double.parseDouble(r2.getValue("cons.price.idx")));
			double tmp2 = tmp1/(maxConsPriceIdx-minConsPriceIdx);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("cons.conf.idx")))
		{
			double tmp1 = Math.abs(Double.parseDouble(r1.getValue("cons.conf.idx")) - Double.parseDouble(r2.getValue("cons.conf.idx")));
			double tmp2 = tmp1/(maxConsConfIdx-minConsConfIdx);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("euribor3m")))
		{
			double tmp1 = Math.abs(Double.parseDouble(r1.getValue("euribor3m")) - Double.parseDouble(r2.getValue("euribor3m")));
			double tmp2 = tmp1/(maxEuribor3m-minEuribor3m);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		if((curIndex<len) && (true == attrList.get(curIndex).equals("nr.employed")))
		{
			double tmp1 = Math.abs(Double.parseDouble(r1.getValue("nr.employed")) - Double.parseDouble(r2.getValue("nr.employed")));
			double tmp2 = tmp1/(maxNrEmployed-minNrEmployed);
			distance += tmp2;
			diataSum++;
			curIndex++;
		}
		
		
		return distance/diataSum;
	}
}