/**/

configuration LightSensorC
{
  provides interface Sensor;
}
implementation
{
	components new HamamatsuS10871TsrC() as Light;
	components LightSensorP;


	LightSensorP.Light -> Light;
	Sensor = LightSensorP;
	
}
