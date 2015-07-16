/**/

configuration TemperatureSensorC
{
  provides interface Sensor;
}
implementation
{
	components new TempC() as Temperature;
	components TemperatureSensorP;


	TemperatureSensorP.Temperature -> Temperature;
	Sensor = TemperatureSensorP;
	
}
