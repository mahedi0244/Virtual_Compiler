
class MultipleExpr extends EList
{
	Expr expr;
	EList eList;

	MultipleExpr(Expr e, EList el)
	{
		expr = e;
		eList = el;
	}
	
	String getArrayVar(){
		return expr.getArrayVar();
	}
	
	
	
	void emitInstructions(){
		
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
		
		
		eList.emitInstructions();
		if (eList.getClass() == ArrayVar.class){
			
			String myString = TypeChecker.typeMap.get(eList.getArrayVar()).toString();
			
			StringBuilder sb = new StringBuilder(myString);
			
			sb.deleteCharAt(myString.length()-1);
			sb.deleteCharAt(0);
			
			String modified = sb.toString();
			
			String [] array = modified.split(",");
			
			
			for(int i = 2; i < array.length; i++){ 
				
				IO.displayln(InterCodeGenerator.indent+"push"+array[i]);
			
			}
			
			IO.displayln(InterCodeGenerator.indent+"loadArrayElem "+TypeChecker.MemoryMap.get(eList.getArrayVar())+", "+array[1]);
			
		}
			
	}
}