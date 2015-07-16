/**/

#include "PrecompilerRules.c"
#include "BMF.h"


configuration ProcessingManagerC{
	provides interface ProcessingManager;
}
implementation {
	components ProcessingManagerP as PM; 
	
	components MainC;
	ProcessingManager = PM;
	PM.Boot -> MainC.Boot;

	components RequestsManagerC;
	PM.RequestsManager -> RequestsManagerC;
	
	components SensingManagerC;
	PM.SensingManager -> SensingManagerC;
	
	components ActuatingManagerC;
	PM.ActuatingManager -> ActuatingManagerC;
	
	components FunctionCalculatorC;
	PM.FunctionCalculator -> FunctionCalculatorC;

}



