/**/

module ElectricitySensorP
{
	//This Component is not in the current SENSORBOARD
	//uses interface Read<uint16_t> as Electricity;
	
	uses{
		interface Boot;
		interface Receive;
		interface SplitControl as AMControl;
		interface Leds;
	}
	
	provides interface Sensor;
	
}

implementation
{
	uint16_t lastElectricity = 0;
	
	event void Boot.booted() {
		call AMControl.start();
	}

	event void AMControl.startDone(error_t err) {
		if (err != SUCCESS) {
			call AMControl.start();
		}
	}

	event void AMControl.stopDone(error_t err) {
		// do nothing
	}

	event message_t* Receive.receive(message_t* buf, void* payload, uint8_t len) {
		
		uint8_t* payloadInt8 = (uint8_t*)payload;
		
		uint8_t senderID;
		
		uint8_t elect1;
		uint8_t elect2;

		memcpy(&senderID, &(buf->header[8]), 1);
		
		
		if(senderID==PLUG){
			
			call Leds.led2Toggle();
			
			memcpy(&elect1, (payloadInt8+4), 1);
			memcpy(&elect2, (payloadInt8+5), 1);
			
			lastElectricity = ((elect1 & 0xFF)  << 8) | (elect2 & 0xFF);
			
			#if DEBUG == VERBOSE
			printf("New value in ElectricitySensor: %d\n", lastElectricity);
			printfflush(); 
			#endif
		}
		
		return buf;
	}
	

	command error_t Sensor.read(){
		signal Sensor.readDone( SUCCESS, lastElectricity );
		lastElectricity=0;
		return SUCCESS;
	}

	/*
	* Here we have data to equalize from Sensor
	* Since this Component is not in the current SENSORBOARD, this method has to be commented
	*/
	/*event void Electricity.readDone(error_t result, uint16_t data) {
		if (result != SUCCESS)
		{
			data = 0xffff;
		}
		signal Sensor.readDone( result, data );
	}*/
	


}
