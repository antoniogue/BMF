
/**/

configuration ElectricitySensorC
{
  provides interface Sensor;
}
implementation
{
	//This Component is not in the current SENSORBOARD
	//components new ElectricityC() as Electricity;
	components ElectricitySensorP;

	//This Component is not in the current SENSORBOARD
	//ElectricitySensorP.Electricity -> Electricity;
	Sensor = ElectricitySensorP;
	
}
