/**/

#include "PrecompilerRules.c"
#include "BMF.h"


configuration FunctionCalculatorC{
	provides interface FunctionCalculator;
}
implementation {
	components FunctionCalculatorP; 
	
	//components MainC;
	FunctionCalculator = FunctionCalculatorP.FunctionCalculator;
	//PM.Boot -> MainC.Boot;


}



