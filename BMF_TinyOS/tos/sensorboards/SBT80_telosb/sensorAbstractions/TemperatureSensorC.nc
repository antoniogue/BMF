/**/

configuration TemperatureSensorC
{
  provides interface Sensor;
}
implementation
{
	components new SBT80_ADCconfigC() as Temperature;
	components TemperatureSensorP;


	// These component are needed by SBT80 platform
	components HplMsp430GeneralIOC;
	components MainC;
	TemperatureSensorP.SBcontrol -> HplMsp430GeneralIOC.Port23;
	TemperatureSensorP.SBswitch  -> HplMsp430GeneralIOC.Port26;
	TemperatureSensorP.Boot -> MainC;


	TemperatureSensorP.Temperature -> Temperature.ReadADC3;
	Sensor = TemperatureSensorP;
	
}
