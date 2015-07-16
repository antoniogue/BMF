/**/

configuration HumiditySensorC
{
  provides interface Sensor;
}
implementation
{
	//No Humidity Component in current SENSORBOARD
	//components new HumidityC() as Humidity;
	components HumiditySensorP;

	//No Humidity Component in current SENSORBOARD
	//HumiditySensorP.Humidity -> Humidity;
	Sensor = HumiditySensorP;
	
}
