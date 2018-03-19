import java.io.*;
public class WCmethod {
	char[] tempchars = new char[200];
	char[] token = new char[10];
	String file;
	char[] outfile = new char[50];
    int p = 0; //数组下标
    int c = 0; //字符数
    int w = 0; //单词数
    int l = 0; //行数
    boolean c_out;
    boolean w_out;
    boolean l_out;
public void readfile(String fileName)
	{
		//File file = new File(fileName);  
        Reader reader = null; 
        try 
        {  
            //System.out.println("以字符为单位读取文件内容，一次读多个字节：");  
            // 一次读多个字符  
            char[] temp = new char[200];  
            int charread = 0;  
            reader = new InputStreamReader(new FileInputStream(fileName));  
            // 读入多个字符到字符数组中，charread为一次读取字符数  
            while ((charread = reader.read(temp)) != -1) 
            {  
                // 同样屏蔽掉\r不显示  
                if ((charread == temp.length)  
                        && (temp[temp.length - 1] != '\r')) 
                {  
                    System.out.print(temp);  
                } 
            } 
            tempchars = temp;
        }
        catch (Exception e1) {  
            e1.printStackTrace();  
        } 
        finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } 
                catch (IOException e1) {  
                }  
            }  
        }  
	}

public void Readfile(String fileName)
{
	try{
		FileReader in = new FileReader(fileName);
		in.read(tempchars);
		in.close();	
	}
	catch (IOException e){
		
	}
}

public void analyse(String fileName)
{
	Readfile(fileName);
	file = fileName;
	int flag = 0;//判断上一个字符的标志，用来推断单词数是否增加
	while(tempchars[p] != '\0')
    {
		if(tempchars[p] == '\n')
		{
			flag = 0;
			l++;
			p++;
		}
		else if(tempchars[p] == '\r')
		{
			flag = 0;
			p++;
		}
		else if(tempchars[p] == '\t')
		{
			flag = 0;
			p++;
		}
		else if(tempchars[p] == ' ')
		{
			flag = 0;
			p++;
		}
		else
		{
			if(flag == 0){
				w++;
			}
			flag = 1;
			p++;
		}
	}
}

public void writeResult(boolean f1,boolean f2,boolean f3,boolean f4,String outputFile)
{
	p = p - 2*l;//统计了两次换行
	l++;//换行数加1为文件行数
	String outputfile;
	try{
		if(!f4)
		{
			outputfile = "result.txt";
		}
		else 
		{
			outputfile = outputFile;
		}
			FileWriter out = new FileWriter(outputfile);
		if(f1)
		{
			out.write(file + "，字符数：" + p +'\r'+'\n');
		}
		if(f2)
		{
			out.write(file + "，单词数：" + w +'\r'+'\n');
		}
		if(f3)
		{
			out.write(file + "，行数：" + l + '\r'+'\n');
		}
		out.close();
	}
	catch(IOException e){
		
	}
	//System.out.println("字符"+p+ "单词" +w+ "行"+l);
}

}
