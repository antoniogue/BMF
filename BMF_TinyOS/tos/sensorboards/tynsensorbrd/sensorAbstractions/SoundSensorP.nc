/**/

module SoundSensorP
{
	uses interface Read<uint16_t> as Sound;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call Sound.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void Sound.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			
			#if DEBUG == VERBOSE
			printf("SoundSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif
			
		}
		signal Sensor.readDone( result, data );
	}
	


}
