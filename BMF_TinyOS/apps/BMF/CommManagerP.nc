/**/

#include "BMF.h"
#include "PacketsManager.c"

module CommManagerP
{
  uses {
	//interface Boot;
	interface SplitControl as AMControl; // AMControl.stop() will stop the Radio!
	
	
	
	// COLLECTION STUFF
	interface Send as CollectionSend;			// used by a node which want to send data to the root
	interface Receive as Snoop;					// used when we have to forward data
	interface Receive as CollectionReceive;		// used for reception of data collected by the root of the network
	interface RootControl;						// used to specify which one of the mote is the root
	interface StdControl as CollectionControl;		// used to start the service protocol
	interface Intercept;
	interface CtpInfo as CollectInfo;			// specific to the ctp protocol, used to get some data (parent and quality here)
	// END OF COLLECTION STUFF
	
	
	// DISSEMINATION STUFF
	interface StdControl as DisseminationControl;
	interface DisseminationUpdate<eight_b_msg_t> as DisseminationUpdate8;	// for the root
	interface DisseminationValue<eight_b_msg_t> as DisseminationValue8;		// for the motes
	
	interface DisseminationUpdate<sixteen_b_msg_t> as DisseminationUpdate16;	// for the root
	interface DisseminationValue<sixteen_b_msg_t> as DisseminationValue16;	// for the motes
	
	interface DisseminationUpdate<max_length_msg_t> as DisseminationUpdateMax;	// for the root
	interface DisseminationValue<max_length_msg_t> as DisseminationValueMax;	// for the motes
	// END OF DISSEMINATION STUFF
	
	//UART stuff
#if DEBUG == NO_DEBUG
	interface SplitControl as Serial;
	interface AMSend as SerialSend;
	interface Receive as SerialReceive;
	interface Queue<message_t *> as UARTQueue;
	interface Pool<message_t> as UARTMessagePool;
#endif
	//END of UART stuff
	
	
	// RADIO STUFF for LOW POWER
	interface LowPowerListening;
	
	//Timer for periodic sending
	interface Timer<TMilli> as TimerPeriodicSending;
	
  }
  
	provides interface CommManager;

}

