/**/

configuration LightSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new LightC() as Light;
	components LightSensorP;

	//This Component is not in the current SENSORBOARD
	//ightSensorP.Light -> Light;
	Sensor = LightSensorP;
	
}
