/**/

configuration InternalVoltageSensorC
{
  provides interface Sensor;
}
implementation
{
	components new VoltageC() as Voltage;
	components InternalVoltageSensorP;


	InternalVoltageSensorP.Voltage -> Voltage;
	Sensor = InternalVoltageSensorP;
	
}
