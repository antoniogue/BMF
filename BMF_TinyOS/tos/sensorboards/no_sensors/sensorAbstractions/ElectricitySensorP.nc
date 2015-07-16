/**/

module ElectricitySensorP
{
	//This Component is not in the current SENSORBOARD
	//uses interface Read<uint16_t> as Electricity;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		//This Component is not in the current SENSORBOARD
		//return call Electricity.read();
		#if DEBUG == VERBOSE
		printf("ElectricitySensorP - NO ElectricitySensor in this platform\n");
		printfflush(); 
		#endif
		return FAIL;
	}

	/*
	* Here we have data to equalize from Sensor
	* Since this Component is not in the current SENSORBOARD, this method has to be commented
	*/
	/*event void Electricity.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
		}
		signal Sensor.readDone( result, data );
	}*/
	


}
