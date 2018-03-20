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
    int p = 0; //�����±�
    int c = 0; //�ַ���
    int w = 0; //������
    int l = 0; //����
    int s = 0; //ͣ�ô���
    int code = 0;//������
    int empty = 0;//����
    int comment = 0;//ע����
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

public void ReadStoplist(String stoplistFile)//��ͣ�ôʱ�
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
			if(flag == 1)//�����һ���ַ�Ϊ�ַ����Ҵ��ַ�Ϊ�ո񣬽��м�������ʴ���ʱ�
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
				stopword.add(word);//�����ʴ���ʱ�
				flag = 0;
			}
			i++;
		}
		else
		{
			if(flag == 0)//�����һ���ַ�Ϊ�ո񣬱������ַ���λ�ã���Ϊͣ�ôʱ� �Ŀ�ͷ
			{
				p = i;
			}
			flag = 1;
			i++;
		}
	}
	//���������һ������ʱ�
	if(p != i-1)
	{
		word = stopList.substring(p, i);
	}
	else
	{
		char a = stopList.charAt(p);
		word = String.valueOf(a);
	}
	stopword.add(word);//�����ʴ���ʱ�
	
	
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
	int flag = 0;//�ж���һ���ַ��ı�־�������ƶϵ������Ƿ����ӣ����뵥�ʷָ�����Ϊ0���������ʾ�ַ���Ϊ1
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
		else//����ǿ���ʾ���ַ�
		{
			if(flag == 0)//�����һ���ǵ��ʷָ����򵥴�����һ
			{
				w++;
				if(f5)//�����ͣ�ôʱ�����з���
				{
					int a=p;
					while(tempchars[p] != '\n'&&tempchars[p] != '\r'&&tempchars[p] != '\t'&& tempchars[p] != ' '&& tempchars[p] != ','&&tempchars[p]!='\0')
					{
						p++;
					}//��������ʵĿ�ʼλ�úͽ���λ�������
					tempword = tep.substring(a, p);//��������ʽ�ȡ����
					for(int i=0;i<stopword.size();i++)//��������ͣ�ôʱ���жԱ�
					{
						if(tempword.equals(stopword.get(i)))
						{
							s++;
						}
					}
					p=p-1;//��ǰpָ�򵥴ʼ�ķָ������˻ص����ַ���ǰ����
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
	int flag = 1; //�����Ƿ����
	int cflag = 0; //�����Ƿ���ע����
	int dflag = 0; // �����Ƿ��ǡ�*/����ע����
	int count =0; //ÿ����Ч�ַ�����
	while(tempchars[i] != '\0')
	{
		if(tempchars[i] == ' '||tempchars[i] == '\t'||tempchars[i] == '\r')//����ǿո�һ���ֱ������
		{
			i++;
		}
		else if(tempchars[i] == '/')
		{
			count++;
			if(tempchars[i+1] == '/')
			{
				if(flag == 1 && count<3 && cflag == 0)//���������ֱ���ж�Ϊע����
				{
					comment++;
					flag = 0;
				}
			}
			else if(tempchars[i+1] == '*')
			{
				cflag = 1;
				i++;//����һ���ַ�����ֹ��һ���ǡ�/�������
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
					i++;//����һ���ַ�����ֹ��һ���ǡ�*�������
					count = 0;
					dflag = 1;
				}
			}
			i++;
		}
		else if(tempchars[i] == '\n')//��ÿ��ĩβ��������û�з�������ݵ�ǰ״̬���з���
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
		else//����������ַ�
		{
			count++;
			if(count > 1 && flag == 1 && cflag == 0)//���������ֱ���ж�Ϊ������
			{
				code++;
				flag = 0;
			}
			i++;
		}
	}
	//�����һ��û�з��䣬��ͨ����ǰ״̬���з���
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
	p = p - l;//ͳ�������λ���
	l++;//��������1Ϊ�ļ�����
	s=w-s;//�ܴ�����ȥͣ�ô���
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
			resulttext += file + "���ַ�����" + p +'\r'+'\n';
			//out.write(file + "���ַ�����" + p +'\r'+'\n');
		}
		if(f2)
		{
			resulttext += file + "����������" + w +'\r'+'\n';
			//out.write(file + "����������" + w +'\r'+'\n');
		}
		if(f3)
		{
			resulttext += file + "��������" + l + '\r'+'\n';
			//out.write(file + "��������" + l + '\r'+'\n');
		}
		if(f5)
		{
			resulttext += file + "��ͣ�ú󵥴�����" + s + '\r'+'\n';
			//out.write(file + "��ͣ�ú󵥴�����" + s + '\r'+'\n');
		}
		if(f6)
		{
			resulttext += file + "��������/����/ע���У� " + code +"/"+empty+"/"+comment + '\r'+'\n';
			//out.write(file + "��������/����/ע���У� " + code +"/"+empty+"/"+comment);
		}
		out.write(resulttext);
		out.close();
	}
	catch(IOException e){
		
	}
	//System.out.println("�ַ�"+p+ "����" +w+ "��"+l);
}



}

