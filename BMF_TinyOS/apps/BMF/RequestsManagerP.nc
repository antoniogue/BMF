/**/

#include "Utils.c"


module RequestsManagerP
{
	uses {
		interface Boot;
		interface Timer<TMilli> as Timers[uint8_t idTimer];
		interface Timer<TMilli> as LifetimeTimers[uint8_t idLifetimeTimer];
		
	}
  
	provides interface RequestsManager;

}

implementation
{
	
	/*
	*  Since Timers and myRequests have the same size, Timer in position "i" 
	*  will match with the request in position "i".
	*/
	request_t myRequests[MAX_REQUESTS];
	uint8_t myRequestsSize = 0;
	
	
	void printRequest(request_t* req);
	
	
	/**
	*
	*/	
	event void Boot.booted() {
		
		
		uint8_t i = 0;
		for(;i<MAX_REQUESTS;i++){
			myRequests[i].requestID = 0;
		}
		
		
		
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - Request Manager booted()\n",__FILE__ ,__LINE__);
		printfflush(); 
		#endif
	}


	/**
	*
	*/
	command error_t RequestsManager.startPeriodic(request_t* req, uint32_t periodMilli){
		
		uint8_t i;
		//uint8_t freePlace;
		
		// first of all, let's see if we have free space to manage the request
		// if there is no space, let's return "FAIL"
		if(myRequestsSize == MAX_REQUESTS) return FAIL;
		
		// if we have space to manage the request, let's allocate it for the request
		for(i=0; i<MAX_REQUESTS; i++){
			if(myRequests[i].requestID == 0) break; // out of the FOR the free place will be in "i"
		}
		
		if(i == MAX_REQUESTS) return FAIL;
		
		memcpy(&myRequests[i], req, sizeof(request_t));
		
		// Let's inizialize the Timer corresponding to the "i" position
		//call Timers.startPeriodicAt[i](TOS_NODE_ID*DISALIGNMENT_FACTOR, periodMilli);  
		call Timers.startPeriodic[i](periodMilli);  
		
		// Let's inizialize the corresponding LifetimeTimer, if it has a value.
		if(myRequests[i].lifetimeValue != 0){
			// at the lifetime we add 900 ms to allow the last request to execute
			//call LifetimeTimers.startOneShotAt[i](TOS_NODE_ID*DISALIGNMENT_FACTOR, (Utils_getMultiplierFromTimescale(myRequests[i].lifetimeTimescale) * myRequests[i].lifetimeValue) +900);
			call LifetimeTimers.startOneShot[i]((Utils_getMultiplierFromTimescale(myRequests[i].lifetimeTimescale) * myRequests[i].lifetimeValue) + 900);
		}
		
		
		myRequestsSize++;
		
		//printRequest(&myRequests[0]);
		//printRequest(req);
		
		return SUCCESS;
	}
	



	/**
	*
	*/
	command bool RequestsManager.isRunning(request_t* req){
		
		uint8_t i;
		
		#if DEBUG == VERBOSE
		printf("RequestsManager.isRunning -  myRequestsSize = %d\n",myRequestsSize);
		printfflush(); 
		#endif
		
		if(myRequestsSize == 0) return FALSE;
		
		for(i=0; i<MAX_REQUESTS; i++){
			if(myRequests[i].requestID == req->requestID){
				
				return TRUE;
				
			}
		}
		return FALSE;
		
	}



	/**
	*
	*/
	command error_t RequestsManager.stop(request_t* req){
		
		uint8_t i;
		
		#if DEBUG == VERBOSE
		printf("RequestsManager.stop request %d\n",req->requestID);
		printfflush(); 
		#endif
		
		if(myRequestsSize == 0) return FAIL;
		
		for(i=0; i<MAX_REQUESTS; i++){
			if(myRequests[i].requestID == req->requestID){
				
				myRequests[i].requestID = 0; // I know a place is busy if the requestID != 0
				// when a request in the pool is found, reset the corresponding Timer and LifetimeTimer
				call Timers.stop[i](); 
				call LifetimeTimers.stop[i](); 
				
				myRequestsSize--;
				
				#if DEBUG == VERBOSE
				printf("RequestsManager.stop request STOPPED %d\n",req->requestID);
				printfflush(); 
				#endif

				return SUCCESS;
				
			}
		}
		return FAIL;
		
	}

	/**
	*
	*/
	event void Timers.fired[uint8_t idTimer]() {
		#if DEBUG == VERBOSE
		printf("RequestsManager.Timer %d fired \n",idTimer);
		printfflush(); 
		#endif
		
		signal RequestsManager.fired(&myRequests[idTimer]);
	}
	
	
	/**
	* When a LifetimeTimer fires, a request has to be descheduled from the queue and a timer in Timers
	* has to be reset
	*/
	event void LifetimeTimers.fired[uint8_t idLifetimeTimer]() {
		#if DEBUG == VERBOSE
		printf("LifetimeTimer %d fired \n",idLifetimeTimer);
		printfflush(); 
		#endif
		
		if(myRequests[idLifetimeTimer].requestID != 0){
			myRequests[idLifetimeTimer].requestID = 0;
			call Timers.stop[idLifetimeTimer]();
		}
		
    }

	command error_t RequestsManager.resetRequestsManager(){
		uint8_t i = 0;
		myRequestsSize = 0;
		for(;i<MAX_REQUESTS;i++){
			myRequests[i].requestID = 0;
			call Timers.stop[i]();
			call LifetimeTimers.stop[i]();
		}
		return SUCCESS;
	}






	/**
	* These commands are because of parametric interface
	* These commands are called when there is no explicit implementation
	*/
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	void timerInvocationError(uint8_t idTimer){
		#if DEBUG == VERBOSE
		printf("ERROR to call the Timer/LifetimeTimer with id=%d - This Timer does not exist!\n", idTimer);
		printfflush(); 
		#endif
	}
	default command void Timers.startPeriodic[uint8_t idTimer](uint32_t dt) { timerInvocationError(idTimer); }
	default command void Timers.startOneShot[uint8_t idTimer](uint32_t dt) { timerInvocationError(idTimer); }
	default command void Timers.startPeriodicAt[uint8_t idTimer]( uint32_t t0, uint32_t dt ){ timerInvocationError(idTimer); }
	default command void Timers.startOneShotAt[uint8_t idTimer]( uint32_t t0, uint32_t dt ){ timerInvocationError(idTimer); }
	default command void Timers.stop[uint8_t idTimer]() { timerInvocationError(idTimer); }
	default command void LifetimeTimers.startPeriodic[uint8_t idLifetimeTimer](uint32_t dt) { timerInvocationError(idLifetimeTimer); }
	default command void LifetimeTimers.startOneShot[uint8_t idLifetimeTimer](uint32_t dt) { timerInvocationError(idLifetimeTimer); }
	default command void LifetimeTimers.startPeriodicAt[uint8_t idLifetimeTimer]( uint32_t t0, uint32_t dt ){ timerInvocationError(idLifetimeTimer); }
	default command void LifetimeTimers.startOneShotAt[uint8_t idLifetimeTimer]( uint32_t t0, uint32_t dt ){ timerInvocationError(idLifetimeTimer); }
	default command void LifetimeTimers.stop[uint8_t idLifetimeTimer]() { timerInvocationError(idLifetimeTimer); }
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}


