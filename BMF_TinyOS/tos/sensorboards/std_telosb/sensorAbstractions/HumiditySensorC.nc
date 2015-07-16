/**/

configuration HumiditySensorC
{
  provides interface Sensor;
}
implementation
{
	components new SensirionSht11C() as Humidity;
	components HumiditySensorP;

	HumiditySensorP.Humidity -> Humidity.Humidity;
	Sensor = HumiditySensorP;
	
}
