
class Program
{
	VarDecList varDecList;
	Statement statement;

	Program(VarDecList v, Statement s)
	{
		varDecList = v;
		statement = s;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
		
		IO.displayln(indent + indent.length() + " <program>");
		varDecList.printParseTree(indent1);
		statement.printParseTree(indent1);
	}
	
	boolean buildTypeMap()
	
	// Builds the type map in "HashMap<String,TypeVal> typeMap" included in the class "TypeChecker.java".
	// Returns true if there is an error in the variable declarations; returns false if no error found.

	{
		return varDecList.buildTypeMap();
	}
	
	TypeVal typeEval(){
		return statement.typeEval();
	}
	
	void emitInstructions(){
		statement.emitInstructions();
	}

	
}