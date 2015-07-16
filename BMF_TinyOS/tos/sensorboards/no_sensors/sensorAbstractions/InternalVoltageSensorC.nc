/**/

configuration InternalVoltageSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	
	components InternalVoltageSensorP;

	//This Component is not in the current SENSORBOARD
	
	Sensor = InternalVoltageSensorP;
	
}
