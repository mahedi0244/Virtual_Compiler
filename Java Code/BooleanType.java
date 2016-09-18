
class BooleanType extends PrimitiveType
{
	String getType()
	{
		return "boolean";
	}
	
	Boolean_Type typeVal()
	{
		return TypeChecker.boolean_Type;
	}
	
	int getRangeValue(){
		return 0;
	}
}