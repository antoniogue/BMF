/**/

configuration SoundSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SBT80_ADCconfigC() as Sound;
	components SoundSensorP;


	// These component are needed by SBT80 platform
	components HplMsp430GeneralIOC;
	components MainC;
	SoundSensorP.SBcontrol -> HplMsp430GeneralIOC.Port23;
	SoundSensorP.SBswitch  -> HplMsp430GeneralIOC.Port26;
	SoundSensorP.Boot -> MainC;

	//change from ADC1 to ADC2 as per WIEYE datasheet for sound recording
	SoundSensorP.Sound -> Sound.ReadADC2;
	Sensor = SoundSensorP;
	
}

