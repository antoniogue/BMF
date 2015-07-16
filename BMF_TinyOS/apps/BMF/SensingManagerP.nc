/**/
#include "BMF.h"

module SensingManagerP
{
	uses {
		interface Boot;
		
		interface Sensor as AccX;
		interface Sensor as AccY;
		interface Sensor as AccZ;
		interface Sensor as Electricity;
		interface Sensor as Humidity;
		interface Sensor as IR;
		interface Sensor as Light;
		interface Sensor as MagneticFieldX;
		interface Sensor as MagneticFieldY;
		interface Sensor as Sound;
		interface Sensor as Temperature;
		interface Sensor as Voltage;
		
		//ENERGY STUFF
		#ifdef ENERGY_MONITORING
		interface Energy;
		interface CommManager; 
		#endif
	}
  
	provides interface SensingManager;

}

implementation
{
	
	uint8_t lastSensorType;
	
	event void Boot.booted() {
		#if DEBUG == VERBOSE
		printf("File: %s - Line:  %d - Sensing Manager booted()\n",__FILE__ ,__LINE__);
		printfflush(); 
		#endif
	}

	event void AccX.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_ACC_X, &dataNew );
	}
	
	event void AccY.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_ACC_Y, &dataNew );
	}
	
	event void AccZ.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_ACC_Z, &dataNew );
	}
	
	event void Electricity.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_ELECTRICITY, &dataNew );
	}
	
	event void Humidity.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_HUMIDITY, &dataNew );
	}
	
	event void IR.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_IR, &dataNew );
	}
	
	event void Light.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_LIGHT, &dataNew );
	}
	
	event void MagneticFieldX.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_MAGNETIC_X, &dataNew );
	}
	
	event void MagneticFieldY.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_MAGNETIC_Y, &dataNew );
	}
	
	event void Sound.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_SOUND, &dataNew );
	}
	
	event void Temperature.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_TEMPERATURE, &dataNew );
	}
	
	event void Voltage.readDone( error_t result, uint16_t data ){
		uint32_t dataNew = data;
		signal SensingManager.readDone( result, SENSOR_TYPE_INTERNAL_VOLTAGE, &dataNew );
	}

	task void readEnergyStuff(){
		
		#ifdef ENERGY_MONITORING
		
			
			uint32_t totalEnergy;
			uint32_t time[3];
			
			error_t result = SUCCESS;
			

			//TOTALENERGY now is equals to radioEnergy
//			totalEnergy = call Energy.getTotalEnergy(lastLocalDutyCycle); 

totalEnergy = call Energy.getTotalEnergy(call CommManager.getLastLocalDutyCycle()); 			


			time[RX_TIME_POSITION] = call Energy.getRxTimeMicroSec();
			time[SEND_TIME_POSITION] = call Energy.getSendTimeMicroSec();
			time[SLEEP_TIME_POSITION] = call Energy.getSleepTimeMicroSec();
			
			switch(lastSensorType){
				case SENSOR_TYPE_ENERGY:				
					signal SensingManager.readDone( result, lastSensorType, &totalEnergy );
					break;
				case SENSOR_TYPE_RX_TIME:
					signal SensingManager.readDone( result, lastSensorType, time );
					break;
				case SENSOR_TYPE_SEND_TIME:
					signal SensingManager.readDone( result, lastSensorType, (time+1) );
					break;
				case SENSOR_TYPE_SLEEP_TIME:
					signal SensingManager.readDone( result, lastSensorType, (time+2) );
					break;
				case SENSOR_TYPE_RX_SEND_SLEEP_TIME:
					signal SensingManager.readDone( result, lastSensorType, time );
					break;
			}
			
			
		#endif
		
	}
	
	command error_t SensingManager.read(uint8_t sensorType){
		switch(sensorType){

			case SENSOR_TYPE_ACC_X: 				return call AccX.read();
			case SENSOR_TYPE_ACC_Y: 				return call AccY.read();
			case SENSOR_TYPE_ACC_Z: 				return call AccZ.read();
			case SENSOR_TYPE_ELECTRICITY: 			return call Electricity.read();
			case SENSOR_TYPE_HUMIDITY: 				return call Humidity.read();
			case SENSOR_TYPE_IR: 					return call IR.read();
			case SENSOR_TYPE_LIGHT: 				return call Light.read();
			case SENSOR_TYPE_MAGNETIC_X: 			return call MagneticFieldX.read();
			case SENSOR_TYPE_MAGNETIC_Y: 			return call MagneticFieldY.read();
			case SENSOR_TYPE_SOUND: 				return call Sound.read();
			case SENSOR_TYPE_TEMPERATURE: 			return call Temperature.read();
			case SENSOR_TYPE_INTERNAL_VOLTAGE: 		return call Voltage.read();
			
			// not specific sensors cases - can be used only ONE PER TIME:
			case SENSOR_TYPE_ENERGY:				//return energyStuff(sensorType);
			case SENSOR_TYPE_RX_TIME:				//return energyStuff(sensorType);
			case SENSOR_TYPE_SEND_TIME:				//return energyStuff(sensorType);
			case SENSOR_TYPE_SLEEP_TIME:			//return energyStuff(sensorType);
			case SENSOR_TYPE_RX_SEND_SLEEP_TIME:	
				lastSensorType = sensorType;
				post readEnergyStuff();
				return SUCCESS;
			
			default: return FAIL;
		}
	}
	
	#ifdef ENERGY_MONITORING
	event void Energy.scaleVariation(uint16_t valRadio, uint16_t valMcu, uint16_t valLed, uint16_t valTotal){
		//scale = valTotal;
		//scale = valRadio;
		//if(scale==10) scaleChanged = TRUE;
	}

	event void CommManager.startDone(error_t err){}
	event void CommManager.messageReceivedFromBS(void* packetPayload) {}
	#endif

}


