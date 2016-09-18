
abstract class VarDecList
{
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";

		IO.displayln(indent + indent.length() + " <var dec list>");

		VarDecList p = this;
		while ( p.getClass() == MultipleVarDec.class )
		{
			((MultipleVarDec)p).varDec.printParseTree(indent1);
			p = ((MultipleVarDec)p).varDecList;
		}
		p.printParseTree(indent1); // p is the last VarDec
	}

	abstract boolean buildTypeMap();
}