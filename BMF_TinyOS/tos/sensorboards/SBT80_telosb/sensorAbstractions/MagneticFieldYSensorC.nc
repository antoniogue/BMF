/**/

configuration MagneticFieldYSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SBT80_ADCconfigC() as MagY;
	components MagneticFieldYSensorP;


	// These component are needed by SBT80 platform
	components HplMsp430GeneralIOC;
	components MainC;
	MagneticFieldYSensorP.SBcontrol -> HplMsp430GeneralIOC.Port23;
	MagneticFieldYSensorP.SBswitch  -> HplMsp430GeneralIOC.Port26;
	MagneticFieldYSensorP.Boot -> MainC;
	
	
	MagneticFieldYSensorP.MagY -> MagY.ReadADC7;
	Sensor = MagneticFieldYSensorP;
	
}
