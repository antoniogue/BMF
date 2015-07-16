/**/

configuration LightSensorC
{
  provides interface Sensor;
}
implementation
{
	components new PhotoC() as Light;
	components LightSensorP;


	LightSensorP.Light -> Light;
	Sensor = LightSensorP;
	
}
