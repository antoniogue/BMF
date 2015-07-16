/**/

module ElectricitySensorP
{

	uses interface ACMeterNEW as Electricity;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call Electricity.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void Electricity.readDone(uint32_t data) {
		signal Sensor.readDone( SUCCESS, data );
	}
	


}
