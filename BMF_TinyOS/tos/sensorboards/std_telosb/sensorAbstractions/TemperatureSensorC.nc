/**/

configuration TemperatureSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SensirionSht11C() as Temperature;
	components TemperatureSensorP;


	TemperatureSensorP.Temperature -> Temperature.Temperature;
	Sensor = TemperatureSensorP;
	
}
