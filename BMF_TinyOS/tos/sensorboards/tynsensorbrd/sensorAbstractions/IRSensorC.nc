/**/

configuration IRSensorC
{
  provides interface Sensor;
}
implementation
{
	//No IR Component in current SENSORBOARD
	//components new IRC() as IR;
	components IRSensorP;

	//No IR Component in current SENSORBOARD
	//IRSensorP.IR -> IR;
	Sensor = IRSensorP;
	
}

