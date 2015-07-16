/**/

#include "Utils.c"


module ProcessingManagerP
{
  uses {
	interface Boot;
	interface RequestsManager;
	interface SensingManager;
	interface ActuatingManager;
	interface FunctionCalculator;
	
  }
  
	provides interface ProcessingManager;
	

}

implementation
{

	uint8_t MAX_REQUESTS_QUEUE = 2 * MAX_REQUESTS;
	uint8_t placesBusy = 0; // the places in the requestQueue which are busy
	request_t *requestsQueue[2 * MAX_REQUESTS];
	uint8_t wait4SensorData[2 * MAX_REQUESTS]; // the positions at 1 will correspond to the requests in the requestsQueue which are waiting for data from sensor
	uint8_t nextPositionToExecute = 0;
	uint8_t nextPositionToEnqueue = 0;
	uint32_t transitionValueToSend;
	
	
	event void Boot.booted() {
		
		//request_t request;
		
		uint8_t i = 0;
		for(;i<MAX_REQUESTS_QUEUE;i++){
			wait4SensorData[i] = 0;
		}
		
/*
		request.requestID = 230;
		request.periodTimescale = TIMESCALE_SEC; 
		request.periodValue=2;
		request.lifetimeTimescale=TIMESCALE_MIN;
		request.lifetimeValue=1;
		request.action=235; 
		request.sensor_actuatorType=236;
		request.actuatorParams=31555; // actuator case
		request.dataType=237; 
		request.syntheticData=238;
		// if dataType == sensedData from here should be all before accumulators null
		request.thresholdNumber=239; 
		request.sensorIfThreshold=240;
		request.thresholdType[0]=241; 
		request.thresholdType[1]=242; 
		request.thresholdValue[0]=31556; 
		request.thresholdValue[1]=31557; 
		request.sensorTypeMoreThreshold[0]=243;
		//uint8_t associatedTimerID;
		///////////////////////////////
		request.accumulator = 0;
		request.samplesToCollect = 0;
		request.samplesCollected = 0;

		Utils_printRequest("*** INITIAL REQUEST \n", &request);
		call ProcessingManager.process(&request);
*/


		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - ProcessingManager booted()\n",__FILE__ ,__LINE__);
		printfflush(); 
		#endif
	}

	/**
	* 
	* A request is in the queue only if it is a sensorRead request
	*/
	task void executeNext(){
	
		request_t* reqToExecute;
		
		if(nextPositionToExecute == nextPositionToEnqueue) return;
		
		reqToExecute = requestsQueue[nextPositionToExecute]; //we'll increment the nextPositionToExecute variable at the end of the task
		
		#if DEBUG == VERBOSE
		Utils_printRequest("Processing-executing...updating following req: \n", reqToExecute);
		#endif
		
		
		// we are sure we have a SensorRead request because the test in prepareToExecute() method
		if(call SensingManager.read(reqToExecute->sensor_actuatorType) == SUCCESS)
			wait4SensorData[nextPositionToExecute] = 1;
		
		
		
		nextPositionToExecute = (nextPositionToExecute + 1) % MAX_REQUESTS_QUEUE;
		
	}

	void prepareToExecute(request_t* req){
		
		// Here we check if we have to sample a sensor
		if((req->action) == ACTION_SENSING){
			
			if(placesBusy == MAX_REQUESTS_QUEUE) return;
			requestsQueue[nextPositionToEnqueue] = req;
			nextPositionToEnqueue = (nextPositionToEnqueue + 1) % MAX_REQUESTS_QUEUE;
			placesBusy++;
		
			post executeNext();	
		}
		else { // (reqToExecute->action) == ACTION_ACTUATING
			call ActuatingManager.actuate(req->sensor_actuatorType, req->actuatorParams);
		}
	}
	
	void release(uint8_t position){
		if(placesBusy == 0) return;
		
		wait4SensorData[position] = 0;
		placesBusy--;
	}
	
	
	void signalNewDataProcessed(uint8_t requestID, uint32_t* result, uint8_t resultLength){
	
		uint8_t i;
		uint8_t bitsOfResult[resultLength];
		uint32_t resultCopy[resultLength];
		
		memcpy(&resultCopy, result, 4*resultLength);
		
		// Let's calculate the bits of result
		for(i=0; i<resultLength; i++){
			if(resultCopy[i] > 0xFFFF || isDynamicLengthSystemActivated == ACTUATION_TYPE_DYNAMIC_LENGHT_PARAM_NO) 
				bitsOfResult[i] = RESULT_BITS_32;
			else if(resultCopy[i] > 0xFF) bitsOfResult[i] = RESULT_BITS_16;
			else bitsOfResult[i] = RESULT_BITS_8;
		}
		
		// check for requestsQueue[i]->accumulator pointer
		signal ProcessingManager.newDataProcessed(requestID, bitsOfResult, result, resultLength);	
	}

	/**
	* Here I've to accumulate the read value in the right request which is in queue.
	* If I sensed enough data, I've to signal the data (calculated or raw) to the BMF component
	* After the management of the sensed data, the place in the queue is empty.
	*/
	event void SensingManager.readDone( error_t result, uint8_t sensorType, uint32_t *dataArray ){
	
		uint8_t i, dataArraySize;
		
		
		
		/*
		
		uint32_t data[3];
		
		memcpy(&data[0], dataArray, 4);
		memcpy(&data[1], dataArray+1, 4);
		memcpy(&data[2], dataArray+2, 4);
		
		*/
		
		
		
		
		if(sensorType == SENSOR_TYPE_RX_SEND_SLEEP_TIME) dataArraySize = 3;
		else dataArraySize = 1;
		
		
		

		
		
		
		
		if(placesBusy==0) return;
		
		if(result == SUCCESS){
		
			for(i=0; i<MAX_REQUESTS_QUEUE; i++){ //I'm looking for the request to the "sensorType" sensor
			
				if( (wait4SensorData[i] == 1) && (requestsQueue[i]->sensor_actuatorType == sensorType)){
					
					// if requestsQueue[i]->samplesCollected == requestsQueue[i]->samplesToCollect then I've to send something
					// or check for alarms!
					// else I just accumulate the current value
					
					
					requestsQueue[i]->samplesCollected++;
					
					if(call FunctionCalculator.calculate( requestsQueue[i]->syntheticData, requestsQueue[i]->accumulator, 
															dataArray, dataArraySize, requestsQueue[i]->samplesCollected, 
															requestsQueue[i]->samplesToCollect) == SUCCESS){
						
						
	
			
						
						
						
						
	/*
						//I've to think about the synthetic data in two points (depends from the calculation type):
						//	every time a new data comes
						//	when a new data has to be sent
						
						for(j=0; j<dataRead; j++){
							switch(requestsQueue[i]->syntheticData){
								case SYNTHETIC_DATA_NO_SYNTHETIC:
									requestsQueue[i]->accumulator[j] = data[j];
									break;
								case SYNTHETIC_DATA_AVERAGE:
									if(requestsQueue[i]->samplesCollected == 0) 
										requestsQueue[i]->accumulator[j] = data[j];
									else 
										requestsQueue[i]->accumulator[j] += data[j];
									break;
								case SYNTHETIC_DATA_MIN:
									if(requestsQueue[i]->samplesCollected == 0) 
										requestsQueue[i]->accumulator[j] = data[j];
									else 
										requestsQueue[i]->accumulator[j] = Utils_min(requestsQueue[i]->accumulator[j], data[j]);
									break;
								case SYNTHETIC_DATA_MAX:
									if(requestsQueue[i]->samplesCollected == 0) 
										requestsQueue[i]->accumulator[j] = data[j];
									else 
										requestsQueue[i]->accumulator[j] = Utils_max(requestsQueue[i]->accumulator[j], data[j]);
									break;
								default: // default is the SYNTHETIC_DATA_NO_SYNTHETIC case
									requestsQueue[i]->accumulator[j] = data[j];
									break;
							}
						}
						requestsQueue[i]->samplesCollected++;
	*/




						
						
						
						if(requestsQueue[i]->samplesCollected == requestsQueue[i]->samplesToCollect){
						
							
							requestsQueue[i]->samplesCollected = 0; // reset the sampleCollected field for the next time
							






	/*
							// last analysis on the computing functions
							for(j=0; j<dataRead; j++){
								switch(requestsQueue[i]->syntheticData){
									case SYNTHETIC_DATA_AVERAGE:
										requestsQueue[i]->accumulator[j] = requestsQueue[i]->accumulator[j] / requestsQueue[i]->samplesToCollect;
										break;
									default: break;
								}
							}
	*/








							
							if(requestsQueue[i]->dataType == DATA_TYPE_SENSED_DATA){ 
								
								signalNewDataProcessed(requestsQueue[i]->requestID, requestsQueue[i]->accumulator, dataArraySize);
								
								
								//FOLLOWING STUFF HAVE BEEN MOVED TO "signalNewDataProcessed" method
								// Let's calculate the bits of result
								//if(requestsQueue[i]->accumulator > 0xFFFF) bitsOfResult = RESULT_BITS_32;
								//else bitsOfResult = RESULT_BITS_16;
								
								// check for requestsQueue[i]->accumulator pointer
								//signal ProcessingManager.newDataProcessed(requestsQueue[i]->requestID, bitsOfResult, &requestsQueue[i]->accumulator);
								
							
							}
							
							
							
//////////////////////////////////////////// THRESHOLD STUFF
#if FUNCTION == STANDARD || FUNCTION == STANDARD_THRESHOLD_ONLY
							else{ // requestsQueue[i]->dataType == DATA_TYPE_THRESHOLD_NOTIFICATION






	// THE DATA_TYPE_THRESHOLD_NOTIFICATION CASE HAS TO BE IMPLEMENTED!!!









	// we are implementing only a single THRESHOLD_TYPE_TRANSITION threshold. 
	// if we cross the threshold line from down to up, we send THRESHOLD_TRANSITION_DOWN_UP; 
	//     from up to down, we send THRESHOLD_TRANSITION_UP_DOWN!

								if(requestsQueue[i]->thresholdNumber == 1 && dataArraySize==1){
									
									if(requestsQueue[i]->thresholdType[0] == THRESHOLD_TYPE_TRANSITION){
										
										//the thresholdValue value is the smallest value bigger than the threshold
										if(requestsQueue[i]->accumulator[1] < requestsQueue[i]->thresholdValue[0] &&
										   requestsQueue[i]->accumulator[0] >= requestsQueue[i]->thresholdValue[0]){
											
											transitionValueToSend = THRESHOLD_TRANSITION_DOWN_UP;
											signalNewDataProcessed(requestsQueue[i]->requestID, &transitionValueToSend, 0x1);
										}
										else if(requestsQueue[i]->accumulator[1] >= requestsQueue[i]->thresholdValue[0] &&
												requestsQueue[i]->accumulator[0] < requestsQueue[i]->thresholdValue[0]){
											
											transitionValueToSend = THRESHOLD_TRANSITION_UP_DOWN;
											signalNewDataProcessed(requestsQueue[i]->requestID, &transitionValueToSend, 0x1);
											
										}
										
										requestsQueue[i]->accumulator[1] = requestsQueue[i]->accumulator[0];
										
										
										
									}
									
								}
								


							}
#endif
								
//////////////////////////////////////////////////////////////////////////////

						}
					
					}// if of FunctionCalculator.calculate() == SUCCESS
					
					
					// I release the resource both if I'm able to calculate the function or not.
					release(i);
					break;
				}
				
				
/*				
data type --> sensed data or threshold notification on sensed data?
synthetic data --> this field says if we want synthetic data or not. If we want synthetic data, here we write the synthetic type
threshold type --> the type of threshold like <; > 
sensor to send if threshold --> if 0 will be sent the calculated data for the threshold check, otherwise data from this sensor will be sent
threshold number (M)--> the number of thresholds to consider before sending something. In the first implementation, this value is no more than 2 but at least 1 (M = (threshold number )+1 )

uint8_t dataType; 
uint8_t syntheticData;
// if dataType == sensedData from here should be all before accumulators null
uint8_t thresholdNumber; 
uint8_t sensorIfThreshold;
uint8_t thresholdType[2]; 
uint16_t thresholdValue[2]; 
uint8_t sensorTypeMoreThreshold[1];
uint32_t accumulator;
uint32_t accumulator2;
uint16_t samplesToCollect;
uint16_t samplesCollected;
*/
			}
		
		}
	
	}
	

	
	/**
	* This command is called when a new Configuration packet arrives.
	* Here the request is processed and 
	*     if is already exists, it is stopped and
	*     if the new one needs a timer it is forwarded to the RequestsManager
	*     otherwise it's executed
	*/
	command error_t ProcessingManager.process(request_t* req, uint8_t requestType){
		
		uint32_t timePerSample;
		uint16_t samplesNeeded;
		
		#if DEBUG == VERBOSE
		printf("ProcessingManager.process Request\n");
		printfflush(); 
		#endif
		
		if(call RequestsManager.isRunning(req)) {
			if(call RequestsManager.stop(req) != SUCCESS)
				return FAIL;
		}
		
		// Here we already UnScheduled the request
		if(requestType == PKT_TYPE_CONFIGURATION_UNSCHEDULE) return SUCCESS;
		
		
		if(req->periodValue != 0){ // PERIODIC REQUEST: the request has to be forwarded to the RequestsManager
			
			#if DEBUG == VERBOSE
			printf("ProcessingManager.process - PERIODIC REQUEST RECEIVED\n");
			printfflush(); 
			#endif
			
			
			if(req->syntheticData != SYNTHETIC_DATA_NO_SYNTHETIC){ // in this case we collect data to have synthetic values on a period
			
				switch(req->periodTimescale){ // Let's set the timePerSample and the total sample to calculate before sending some data
					case TIMESCALE_SEC: // samplesToCollect = periodValue / SECONDS_PER_SAMPLE_TIMESCALE_SEC
						timePerSample = SECONDS_PER_SAMPLE_TIMESCALE_SEC * TIMESCALE_SEC_MULTIPLIER;
						samplesNeeded = (req->periodValue) / SECONDS_PER_SAMPLE_TIMESCALE_SEC;
						break;
					case TIMESCALE_MIN:
						timePerSample = SECONDS_PER_SAMPLE_TIMESCALE_MIN * TIMESCALE_SEC_MULTIPLIER;
						samplesNeeded = (req->periodValue) * (60 / SECONDS_PER_SAMPLE_TIMESCALE_MIN); // req->periodValue has to be converted in seconds
						break;
					case TIMESCALE_HOUR:
						timePerSample = SECONDS_PER_SAMPLE_TIMESCALE_HOUR * TIMESCALE_SEC_MULTIPLIER;
						samplesNeeded = (req->periodValue) * (3600 / SECONDS_PER_SAMPLE_TIMESCALE_HOUR); // req->periodValue has to be converted in seconds
						break;
					case TIMESCALE_DAY:
						// here timePerSample is temporary used as accumulator since we need here a temporary 32 bits variable
						timePerSample = (req->periodValue) * (86400 / SECONDS_PER_SAMPLE_TIMESCALE_DAY); // req->periodValue has to be converted in seconds
						samplesNeeded = (uint16_t)timePerSample;
						timePerSample = SECONDS_PER_SAMPLE_TIMESCALE_DAY * TIMESCALE_SEC_MULTIPLIER;
						break;
					default: return FAIL;
				}
				

			}
			else{ // if we don't need synthetic data, we sample every periodValue time
				timePerSample = Utils_getMultiplierFromTimescale(req->periodTimescale) * req->periodValue;
				samplesNeeded = 1;
			}
			
			
			req->samplesToCollect = samplesNeeded;
			req->samplesCollected = 0;
			req->accumulator[0] = 0;
			req->accumulator[1] = 0;
			req->accumulator[2] = 0;
			return call RequestsManager.startPeriodic(req, timePerSample);
		}
		else{ // the request is ONE_SHOT and it has to be executed
			
			#if DEBUG == VERBOSE
			printf("ProcessingManager.process - ONE-SHOT REQUEST RECEIVED\n");
			printfflush(); 
			#endif
			
			req->samplesToCollect = 1;
			req->samplesCollected = 0;
			req->accumulator[0] = 0;
			req->accumulator[1] = 0;
			req->accumulator[2] = 0;
			
			prepareToExecute(req);
			return SUCCESS;
		}
		
	}
	
	
	/**
	*
	*/
	event void RequestsManager.fired(request_t* req){
		
		prepareToExecute(req);
		
	}
	
	command error_t ProcessingManager.resetProcessingManager(){
		uint8_t i = 0;
		error_t requestsManagerResetResult = call RequestsManager.resetRequestsManager();
		placesBusy = 0; 
		nextPositionToExecute = 0;
		nextPositionToEnqueue = 0;
		for(;i<MAX_REQUESTS_QUEUE;i++){
			wait4SensorData[i] = 0;
		}
		
		return requestsManagerResetResult;
	}

}


