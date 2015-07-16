/**/

module InternalVoltageSensorP
{
	uses interface Read<uint16_t> as Voltage;
	
	provides interface Sensor;
}

implementation
{

	command error_t Sensor.read(){
		return call Voltage.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void Voltage.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			#if DEBUG == VERBOSE
			printf("InternalVoltageSensor - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif
		}
		signal Sensor.readDone( result, data );
	}
	


}