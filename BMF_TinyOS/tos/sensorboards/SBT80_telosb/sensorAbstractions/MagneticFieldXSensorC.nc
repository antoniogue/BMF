/**/

configuration MagneticFieldXSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SBT80_ADCconfigC() as MagX;
	components MagneticFieldXSensorP;


	// These component are needed by SBT80 platform
	components HplMsp430GeneralIOC;
	components MainC;
	MagneticFieldXSensorP.SBcontrol -> HplMsp430GeneralIOC.Port23;
	MagneticFieldXSensorP.SBswitch  -> HplMsp430GeneralIOC.Port26;
	MagneticFieldXSensorP.Boot -> MainC;
	
	
	MagneticFieldXSensorP.MagX -> MagX.ReadADC6;
	Sensor = MagneticFieldXSensorP;
	
}
