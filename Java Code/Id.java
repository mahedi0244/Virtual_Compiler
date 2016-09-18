
class Id extends Var
{
	String id;

	Id(String s)
	{
		id = s;
	}
	
	String getVariable(){
		return id;
	}
	
	String getArrayVar(){
		return "";
	}
	
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " " + id);
	}
	
	TypeVal typeEval()
	{
		TypeVal idType = TypeChecker.typeMap.get(id);
		if ( idType != null )
			return idType;
		else
		{
			IO.displayln("Error: undeclared variable  "+id);
			return TypeChecker.typeError;
		}
	}
	
	void emitInstructions() {
		
		IO.displayln(InterCodeGenerator.indent +"push #" + TypeChecker.MemoryMap.get(id));
	
	}
}