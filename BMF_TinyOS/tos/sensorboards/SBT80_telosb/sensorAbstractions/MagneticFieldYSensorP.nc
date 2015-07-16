/**/

module MagneticFieldYSensorP
{
	uses interface Boot;
	uses interface HplMsp430GeneralIO as SBcontrol;
	uses interface HplMsp430GeneralIO as SBswitch;
	
	uses interface Read<uint16_t> as MagY;
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
	
		call SBswitch.set();  // High = Magnetic
		call SBswitch.makeOutput();
		call SBswitch.selectIOFunc();	
		
		return call MagY.read();
	}

	/*
	* Here we have data to equalize from Sensor
	*/
	event void MagY.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
			#if DEBUG == VERBOSE
			printf("MagneticFieldYSensorP - NO SUCCESS reading operation\n");
			printfflush(); 
			#endif
		}
		signal Sensor.readDone( result, data );
	}
	


}