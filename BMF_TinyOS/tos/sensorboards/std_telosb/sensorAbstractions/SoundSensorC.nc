
/**/

configuration SoundSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new SoundC() as Sound;
	components SoundSensorP;

	//This Component is not in the current SENSORBOARD
	//IRSensorP.Sound -> Sound;
	Sensor = SoundSensorP;
	
}
