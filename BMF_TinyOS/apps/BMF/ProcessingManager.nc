/**/

interface ProcessingManager{

	command error_t process(request_t* req, uint8_t requestType);
	
	command error_t resetProcessingManager();
	
	event void newDataProcessed(uint8_t request_ID, uint8_t* bitsOfResult, void* result, uint8_t resultLength);
	

}