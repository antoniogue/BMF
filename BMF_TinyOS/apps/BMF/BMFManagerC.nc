/**/

#include "PrecompilerRules.c"
#include "Utils.c"
#include "GroupsManager.c"
#include "PacketsManager.c"
#include "InternalRequests.c" 


module BMFManagerC
{
  uses {
	interface Boot;

	
	interface CommManager;
	
	interface ProcessingManager;
	
	//ENERGY STUFF
	//interface Energy;
	
  }

}

implementation
{
	///// INPUT packet types: /////
	header_t *headerIN;
	request_t *requestIN;
	membership_t *membershipIN;
	///////////////////////////////
	
	
	bool isNodeActive = FALSE; // a node is active when is computing something
	
	

	
	
	
	event void Boot.booted() {
		
		//call Leds.led0Toggle();
		
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - booting...\n",__FILE__ ,__LINE__);
		printfflush(); 
		#endif
		
		//let's set DynamicLenghtSystem to true
		isDynamicLengthSystemActivated = ACTUATION_TYPE_DYNAMIC_LENGHT_PARAM_YES;
		
		call CommManager.start();
		GroupsManager_initializeMyGroups();
		isNodeActive = FALSE;
		
		//#if DEBUG == VERBOSE
		//printf("File: %s - Line:  %d - CommunicationManager.starting...\n", __FILE__ ,__LINE__);	
		//printfflush(); 
		//#endif

	}
	
	event void CommManager.startDone(error_t err){
		//call Leds.led1Toggle();
		call ProcessingManager.process(InternalRequests_makeActuationRequest(ACTUATION_TYPE_LED, ACTUATION_PARAM_LED_1_TOGGLE), PKT_TYPE_CONFIGURATION_SCHEDULE);
		//#warning "Here you can put a call to ProcessingManager 4 DEBUG=LEDS stuff"
		
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - CommunicationManager.startDone\n", __FILE__ ,__LINE__);	printfflush(); 
		#endif
		
		if (TOS_NODE_ID % 500 != 0){
			call CommManager.sendPeriodicPacketToBS(TOS_NODE_ID*DISALIGNMENT_FACTOR, NEW_NODE_PKT_PERIOD, PacketsManager_getNewNodePacket());
		}
	}



/*
	event void TimerSendEnergy.fired() {
		
		uint32_t RxTime, SendTime, SleepTime;
		
		//TOTAENERGY now is equals to radioEnergy
		call Energy.getTotalEnergy(call CommManager.getLastLocalDutyCycle()); // Right now, I don't need to save this value
		
		RxTime = call Energy.getRxTimeMicroSec();
		SendTime = call Energy.getSendTimeMicroSec();
		SleepTime = call Energy.getSleepTimeMicroSec();
		
		call CommManager.sendPacketToBS(PacketsManager_getDataPacketAggregate3(
								SENSOR_TYPE_RX_TIME, RESULT_BITS_32, &RxTime, 
								SENSOR_TYPE_SEND_TIME, RESULT_BITS_32, &SendTime, 
								SENSOR_TYPE_SLEEP_TIME, RESULT_BITS_32, &SleepTime));
								
	}
*/


	
	event void CommManager.messageReceivedFromBS(void* packetPayload) {
		#if DEBUG != NO_DEBUG
		printf("File: %s - Line:  %d - Message Received \n", __FILE__ ,__LINE__);
		printfflush(); 
		#endif
		
		PacketsManager_newPacketIn(packetPayload);
		headerIN = PacketsManager_parseHeader();
		if(! Utils_isPacketForMe(headerIN)) return;
		
		if(!isNodeActive) {
			if(headerIN->packetType == PKT_TYPE_RESET_NODE) return;
			else{
				isNodeActive = TRUE;
				call CommManager.stopPeriodicPacketToBS();
			}
		}
		
		
		switch(headerIN->packetType){
		
			case PKT_TYPE_CONFIGURATION_SCHEDULE: // we received a configuration packet
			case PKT_TYPE_CONFIGURATION_UNSCHEDULE:
				
				requestIN = PacketsManager_parseConfigurationPacket(headerIN->packetType);
				
				// We send the ACK_PKT only if the request is not ONE-SHOT 
				if(requestIN->periodValue != 0 || requestIN->action == ACTION_ACTUATING || headerIN->packetType==PKT_TYPE_CONFIGURATION_UNSCHEDULE) 
					call CommManager.sendPacketToBS(PacketsManager_getAckPacket(headerIN->packetType, requestIN->requestID));
					//call sendAckToBS(headerIN->packetType, requestIN->requestID);
				
				
				
				
				
				
				
				
				//Here there is the call for the duty cycle change
				if(requestIN->action == ACTION_ACTUATING && 
								headerIN->packetType==PKT_TYPE_CONFIGURATION_SCHEDULE &&
								requestIN->sensor_actuatorType == ACTUATION_TYPE_LOW_POWER){
					
					call CommManager.setLocalDutyCycle(requestIN->actuatorParams);
					return;
				}
				
				
				
				
				
				
				
				
				
				//Here is the possibility to activate/deactivate the dynamic lenght packet system
				// regarding data packets from node to BS
				if(requestIN->action == ACTION_ACTUATING && 
								headerIN->packetType==PKT_TYPE_CONFIGURATION_SCHEDULE &&
								requestIN->sensor_actuatorType == ACTUATION_TYPE_DYNAMIC_LENGHT_PACKET){
					
					isDynamicLengthSystemActivated = requestIN->actuatorParams;
					return;
				}
				
				
				
				
				
				// 2012-02 - modifications to send link quality of the nodes and their parent ID
				// Here the BMFManager is intercepting a request for link quality and parent node ID
				if(headerIN->packetType==PKT_TYPE_CONFIGURATION_SCHEDULE  && requestIN->action == ACTION_SENSING 
							&& requestIN->sensor_actuatorType == SENSOR_TYPE_NODE_LINK_QUALITY_PARENT_ID ){ 
					
					call CommManager.sendPeriodicLinkQuality_ParentIDPacketToBS(requestIN->periodValue, requestIN->requestID);
				
					return;
				}
				
				if(headerIN->packetType==PKT_TYPE_CONFIGURATION_UNSCHEDULE  && requestIN->requestID == call CommManager.getNodeLinkQualityParentID_RequestID() 
							&& call CommManager.isNodeLinkQualityParentID_Requested()  ){ 
					
					call CommManager.stopPeriodicPacketToBS();
					return;
				}
				
				// 2012-02 end
				
				
				
				
				
				
				call ProcessingManager.process(requestIN, headerIN->packetType);
				
				/*
				if(action == ACTION_ACTUATING && lastSensorRequested == ACTUATION_TYPE_LOW_POWER){
					
					// THIS IS TO TRY THE LocalDutyCycle CHANGE
					call CommManager.setLocalDutyCycle(actuatorParams);
					
					#if DEBUG == VERBOSE
					printf("NEW DUTY CYCLE\n");
					printfflush();
					//if(call TimerSendRawData.isRunning()){
					//	printf("TimerSendRawData.isRunning\n");
					//	call TimerSendRawData.stop();
					//	call TimerSendRawData.startPeriodic(timerSendDataPeriod);
					//}
					//else printf("TimerSendRawData.isNOTRunning\n");
					//printfflush();
					#endif
				}
				*/
				break;

			case PKT_TYPE_RESET_NODE:
				isNodeActive = FALSE;
				
				//let's set DynamicLenghtSystem to true
				isDynamicLengthSystemActivated = ACTUATION_TYPE_DYNAMIC_LENGHT_PARAM_YES;
		
				GroupsManager_initializeMyGroups();
				call ProcessingManager.resetProcessingManager();
				call CommManager.resetCommManager();
				call CommManager.sendPeriodicPacketToBS(TOS_NODE_ID*DISALIGNMENT_FACTOR, NEW_NODE_PKT_PERIOD, PacketsManager_getNewNodePacket());
				break;
				
			case PKT_TYPE_MEMBERSHIP: 
				membershipIN = PacketsManager_parseMembershipPacket();
				GroupsManager_processMembership(membershipIN);
				call CommManager.sendPacketToBS(PacketsManager_getAckPacket(headerIN->packetType, membershipIN->membershipGroups));
				break;
			
			default: 
				#if DEBUG != NO_DEBUG
				printf("UNKNOWN PACKET RECEIVED!\n");
				printfflush();
				#endif
				break;
			
		}
	}

	
	event void ProcessingManager.newDataProcessed(uint8_t request_ID, uint8_t* bitsOfResult, void* result, uint8_t resultLength){
		#if DEBUG != NO_DEBUG
			uint32_t result32bits;
			uint8_t i;
			uint32_t* result32 = (uint32_t*)result;
			
			for(i=0; i<resultLength; i++){
				
				memcpy(&result32bits, result32+i, 4);
				
				printf("ProcessingManager.newDataProcessed - request=%d - result[%d]=", request_ID, i);
				if(bitsOfResult[i] == RESULT_BITS_8){
				
					
					printf("%ld - 8bits\n", result32bits);
				}else if(bitsOfResult[i] == RESULT_BITS_16){
				
					
					printf("%ld - 16bits\n", result32bits);
				}else{ // bitsOfResult == RESULT_BITS_32
					printf("%ld - 32 bits\n", result32bits);
				}
				printfflush(); 
			}
		#endif
		
		call CommManager.sendPacketToBS(PacketsManager_getDataPacketPassingArrays(request_ID, bitsOfResult, result, resultLength));
	}
  
  
	/*event void Energy.scaleVariation(uint16_t valRadio, uint16_t valMcu, uint16_t valLed, uint16_t valTotal){
		//scale = valTotal;
		//scale = valRadio;
		//if(scale==10) scaleChanged = TRUE;
	}*/
	

}

