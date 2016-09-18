
public class InterCodeGenerator extends TypeChecker {
	static String indent = "      ";
	static int jump = 0;
	
	public static void main(String argv[]){
		
		setIO( argv[0], argv[1] );
		setLex();
		
		getToken();
		
		Program program = program(); // build a parse tree
		if ( ! t.isEmpty() )
			displayln(t + "  -- unexpected symbol");
		
		else if ( ! syntaxErrorFound ){
			
			boolean varDecErrorFound = program.buildTypeMap();
			
			if ( ! varDecErrorFound ){
				TypeVal programType = program.typeEval(); // perform type checking
				if ( programType == typeCorrect ){
					
					program.emitInstructions();
				}
			}
		}

		closeIO();
		
	}
}


