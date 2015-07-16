/**/

configuration CommManagerC{
	provides interface CommManager;
}
implementation {
	components CommManagerP as CM; 
	//components MainC;
	
	CommManager = CM;

	components ActiveMessageC;
	
	//CM.Boot -> MainC.Boot;
	CM.AMControl -> ActiveMessageC;


	// RADIO COLLECTION STUFF: (the BaseStation is the Collector)
	components CollectionC as Collector;  					// Collection layer
	components new CollectionSenderC(AM_BMF_MSG); 			// Sends multihop RF
	components CtpP as CollectP;
	
	CM.CollectionSend -> CollectionSenderC;			// used by a node which wants to send data to the root
	CM.Snoop -> Collector.Snoop[AM_BMF_MSG];
	CM.CollectionReceive -> Collector.Receive[AM_BMF_MSG];
	CM.Intercept -> Collector.Intercept[AM_BMF_MSG];
	CM.RootControl -> Collector;
	CM.CollectionControl -> Collector;
	
	CM.CollectInfo -> CollectP;
	// END OF COLLECTION STUFF
	
	
	// DISSEMINATION STUFF
	components DisseminationC;
	components new DisseminatorC(eight_b_msg_t, DISSEMINATION_KEY_8) as DisseminatorC8;
	components new DisseminatorC(sixteen_b_msg_t, DISSEMINATION_KEY_16) as DisseminatorC16;
	components new DisseminatorC(max_length_msg_t, DISSEMINATION_KEY_MAX) as DisseminatorCMax;
	
	CM.DisseminationControl -> DisseminationC;
	CM.DisseminationUpdate8 -> DisseminatorC8;
	CM.DisseminationValue8 -> DisseminatorC8;
	CM.DisseminationUpdate16 -> DisseminatorC16;
	CM.DisseminationValue16 -> DisseminatorC16;
	CM.DisseminationUpdateMax -> DisseminatorCMax;
	CM.DisseminationValueMax -> DisseminatorCMax;
	// END OF DISSEMINATION STUFF
	
	
	
	//UART stuff
	
#if DEBUG == NO_DEBUG
	components SerialActiveMessageC;        // Serial messaging
	components new SerialAMSenderC(AM_BMF_MSG);   // Sends to the serial port
	components new SerialAMReceiverC(AM_BMF_MSG);   // Receives from the serial port 
	components new PoolC(message_t, 10) as UARTMessagePoolP;
	components new QueueC(message_t*, 10) as UARTQueueP;
	
	CM.Serial -> SerialActiveMessageC;
	CM.SerialSend -> SerialAMSenderC.AMSend;
	CM.SerialReceive -> SerialAMReceiverC.Receive;
	CM.UARTMessagePool -> UARTMessagePoolP;
	CM.UARTQueue -> UARTQueueP;
#endif
	//END of UART stuff

	
	
	// RADIO STUFF for LOW POWER - no uniform way of providing LPL support
	
	#if defined(PLATFORM_MICA2)
	components CC1000CsmaRadioC as LPLProvider;
	#elif defined(PLATFORM_MICAZ) || defined(PLATFORM_AQUISGRAIN) || defined(PLATFORM_TELOSA) || defined(PLATFORM_TELOSB) || defined(PLATFORM_TYNDALL25) || defined(PLATFORM_EPIC) || defined(PLATFORM_SPAN) || defined(PLATFORM_SHIMMER2R)
	components CC2420ActiveMessageC as LPLProvider;
	//#elif defined(PLATFORM_IRIS)
	//components RF230ActiveMessageC as LPLProvider;
	#else
	#error "BMF is only supported for mica2, micaz, telos, tyndall25, epic, aquisgrain, and shimmer nodes"
	#endif
	
	CM.LowPowerListening -> LPLProvider;
	
	//Timer for periodic sending
	components new TimerMilliC() as TimerPeriodicSending;
	CM.TimerPeriodicSending -> TimerPeriodicSending;



}



