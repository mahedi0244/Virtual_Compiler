
class SingleId extends IdList
{
	Id id;

	SingleId(Id i)
	{
		id = i;
	}
	
	void printParseTree(String indent)
	{		
		id.printParseTree(indent);
	}
}