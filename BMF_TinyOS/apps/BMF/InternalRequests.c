
#ifndef INTERNAL_REQUESTS
#define INTERNAL_REQUESTS

request_t internal_request;

/**
*
*/
request_t* InternalRequests_makeActuationRequest(uint8_t actuatorType, uint16_t actuatorParams){

	internal_request.requestID = INTERNAL_REQUEST_REQUEST_ID;
	internal_request.periodValue = 0x0;
	internal_request.action = ACTION_ACTUATING;
	internal_request.sensor_actuatorType = actuatorType;
	internal_request.actuatorParams = actuatorParams;
	
	return &internal_request;
}


#endif //INTERNAL_REQUESTS