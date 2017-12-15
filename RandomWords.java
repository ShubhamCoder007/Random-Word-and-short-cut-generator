import java.util.Random;
import java.io.*;
import java.lang.String;
class RandomWords
{
    static volatile boolean running=true;
    public static String generateWord(int size)
    {
        Random r=new Random();
        String s="";
        for(int i=0;i<size;i++)
        {
            int n=65+r.nextInt(26);
            s=s+(char)n;
        }
        return s;
    }
    public static void shutdown()
    {
        running=false;
    }
    public static String fullForm(String s,int l)throws IOException
    {
        String path="",full="",s1="";
        char c;
        BufferedReader in,br=null;
        Random r=new Random();
        //in=new BufferedReader(new InputStreamReader(System.in));
        //path=br.readLine();
        path="C:\\Users\\nEW u\\Desktop\\java\\word database\\DB\\";
        try{
            for(int i=0;i<l;i++)
            {
                c=s.charAt(i);
                path=path+c+".txt";
            br=new BufferedReader(new FileReader(path));
            path="C:\\Users\\nEW u\\Desktop\\java\\word database\\DB\\";
            s1="";
            int n=r.nextInt(10)+1;
            int j=0;
            do
            {
                if(j==n)
                {
                    break;
                }
                j++;        
            }
            while((s1=br.readLine())!=null);
            full=full+" "+s1;
        }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            br.close();
        }
        return full;
    }
    public static void main(String[] args)throws IOException
    {
        int size;
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the appropriate word length : ");
        size=Integer.parseInt(br.readLine());
        
        Thread t=new Thread(new Runnable(){
            public void run()
            {    
                while(running)
                {
                    String w=generateWord(size);
                System.out.println("Generated Word is : "+w);
                try{
                System.out.println("Proposed Full Form is : "+fullForm(w,size)+"\n");
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
                try{
                Thread.sleep(800);
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                }
            }
        });
        t.start();
        System.out.println("Press Enter to terminate\n");
        br.readLine();
        shutdown();
        System.out.println("\n\nThank You for using\n\nCreated by :- Shubham Banerjee");
    }
}