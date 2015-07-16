/**/

#include "PrecompilerRules.c"
#include "BMF.h"


configuration ActuatingManagerC{
	provides interface ActuatingManager;
}
implementation {
	components ActuatingManagerP as AM; 
	components MainC;
	
	ActuatingManager = AM;
	
	AM.Boot -> MainC.Boot;
	
	components LedsC;
	AM.Leds -> LedsC;
}



