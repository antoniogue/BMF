/**/

module LightSensorP
{
	uses interface Read<uint16_t> as Light;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call Light.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void Light.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			#if DEBUG == VERBOSE
			printf("LightSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif
		}
		signal Sensor.readDone( result, data );
	}
	


}