
abstract class Statement extends SList
{
	void printParseTree(String indent)
	{
		IO.displayln(indent + indent.length() + " <statement>");
	}

	abstract TypeVal typeEval();
	abstract void emitInstructions();
}