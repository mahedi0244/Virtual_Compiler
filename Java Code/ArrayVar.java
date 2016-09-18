
class ArrayVar extends Var
{
	Id id;  // Id object containing array name
	ArrayIndex arrayIndex;

	ArrayVar(Id i, ArrayIndex ai)
	{
		id = i;
		arrayIndex = ai;
	}
	
	String getVariable(){
		return id.id;
	}
	
	String getArrayVar(){
		return id.id;
	}
	
	void printParseTree(String indent)
	{
		String indent1 = indent + " ";

		IO.displayln(indent + indent.length() + " <array var>");
		id.printParseTree(indent1);
		arrayIndex.printParseTree(indent1);
	}

	int getDimension()
	{
		Array_Type arrayType = (Array_Type)(TypeChecker.typeMap.get(id.id));
		return arrayType.dimension;
	}

	int[] getRangeList()
	{
		Array_Type arrayType = (Array_Type)(TypeChecker.typeMap.get(id.id));
		return arrayType.rangeList;
	}
	
	TypeVal typeEval()
	{
		TypeVal arrayType;  // will be set to the Array_Type object of this array variable
		TypeVal arrayVarType;  // will be set to the array element type of this array variable
		String arrayName = id.id;

		arrayType = TypeChecker.typeMap.get(arrayName);
		if ( arrayType != null )
		{
			if ( arrayType.getClass() != Array_Type.class )
			{
				IO.displayln("Type Error: "+arrayName+" declared to be non-array type");
				return TypeChecker.typeError;
			}
			else
				arrayVarType = ((Array_Type)arrayType).elemType; // set arrayVarType to the array element type of this array variable

			// perform type checking of index expressions

			TypeVal exprType;
			int i = 1; // count # of index expressions
			EList p = arrayIndex.eList;
			while ( p.getClass() == MultipleExpr.class )
			{
				exprType = ((MultipleExpr)p).expr.typeEval();
				if ( exprType != TypeChecker.int_Type )
				{
					IO.displayln("Type Error: index expression #"+i+ " of array "+arrayName+" does not have int type");
					arrayVarType = TypeChecker.typeError;
				}
				i++;
				p = ((MultipleExpr)p).eList;
			}
			exprType = ((Expr)p).typeEval(); // p is the last Expr
			if ( exprType != TypeChecker.int_Type )
			{
				IO.displayln("Type Error: index expression #"+i+ " of array "+arrayName+" does not have int type");
				arrayVarType = TypeChecker.typeError;
			}
			if ( i != ((Array_Type)arrayType).dimension )
			{
				IO.displayln("Type Error: # of index expressions "+i+" does not match "+
				             "declared dimension "+((Array_Type)arrayType).dimension+ " of array "+arrayName);
				arrayVarType = TypeChecker.typeError;
			}
			
			return arrayVarType;
		}
		else
		{
			IO.displayln("Error: undeclared array variable  "+arrayName);
			return TypeChecker.typeError;
		}
	}
	
	void emitInstructions(){
		
		arrayIndex.emitInstructions();
	}
	
}