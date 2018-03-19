import java.io.*;
public class WCmethod {
	char[] tempchars = new char[200];
	char[] token = new char[10];
	String file;
	char[] outfile = new char[50];
    int p = 0; //�����±�
    int c = 0; //�ַ���
    int w = 0; //������
    int l = 0; //����
    boolean c_out;
    boolean w_out;
    boolean l_out;
public void readfile(String fileName)
	{
		//File file = new File(fileName);  
        Reader reader = null; 
        try 
        {  
            //System.out.println("���ַ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�����ֽڣ�");  
            // һ�ζ�����ַ�  
            char[] temp = new char[200];  
            int charread = 0;  
            reader = new InputStreamReader(new FileInputStream(fileName));  
            // �������ַ����ַ������У�charreadΪһ�ζ�ȡ�ַ���  
            while ((charread = reader.read(temp)) != -1) 
            {  
                // ͬ�����ε�\r����ʾ  
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
	int flag = 0;//�ж���һ���ַ��ı�־�������ƶϵ������Ƿ�����
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
	p = p - 2*l;//ͳ�������λ���
	l++;//��������1Ϊ�ļ�����
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
			out.write(file + "���ַ�����" + p +'\r'+'\n');
		}
		if(f2)
		{
			out.write(file + "����������" + w +'\r'+'\n');
		}
		if(f3)
		{
			out.write(file + "��������" + l + '\r'+'\n');
		}
		out.close();
	}
	catch(IOException e){
		
	}
	//System.out.println("�ַ�"+p+ "����" +w+ "��"+l);
}

}
