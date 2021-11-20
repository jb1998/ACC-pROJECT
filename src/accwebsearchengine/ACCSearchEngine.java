package accwebsearchengine;

import java.io.*;
import java.util.*;
import textprocessing.*;
import sorting.*;
import static java.util.stream.Collectors.toMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ACCSearchEngine {
	

	
	public static String[] getKeywords(String inputStr) 
	{
		int i_here = 0;
		In in = new In("/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/stop-words.txt");	
		inputStr = inputStr.toLowerCase();
		
		while (!in.isEmpty()) 
		{
			
			String text = in.readLine();
			text = text.toLowerCase();
			text = "\\b"+text+"\\b";
			inputStr = inputStr.replaceAll(text,"");	
		}
		
		//System.out.println(inputStr);
		
		StringTokenizer st  = new StringTokenizer(inputStr, " ");
		String[] keyWords = new String[st.countTokens()];
		
		while (st.hasMoreTokens()) 
		{ 
			keyWords[i_here]=st.nextToken();
			i_here++;
        }
		return keyWords;
	}
	
	


	public static HashMap<Integer,String> indexURLS() 
	{	
		int i = 0;
		HashMap<Integer,String> UrlIndex = new HashMap<Integer,String>();	
		In in = new In("/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/websitesurlfile.txt");
		
        while (!in.isEmpty()) 
        {
        	
        	String text = in.readLine();
        	UrlIndex.put(i,text);
        	i++;	    	
        }
		return UrlIndex;
	}
	
	
	
	public static TST<Integer> getTST(String finalPath) 
	{	
		int j = 0;
		TST<Integer> tst = new TST<Integer>();	
		In in = new In(finalPath);
		
        while (!in.isEmpty()) 
        {
        	String text = in.readLine();
	        if (j == 0) {
	            	 
	        	j = 1;
	            continue;
	            	 
	        } else if (j == 1) {  
	        	j = 0; 
	        	
	        	StringTokenizer st  = new StringTokenizer(text, " ");
	        	while (st.hasMoreTokens()) { 
	    			
	    			String word = st.nextToken();
	    			word = word.toLowerCase();
	    			//System.out.println(word);
	    			
	        		if(tst.contains(word)) {
	        			
	        			tst.put(word, tst.get(word)+1);
	        			//System.out.println("true");
	        			
	        		} else {
	        			
	        			tst.put(word, 1);
	        		}
	            }
	        }	
        }
//        
//    	int t=sc.nextInt();
//		while(t>0) {
//			t--;
//			Long a=sc.nextLong();
//			Long b=sc.nextLong();
//			
//			long ans=0;
//			ans=ans+(long)((a-b)*2);
//			long temp=(long)(b*(b-1)/2);
//			ans=ans+(long)((temp/b)*2);
//			output.write(ans+ "\n");
//			output.flush();
//
//		}
        
		return tst;
	}
	
	
	
	public static HashMap<Integer, Integer> getFreqList(String[] keyWords){
		
		
		//Map each text file to its corresponding number into an arraylist
		ArrayList<String> textList = new ArrayList<>();
		HashMap<Integer,Integer > freqList = new HashMap<Integer, Integer>();
	       
		File folder = new File("/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/urls"); 
        File[] files = folder.listFiles();
 
        for (File file : files)
        {
        	
            String myURL = file.getName();
            //myURL = myURL.substring(0, (myURL.length()-4));
            textList.add(myURL);
        	
        }
        
        for (int i = 0 ; i < textList.size() ; i++) {
        	
	        String filePath = "/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/urls/";
	        String fileName = textList.get(i);
	        String finalPath = filePath+fileName;
	        int fileIndex=0;
	        //System.out.println(finalPath);
	        try{

	        	String tempFileIndex = fileName.substring(0, (fileName.length()-4));
	        	fileIndex = Integer.parseInt(tempFileIndex);
	        } catch(NumberFormatException ex){ 
	            System.out.println("");
	        }
	        
	
			
	        TST<Integer> tst = new TST<Integer>();
	        tst = ACCSearchEngine.getTST(finalPath);

	        
	        int counter = 0;
	        
	        for (String str: keyWords) {	        	
	        	if (tst.contains(str)){
	
	        		int count = tst.get(str);
	      
	        		counter = counter + count;        		
	        	}
	        }
	       
	        freqList.put(fileIndex, counter);
        }  
        

		return freqList;
	}

	

	public static HashMap<Integer, Integer> sortHashMap(HashMap<Integer,Integer> freqList)
	{	
		  HashMap<Integer, Integer> sortedFreqList = freqList
		          .entrySet()
		          .stream()
		          .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		          .collect(
		              toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
		                  LinkedHashMap::new)); 
		
		return sortedFreqList;		
	}
	
	
	
	
	public static void storeHashMap(HashMap<Integer, Integer> freqList, String[] keyWords) {

		Sort.mergeSort(keyWords);
		String fileNameToStoreHashMap = "";

		for (String str : keyWords) {

			fileNameToStoreHashMap = fileNameToStoreHashMap + str + "_";
		}

		fileNameToStoreHashMap = fileNameToStoreHashMap + ".dat";

		String filePath = "/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/hashmap_data/";
		String finalPath = filePath + fileNameToStoreHashMap;

		try {

			FileOutputStream fileOut = new FileOutputStream(finalPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(freqList);
			out.close();
			fileOut.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	

	public static HashMap<Integer,Integer> retreiveHashMap(String[] keyWords) {
		
		Sort.mergeSort(keyWords);

		String fileNameToStoreHashMap = "";

		for (String str : keyWords) {

			fileNameToStoreHashMap = fileNameToStoreHashMap + str + "_";
		}

		fileNameToStoreHashMap = fileNameToStoreHashMap + ".dat";
		String filePath = "/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/hashmap_data/";
		String finalPath = filePath + fileNameToStoreHashMap;
		
		  HashMap<Integer,Integer> freqList = new HashMap<Integer,Integer>();
		  freqList = null;
		  
		  try{
			  
			  FileInputStream fileIn = new FileInputStream(finalPath);
			  ObjectInputStream in = new ObjectInputStream(fileIn);
			  freqList = (HashMap<Integer, Integer>)in.readObject();
			  in.close();
			  fileIn.close();
			  
		  } catch (Exception e){
			  
		  e.printStackTrace();
		  }
	
		  return freqList;
		
	}
	

	public static void main(String[] args) {
		
		String mySearch = "COVID";
		
		String[] keyWords = ACCSearchEngine.getKeywords(mySearch);
		Sort.mergeSort(keyWords);
			
		String fileName = "";
		for (String str : keyWords) {

			fileName = fileName + str + "_";
		}

		fileName = fileName + ".dat";
		
		boolean fileExist = false;
		
		File folder = new File("/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/hashmap_data"); 
        File[] files = folder.listFiles();
 
		for (File file : files) {

			String myFileName = file.getName();

			if (myFileName.compareTo(fileName) == 0) {

				fileExist = true;
				break;

			}

		}	
        
		if (fileExist == true){
			
			HashMap<Integer,String> urlIndex = new HashMap<Integer, String>();
			urlIndex = ACCSearchEngine.indexURLS();
			
			HashMap<Integer,Integer> freqList = new HashMap<Integer,Integer>();
			freqList = ACCSearchEngine.retreiveHashMap(keyWords);
			
			System.out.println("Top Ten Search Results for \""+mySearch +"\" are:\n");
			
			int j = 0;
			for (HashMap.Entry<Integer, Integer> entry : freqList.entrySet()) {
				
				if (j < 10) {
					
					//System.out.println(entry.getKey() + " = " + entry.getValue());
					int urlKey = entry.getKey();
					System.out.println(urlIndex.get(urlKey)+"\n");
					j++;
					
				} else {
					
					break;
				}
			}
				
		} else if (fileExist == false) {
			
			HashMap<Integer,String> urlIndex = new HashMap<Integer, String>();
			urlIndex = ACCSearchEngine.indexURLS();
			
			HashMap<Integer,Integer> freqList = new HashMap<Integer,Integer>();
			freqList = ACCSearchEngine.getFreqList(keyWords);
			
			freqList = ACCSearchEngine.sortHashMap(freqList);
			
			ACCSearchEngine.storeHashMap(freqList, keyWords);
					
			System.out.println("Top Ten Search Results for \""+mySearch +"\" are:\n");
			int j = 0;
			
			for (HashMap.Entry<Integer, Integer> entry : freqList.entrySet()) {
				
				if (j < 10) {
					
		
					int urlKey = entry.getKey();
					System.out.println(urlIndex.get(urlKey)+"\n");
					j++;
					
				} else {
					
					break;
				}
			}	
			
		}	

	}

}
