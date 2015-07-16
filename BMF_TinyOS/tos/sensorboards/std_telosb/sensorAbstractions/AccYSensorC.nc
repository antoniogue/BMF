/**/

configuration AccYSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new AccYC() as AccY;
	components AccYSensorP;

	//This Component is not in the current SENSORBOARD
	//AccXSensorP.AccY -> AccY;
	Sensor = AccYSensorP;
	
}
