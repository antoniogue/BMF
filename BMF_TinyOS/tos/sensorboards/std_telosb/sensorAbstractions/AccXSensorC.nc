/**/

configuration AccXSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new AccXC() as AccX;
	components AccXSensorP;

	//This Component is not in the current SENSORBOARD
	//AccXSensorP.AccX -> AccX;
	Sensor = AccXSensorP;
	
}
