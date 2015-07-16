/**/

configuration AccXSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SBT80_ADCconfigC() as AccX;
	components AccXSensorP;


	// These component are needed by SBT80 platform
	components HplMsp430GeneralIOC;
	components MainC;
	AccXSensorP.SBcontrol -> HplMsp430GeneralIOC.Port23;
	AccXSensorP.SBswitch  -> HplMsp430GeneralIOC.Port26;
	AccXSensorP.Boot -> MainC;
	
	
	AccXSensorP.AccX -> AccX.ReadADC6;
	Sensor = AccXSensorP;
	
}
