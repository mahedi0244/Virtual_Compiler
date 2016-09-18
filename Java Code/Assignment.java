
class Assignment extends Statement
{
	Var var;
	Expr expr;

	Assignment(Var v, Expr e)
	{
		var = v;
		expr = e;
	}

	String getVariable(){
		return var.getVariable();
	}
	
	
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		String indent2 = indent + "  ";

		super.printParseTree(indent);
		IO.displayln(indent1 + indent1.length() + " <assignment>");
		var.printParseTree(indent2);
		expr.printParseTree(indent2);
	}
	
	TypeVal typeEval()
	{
		TypeVal eType = expr.typeEval();
		if ( eType == TypeChecker.typeError )
			return TypeChecker.typeError;
		else
		{
			TypeVal varType = var.typeEval();
			if ( varType == TypeChecker.typeError )
			{
				return TypeChecker.typeError;
			}
			else if ( varType == eType )
				return TypeChecker.typeCorrect;
			else
			{
				IO.displayln("Type Error: "+eType.toString()+" cannot be assigned to "+varType.toString());
				return TypeChecker.typeError;
			}
		}
	}
	
	void emitInstructions(){
		
		if (var.getClass() == Id.class){
			
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
			
			IO.displayln(InterCodeGenerator.indent+"pop #" + TypeChecker.MemoryMap.get(var.getVariable()));
		}
		else{
			
			var.emitInstructions();
		
			String myString = TypeChecker.typeMap.get(var.getVariable()).toString();
			
			StringBuilder sb = new StringBuilder(myString);
			
			sb.deleteCharAt(myString.length()-1);
			sb.deleteCharAt(0);
			
			String modified = sb.toString();
			
			String [] array = modified.split(",");
			for(int i = 2; i < array.length; i++){ 
				
				IO.displayln(InterCodeGenerator.indent+"push"+array[i]);
			}
		
			expr.emitInstructions();
			if (expr.getClass() == ArrayVar.class){
				
				myString = TypeChecker.typeMap.get(expr.getArrayVar()).toString();
				
				sb = new StringBuilder(myString);
				
				sb.deleteCharAt(myString.length()-1);
				sb.deleteCharAt(0);
				
				modified = sb.toString();
				
				array = modified.split(",");
				
				
				for(int i = 2; i < array.length; i++){ 
					
					IO.displayln(InterCodeGenerator.indent+"push"+array[i]);
				
				}
				
				IO.displayln(InterCodeGenerator.indent+"loadArrayElem "+TypeChecker.MemoryMap.get(expr.getArrayVar())+", "+array[1]);
				
			}
			
			IO.displayln(InterCodeGenerator.indent+"storeArrayElem "+TypeChecker.MemoryMap.get(var.getVariable())+", "+array[1]);
		}
		
		
	}
	
	
	
	
}