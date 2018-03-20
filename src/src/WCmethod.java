import java.io.*;
import java.util.ArrayList;
public class WCmethod {
	char[] tempchars = new char[1000];
	char[] stoplist = new char[100];
	char[] token = new char[20];
	static String resulttext="";
	ArrayList<String> stopword = new ArrayList<String>();
	String file;
	char[] outfile = new char[50];
    int p = 0; //数组下标
    int c = 0; //字符数
    int w = 0; //单词数
    int l = 0; //行数
    int s = 0; //停用词数
    int code = 0;//代码行
    int empty = 0;//空行
    int comment = 0;//注释行
    boolean c_out;
    boolean w_out;
    boolean l_out;
    
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

public void ReadStoplist(String stoplistFile)//读停用词表
{
	try{
		FileReader in = new FileReader(stoplistFile);
		in.read(stoplist);
		in.close();
	}
	catch (IOException e){
		
	}
	int i=0;
	int p=0;
	int flag = 0;
	String word;
	String stopList = new String(stoplist);
	while(stoplist[i] != '\0')
	{
		if(stoplist[i] ==' ')
		{
			if(flag == 1)//如果上一个字符为字符，且此字符为空格，将中间这个单词存入词表
			{
				if(p != i-1)
				{
					word = stopList.substring(p, i);
				}
				else
				{
					char a = stopList.charAt(p);
					word = String.valueOf(a);
				}
				stopword.add(word);//将单词存入词表
				flag = 0;
			}
			i++;
		}
		else
		{
			if(flag == 0)//如果上一个字符为空格，标记这个字符的位置，作为停用词表 的开头
			{
				p = i;
			}
			flag = 1;
			i++;
		}
	}
	//结束后将最后一个存入词表
	if(p != i-1)
	{
		word = stopList.substring(p, i);
	}
	else
	{
		char a = stopList.charAt(p);
		word = String.valueOf(a);
	}
	stopword.add(word);//将单词存入词表
	
	
}

public void analyse(String fileName,String stoplistFile,boolean f5)
{
	Readfile(fileName);
	if(f5)
	{
		ReadStoplist(stoplistFile);
	}
	file = fileName;
	String tempword;
	String tep = new String(tempchars);
	int flag = 0;//判断上一个字符的标志，用来推断单词数是否增加；进入单词分隔符则为0，进入可显示字符则为1
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
		else if(tempchars[p] == ',')
		{
			flag = 0;
			p++;
		}
		else//如果是可显示的字符
		{
			if(flag == 0)//如果上一个是单词分隔符则单词数加一
			{
				w++;
				if(f5)//如果有停用词表则进行分析
				{
					int a=p;
					while(tempchars[p] != '\n'&&tempchars[p] != '\r'&&tempchars[p] != '\t'&& tempchars[p] != ' '&& tempchars[p] != ','&&tempchars[p]!='\0')
					{
						p++;
					}//将这个单词的开始位置和结束位置做标记
					tempword = tep.substring(a, p);//将这个单词截取下来
					for(int i=0;i<stopword.size();i++)//将单词与停用词表进行对比
					{
						if(tempword.equals(stopword.get(i)))
						{
							s++;
						}
					}
					p=p-1;//当前p指向单词间的分隔符，退回到此字符的前两个
				}
			}
			flag = 1;
			p++;
		}
	}
}

public void analyseAddtion()
{
	int i=0;
	int flag = 1; //此行是否分配
	int cflag = 0; //此行是否处于注释里
	int dflag = 0; // 此行是否是‘*/’的注释行
	int count =0; //每行有效字符计数
	while(tempchars[i] != '\0')
	{
		if(tempchars[i] == ' '||tempchars[i] == '\t'||tempchars[i] == '\r')//如果是空格一类的直接跳过
		{
			i++;
		}
		else if(tempchars[i] == '/')
		{
			count++;
			if(tempchars[i+1] == '/')
			{
				if(flag == 1 && count<3 && cflag == 0)//此种情况可直接判断为注释行
				{
					comment++;
					flag = 0;
				}
			}
			else if(tempchars[i+1] == '*')
			{
				cflag = 1;
				i++;//跳过一个字符，防止下一个是‘/’的情况
			}
			i++;
		}
		else if(tempchars[i] == '*')
		{
			count++;
			if(tempchars[i+1] == '/')
			{
				if(cflag ==1)
				{
					cflag = 0;
					i++;//跳过一个字符，防止下一个是‘*’的情况
					count = 0;
					dflag = 1;
				}
			}
			i++;
		}
		else if(tempchars[i] == '\n')//到每行末尾，若此行没有分配则根据当前状态进行分配
		{
			if(flag == 1 && cflag == 0 && dflag == 0 && count<2)
			{
				empty++;
			    flag = 0;
			}
			if(flag == 1 && cflag == 0 && dflag == 0 && count>1)
			{
				code++;
				flag = 0;
			}
			if(flag == 1 && cflag == 1)
			{
				comment++;
				flag = 0;
			}
			if(flag == 1 && cflag == 0 && dflag == 1)
			{
				comment++;
				flag = 0;
				dflag = 0;
			}
			count = 0;
			flag = 1;
			i++;
		}
		else//如果是其他字符
		{
			count++;
			if(count > 1 && flag == 1 && cflag == 0)//此种情况可直接判断为代码行
			{
				code++;
				flag = 0;
			}
			i++;
		}
	}
	//若最后一行没有分配，会通过当前状态进行分配
	if(flag == 1 && dflag == 1)
	{
		comment++;
	}
	if(flag == 1 && cflag == 1)
	{
		comment++;
	}
	if(flag == 1 && cflag == 0 && dflag == 0 && count<2)
	{
		empty++;
	}
	if(flag == 1 && cflag == 0 && dflag == 0 && count>1)
	{
		code++;
	}
}


public void writeResult(boolean f1,boolean f2,boolean f3,boolean f4,boolean f5,boolean f6,boolean f7,String outputFile)
{
	p = p - l;//统计了两次换行
	l++;//换行数加1为文件行数
	s=w-s;//总词数减去停用词数
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
			resulttext += file + "，字符数：" + p +'\r'+'\n';
			//out.write(file + "，字符数：" + p +'\r'+'\n');
		}
		if(f2)
		{
			resulttext += file + "，单词数：" + w +'\r'+'\n';
			//out.write(file + "，单词数：" + w +'\r'+'\n');
		}
		if(f3)
		{
			resulttext += file + "，行数：" + l + '\r'+'\n';
			//out.write(file + "，行数：" + l + '\r'+'\n');
		}
		if(f5)
		{
			resulttext += file + "，停用后单词数：" + s + '\r'+'\n';
			//out.write(file + "，停用后单词数：" + s + '\r'+'\n');
		}
		if(f6)
		{
			resulttext += file + "，代码行/空行/注释行： " + code +"/"+empty+"/"+comment + '\r'+'\n';
			//out.write(file + "，代码行/空行/注释行： " + code +"/"+empty+"/"+comment);
		}
		out.write(resulttext);
		out.close();
	}
	catch(IOException e){
		
	}
	//System.out.println("字符"+p+ "单词" +w+ "行"+l);
}



}

