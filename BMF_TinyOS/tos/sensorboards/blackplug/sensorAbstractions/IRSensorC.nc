/**/

configuration IRSensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new IRC() as IR;
	components IRSensorP;

	//This Component is not in the current SENSORBOARD
	//IRSensorP.IR -> IR;
	Sensor = IRSensorP;
	
}

