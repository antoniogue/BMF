/****************************************************************\
|POWER_RADIO_OFF is defined in micro Watt --> 1278 * 10^-6 Watt  |
|POWER_RADIO_RX is defined in 10^-4 power--> 564 * 10^-4 Watt    |
|POWER_RADIO_TX is defined in 10^-4 power--> 522 * 10^-4 Watt    |
\****************************************************************/

#include "Energy.h"

module EnergyP {
	provides {
		interface Energy;
	}
	uses{
		interface Leds;
		interface Timer<TMilli> as General;			//It keeps track of the time between two getPartialEnergy calls.
		interface Timer<TMilli> as TimerLed;	
		
		
		//Counters with Micro precision are platform specific,
		//the only counter platform independent is a 32kHz Counter: Counter32khz32C
		#if defined(PLATFORM_TELOSA) || defined(PLATFORM_TELOSB) || defined(PLATFORM_EPIC)
		interface Counter<TMicro, uint16_t> as Timer;						//Timer for the awake time
		interface Counter<TMicro, uint16_t> as CounterMicro;			//Timer for the time spent during the transmission
		#elif defined(PLATFORM_TYNDALL25) || defined(PLATFORM_MICA2) || defined(PLATFORM_MICAZ) 
		interface Counter<TMicro, uint32_t> as Timer;						//Timer for the awake time
		interface Counter<TMicro, uint32_t> as CounterMicro;			//Timer for the time spent during the transmission
		#endif
		
		//interface Counter<TMicro, uint16_t> as Timer;						//Timer for the awake time
		//interface Counter<TMicro, uint16_t> as Msp430CounterMicro;			//Timer for the time spent during the transmission
		
		
	}
}
implementation {

	bool timerIsOff = TRUE;
	bool awakeSent = TRUE;
	bool sendSent = TRUE;
	bool toggle0 = TRUE;
	bool toggle1 = TRUE;
	bool toggle2 = TRUE;
	
	/**********************************************************************************************\
	* The scale variable indicates the accuracy of the energy values stored in the energy variable * 
	*                                                                                              *
	* scale = 1   -> The energy value is returned in nano Joules 10^-9 Joules                      *
	* scale = 10  -> The energy value is returned in 10^-8 Joules                                  *
	* scale = 100 -> The energy value is returned in 10^-7 Joules                                  *
	* . . .                                                                                        *
	*                                                                                              *
	\**********************************************************************************************/
	
	uint16_t scaleRadio = 1;
	uint16_t scaleMcu = 1;
	uint16_t scaleLed = 1;
	uint16_t scaleTotal = 1;

	uint32_t tmpRadio = 0;
	uint32_t tmpMcu = 0;	
	uint32_t tmpLed = 0;
	uint32_t offset = 65535UL;			//Offset for an unsigned 16-bit variable
	uint32_t offset2 = 2147483648UL;		//Offset for a 32-bit variable
	uint32_t energy = 0;
	uint32_t totalEnergy = 0;
	uint32_t totalMcuEnergy = 0;
	uint32_t totalLedEnergy = 0;
	uint32_t radioEnergy = 0;
	uint32_t energyMcu = 0;
	uint32_t rxTime = 0;
	uint32_t sleep = 0;
	uint32_t relSendTime = 0;
	uint32_t sendTime = 0;
	uint32_t startSend = 0;	
	uint32_t stopSend = 0;
	uint32_t relAwakeTime = 0;
	uint32_t awakeTime = 0;	
	uint32_t startTimer = 0;	
	uint32_t stopTimer = 0;
	uint32_t newCall = 0;
	uint32_t oldCall = 0;
	
	uint32_t ledEnergy = 0;
	uint32_t startLed0 = 0;
	uint32_t startLed1 = 0;
	uint32_t startLed2 = 0;
	uint32_t stopLed0 = 0;
	uint32_t stopLed1 = 0;
	uint32_t stopLed2 = 0;
	uint32_t timeLed0 = 0;
	uint32_t timeLed1 = 0;
	uint32_t timeLed2 = 0;
	
	/**********************************************************************************************\
	* This function allows to get the energy consumed between two different calls                  * 
	*                                                                                              *
	* The PartialEnergy value is returned in Joule * 10^-9                                         *
	* It sums the energy consumed during the transmission, receiving and sleeping mode             *
	*                                 															   *
	\**********************************************************************************************/
	
	command uint32_t Energy.getPartialEnergy(uint16_t awakeDutyCycle){
		atomic {
			oldCall = newCall;
			if (call General.isRunning()){
				newCall = call General.getNow();
				call General.stop();
			}	
			else{
				relAwakeTime = 0;
				totalEnergy = 0;
				call TimerLed.startOneShot(100000000);
			}
			call General.startOneShot(1000000);

			if (awakeDutyCycle == 10000){
				awakeTime = newCall - oldCall;
				sendTime = relSendTime;
				sleep = 0;
			}
			else {
				awakeTime = relAwakeTime;
				sendTime = relSendTime;

				sleep = ((newCall - oldCall)*1000 - awakeTime)/1000;
//#if DEBUG == VERBOSE				
//printf("sleep = %ld - real SLEEP = %li\n", sleep, ((newCall - oldCall)*1000 - awakeTime)/1000);
//printfflush();
//#endif
				
				
				awakeSent = TRUE;
				sendSent = TRUE;
			}
			
			rxTime = awakeTime - sendTime;
//#if DEBUG == VERBOSE
//printf("rxTime = %ld \n", rxTime);
//printfflush();
//#endif
		#if DEBUG == VERBOSE
		printf("RX_SEND_SLEEP_TIME %lu;%lu;%lu\n", rxTime, sendTime, sleep*1000);
		printfflush(); 
		#endif
			
			energy = (sendTime * POWER_RADIO_TX / 100 + rxTime * POWER_RADIO_RX / 100 + (sleep * POWER_RADIO_OFF)*10)/10;
			energyMcu = (sendTime * POWER_MCU_TX / 100 + rxTime * POWER_MCU_RX / 100 + (sleep * POWER_MCU_OFF)*10)/10;
			
			return energy;
		}
	}
	
	command uint32_t Energy.getRxTimeMicroSec(){ return rxTime;}
	command uint32_t Energy.getSendTimeMicroSec(){ return sendTime;}
	command uint32_t Energy.getSleepTimeMicroSec(){ return sleep*1000;}
	
	
	/**********************************************************************************************\
	* This function allows to get the energy consumed from the last reset instant                  * 
	\**********************************************************************************************/
	
	command uint32_t Energy.getTotalEnergy(uint16_t duty_cycle){
	
		tmpRadio = call Energy.getRadioEnergy(duty_cycle);
#if DEBUG == VERBOSE
printf("tmpRadio = %ld \n", tmpRadio);
printf("duty_cycle = %d \n", duty_cycle);
printfflush();
#endif
		//tmpMcu = call Energy.getMcuEnergy(duty_cycle);
		//tmpLed = call Energy.getLedEnergy();
		
		tmpMcu = 0;
		tmpLed = 0;
		
		if(offset2 -((tmpRadio/scaleTotal)*scaleRadio + (tmpMcu/scaleTotal)*scaleMcu + (tmpLed/scaleTotal)*scaleLed) > offset2 +((tmpRadio/scaleTotal)*scaleRadio + (tmpMcu/scaleTotal)*scaleMcu + (tmpLed/scaleTotal)*scaleLed)){
			scaleTotal = 10 * scaleTotal;
		}
		signal Energy.scaleVariation(scaleRadio, scaleMcu, scaleLed, scaleTotal);
		totalEnergy = (tmpRadio/scaleTotal)*scaleRadio + (tmpMcu/scaleTotal)*scaleMcu + (tmpLed/scaleTotal)*scaleLed;
		return totalEnergy;
	}
	
	command uint32_t Energy.getRadioEnergy(uint16_t duty_cycle){
		call Energy.getPartialEnergy(duty_cycle);
		if((offset2 - energy/scaleRadio) < radioEnergy){
			scaleRadio = scaleRadio * 10;
			radioEnergy = radioEnergy/10 + energy/scaleRadio;
			signal Energy.scaleVariation(scaleRadio, scaleMcu, scaleLed, scaleTotal);
		}
		else{
			radioEnergy = radioEnergy + energy/scaleRadio;
		}
		return radioEnergy;
	}
	
	command uint32_t Energy.getMcuEnergy(uint16_t duty_cycle){
		call Energy.getPartialEnergy(duty_cycle);
		if((offset2 - energyMcu/scaleMcu) < totalMcuEnergy){
			scaleMcu = scaleMcu * 10;
			totalMcuEnergy = totalMcuEnergy/10 + energyMcu/scaleMcu;
			signal Energy.scaleVariation(scaleRadio, scaleMcu, scaleLed, scaleTotal);
		}
		else{
			totalMcuEnergy = totalMcuEnergy + energyMcu/scaleMcu;
		}
		return totalMcuEnergy;
	}
	
	command uint32_t Energy.getLedEnergy(){		
		ledEnergy = timeLed0 * POWER_LED0 + timeLed1 * POWER_LED1 + timeLed2 * POWER_LED2;
		if((offset2 - ledEnergy/scaleLed) < totalLedEnergy){
			scaleLed = scaleLed * 10;
			totalLedEnergy = totalLedEnergy/10 + ledEnergy/scaleLed;
			signal Energy.scaleVariation(scaleRadio, scaleMcu, scaleLed, scaleTotal);
		}
		else{
			totalLedEnergy = totalLedEnergy + ledEnergy/scaleLed;
		}

		return totalLedEnergy;
	}
	
	//----------Functions to measure the send period-----------//
	
	async command void Energy.startSend(){
//#if DEBUG == VERBOSE
//printf("ENERGY COMPONENT - ENERGY COMPONENT - ENERGY COMPONENT - ENERGY COMPONENT\n");
//printfflush();
//#endif
	atomic {
			startSend = (uint32_t)(call CounterMicro.get() /*Msp430CounterMicro.get()*/);
		}
	}	
	
	async command void Energy.stopSend(){
		atomic {
			stopSend = (uint32_t)(call CounterMicro.get() /*Msp430CounterMicro.get()*/);
			if(sendSent){
				relSendTime = 0;
				sendSent = FALSE;
			}
			if (stopSend > startSend){
				relSendTime = (stopSend - startSend) + relSendTime;
			}
			else{
				relSendTime = (stopSend - startSend + offset) + relSendTime;
			}
		}
	}	
	
	//----------Functions to measure the awake period----------//
	
	command void Energy.startTimer(){
		atomic {
			if (timerIsOff){
				startTimer = (uint32_t)(call Timer.get());
				timerIsOff = FALSE;
			}
		}
	}
	
	command void Energy.stopTimer(){
		atomic {
			if (!timerIsOff){
				if(awakeSent){
					relAwakeTime = 0;
					awakeSent = FALSE;
				}
				stopTimer = (uint32_t)(call Timer.get());
				if (stopTimer > startTimer){
					relAwakeTime = (stopTimer - startTimer) + relAwakeTime;
				}
				else{
					relAwakeTime = (stopTimer - startTimer + offset) + relAwakeTime;
				}
				timerIsOff = TRUE;
				

				
			}
		}
	}
	
	//------------------------events---------------------------//
	
	command void Energy.startLed(uint8_t led){
		atomic{
			if(led == 0){
				startLed0 = call TimerLed.getNow();
				toggle0 = FALSE;
			}
			else if(led == 1){
				startLed1 = call TimerLed.getNow();
				toggle1 = FALSE;
			}
			else if(led == 2){
				startLed2 = call TimerLed.getNow();
				toggle2 = FALSE;
			}
		}
	}
	
	command void Energy.stopLed(uint8_t led){
		atomic{
			if(led == 0){
				stopLed0 = call TimerLed.getNow();
				if(startLed0 == 0){
					startLed0 = stopLed0;
				}
				toggle0 = TRUE;
				if (stopLed0 >= startLed0){
					timeLed0 = timeLed0 + stopLed0 - startLed0;
				}
				else{
					timeLed0 = timeLed0 + stopLed0 - startLed0 + offset2;
				}
			}
			else if(led == 1){
				stopLed1 = call TimerLed.getNow();
				if(startLed1 == 0){
					startLed1 = stopLed1;
				}
				toggle1 = TRUE;
				if (stopLed1 >= startLed1){
					timeLed1 = timeLed1 + stopLed1 - startLed1;
				}
				else{
					timeLed1 = timeLed1 + stopLed1 - startLed1 + offset2;
				}
			}
			else if(led == 2){
				stopLed2 = call TimerLed.getNow();
				if(startLed2 == 0){
					startLed2 = stopLed2;
				}
				toggle2 = TRUE;
				if (stopLed2 >= startLed2){
					timeLed2 = timeLed2 + stopLed2 - startLed2;
				}
				else{
					timeLed2 = timeLed2 + stopLed2 - startLed2 + offset2;
				}
			}
		}
	}
	
	command void Energy.toggleLed(uint8_t led){
		atomic{
			if(led == 0){
				if(!toggle0){
					stopLed0 = call TimerLed.getNow();
					if(startLed0 == 0){
						startLed0 = stopLed0;
					}
					toggle0 = TRUE;
					if (stopLed0 >= startLed0){
						timeLed0 = timeLed0 + stopLed0 - startLed0;
					}
					else{
						timeLed0 = timeLed0 + stopLed0 - startLed0 + offset2;
					}
				}
				else{
					startLed0 = call TimerLed.getNow();
					toggle0 = FALSE;
				}
			}
			else if(led == 1){
				if(!toggle1){
					stopLed1 = call TimerLed.getNow();
					if(startLed1 == 0){
						startLed1 = stopLed1;
					}
					toggle1 = TRUE;
					if (stopLed1 >= startLed1){
						timeLed1 = timeLed1 + stopLed1 - startLed1;
					}
					else{
						timeLed1 = timeLed1 + stopLed1 - startLed1 + offset2;
					}
				}
				else{
					startLed1 = call TimerLed.getNow();
					toggle1 = FALSE;
				}
			}
			else if(led == 2){
				if(!toggle2){
					stopLed2 = call TimerLed.getNow();
					if(startLed2 == 0){
						startLed2 = stopLed2;
					}
					toggle2 = TRUE;
					if (stopLed2 >= startLed2){
						timeLed2 = timeLed2 + stopLed2 - startLed2;
					}
					else{
						timeLed2 = timeLed2 + stopLed2 - startLed2 + offset2;
					}
				}
				else{
					startLed2 = call TimerLed.getNow();
					toggle2 = FALSE;
				}
			}
		}	
	}
	
	default event void Energy.scaleVariation(uint16_t val, uint16_t valMcu, uint16_t valLed, uint16_t valTotal) {}
	
	async event void Timer.overflow() {}
	
	event void General.fired() {}	
	
	event void TimerLed.fired() {}
	
	//async event void Msp430CounterMicro.overflow() {}
	async event void CounterMicro.overflow() {}

	}
