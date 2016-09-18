
class Neg extends UnaryOperatorApplication
{
	Neg(Expr e)
	{
		expr = e;
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
		TypeVal eType = expr.typeEval();

		if ( eType == TypeChecker.typeError )
			return TypeChecker.typeError;
		else if ( eType.isNumberType() )
			return eType;
		else
		{
			IO.displayln("Type Error: unary - operator cannot be applied to "+eType.toString());
			return TypeChecker.typeError;
		}
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
		IO.displayln(InterCodeGenerator.indent+ "neg");
	}
}