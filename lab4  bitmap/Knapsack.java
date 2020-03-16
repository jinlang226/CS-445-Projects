//package proj4;

import java.io.*;
import java.util.*;

public class Knapsack
{
	public static void main(String args[]) throws Exception
	{
		final int SET_LEN = 16;

		int[] set = new int[SET_LEN];
		int target;

		if ( args.length < 1 )
		{	System.out.println("FATAL ERROR: must enter input filename on cmd line!\n");
			System.exit(-1);
		}

		// READ IN THE SET AND THE TARGET. ECHO THEM BOTH ON THIER OWN LINE TO STDOUT

		Scanner infile = new Scanner(new FileReader(args[0]));
		for (int i = 0 ; i < SET_LEN ; ++i)
		{	set[i] = infile.nextInt();
			System.out.printf("%d ", set[i]);
		}

		System.out.println();
		target=infile.nextInt();
		System.out.printf("%d\n", target);

		//System.exit(0);
		//（-1 >>> 15) 


		int sumOfSet = 0;
		String stringOfSet = "";

		for (int bitMap = 0xFFFF; bitMap > 0; bitMap--)
		{	

			sumOfSet = 0;
			stringOfSet = "";
			for(int bitIndex = 0; bitIndex < 16; bitIndex++)
			{
				if((bitMap >> bitIndex) % 2 == 1)
				{
					stringOfSet += set[bitIndex] + " ";
					sumOfSet += set[bitIndex];
				}
			}

			//System.out.println("stringOfSet: " + stringOfSet);
			//System.out.println("sumOfSet: " + sumOfSet);
			//sumOfSet = 0;
			

			if(sumOfSet == target)
			{
				//System.out.println("hit!");
				System.out.println( stringOfSet );
			}
			/*
			else
				System.out.println("print was NOT A HIT");

			Scanner kbd = new Scanner( System.in );
			System.out.print("Press c then return to continue" );
			String junk = kbd.nextLine();
			*/
		}
	}
}