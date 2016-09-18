
class Sub extends BinaryOperatorApplication
{
	Sub(Expr e1, Expr e2)
	{
		expr1 = e1;
		expr2 = e2;
	}

	String getOp()
	{
		return "-";
	}

	String getArrayVar(){
		return "";
	}
	
	TypeVal typeEval()
	{
		TypeVal e1Type = expr1.typeEval();
		TypeVal e2Type = expr2.typeEval();

		if ( e1Type == TypeChecker.typeError || e2Type == TypeChecker.typeError )
			return TypeChecker.typeError;
		else if ( !e1Type.isNumberType() )
		{
			IO.displayln("Type Error: - operator cannot be applied to "+e1Type.toString());
			return TypeChecker.typeError;
		}
		else if ( !e2Type.isNumberType() )
		{
			IO.displayln("Type Error: - operator cannot be applied to "+e2Type.toString());
			return TypeChecker.typeError;
		}
		else if ( e1Type == TypeChecker.int_Type && e2Type == TypeChecker.int_Type )
			return TypeChecker.int_Type;
		else if ( e1Type == TypeChecker.float_Type && e2Type == TypeChecker.float_Type )
			return TypeChecker.float_Type;
		else
		{
			IO.displayln("Type Error: - operator cannot be applied to "+
			             " ("+e1Type.toString()+", "+e2Type.toString()+")");
			return TypeChecker.typeError;
		}
	}
	void emitInstructions() {
		
		expr1.emitInstructions();
		if (expr1.getClass() == ArrayVar.class){
			
			String myString = TypeChecker.typeMap.get(expr1.getArrayVar()).toString();
			
			StringBuilder sb = new StringBuilder(myString);
			
			sb.deleteCharAt(myString.length()-1);
			sb.deleteCharAt(0);
			
			String modified = sb.toString();
			
			String [] array = modified.split(",");
			
			
			for(int i = 2; i < array.length; i++){ 
				
				IO.displayln(InterCodeGenerator.indent+"push"+array[i]);
			
			}
			
			IO.displayln(InterCodeGenerator.indent+"loadArrayElem "+TypeChecker.MemoryMap.get(expr1.getArrayVar())+", "+array[1]);
			
		}
		
		expr2.emitInstructions();
		if (expr2.getClass() == ArrayVar.class){
			
			String myString = TypeChecker.typeMap.get(expr2.getArrayVar()).toString();
			
			StringBuilder sb = new StringBuilder(myString);
			
			sb.deleteCharAt(myString.length()-1);
			sb.deleteCharAt(0);
			
			String modified = sb.toString();
			
			String [] array = modified.split(",");
			
			
			for(int i = 2; i < array.length; i++){ 
				
				IO.displayln(InterCodeGenerator.indent+"push"+array[i]);
			
			}
			
			IO.displayln(InterCodeGenerator.indent+"loadArrayElem "+TypeChecker.MemoryMap.get(expr2.getArrayVar())+", "+array[1]);
			
		}
		IO.displayln(InterCodeGenerator.indent+ "sub");
	}
}