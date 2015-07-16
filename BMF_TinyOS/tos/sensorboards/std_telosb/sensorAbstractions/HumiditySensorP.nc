/**/

module HumiditySensorP
{
	uses interface Read<uint16_t> as Humidity;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call Humidity.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void Humidity.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			#if DEBUG == VERBOSE
			printf("HumiditySensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif
		}
		signal Sensor.readDone( result, data );
	}
	


}