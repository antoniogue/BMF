/**/

module IRSensorP
{
	//This Component is not in the current SENSORBOARD
	//uses interface Read<uint16_t> as IR;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		//This Component is not in the current SENSORBOARD
		//return call IR.read();
		#if DEBUG == VERBOSE
		printf("IRSensorP - NO IRSensor in this platform\n");
		printfflush(); 
		#endif
		return FAIL;
	}

	/*
	* Here we have data to equalize from Sensor
	* Since this Component is not in the current SENSORBOARD, this method has to be commented
	*/
	/*event void IR.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
		}
		signal Sensor.readDone( result, data );
	}*/
	


}