
import java.util.*;

public abstract class TypeChecker extends Parser
{
	public static HashMap<String,TypeVal> typeMap = new HashMap<String,TypeVal>(); // records declared types of variables
	
	public static HashMap<String,Integer> MemoryMap = new HashMap<String, Integer>(); //records contiguous memory location of variables
	
	public static int BaseAddress = 0;
	
	
	public final static Int_Type int_Type = new Int_Type();
	public final static Float_Type float_Type = new Float_Type();
	public final static Boolean_Type boolean_Type = new Boolean_Type();
	public final static TypeCorrect typeCorrect = new TypeCorrect();
	public final static TypeError typeError = new TypeError();

	public static void main(String argv[])
	{
		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		Program program = program(); // build a parse tree
		if ( ! t.isEmpty() )
			displayln(t + "  -- unexpected symbol");
		else if ( ! syntaxErrorFound )
		{
			boolean varDecErrorFound = program.buildTypeMap();

			displayln("");
			displayln("Display types of variables:");
			displayln("");
			displayln(typeMap.toString());
			displayln("");

			if ( ! varDecErrorFound )
			{
				TypeVal programType = program.typeEval(); // perform type checking
				if ( programType == typeCorrect )
					displayln("The program has passed type checking.");
			}
			
			displayln("");
			displayln("Display addresses and base addresses:");
			displayln("");
			displayln(MemoryMap.toString());
		}

		closeIO();
	}
}
