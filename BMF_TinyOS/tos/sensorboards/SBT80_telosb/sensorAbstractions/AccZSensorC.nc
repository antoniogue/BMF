/**/

configuration AccZSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new AccelZC() as AccZ;
	components AccZSensorP;

	//This Component is not in the current SENSORBOARD
	//AccZrP.AccZ -> AccZ;
	Sensor = AccZSensorP;
	
}
