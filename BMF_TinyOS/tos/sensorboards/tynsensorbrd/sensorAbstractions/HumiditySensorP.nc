/**/

module HumiditySensorP
{
	//No Humidity Component in current SENSORBOARD
	//uses interface Read<uint16_t> as Humidity;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		//No Humidity Component in current SENSORBOARD
		//return call Humidity.read();
		#if DEBUG == VERBOSE
		printf("HumiditySensorP - NO HumiditySensor in this platform\n");
		printfflush(); 
		#endif
		return FAIL;
	}

	/*
	* Here we have data to equalize from Sensor
	* Since we have No Humidity Component in current SENSORBOARD, this method has to be commented
	*/
	/*event void Humidity.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
		}
		signal Sensor.readDone( result, data );
	}*/
	


}