
#ifndef PACKETS_MANAGER
#define PACKETS_MANAGER

#include "BMF.h"
#include "Utils.c"

packet_to_send_t pktBuilt;

uint8_t newPacketToSend[PKT_MAX_LENGTH];
uint8_t newPacketToSendLength=0;

uint8_t* PM_payload;
uint8_t PM_payloadIndex = 1;
	

header_t PM_header;
	// PacketsManager_first packet fields:
	/*
	uint8_t lastPacketType;
	uint8_t senderID;
	uint8_t addresseeType; 
	uint8_t howManyGroups; //group case
	uint8_t isNotGroup[4]; //group case
	uint8_t destGroupIDs[4]; //group case
	uint8_t associativeRules[3]; //group case 
	uint8_t destNodeID; //node case
	*/
	
request_t PM_request;
	// PacketsManager_parseConfigurationPacket fields:
	/*
	uint8_t requestID;
	uint8_t periodTimescale; 
	uint8_t periodValue;
	uint8_t lifetimeTimescale;
	uint8_t lifetimeValue;
	uint8_t action; 
	uint8_t sensor_actuatorType;
	uint16_t actuatorParams; // actuator case
	uint8_t dataType=0; 
	uint8_t syntheticData=0;
	// if dataType == sensedData from here should be all null
	uint8_t thresholdNumber; 
	uint8_t sensorIfThreshold;
	uint8_t thresholdType[2]; 
	uint16_t thresholdValue[2]; 
	uint8_t sensorTypeMoreThreshold[1];
	*/
	
	
membership_t PM_membership;
	// PacketsManager_parseMembershipPacket fields:
	/*
	uint8_t membershipType;
	uint8_t membershipGroups;
	uint8_t groupIDs[8];
	*/
	
	
