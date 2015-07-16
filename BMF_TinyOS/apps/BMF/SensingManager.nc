/**/

interface SensingManager{

	event void readDone( error_t result, uint8_t sensorType, uint32_t *data );
	
	command error_t read(uint8_t sensorType);
	
}