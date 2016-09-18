
class MultipleStatement extends SList
{
	Statement statement;
	SList sList;

	MultipleStatement(Statement s, SList sl)
	{
		statement = s;
		sList = sl;
	}
	
	void emitInstructions(){
		statement.emitInstructions();
		sList.emitInstructions();
	}
}