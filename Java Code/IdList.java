
abstract class IdList
{
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";

		IdList p = this;
		while ( p.getClass() == MultipleId.class )
		{
			((MultipleId)p).id.printParseTree(indent1);
			p = ((MultipleId)p).idList;
		}
		p.printParseTree(indent1); // p is the last SingleId
	}
}