/**/

configuration AccZSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new AccZC() as AccZ;
	components AccZSensorP;

	//This Component is not in the current SENSORBOARD
	//AccXSensorP.AccZ -> AccZ;
	Sensor = AccZSensorP;
	
}
