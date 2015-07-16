/**/

module AccYSensorP
{
	uses interface Read<uint16_t> as AccY;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call AccY.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void AccY.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			#if DEBUG == VERBOSE
			printf("AccYSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif
		}
		signal Sensor.readDone( result, data );
	}
	


}