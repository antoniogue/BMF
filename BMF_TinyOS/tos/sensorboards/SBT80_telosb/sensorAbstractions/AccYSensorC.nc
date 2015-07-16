/**/

configuration AccYSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SBT80_ADCconfigC() as AccY;
	components AccYSensorP;


	// These component are needed by SBT80 platform
	components HplMsp430GeneralIOC;
	components MainC;
	AccYSensorP.SBcontrol -> HplMsp430GeneralIOC.Port23;
	AccYSensorP.SBswitch  -> HplMsp430GeneralIOC.Port26;
	AccYSensorP.Boot -> MainC;
	

	AccYSensorP.AccY -> AccY.ReadADC7;
	Sensor = AccYSensorP;
	
}
