
class IntType extends PrimitiveType
{
	String getType()
	{
		return "int";
	}
	
	Int_Type typeVal()
	{
		return TypeChecker.int_Type;
	}
	
	int getRangeValue(){
		return 0;
	}
}