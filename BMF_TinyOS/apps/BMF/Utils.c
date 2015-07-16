

#ifndef UTILS
#define UTILS

#if DEBUG != NO_DEBUG
#include "printf.h"
#endif

#include "GroupsManager.c"

/**
*
*/
bool Utils_isPacketForMe(header_t *head){

	bool toReturn = FALSE;
	uint8_t i = 0;
	
	if(head->addresseeType==ADDRESSEE_TYPE_NODE) {
		if(head->destNodeID==TOS_NODE_ID || head->destNodeID==ADDRESSEE_BROADCAST) return TRUE;
		else return FALSE;
	}
	else{ //addresseeType==ADDRESSEE_TYPE_GROUP
		if(head->howManyGroups<1) toReturn = FALSE;
		else{
			if(head->isNotGroup[0] == IS_NOT_FIELD_TRUE) toReturn = !GroupsManager_isInMyGroups(head->destGroupIDs[0]);
			else toReturn = GroupsManager_isInMyGroups(head->destGroupIDs[0]);
			
			for(i=1; i<head->howManyGroups; i++){
				if(head->isNotGroup[i] == IS_NOT_FIELD_TRUE){
					if(head->associativeRules[i-1] == ASSOCIATIVE_RULE_AND) 
						toReturn = toReturn && !GroupsManager_isInMyGroups(head->destGroupIDs[i]);
					else  //associativeRules[i-1] == ASSOCIATIVE_RULE_OR
						toReturn = toReturn || !GroupsManager_isInMyGroups(head->destGroupIDs[i]);
				}
				else{ //if(isNotGroup[i] == IS_NOT_FIELD_FALSE)
					if(head->associativeRules[i-1] == ASSOCIATIVE_RULE_AND) 
						toReturn = toReturn && GroupsManager_isInMyGroups(head->destGroupIDs[i]);
					else  //associativeRules[i-1] == ASSOCIATIVE_RULE_OR
						toReturn = toReturn || GroupsManager_isInMyGroups(head->destGroupIDs[i]);
				}
			} // for
		}//else
	}//else
	
	#if DEBUG == VERBOSE
	if(toReturn) printf("Utils_isPacketForMe: The Packet is for me!\n");
	else printf("Utils_isPacketForMe: The Packet is NOT for me!\n");
	printfflush();
	#endif
	
	return toReturn;
}


/**
*
*/
void Utils_debugValue(char* str, uint16_t value){
	#if DEBUG == VERBOSE
	printf(str, value);
	printfflush();
	#endif
}


/**
*
*/
uint32_t Utils_getMultiplierFromTimescale(uint8_t timescale){
	switch(timescale){
		case TIMESCALE_SEC: return TIMESCALE_SEC_MULTIPLIER;
		case TIMESCALE_MIN: return TIMESCALE_MIN_MULTIPLIER;
		case TIMESCALE_HOUR: return TIMESCALE_HOUR_MULTIPLIER;
		case TIMESCALE_DAY: return TIMESCALE_DAY_MULTIPLIER;
		default: return 0x5;
	}
}


/**
*
*/
uint32_t Utils_min(uint32_t a, uint32_t b){
	if(a < b) return a;
	else return b;
}


/**
*
*/
uint32_t Utils_max(uint32_t a, uint32_t b){
	if(a > b) return a;
	else return b;
}


/**
*
*/
void Utils_printRequest(char* str, request_t* req){
	#if DEBUG == VERBOSE
		printf(str);
		printf("requestID = %d \n", req->requestID);
		printf("periodTimescale = %d \n", req->periodTimescale);
		printf("periodValue = %d \n", req->periodValue);
		printf("lifetimeTimescale = %d \n", req->lifetimeTimescale);
		printf("lifetimeValue = %d \n", req->lifetimeValue);
		printfflush(); 
		printf("action = %d \n", req->action);
		printf("sensor_actuatorType = %d \n", req->sensor_actuatorType);
		printf("actuatorParams = %d \n", req->actuatorParams);
		printf("dataType = %d \n", req->dataType);
		printf("syntheticData = %d \n", req->syntheticData);
		printfflush(); 
		printf("thresholdNumber = %d \n", req->thresholdNumber);
		printf("sensorIfThreshold = %d \n", req->sensorIfThreshold);
		printf("thresholdType[0] = %d \n", req->thresholdType[0]);
		printf("thresholdType[1] = %d \n", req->thresholdType[1]);
		printf("thresholdValue[0] = %d \n", req->thresholdValue[0]);
		printfflush(); 
		printf("thresholdValue[1] = %d \n", req->thresholdValue[1]);
		printf("sensorTypeMoreThreshold[0] = %d \n", req->sensorTypeMoreThreshold[0]);
		printf("accumulator[0] = %ld \n", req->accumulator[0]);
		printf("accumulator[1] = %ld \n", req->accumulator[1]);
		printf("accumulator[2] = %ld \n", req->accumulator[2]);
		printf("samplesToCollect = %d \n", req->samplesToCollect);
		printf("samplesCollected = %d \n", req->samplesCollected);
		printfflush(); 
	#endif
}


#endif //UTILS