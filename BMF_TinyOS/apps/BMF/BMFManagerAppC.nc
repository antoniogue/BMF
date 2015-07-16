/**/

#include "BMF.h"

configuration BMFManagerAppC{}

implementation {
	
	// Communication Component
	components CommManagerC;
	BMF.CommManager -> CommManagerC;
	
	components BMFManagerC as BMF, MainC;
	
	BMF.Boot -> MainC.Boot;
	
	
	components ProcessingManagerC;
	BMF.ProcessingManager -> ProcessingManagerC;
	

	#if DEBUG != NO_DEBUG
		components PrintfC;
		components SerialStartC;
	#endif	

	
	//App.AMControl -> ActiveMessageC;
	
	//components new TimerMilliC() as TimerSendRawData;
	//BMFC.TimerSendRawData -> TimerSendRawData;
	
	//components new TimerMilliC() as TimerSendEnergy;
	//BMFC.TimerSendEnergy -> TimerSendEnergy;
	
	
	
	//SENSORS STUFF
	//components LightSensorC;
	//BMFC.Light -> LightSensorC;
	//components HumiditySensorC;
	//BMFC.Humidity -> HumiditySensorC;
	//components TemperatureSensorC;
	//BMFC.Temperature -> TemperatureSensorC;
	//components ElectricitySensorC;
	//BMFC.Humidity -> ElectricitySensorC;
	//END SENSORS STUFF
	
	
	// ENERGY STUFF
	//components EnergyC;
	//BMFC.Energy -> EnergyC;
	
}



