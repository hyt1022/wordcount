import java.io.File;
import java.util.ArrayList;

public class WC {

	/**
	 * @param args
	 */
	private static void getAllFile(ArrayList<String>sourceNameArray, String rootPath, String keyword, boolean function_s)   //��ȡ�����ļ�("*.c")
	{
	    File rootFile = new File(rootPath);
	    File[] files = rootFile.listFiles();
	    if (files != null)
	    {
	        for (File f : files)
	        {
	            if (f.isDirectory() && function_s) //�ж����ļ���
	                getAllFile(sourceNameArray,f.getPath(), keyword, true);
	            else if (f.getName().indexOf(keyword) == f.getName().length() - keyword.length())
	            {
	                sourceNameArray.add(f.getPath());
	            }
	        }
	    }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename="";
		String filename1="";
		String outputfile="result.txt";
		String stoplistfile=" ";
		boolean[] func = new boolean[7];
		ArrayList<String>sourcefile = new ArrayList<String>();
		ArrayList<String>sourcefilename = new ArrayList<String>();
		for(int i=0; i<6;i++)
		{
			func[i] = false;
		}
		boolean error = false;
		for(int i=0; i<args.length;i++)//ѭ���ж�ÿһ���ַ���
		{
			if(args[i].equals("-c"))
			{
				func[0] = true;
				if((i+1) == args.length)//������ַ���Ϊ���һ�����򲻷��ϸ�ʽ����ͬ
				{
					error = true;
					break;
				}
				if(args[i+1].charAt(0) != '-')//�����һ������'-'��ͷ��ָ���Ĭ��Ϊ�ļ��������ж�ȡ����ͬ
				{
					i++;
					filename = args[i];
				}
			}
			else if(args[i].equals("-w"))
			{
				func[1] = true;
				if((i+1) == args.length)
				{
					error = true;
					break;
				}
				if(args[i+1].charAt(0) != '-')
				{
					i++;
					filename = args[i];
				}
			}
			else if(args[i].equals("-l"))
			{
				func[2] = true;
				if((i+1) == args.length)
				{
					error = true;
					break;
				}
				if(args[i+1].charAt(0) != '-')
				{
					i++;
					filename = args[i];
				}
			}
			else if(args[i].equals("-o"))
			{
				func[3] = true;
				if((i+1) == args.length)
				{
					error = true;
					break;
				}
				i++;
				outputfile = args[i];//'-o'ָ�����������ļ��������ж�ȡ
			}
			else if(args[i].equals("-e"))
			{
				func[4] = true;
				if((i+1) == args.length)
				{
					error = true;
					break;
				}
				i++;
				stoplistfile = args[i];//'-e'ָ�������ͣ�ôʱ��ļ��������ж�ȡ
			}
			else if(args[i].equals("-a"))
			{
				func[5] = true;
				if((i+1) == args.length)
				{
					error = true;
					break;
				}
				if(args[i+1].charAt(0) != '-')
				{
					i++;
					filename = args[i];
				}
			}
			else if(args[i].equals("-s"))
			{
				func[6] = true;
				if((i+1) == args.length)
				{
					error = true;
					break;
				}
				if(args[i+1].charAt(0) != '-')
				{
					i++;
					filename = args[i];
				}
			}
			else
			{
				error = true;
				break;
			}
		}
		if(error)
		{
			System.out.println("input error");
		}
		else
		{
			if(func[6])
			{
				if(filename.contains("*.c"))
				{
					String filepath = System.getProperty("user.dir");
					getAllFile(sourcefile,filepath,".c",func[6]);
				}
				if(filename.contains("*.txt"))
				{
					String filepath = System.getProperty("user.dir");
					getAllFile(sourcefile,filepath,".txt",func[6]);
				}
				
				for(int i=0;i<sourcefile.size();i++)
				{
					WCmethod wc = new WCmethod();
					wc.analyse(sourcefile.get(i),stoplistfile,func[4]);
					if(func[5])
					{
						wc.analyseAddtion();
					}
					wc.writeResult(func[0],func[1],func[2],func[3],func[4],func[5],func[6],outputfile);
				}
			}
			else{
				WCmethod wc = new WCmethod();
				wc.analyse(filename,stoplistfile,func[4]);
				if(func[5])
				{
					wc.analyseAddtion();
				}
				wc.writeResult(func[0],func[1],func[2],func[3],func[4],func[5],func[6],outputfile);
			}
		}
		
	}
}
