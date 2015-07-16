/**/

configuration MagneticFieldYSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new SBT80_ADCconfigC() as MagY;
	components MagneticFieldYSensorP;

	//This Component is not in the current SENSORBOARD
	//MagneticFieldXSensorP.MagY -> MagY;
	Sensor = MagneticFieldYSensorP;
	
}
