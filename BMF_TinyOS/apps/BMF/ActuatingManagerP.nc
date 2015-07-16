/**/


module ActuatingManagerP
{
	uses {
		interface Boot;
		
		// ACTUATORS
		interface Leds;
	}
  
	provides interface ActuatingManager;

}

implementation
{
	
	event void Boot.booted() {
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - Actuating Manager booted()\n",__FILE__ ,__LINE__);
		printfflush(); 
		#endif
	}
	
	
	command error_t ActuatingManager.actuate(uint8_t type, uint16_t param){
		
		switch(type){
		
			case ACTUATION_TYPE_LOW_POWER:
				#warning ACTUATION_TYPE_LOW_POWER stuff TO BE IMPLEMENTED! Right now is done in the COMMUNICATION MANAGER!
				break;
				
			case ACTUATION_TYPE_LED:
				
				switch(param){
					case ACTUATION_PARAM_LED_0_TOGGLE:
						call Leds.led0Toggle();
						break;
					case ACTUATION_PARAM_LED_1_TOGGLE:
						call Leds.led1Toggle();
						break;
					case ACTUATION_PARAM_LED_2_TOGGLE:
						call Leds.led2Toggle();
						break;
				}
				break;
				
			default:
				return FAIL;
		
		}
		
		return SUCCESS;
	}

}


