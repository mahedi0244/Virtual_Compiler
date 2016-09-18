
class MultipleVarDec extends VarDecList
{
	VarDec varDec;
	VarDecList varDecList;

	MultipleVarDec(VarDec vd, VarDecList vdl)
	{
		varDec = vd;
		varDecList = vdl;
		
	}

	boolean buildTypeMap()
	{
		boolean b1 = varDec.buildTypeMap(); // b1 = true if there is an error in varDec
		boolean b2 = varDecList.buildTypeMap();  // b2 = true if there is an error in varDecList
		return b1 || b2;
	}
}
