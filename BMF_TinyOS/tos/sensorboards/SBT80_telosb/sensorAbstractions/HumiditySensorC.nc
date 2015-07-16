/**/

configuration HumiditySensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new HumidityC() as Humidity;
	components HumiditySensorP;

	//This Component is not in the current SENSORBOARD
	//HumiditySensorP.Humidity -> Humidity;
	Sensor = HumiditySensorP;
	
}
