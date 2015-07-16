/**/

module InternalVoltageSensorP
{
	//This Component is not in the current SENSORBOARD
	//uses interface Read<uint16_t> as Voltage;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		//This Component is not in the current SENSORBOARD
		//return call Voltage.read();
		#if DEBUG == VERBOSE
		printf("InternalVoltageSensorP - NO InternalVoltageSensor in this platform\n");
		printfflush(); 
		#endif
		return FAIL;
	}

	/*
	* Here we have data to equalize from Sensor
	* Since this Component is not in the current SENSORBOARD, this method has to be commented
	*/
	/*event void Voltage.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			
			#if DEBUG == VERBOSE
			printf("InternalVoltageSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif

		}
		signal Sensor.readDone( result, data );
	}*/
	


}