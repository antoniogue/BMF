/**/

configuration LightSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SBT80_ADCconfigC() as Light;
	components LightSensorP;

	
	// These component are needed by SBT80 platform
	components HplMsp430GeneralIOC;
	components MainC;
	LightSensorP.SBcontrol -> HplMsp430GeneralIOC.Port23;
	LightSensorP.SBswitch  -> HplMsp430GeneralIOC.Port26;
	LightSensorP.Boot -> MainC;


	LightSensorP.Light -> Light.ReadADC0;
	Sensor = LightSensorP;
	

	
}
