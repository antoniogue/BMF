/**/

configuration SoundSensorC
{
  provides interface Sensor;
}
implementation
{
	components new MicrophoneC() as Sound;
	components SoundSensorP;


	SoundSensorP.Sound -> Sound;
	Sensor = SoundSensorP;
	
}

