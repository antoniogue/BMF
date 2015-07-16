/**/

module AccYSensorP
{
	uses interface Boot;
	uses interface HplMsp430GeneralIO as SBcontrol;
	uses interface HplMsp430GeneralIO as SBswitch;
	
	uses interface Read<uint16_t> as AccY;
	provides interface Sensor;
}

implementation
{

	event void Boot.booted() {
		#ifndef SBT80_SENSORBOARD_INITED
		#define SBT80_SENSORBOARD_INITED
		/* Wake up the sensor board */
		call SBcontrol.clr();
		call SBcontrol.makeOutput();
		call SBcontrol.selectIOFunc();
		#endif
	}
	
	command error_t Sensor.read(){
	
		call SBswitch.clr();  // Low = Acceleration
		call SBswitch.makeOutput();
		call SBswitch.selectIOFunc();	
	
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