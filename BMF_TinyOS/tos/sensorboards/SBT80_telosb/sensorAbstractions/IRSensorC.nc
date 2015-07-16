/**/

configuration IRSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SBT80_ADCconfigC() as IR;
	components IRSensorP;


	// These component are needed by SBT80 platform
	components HplMsp430GeneralIOC;
	components MainC;
	IRSensorP.SBcontrol -> HplMsp430GeneralIOC.Port23;
	IRSensorP.SBswitch  -> HplMsp430GeneralIOC.Port26;
	IRSensorP.Boot -> MainC;


	IRSensorP.IR -> IR.ReadADC0;
	Sensor = IRSensorP;
	
}

