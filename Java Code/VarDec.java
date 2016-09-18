
class VarDec extends VarDecList
{
	
	Type type;
	IdList idList;

	VarDec(Type t, IdList i)
	{
		type = t;
		idList = i;
	}

	void printParseTree(String indent)
	{
		String indent1 = indent + " ";
	
		IO.displayln(indent + indent.length() + " <var dec>");
		type.printParseTree(indent1);
		idList.printParseTree(indent1);
	}
	
	boolean buildTypeMap()
	{
		
		int increment;
		boolean varDecError = false;

		Valid_Type typeVal = type.typeVal(); // converts "type" to the corresponding "typeVal"
		
		if ( typeVal == null ) // an error found in array index bounds
		{
			return true;
		}

		// associate variables in "idList" with "typeVal" and enter them into type map

		String ident;
		IdList p = idList;
		while ( p.getClass() == MultipleId.class )
		{
			ident = ((MultipleId)p).id.id;
			
			if ( TypeChecker.typeMap.containsKey(ident) )
			{
				varDecError = true;
				IO.displayln("Error: variable "+ident+" already declared");
			}
			else
			{	
				
				TypeChecker.typeMap.put(ident, typeVal);

				if (type.getClass() == ArrayType.class)
					increment = type.getRangeValue();
						
				else 
					increment = 1;
			
				//System.out.println("Base address for "+ident + " is "+TypeChecker.BaseAddress);
				TypeChecker.MemoryMap.put(ident, TypeChecker.BaseAddress );
				TypeChecker.BaseAddress = TypeChecker.BaseAddress + increment;
			
			}
			p = ((MultipleId)p).idList;
		}
		ident = ((SingleId)p).id.id; // p is the last SingleId
		if ( TypeChecker.typeMap.containsKey(ident) )
		{
			varDecError = true;
			IO.displayln("Error: variable "+ident+" already declared");
		}
		else
		{
			TypeChecker.typeMap.put(ident, typeVal);
			
			
			if (type.getClass() == ArrayType.class)
				increment = type.getRangeValue();
					
			else 
				increment = 1;
		
			//System.out.println("Base address for "+ident + " is "+TypeChecker.BaseAddress);
			TypeChecker.MemoryMap.put(ident, TypeChecker.BaseAddress );
			TypeChecker.BaseAddress = TypeChecker.BaseAddress + increment;
			
		}

		return varDecError; 
	}
}