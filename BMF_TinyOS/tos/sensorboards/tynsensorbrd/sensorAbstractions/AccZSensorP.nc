/**/

module AccZSensorP
{
	uses interface Read<uint16_t> as AccZ;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call AccZ.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void AccZ.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			#if DEBUG == VERBOSE
			printf("AccZSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif
		}
		signal Sensor.readDone( result, data );
	}
	


}