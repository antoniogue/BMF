/**/

configuration AccYSensorC
{
  provides interface Sensor;
}
implementation
{
	components new AccelYC() as AccY;
	components AccYSensorP;


	AccYSensorP.AccY -> AccY;
	Sensor = AccYSensorP;
	
}
