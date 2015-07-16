/**/

configuration AccXSensorC
{
  provides interface Sensor;
}
implementation
{
	components new AccelXC() as AccX;
	components AccXSensorP;


	AccXSensorP.AccX -> AccX;
	Sensor = AccXSensorP;
	
}
