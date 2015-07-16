
/**/

#warning "*** IN ORDER TO USE BLACKPLUG SENSORBOARD YOU NEED TO SET  CC2420_CHANNEL=18  ***"


#ifndef BLACK_PLUG_AM_TYPE
#define BLACK_PLUG_AM_TYPE 8
#endif



configuration ElectricitySensorC
{
  provides interface Sensor;
}
implementation
{
	components MainC;
	components new AMReceiverC(BLACK_PLUG_AM_TYPE);
	components ActiveMessageC;
	components ElectricitySensorP;
	
	components LedsC;
	
	ElectricitySensorP.Boot -> MainC.Boot;
	ElectricitySensorP.Receive -> AMReceiverC;
	ElectricitySensorP.AMControl -> ActiveMessageC;
	
	ElectricitySensorP.Leds -> LedsC;
	
	Sensor = ElectricitySensorP;
	
}
