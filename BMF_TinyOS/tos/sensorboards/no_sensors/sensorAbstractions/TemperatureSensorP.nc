/**/

module TemperatureSensorP
{
	//This Component is not in the current SENSORBOARD
	//uses interface Read<uint16_t> as Temperature;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		//This Component is not in the current SENSORBOARD
		//return call Temperature.read();
		#if DEBUG == VERBOSE
		printf("TemperatureSensorP - NO TemperatureSensor in this platform\n");
		printfflush(); 
		#endif
		return FAIL;
	}

	/*
	* Here we have data to equalize from Sensor
	* Since this Component is not in the current SENSORBOARD, this method has to be commented
	*/
	/*event void Temperature.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			
			#if DEBUG == VERBOSE
			printf("TemperatureSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif

		}
		signal Sensor.readDone( result, data );
	}*/
	


}