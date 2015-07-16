/**/

interface RequestsManager{

	//command error_t startPeriodic(request_t* req);
	command error_t startPeriodic(request_t* req, uint32_t periodMilli);
	
	command error_t stop(request_t* req);
	
	event void fired(request_t* req);
	
	command bool isRunning(request_t* req);
	
	command error_t resetRequestsManager();
	
}