package titlegenerator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class CreateWordDataSet {

	//regex: \"targetTerm\":\"([^"]+)
	private String word;
	private String initials;
	
	public CreateWordDataSet() {
		this.word = "";
		this.initials = "";
	}
	
	public CreateWordDataSet(String word, String initials) {
		this.word = word.toLowerCase();
		this.initials = initials;
	}
	
	public void setWord(String word) {
		this.word = word.toLowerCase();
	}
	
	public String getWord() {
		return this.word;
	}
	
	public void setInitials(String intials) {
		this.initials = initials;
	}
	
	public String getInitials() {
		return this.initials;
	}
	
	public void getWordResponses() {
		
		HttpClient client = new HttpClient();
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		GetMethod getMethod = null;
		BufferedOutputStream outputStream = null;
		PostMethod postMethod = null;
		
		String url = "https://www.thesaurus.com/";
		getMethod = new GetMethod(url);
		try {
			client.executeMethod(getMethod);
			File home = new File("D:\\thes.html");
			home.createNewFile();
			outputStream = new BufferedOutputStream(new FileOutputStream(home));
			outputStream.write(getMethod.getResponseBodyAsString().getBytes());
			outputStream.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		//static query string parameter s = t
		url = url+"browse/"+getWord()+"?s=t";
		System.out.println("The url: "+url);
		postMethod = new PostMethod(url);
		postMethod.addRequestHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
		postMethod.addRequestHeader("authority","www.thesaurus.com");
		postMethod.addRequestHeader("path", "/browse/"+getWord()+"?s=t");
		postMethod.addRequestHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		postMethod.addRequestHeader("accept-encoding", "gzip, deflate, br");
//		getMethod.addRequestHeader("referer", "https://www.thesaurus.com/");
		postMethod.addRequestHeader("cookie", "bid=631743-1566569909226; Variation=v0; _ga=GA1.2.239986965.1566569952; _gid=GA1.2.505628623.1566569952; __qca=P0-466791853-1566569952266; __gads=ID=2dd8859d612168c3:T=1566569954:S=ALNI_Mb-ADLKEG7inYdksfqMIRhVxMBBpw; show-editor-experiment-links=false; sid=219678-1566572502981; NPS_693db473_last_seen=1566572504000; _gat_UA-4036279-9=1");
//		getMethod.addRequestHeader("if-none-match", "W/\"13555-JtZ7tVvRpz7J0769S0vgizOWzPk\"");
//		getMethod.addRequestHeader("sec-fetch-mode", "navigate");
//		getMethod.addRequestHeader("sec-fetch-site", "same-origin");
//		getMethod.addRequestHeader("sec-fetch-user", "?1");
//		getMethod.addRequestHeader("upgrade-insecure-requests", "1");
		//getMethod.addRequestHeader("content-type", "text/html; charset=utf-8");
		//getMethod.setQueryString(new NameValuePair[] {new NameValuePair("s","t")});
		
		try {
			client.executeMethod(postMethod);
			File search = new File("D:\\search.html");
			search.createNewFile();
			outputStream = new BufferedOutputStream(new FileOutputStream(search));
			outputStream.write(postMethod.getResponseBodyAsString().getBytes());
			outputStream.close();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String response = null;
		try {
			response = postMethod.getResponseBodyAsStream().toString();
			System.out.println("Response is: "+response);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Pattern pat = Pattern.compile("\\\"targetTerm\\\":\\\"([^\"]+)"); 
		Matcher mat = null;
		
		mat = pat.matcher(response);
		System.out.println("Matcherrr: "+mat.toString());
		
		String[] words = null;
		int count = 0;
		while(mat.find()) {
		//	words[count++] = mat.group(count-1);
			System.out.println("Matcher: "+mat.group(count-1));
		}
		
	}
	
	public static void main(String[] args) {
		
		CreateWordDataSet ob = new CreateWordDataSet("vehicle", "");
		
		ob.getWordResponses();
		
	}
	
}
