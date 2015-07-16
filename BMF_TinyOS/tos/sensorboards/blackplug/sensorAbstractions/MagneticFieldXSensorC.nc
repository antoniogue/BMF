/**/

configuration MagneticFieldXSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new SBT80_ADCconfigC() as MagX;
	components MagneticFieldXSensorP;
	
	//This Component is not in the current SENSORBOARD
	//MagneticFieldXSensorP.MagX -> MagX;
	Sensor = MagneticFieldXSensorP;
	
}
