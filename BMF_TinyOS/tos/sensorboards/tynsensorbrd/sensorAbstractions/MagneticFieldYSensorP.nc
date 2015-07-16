/**/

module MagneticFieldYSensorP
{
	//This Component is not in the current SENSORBOARD
	//uses interface Read<uint16_t> as MagY;
	provides interface Sensor;
}

implementation
{

	
	command error_t Sensor.read(){
		//This Component is not in the current SENSORBOARD
		//return call MagY.read();

		#if DEBUG == VERBOSE
		printf("MagneticFieldYSensorP - NO MagneticFieldYSensor in this platform\n");
		printfflush(); 
		#endif

		return FAIL;
	}

	/*
	* Here we have data to equalize from Sensor
	* Since this Component is not in the current SENSORBOARD, this method has to be commented
	*/
	/*event void MagY.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
		}
		signal Sensor.readDone( result, data );
	}*/
	


}