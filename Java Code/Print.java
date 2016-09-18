
class Print extends Statement
{
	Expr expr;

	Print(Expr e)
	{
		expr = e;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent + "  ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <print>");
		expr.printParseTree(indent2);
	}
	
	TypeVal typeEval()
	{
		TypeVal eType = expr.typeEval();
		if ( eType == TypeChecker.typeError )
			return TypeChecker.typeError;
		else 
			return TypeChecker.typeCorrect;
	}
	
	void emitInstructions() {
		
		expr.emitInstructions();
		if (expr.getClass() == ArrayVar.class){
			
			String myString = TypeChecker.typeMap.get(expr.getArrayVar()).toString();
			
			StringBuilder sb = new StringBuilder(myString);
			
			sb.deleteCharAt(myString.length()-1);
			sb.deleteCharAt(0);
			
			String modified = sb.toString();
			
			String [] array = modified.split(",");
			
			
			for(int i = 2; i < array.length; i++){ 
				
				IO.displayln(InterCodeGenerator.indent+"push"+array[i]);
			
			}
			
			IO.displayln(InterCodeGenerator.indent+"loadArrayElem "+TypeChecker.MemoryMap.get(expr.getArrayVar())+", "+array[1]);
			
		}
	
		IO.displayln(InterCodeGenerator.indent+ "print ");
	}
}
