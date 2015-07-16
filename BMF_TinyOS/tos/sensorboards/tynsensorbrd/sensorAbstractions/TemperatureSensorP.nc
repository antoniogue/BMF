/**/

module TemperatureSensorP
{
	uses interface Read<uint16_t> as Temperature;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call Temperature.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void Temperature.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			
			#if DEBUG == VERBOSE
			printf("TemperatureSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif

		}
		signal Sensor.readDone( result, data );
	}
	


}