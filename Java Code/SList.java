
abstract class SList
{
	void printParseTree(String indent)
	{
		SList p = this;
		while ( p.getClass() == MultipleStatement.class )
		{
			((MultipleStatement)p).statement.printParseTree(indent);
			p = ((MultipleStatement)p).sList;
		}
		p.printParseTree(indent); // p is the last Statement
	}

	TypeVal typeEval()
	{
		TypeVal statementTypeVal;
		TypeVal sListTypeVal = TypeChecker.typeCorrect;

		SList p = this;
		while ( p.getClass() == MultipleStatement.class )
		{
			statementTypeVal = ((MultipleStatement)p).statement.typeEval();
			if ( statementTypeVal == TypeChecker.typeError )
				sListTypeVal = TypeChecker.typeError;
			p = ((MultipleStatement)p).sList;
		}
		statementTypeVal = ((Statement)p).typeEval(); // p is the last Statement
		if ( statementTypeVal == TypeChecker.typeError )
				sListTypeVal = TypeChecker.typeError;

		return sListTypeVal;
	}
	
	abstract void emitInstructions();
	
	
}