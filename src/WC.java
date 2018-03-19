

public class WC {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename="";
		String outputfile="result.txt";
		boolean[] func = new boolean[6];
		for(int i=0; i<6;i++)
		{
			func[i] = false;
		}
		boolean error = false;
		for(int i=0; i<args.length;i++)
		{
			if(args[i].equals("-c"))
			{
				func[0] = true;
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
				outputfile = args[i];
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
			WCmethod wc = new WCmethod();
			wc.analyse(filename);
			wc.writeResult(func[0],func[1],func[2],func[3],outputfile);
		}
		
	}
}
