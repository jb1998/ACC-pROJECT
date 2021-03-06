package accwebsearchengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Node;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
public class WebCrawlingFile{
	
	 public static void main(String[] args) throws IOException {
//	        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
	        String url = "https://www.wechu.org";
	        print("Fetching %s...", url);

	        Document doc = Jsoup.connect(url).get();
	        Elements links = doc.select("a[href]");
	        Elements media = doc.select("[src]");
	        Elements imports = doc.select("link[href]");

	        print("\nMedia: (%d)", media.size());
	        for (Element src : media) {
	            if (src.normalName().equals("img"))
	                print(" * %s: <%s> %sx%s (%s)",
	                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
	                        trim(src.attr("alt"), 20));
	            else
	                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
	        }

	        print("\nImports: (%d)", imports.size());
	        for (Element link : imports) {
	            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
	        }

	        print("\nLinks: (%d)", links.size());
	        for (Element link : links) {
	            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
	        }
	    }

	    private static void print(String msg, Object... args) {
	        System.out.println(String.format(msg, args));
	    }

	    private static String trim(String s, int width) {
	        if (s.length() > width)
	            return s.substring(0, width-1) + ".";
	        else
	            return s;
	    }
	
	
	
	
//	   public static void main(String[] args) throws IOException {
//
//	        for (String link : findLinks("https://www.wechu.org")) {
//	            System.out.println(link);
//	        }
//
//	    }
//
//	    private static Set<String> findLinks(String url) throws IOException {
//
//	        Set<String> links = new HashSet<>();
//
//	        Document doc = Jsoup.connect(url)
//	                .data("query", "Java")
//	                .userAgent("Mozilla")
//	                .cookie("auth", "token")
//	                .timeout(3000)
//	                .get();
//
//	        Elements elements = doc.select("a[href]");
//	        for (Element element : elements) {
//	            links.add(element.attr("href"));
//	        }
//
//	        return links;
//
//	    }

 }