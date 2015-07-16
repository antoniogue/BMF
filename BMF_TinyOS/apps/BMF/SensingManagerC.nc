/**/

#include "PrecompilerRules.c"
#include "BMF.h"


configuration SensingManagerC{
	provides interface SensingManager;
}
implementation {
	components SensingManagerP as SM; 
	components MainC;
	
	SensingManager = SM;
	SM.Boot -> MainC.Boot;
	
	
	components AccXSensorC;
	SM.AccX -> AccXSensorC;

	components AccYSensorC;
	SM.AccY -> AccYSensorC;

	components AccZSensorC;
	SM.AccZ -> AccZSensorC;

	components ElectricitySensorC;
	SM.Electricity -> ElectricitySensorC;

	components HumiditySensorC;
	SM.Humidity -> HumiditySensorC;

	components IRSensorC;
	SM.IR -> IRSensorC;
	
	components LightSensorC;
	SM.Light -> LightSensorC;

	components MagneticFieldXSensorC;
	SM.MagneticFieldX -> MagneticFieldXSensorC;

	components MagneticFieldYSensorC;
	SM.MagneticFieldY -> MagneticFieldYSensorC;

	components SoundSensorC;
	SM.Sound -> SoundSensorC;

	components TemperatureSensorC;
	SM.Temperature -> TemperatureSensorC;
	
	components InternalVoltageSensorC;
	SM.Voltage -> InternalVoltageSensorC;
	
	// ENERGY STUFF
	#ifdef ENERGY_MONITORING
	components EnergyC;
	SM.Energy -> EnergyC;
	
	components CommManagerC;
	SM.CommManager -> CommManagerC;	
	
	#endif
	

}



