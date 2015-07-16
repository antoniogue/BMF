configuration EnergyC {
	  provides interface Energy;
}
implementation {
	components EnergyP, LedsC, new TimerMilliC();

	Energy = EnergyP;
	
	EnergyP.Leds -> LedsC;
	EnergyP.General -> TimerMilliC;
	EnergyP.TimerLed -> TimerMilliC;
	//EnergyP.Timer -> Msp430CounterMicroC;	
	
	
	
	//Counters with Micro precision are platform specific,
	//the only counter platform independent is a 32kHz Counter: Counter32khz32C
	#if defined(PLATFORM_TELOSA) || defined(PLATFORM_TELOSB) || defined(PLATFORM_EPIC)
	components Msp430CounterMicroC;
	EnergyP.CounterMicro -> Msp430CounterMicroC;
	EnergyP.Timer -> Msp430CounterMicroC;
	#elif defined(PLATFORM_TYNDALL25) || defined(PLATFORM_MICA2) || defined(PLATFORM_MICAZ) 
	components CounterMicro32C;
	EnergyP.CounterMicro -> CounterMicro32C;
	EnergyP.Timer -> CounterMicro32C;
	#endif
	
	
	
	
	
}
