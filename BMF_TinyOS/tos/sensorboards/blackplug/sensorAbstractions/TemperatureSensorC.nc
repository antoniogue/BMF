/**/

configuration TemperatureSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new TempC() as Temperature;
	components TemperatureSensorP;

	//This Component is not in the current SENSORBOARD
	//TemperatureSensorP.Temperature -> Temperature;
	Sensor = TemperatureSensorP;
	
}
