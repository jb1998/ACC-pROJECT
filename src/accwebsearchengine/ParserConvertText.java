package accwebsearchengine;

import java.io.*;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import textprocessing.*;

/**
 * This Class converts the accesses the various URLs and converts
 * them to corresponding text files.
 * Which are used for searching and page ranking.
 */
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

public class ParserConvertText {

	public static void main(String[] args) {
		
		ArrayList<String> urlList = new ArrayList<>();
		
		//Use URL of own PC while running on local computer
		In in = new In("/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/websites.txt");
		
        while (!in.isEmpty()) {
        	
        	String myText = in.readLine();
        	//System.out.println(myText);
        	urlList.add(myText);
        	    	
        }
        
        
        for(int i = 0; i < urlList.size(); i++) {
        	
        	try {
        		
        		org.jsoup.nodes.Document doc = Jsoup.connect(urlList.get(i)).get();
        		String text = doc.text();
            	String FilePath = "/Users/jatinbindra/Downloads/ACC-Web-Search-Engine-master/src/accwebsearchengine/urls/" + (i)+".txt" ;
            	PrintWriter out = new PrintWriter(FilePath);
        		out.println(urlList.get(i));
            	out.println(text);
        		System.out.println(urlList.get(i));
        		out.close();
        		
        		
        	}catch(Exception e){
        		
        		System.out.println("Exception, Cannot be converted to text: "+ urlList.get(i));
        	}
        	
        
        }
	}
}
