/**/

configuration AccZSensorC
{
  provides interface Sensor;
}
implementation
{
	components new AccelZC() as AccZ;
	components AccZSensorP;


	AccZSensorP.AccZ -> AccZ;
	Sensor = AccZSensorP;
	
}