implementation
{

	//variables for radio and serial channel 
	message_t packet;
	bool radioLocked = FALSE;
	//uint16_t lastLocalDutyCycle; // 9000 = 90%; 1000 = 10%;
	
	packet_to_send_t *periodicPacketToSend;

	//UART stuff
#if DEBUG == NO_DEBUG
	uint8_t uartlen;
	message_t uartbuf;
	bool uartbusy=FALSE;
#endif
	
	//Msgs to disseminate
	eight_b_msg_t myMsg8;
	sixteen_b_msg_t myMsg16;
	max_length_msg_t myMsgMax;
	
	// 2012-02 - modifications to send link quality of the nodes and their parent ID
	uint8_t nodeLinkQualityParentID_RequestID=0;
	bool nodeLinkQualityParentID_Requested = FALSE;
	uint16_t lastLinkQuality;
	uint16_t lastParentID;
	// 2012-02 end	
	

	
	//event void Boot.booted() {
		
	//	#if DEBUG == VERBOSE
	//	printf("File: %s - Line:  %d - Boot.booted()\n",__FILE__ ,__LINE__);
	//	printfflush(); 
	//	#endif
	//}

	command error_t CommManager.start(){
		call AMControl.start();
		return SUCCESS;
	}
	
	command void CommManager.resetCommManager(){
		call CommManager.setLocalDutyCycle(INITIAL_DUTY_CYCLE);
		//HERE WE SHOULD RESTART THE SENDING OF NEW_NODE_PACKETS
		
		
		// 2012-02 - modifications to send link quality of the nodes and their parent ID
		nodeLinkQualityParentID_Requested = FALSE;
		call TimerPeriodicSending.stop();
		// 2012-02 end
	}
	
	
	#ifndef TINY_OS_2_1_0
	uint16_t getWakeupInterval(uint16_t ldc){
	/**
	 * This is a measured value of the time in ms the radio is actually on
	 * Measured by Tony O'Donovan
	 */
	#ifndef DUTY_ON_TIME
	#define DUTY_ON_TIME 11 
	#endif
	
	
		uint16_t ldcNew;
		
		if(ldc > 10000) ldcNew = 10000;
		else if(ldc == 0) ldcNew = 1;
		else ldcNew = ldc;
			
		if(ldcNew == 10000) return 0;
		
		return (DUTY_ON_TIME * (10000 - ldcNew)) / ldcNew;
	}
	#endif
	
	void otherInitializations(){
		if (call CollectionControl.start() != SUCCESS){
			#if DEBUG == VERBOSE
			printf("CollectionControl.start()!= SUCCESS\n"); printfflush();
			#endif
		}
		//else{
			//#if DEBUG == VERBOSE
			//printf("CollectionControl.started\n"); printfflush();
			//#endif
		//}
		
		
		if (call DisseminationControl.start() != SUCCESS){
			#if DEBUG == VERBOSE
			printf("DisseminationControl.start()!= SUCCESS\n"); printfflush();
			#endif
		}
		//else{
		//	#if DEBUG == VERBOSE
		//	printf("DisseminationControl.started\n"); printfflush();
		//	#endif
		//}
		
		// This is how to set yourself as a root to the collection layer:
		if (TOS_NODE_ID % 500 == 0){
			call RootControl.setRoot();
			//call Timer.startPeriodic(10100);
			//call Timer.startPeriodic(2500);
			#if DEBUG == VERBOSE
			printf("File: %s - Line:  %d - I'M ROOT\n", __FILE__ ,__LINE__);
			printfflush(); 
			#endif
		}
		else{
			#if DEBUG == VERBOSE
			printf("File: %s - Line:  %d - I'M NOT ROOT\n", __FILE__ ,__LINE__);
			printfflush(); 
			#endif
			lastLocalDutyCycle = INITIAL_DUTY_CYCLE;
			
			#ifdef TINY_OS_2_1_0
				call LowPowerListening.setLocalDutyCycle(lastLocalDutyCycle); // 9000 = 90% duty cycle
				//call CommManager.setLocalDutyCycle(9000);
			#else
				//TinyOS 2.1.1
				call LowPowerListening.setLocalWakeupInterval(getWakeupInterval(lastLocalDutyCycle));
			#endif
		}
		
		//else sendNewNodePacketToBS();
		signal CommManager.startDone(SUCCESS);
	}
	
	
	command uint16_t CommManager.getLastLocalDutyCycle(){
		return lastLocalDutyCycle;
	}
	
	command void CommManager.setLocalDutyCycle(uint16_t newDC){ 

		
		lastLocalDutyCycle = newDC;
			
		#ifdef TINY_OS_2_1_0
			call LowPowerListening.setLocalDutyCycle(lastLocalDutyCycle); // 9000 = 90% duty cycle
			//call CommManager.setLocalDutyCycle(9000);
		#else
			//TinyOS 2.1.1
			call LowPowerListening.setLocalWakeupInterval(getWakeupInterval(lastLocalDutyCycle));
		#endif
		
		
		
		#if DEBUG == VERBOSE
		printf("lastLocalDutyCycle = %d\n", lastLocalDutyCycle);
		printfflush();
		#endif
		
	}
	
	
	event void AMControl.startDone(error_t err) {

		if (err == SUCCESS) {
			#if DEBUG == VERBOSE
			printf("File: %s - Line:  %d - AMControl Started\n", __FILE__ ,__LINE__);
			printfflush(); 
			#endif
			

#if DEBUG == NO_DEBUG
			if (TOS_NODE_ID % 500 == 0){
				if (call Serial.start() != SUCCESS){
					//#if DEBUG == VERBOSE
					//printf("Serial.start() != SUCCESS\n");
					//printfflush();
					//#endif
				}
			}
			else 
#endif
				otherInitializations();
		}
		else {
			call AMControl.start();
		}
	}

#if DEBUG == NO_DEBUG
	event void Serial.startDone(error_t err) {
		if (TOS_NODE_ID % 500 == 0){
			if (err == SUCCESS) {
				//#if DEBUG == VERBOSE
				//printf("File: %s - Line:  %d - Serial Started - MY TOS_ID=%d\n", __FILE__ ,__LINE__, TOS_NODE_ID);
				//printfflush(); 
				//#endif
				
				//TO UNCOMMENT
				otherInitializations();

				//else call Timer.startPeriodic((TOS_NODE_ID*250) + 1000);
				//call TimerSend.startOneShot(200);
			}
			else {
				call Serial.start() ;
			}
		} // end if (TOS_NODE_ID % 500 == 0)
	}
#endif


#if DEBUG == NO_DEBUG
	event void Serial.stopDone(error_t err) { /*do nothing */ }
#endif
	

	event void AMControl.stopDone(error_t err) { /*do nothing */ }

	

	
	
	command void CommManager.sendPacketToBS(packet_to_send_t* pts){
		
		if (radioLocked)	return;
		else { 

			memcpy(call CollectionSend.getPayload(&packet, pts->arrayLength), pts->array, pts->arrayLength);

			if (call CollectionSend.send(&packet, pts->arrayLength) == SUCCESS) {
				radioLocked = TRUE;

				#if DEBUG == VERBOSE
				printf("File: %s - Line:  %d - Sending Something...\n", __FILE__ ,__LINE__);
				printfflush(); 
				#endif
			}

		}
	}
	
	command void CommManager.sendPeriodicPacketToBS(uint32_t startPeriodTo, uint32_t periodNew, packet_to_send_t* pts){
		periodicPacketToSend = pts;
		call CommManager.sendPacketToBS(periodicPacketToSend);
		//call TimerPeriodicSending.startPeriodicAt( startPeriodTo, periodNew );
		call TimerPeriodicSending.startPeriodic( periodNew );
	}
	
	
	
	/** There are two different types of periodic packets that a CommManager can send by itself: 
	    1) the new node packet (which is a packet where the CommManager notify the current node)
		2) the LinkLayer_ParentIDPacket (which is a kind of data packet that send information from the Collection Tree Protocol - ParentID and Link Quality to the parent)
	*/
	command void CommManager.stopPeriodicPacketToBS(){
		
		call TimerPeriodicSending.stop();
		
		// 2012-02 - modifications to send link quality of the nodes and their parent ID
		nodeLinkQualityParentID_Requested = FALSE;
		// 2012-02 end		
		
	}
	
	
	// 2012-02 - modifications to send link quality of the nodes and their parent ID
	void CommManagerP_buildPeriodicPacketToSend_withNodeLinkQualityParentID(){
			call CollectInfo.getEtx(&lastLinkQuality);
			call CollectInfo.getParent(&lastParentID);
			
			periodicPacketToSend = PacketsManager_getDataPacketAggregate2(nodeLinkQualityParentID_RequestID, RESULT_BITS_16, &lastLinkQuality,
																			nodeLinkQualityParentID_RequestID, RESULT_BITS_16, &lastParentID);
	}
	// 2012-02 end
	
	
	event void TimerPeriodicSending.fired(){

		// 2012-02 - modifications to send link quality of the nodes and their parent ID
		if(nodeLinkQualityParentID_Requested == TRUE){
			CommManagerP_buildPeriodicPacketToSend_withNodeLinkQualityParentID();
		}
		// 2012-02 end
		
		call CommManager.sendPacketToBS(periodicPacketToSend);
	}
	
	
	
	// 2012-02 - modifications to send link quality of the nodes and their parent ID
	// This is to schedule a periodic (if periodNew==0 it is one shot) packet to BS with info
	// about the link quality of the node and its parent ID
	command void CommManager.sendPeriodicLinkQuality_ParentIDPacketToBS(uint32_t periodNew, uint8_t requestID){
		

		CommManagerP_buildPeriodicPacketToSend_withNodeLinkQualityParentID();
																		
		call CommManager.sendPacketToBS(periodicPacketToSend);
		
		if(! periodNew==0){ // the request is periodic
			
			nodeLinkQualityParentID_RequestID = requestID;
			nodeLinkQualityParentID_Requested = TRUE;
			call TimerPeriodicSending.startPeriodic( periodNew*TIMESCALE_SEC_MULTIPLIER );
			
			// send one shot and return
		}
		
	}
	
	command uint8_t CommManager.getNodeLinkQualityParentID_RequestID(){
		return nodeLinkQualityParentID_RequestID;
	}
	
	command bool CommManager.isNodeLinkQualityParentID_Requested(){
		return nodeLinkQualityParentID_Requested;
	}
	// 2012-02 end
	

#if DEBUG == NO_DEBUG
	task void uartSendTask() {
		if (call SerialSend.send(0xffff, &uartbuf, uartlen) != SUCCESS) {
			//#if DEBUG == VERBOSE
			//printf("File: %s - Line:  %d - Serial Communication Problems \n", __FILE__ ,__LINE__);
			//printfflush(); 
			//#endif
		} else {
			uartbusy = TRUE;
		}
	}
#endif


#if DEBUG == NO_DEBUG
	event void SerialSend.sendDone(message_t *msg, error_t error) {
		uartbusy = FALSE;
		if (call UARTQueue.empty() == FALSE) {
			// We just finished a UART send, and the uart queue is
			// non-empty.  Let's start a new one.
			message_t *queuemsg = call UARTQueue.dequeue();
			if (queuemsg == NULL) {
				return;
			}
			memcpy(&uartbuf, queuemsg, sizeof(message_t));
			if (call UARTMessagePool.put(queuemsg) != SUCCESS) {
				return;
			}
			post uartSendTask();
		}
	}
#endif


#if DEBUG == NO_DEBUG
	event message_t* SerialReceive.receive(message_t* bufPtr, void* serialPayload, uint8_t len) {
		
		uint8_t* in = (uint8_t*)serialPayload;
		//uint8_t* out;

		if (radioLocked == FALSE) {
			
			if(len <= 8){
				memcpy(myMsg8.array, in, len);
				call DisseminationUpdate8.change(&myMsg8);
			}
			else if(len <= 16){
				memcpy(myMsg16.array, in, len);
				call DisseminationUpdate16.change(&myMsg16);
			}
			else if(len <= PKT_MAX_LENGTH){
				memcpy(myMsgMax.array, in, len);
				call DisseminationUpdateMax.change(&myMsgMax);
			}
			
		}
		return bufPtr;
	}
#endif


/////////////////////////////////////////////////////////////////////////////////////////////
//COLLECTION METHODS 



	//
	// Only the root will receive messages from this interface; its job
	// is to forward them to the serial uart for processing on the pc
	// connected to the sensor network.
	//
	event message_t* CollectionReceive.receive(message_t* msg, void *collectPayload, uint8_t len) {

#if DEBUG == NO_DEBUG
		uint8_t* in = (uint8_t*)collectPayload;
		uint8_t* out;
		
		if (uartbusy == FALSE) {
			
			memcpy(call SerialSend.getPayload(&uartbuf, len), in, len);

			uartlen = len;
			post uartSendTask();
			
		} else {
			// The UART is busy; queue up messages and service them when the
			// UART becomes free.
			message_t *newmsg = call UARTMessagePool.get();
			if (newmsg == NULL) {
				// drop the message on the floor if we run out of queue space.
				
				//#if DEBUG == VERBOSE
				//printf("File: %s - Line:  %d - message dropped - out of queue space \n", __FILE__ ,__LINE__);
				//printfflush(); 
				//#endif
				
				return msg;
			}

			//Serial port busy, so enqueue.
			out = (uint8_t*)call SerialSend.getPayload(newmsg, len);
			if (out == NULL) {
				return msg;
			}
			memcpy(out, in, len);

			if (call UARTQueue.enqueue(newmsg) != SUCCESS) {
				// drop the message on the floor and hang if we run out of
				// queue space without running out of queue space first (this
				// should not occur).
				call UARTMessagePool.put(newmsg);
				
				//#if DEBUG == VERBOSE
				//printf("File: %s - Line:  %d -  message dropped - out of queue space \n", __FILE__ ,__LINE__);
				//printfflush(); 
				//#endif
				
				return msg;
			}
		}
#endif
		return msg;

	}

	//
	// Overhearing other traffic in the network.
	//
	event message_t* Snoop.receive(message_t* msg, void* SnoopPayload, uint8_t len) {
		
		//#if DEBUG == VERBOSE
		//printf("File: %s - Line:  %d - MESSAGE RECEIVED (Snoop.receive) \n", __FILE__ ,__LINE__);
		//printfflush(); 
		//#endif
		return msg;
	}
	
	event bool Intercept.forward(message_t* msg, void* interceptPayload, uint8_t len){
	
		// Here to aggregate two packets we have to create a new array with aggregated
		// data, send it and return FALSE here. If this method returns FALSE the packet 
		// will be not forwarded
		
		//#if DEBUG == VERBOSE
		//printf("File: %s - Line:  %d - Intercept.forward() \n", __FILE__ ,__LINE__);
		//printfflush(); 
		//#endif
		
		return TRUE;
	}

	event void CollectionSend.sendDone(message_t* bufPtr, error_t error) {
		if (&packet == bufPtr) {
			radioLocked = FALSE;
			#if DEBUG == VERBOSE
			printf("File: %s - Line:  %d - Something sent\n", __FILE__ ,__LINE__);
			printfflush(); 
			#endif
		}
	}

//COLLECTION STUFF END
/////////////////////////////////////////////////////////////////////////////////////////////




/////////////////////////////////////////////////////////////////////////////////////////////
//DISSEMINATION STUFF

	event void DisseminationValue8.changed() {
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - DisseminationValue8.changed() \n", __FILE__ ,__LINE__);
		printfflush(); 
		#endif
		
		if(TOS_NODE_ID != 0){
			eight_b_msg_t *newRequest = (eight_b_msg_t *)call DisseminationValue8.get();
			
			signal CommManager.messageReceivedFromBS(newRequest->array);
		}
	}
	
	event void DisseminationValue16.changed() {
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - DisseminationValue16.changed() \n", __FILE__ ,__LINE__);
		printfflush(); 
		#endif
		
		if(TOS_NODE_ID != 0){
			sixteen_b_msg_t *newRequest = (sixteen_b_msg_t *)call DisseminationValue16.get();
			
			signal CommManager.messageReceivedFromBS(newRequest->array);
		}
	}
	
	event void DisseminationValueMax.changed() {
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - DisseminationValueMax.changed() \n", __FILE__ ,__LINE__);
		printfflush(); 
		#endif
		
		if(TOS_NODE_ID != 0){
			max_length_msg_t *newRequest = (max_length_msg_t *)call DisseminationValueMax.get();
			
			signal CommManager.messageReceivedFromBS(newRequest->array);
		}
	}

//DISSEMINATION STUFF END
/////////////////////////////////////////////////////////////////////////////////////////////


}