uint16_t dataPacketCounter = 0;
	
	
	void PacketsManager_newPacketIn(void* packetPayload){
		PM_payload = (uint8_t*)packetPayload;
	}

	
	header_t* PacketsManager_parseHeader(){
		
		uint8_t temp = 0;
		uint8_t i = 0;
		
		PM_payloadIndex = 1;
		
		memcpy(&PM_header.packetType, PM_payload, 1);
		Utils_debugValue("packetType = %d \n", PM_header.packetType);
		
		memcpy(&PM_header.senderID, (PM_payload + PM_payloadIndex), 1);
		PM_payloadIndex++;
		Utils_debugValue("senderID = %d \n", PM_header.senderID);
		
		memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
		PM_payloadIndex++;
		
		PM_header.addresseeType = (temp & 0xC0) >> 6; // 0xC0 = 11000000
		Utils_debugValue("addresseeType = %d \n", PM_header.addresseeType);
		
		if(PM_header.addresseeType == ADDRESSEE_TYPE_NODE){
			memcpy(&PM_header.destNodeID, (PM_payload + PM_payloadIndex), 1);
			PM_payloadIndex++;
			Utils_debugValue("destNodeID = %d \n", PM_header.destNodeID);
		}
		else{ // addresseeType == ADDRESSEE_TYPE_GROUP
			
			PM_header.howManyGroups = (temp & 0x3) + 1; //0x3 = 00000011
			Utils_debugValue("howManyGroups = %d \n", PM_header.howManyGroups);
			
			memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
			PM_payloadIndex++;
			
			PM_header.isNotGroup[0] = (temp & 0x20) >> 5; //0x20 = 00100000
			Utils_debugValue("isNotGroup[0] = %d \n", PM_header.isNotGroup[0]);
			PM_header.destGroupIDs[0] = (temp & 0x1F); // 0x1F = 00011111
			Utils_debugValue("destGroupIDs[0] = %d \n", PM_header.destGroupIDs[0]);
			
			for(i=1; i<PM_header.howManyGroups; i++){
				memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
				PM_payloadIndex++;
				
				PM_header.associativeRules[i-1] =  (temp & 0xC0) >> 6;
				Utils_debugValue("associativeRules[i-1] = %d \n", PM_header.associativeRules[i-1]);
				PM_header.isNotGroup[i] = (temp & 0x20) >> 5;
				Utils_debugValue("isNotGroup[i] = %d \n", PM_header.isNotGroup[i]);
				PM_header.destGroupIDs[i] = (temp & 0x1F);
				Utils_debugValue("destGroupIDs[i] = %d \n", PM_header.destGroupIDs[i]);
			}
			
		}
		return &PM_header;
		
	}
	
	
	request_t* PacketsManager_parseConfigurationPacket(uint8_t configType){
		
		uint8_t temp = 0;
		uint8_t i = 0;
		
		#if DEBUG != NO_DEBUG
		printf("PacketsManager_parseConfigurationPacket: CONFIGURATION RECEIVED!\n");
		printfflush();
		#endif
		
		memcpy(&PM_request.requestID, (PM_payload + PM_payloadIndex), 1);
		PM_payloadIndex++;
		#if DEBUG == VERBOSE
		Utils_debugValue("requestID = %d \n", PM_request.requestID);
		#endif
		
		// if it's an unschedule type, it's enough to know the requestID
		if(configType == PKT_TYPE_CONFIGURATION_UNSCHEDULE){ 
			return &PM_request; 
			PM_request.periodValue = 0x0;
		}
		
		memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
		PM_payloadIndex++;
		
		PM_request.periodTimescale = (temp & 0xC0) >> 6;
		#if DEBUG == VERBOSE
		Utils_debugValue("periodTimescale = %d \n", PM_request.periodTimescale);
		#endif
		PM_request.periodValue = (temp & 0x3F);
		#if DEBUG == VERBOSE
		Utils_debugValue("periodValue = %d \n", PM_request.periodValue);
		#endif
		
		memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
		PM_payloadIndex++;
		
		PM_request.lifetimeTimescale = (temp & 0xC0) >> 6;
		#if DEBUG == VERBOSE
		Utils_debugValue("lifetimeTimescale = %d \n", PM_request.lifetimeTimescale);
		#endif
		PM_request.lifetimeValue = (temp & 0x3F);
		#if DEBUG == VERBOSE
		Utils_debugValue("lifetimeValue = %d \n", PM_request.lifetimeValue);
		#endif
		
		memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
		PM_payloadIndex++;
		
		PM_request.action = (temp & 0xC0) >> 6;
		#if DEBUG == VERBOSE
		Utils_debugValue("action = %d \n", PM_request.action);
		#endif
		PM_request.sensor_actuatorType = (temp & 0x3F);
		#if DEBUG == VERBOSE
		Utils_debugValue("sensor_actuatorType = %d \n", PM_request.sensor_actuatorType);
		#endif
		
		if(PM_request.action == ACTION_ACTUATING){
			
			memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
			PM_payloadIndex++;
			
			PM_request.actuatorParams = temp;
			memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
			PM_payloadIndex++;
			
			PM_request.actuatorParams = (PM_request.actuatorParams << 8 ) | temp;
			
			#if DEBUG == VERBOSE
			Utils_debugValue("actuatorParams = %d \n", PM_request.actuatorParams);
			#endif
		}
		else{ // action == ACTION_SENSING
			memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
			PM_payloadIndex++;
			
			PM_request.dataType = (temp & 0xC0) >> 6;
			#if DEBUG == VERBOSE
			Utils_debugValue("dataType = %d \n", PM_request.dataType);
			#endif
			PM_request.syntheticData = (temp & 0x3F);
			#if DEBUG == VERBOSE
			Utils_debugValue("syntheticData = %d \n", PM_request.syntheticData);
			#endif
			
			if(PM_request.dataType == DATA_TYPE_THRESHOLD_NOTIFICATION){
				memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
				PM_payloadIndex++;
				
				PM_request.thresholdNumber = 1 + ((temp & 0xC0) >> 6);
				#if DEBUG == VERBOSE
				Utils_debugValue("thresholdNumber = %d \n", PM_request.thresholdNumber);
				#endif
				PM_request.sensorIfThreshold = (temp & 0x3F); 
				#if DEBUG == VERBOSE
				Utils_debugValue("sensorIfThreshold = %d \n", PM_request.sensorIfThreshold);
				#endif
				memcpy(&PM_request.thresholdType[0] , (PM_payload + PM_payloadIndex), 1);
				PM_payloadIndex++;
				#if DEBUG == VERBOSE
				Utils_debugValue("thresholdType[0] = %d \n", PM_request.thresholdType[0]);
				#endif
				
				memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
				PM_payloadIndex++;
				
				PM_request.thresholdValue[0] = temp;
				memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
				PM_payloadIndex++;
				
				PM_request.thresholdValue[0] = (PM_request.thresholdValue[0] << 8 ) | temp;
				#if DEBUG == VERBOSE
				Utils_debugValue("thresholdValue[0] = %d \n", PM_request.thresholdValue[0]);
				#endif
				
				
				for(i=1; i<PM_request.thresholdNumber; i++){
					memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
					PM_payloadIndex++;
					
					PM_request.sensorTypeMoreThreshold[i-1] = (temp & 0xFC) >> 2; // 0xFC = 11111100
					#if DEBUG == VERBOSE
					Utils_debugValue("sensorTypeMoreThreshold[i-1] = %d \n", PM_request.sensorTypeMoreThreshold[i-1]);
					#endif
				
					PM_request.thresholdType[i] = (temp & 0x3); // 0x3 = 00000011
					#if DEBUG == VERBOSE
					Utils_debugValue("thresholdType[i] = %u \n", PM_request.thresholdType[i]);
					#endif
					
					memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
					PM_payloadIndex++;
					
					PM_request.thresholdValue[i] = temp;
					memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
					PM_payloadIndex++;
					
					PM_request.thresholdValue[i] = (PM_request.thresholdValue[i] << 8 ) | temp;
					#if DEBUG == VERBOSE
					Utils_debugValue("thresholdValue[i] = %u \n", PM_request.thresholdValue[i]);
					#endif
				}
				
			}
			//else if(dataType == DATA_TYPE_SENSED_DATA) --> packet end
		}
		
		return &PM_request;
	}

	
	membership_t* PacketsManager_parseMembershipPacket(){

		uint8_t temp = 0;
		uint8_t i = 0;
		
		#if DEBUG != NO_DEBUG
		printf("PacketsManager_parseMembershipPacket: MEMBERSHIP RECEIVED!\n");
		printfflush();
		#endif
		
		memcpy(&temp, (PM_payload + PM_payloadIndex), 1);
		PM_payloadIndex++;
		
		PM_membership.membershipType = (temp & 0xC0) >> 6;
		#if DEBUG == VERBOSE
		Utils_debugValue("membershipType = %d \n", PM_membership.membershipType);
		#endif
		
		if(PM_membership.membershipType == MEMBERSHIP_TYPE_RESET) return &PM_membership;
		
		PM_membership.membershipGroups = 1 + (temp & 0x7);
		#if DEBUG == VERBOSE
		Utils_debugValue("membershipGroups = %d \n", PM_membership.membershipGroups);
		#endif
		
		for(i=0; i<PM_membership.membershipGroups; i++){
			memcpy(&PM_membership.groupIDs[i], (PM_payload + PM_payloadIndex), 1);
			PM_payloadIndex++;
			#if DEBUG == VERBOSE
			printf("groupIDs[%d] = %d \n", i, PM_membership.groupIDs[i]);
			printfflush();
			#endif
		}
		
		return &PM_membership;
		
	}

	
	
	
	
	packet_to_send_t* fillAndReturnPacket(){
		pktBuilt.array = newPacketToSend;
		pktBuilt.arrayLength = newPacketToSendLength;
		return &pktBuilt;
	}
	
	packet_to_send_t* PacketsManager_getNewNodePacket(){
		
		uint8_t howManyFunctionsAvailable = 0;
		uint8_t howManyFunctionsPosition = 0;
		
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - Preparing New Node Packet\n", __FILE__ ,__LINE__);
		printfflush(); 
		#endif

		newPacketToSendLength=0;
		newPacketToSend[newPacketToSendLength++] = PKT_TYPE_NEW_NODE;
		newPacketToSend[newPacketToSendLength++] = TOS_NODE_ID;
		newPacketToSend[newPacketToSendLength++] = SENSORBOARD;
		
		howManyFunctionsPosition = newPacketToSendLength;
		newPacketToSendLength++;
		
		//Now, we are sending only standard functions set.
		// TO BE IMPROVED!
		#ifdef FUNCTIONS
		newPacketToSend[newPacketToSendLength++] = FUNCTIONS;
		howManyFunctionsAvailable++;
		#endif
		
		
		newPacketToSend[howManyFunctionsPosition] = howManyFunctionsAvailable;
		
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - MY SENSORBOARD CODE = %d\n", __FILE__ ,__LINE__, SENSORBOARD);
		printfflush(); 
		#endif
		
		return fillAndReturnPacket();
	}
	
	packet_to_send_t* PacketsManager_getAckPacket(uint8_t pkt_type, uint8_t param){
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - Preparing ACK Packet\n", __FILE__ ,__LINE__);
		printfflush(); 
		#endif
		
		newPacketToSendLength=0;
		newPacketToSend[newPacketToSendLength++] = PKT_TYPE_ACK;
		newPacketToSend[newPacketToSendLength++] = TOS_NODE_ID;
		newPacketToSend[newPacketToSendLength++] = pkt_type;
		newPacketToSend[newPacketToSendLength++] = param;
		
		return fillAndReturnPacket();
	}
	
	
	void incrementDataPacketCounter(){
		dataPacketCounter = (dataPacketCounter+1) % 0x7FFF; // it's a 15 bits value!
	}
	
	void buildDataPacketHeader(uint8_t dataCount){
		
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - Preparing Data Packet\n", __FILE__ ,__LINE__);
		printfflush(); 
		#endif
		
		newPacketToSendLength=0;
		newPacketToSend[newPacketToSendLength++] = PKT_TYPE_DATA;
		newPacketToSend[newPacketToSendLength++] = dataCount;	// waiting for aggregation.
																// Aggregation will be called by 
																// CommManager->Intercept.forward event
	}
	
	void buildSequencialDataPart(uint8_t request_ID, uint8_t bitsOfResult, void* result){
		
		uint32_t result32bits;
		uint16_t result16bits;
		uint8_t result8bits;
		
		memcpy(&result32bits, result, 4);
		//if(bitsOfResult == RESULT_BITS_16) result16bits = (uint16_t)result32bits;
		
		//if(bitsOfResult == RESULT_BITS_16)	memcpy(&result16bits, result, 2);
		//else memcpy(&result32bits, result, 4);
		
		newPacketToSend[newPacketToSendLength++] = TOS_NODE_ID;
		
		newPacketToSend[newPacketToSendLength++]= (uint8_t)(((dataPacketCounter >> 8) & 0x7F) | (( bitsOfResult & 0x2)<<6)); // regarding bitOfResult, I have to place the bit number 6 in the first position
		newPacketToSend[newPacketToSendLength++]= (uint8_t)(dataPacketCounter & 0xFF);
		
		
		newPacketToSend[newPacketToSendLength++] = (uint8_t) (((request_ID << 1) & 0xFE) | ( bitsOfResult & 0x1)); 
		
		
		if(bitsOfResult == RESULT_BITS_8){
			result8bits = (uint8_t)result32bits;
			newPacketToSend[newPacketToSendLength++]= (uint8_t)(result8bits & 0xFF);
		}
		else if(bitsOfResult == RESULT_BITS_16){
			result16bits = (uint16_t)result32bits;
			newPacketToSend[newPacketToSendLength++]= (uint8_t)((result16bits >> 8) & 0xFF);
			newPacketToSend[newPacketToSendLength++]= (uint8_t)(result16bits & 0xFF);
		}
		else{ // bitsOfResult == RESULT_BITS_32
			newPacketToSend[newPacketToSendLength++]= (uint8_t)((result32bits >> 24) & 0xFF);
			newPacketToSend[newPacketToSendLength++]= (uint8_t)((result32bits >> 16) & 0xFF);
			newPacketToSend[newPacketToSendLength++]= (uint8_t)((result32bits >> 8) & 0xFF);
			newPacketToSend[newPacketToSendLength++]= (uint8_t)(result32bits & 0xFF);
		}
	}
	
	/**
	* This method allows to build a packet with one data of result in the payload. 
	* To aggregate results see PacketsManager_getDataPacketAggregate2 and PacketsManager_getDataPacketAggregate3.
	*/
	packet_to_send_t* PacketsManager_getDataPacket(uint8_t request_ID1, uint8_t bitsOfResult1, void* result1){
		buildDataPacketHeader(0x1);
		buildSequencialDataPart(request_ID1, bitsOfResult1, result1);
		
		incrementDataPacketCounter();
		return fillAndReturnPacket();
	}
		
	/**
	* This method allows to build a packet with 2 data of result in the payload. 
	* To aggregate more results see PacketsManager_getDataPacketAggregate3.
	* To send one result only see PacketsManager_getDataPacket.
	*/
	packet_to_send_t* PacketsManager_getDataPacketAggregate2(uint8_t request_ID1, uint8_t bitsOfResult1, void* result1,
													uint8_t request_ID2, uint8_t bitsOfResult2, void* result2){
		buildDataPacketHeader(0x2);
		buildSequencialDataPart(request_ID1, bitsOfResult1, result1);
		buildSequencialDataPart(request_ID2, bitsOfResult2, result2);
		
		incrementDataPacketCounter();
		return fillAndReturnPacket();
	}
	
	/**
	* This method allows to build a packet with 3 data of result in the payload. 
	* To aggregate 2 results see PacketsManager_getDataPacketAggregate2.
	* To send one result only see PacketsManager_getDataPacket.
	*/
	packet_to_send_t* PacketsManager_getDataPacketAggregate3(uint8_t request_ID1, uint8_t bitsOfResult1, void* result1,
													uint8_t request_ID2, uint8_t bitsOfResult2, void* result2,
													uint8_t request_ID3, uint8_t bitsOfResult3, void* result3){
		buildDataPacketHeader(0x3);
		buildSequencialDataPart(request_ID1, bitsOfResult1, result1);
		buildSequencialDataPart(request_ID2, bitsOfResult2, result2);
		buildSequencialDataPart(request_ID3, bitsOfResult3, result3);
		
		incrementDataPacketCounter();
		return fillAndReturnPacket();
	}
	
	packet_to_send_t* PacketsManager_getDataPacketPassingArrays(uint8_t request_ID1, uint8_t *bitsOfResult, 
																void* result, uint8_t resultLength){
		
		//uint8_t bitsOfResult1,bitsOfResult2,bitsOfResult3;
		
		uint32_t* result1 = (uint32_t*)result;
		
		switch(resultLength){
			case 0x1: 
				//memcpy(&bitsOfResult1, bitsOfResult, 1);
				return PacketsManager_getDataPacket(request_ID1, bitsOfResult[0], result1);
			
			case 0x2: 
				//memcpy(&bitsOfResult1, bitsOfResult[0], 1);
				//memcpy(&bitsOfResult2, bitsOfResult[1], 1);
				return PacketsManager_getDataPacketAggregate2(request_ID1, bitsOfResult[0], result1,
													request_ID1, bitsOfResult[1], result1+1);
			
			case 0x3: 
				//memcpy(&bitsOfResult1, bitsOfResult[0], 1);
				//memcpy(&bitsOfResult2, bitsOfResult[1], 1);
				//memcpy(&bitsOfResult3, bitsOfResult[2], 1);
				return PacketsManager_getDataPacketAggregate3(request_ID1, bitsOfResult[0], result1,
													request_ID1, bitsOfResult[1], result1+1,
													request_ID1, bitsOfResult[2], result1+2);
			
			default: return PacketsManager_getDataPacket(request_ID1, bitsOfResult[0], result1);
		}
		
	}
	
	
	/*
	packet_to_send_t* PacketsManager_getDataPacket_old(uint8_t request_ID, void* result, uint8_t bitsOfResult){
		
		uint32_t result32bits;
		uint16_t result16bits;
		if(bitsOfResult == RESULT_BITS_16)	memcpy(&result16bits, result, 2);
		else memcpy(&result32bits, result, 4);
		
		
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - Preparing Data Packet\n", __FILE__ ,__LINE__);
		printfflush(); 
		#endif
		
		newPacketToSendLength=0;
		newPacketToSend[newPacketToSendLength++] = PKT_TYPE_DATA;
		newPacketToSend[newPacketToSendLength++] = 0x1;	// waiting for aggregation.
															// Aggregation will be called by 
															// CommManager->Intercept.forward event
		newPacketToSend[newPacketToSendLength++] = TOS_NODE_ID;
		
		newPacketToSend[newPacketToSendLength++]= (uint8_t)((dataPacketCounter >> 8) & 0xFF);
		newPacketToSend[newPacketToSendLength++]= (uint8_t)(dataPacketCounter & 0xFF);
		incrementDataPacketCounter();
		
		newPacketToSend[newPacketToSendLength++] = (uint8_t) (((request_ID << 1) & 0xFE) | ( bitsOfResult & 0x1)); 
		
		if(bitsOfResult == RESULT_BITS_16){
			newPacketToSend[newPacketToSendLength++]= (uint8_t)((result16bits >> 8) & 0xFF);
			newPacketToSend[newPacketToSendLength++]= (uint8_t)(result16bits & 0xFF);
		}
		else{
			newPacketToSend[newPacketToSendLength++]= (uint8_t)((result32bits >> 24) & 0xFF);
			newPacketToSend[newPacketToSendLength++]= (uint8_t)((result32bits >> 16) & 0xFF);
			newPacketToSend[newPacketToSendLength++]= (uint8_t)((result32bits >> 8) & 0xFF);
			newPacketToSend[newPacketToSendLength++]= (uint8_t)(result32bits & 0xFF);
		}
		
		return fillAndReturnPacket();
	}*/




#endif //PACKETS_MANAGER