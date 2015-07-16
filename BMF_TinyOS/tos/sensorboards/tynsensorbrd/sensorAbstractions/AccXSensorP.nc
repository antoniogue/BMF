/**/

module AccXSensorP
{
	uses interface Read<uint16_t> as AccX;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call AccX.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void AccX.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			#if DEBUG == VERBOSE
			printf("AccXSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif
		}
		signal Sensor.readDone( result, data );
	}
	


}