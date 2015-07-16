/**/

#include "Utils.c"

module FunctionCalculatorP
{
  /*uses {
	interface Boot;
  }*/
	provides interface FunctionCalculator;
}

implementation
{

//	event void Boot.booted() {
//	}


	command error_t FunctionCalculator.calculate( uint8_t idFunction, uint32_t* accumulatorArray, uint32_t* newDataArray, uint8_t newDataArraySize,
													uint16_t samplesCollected, uint16_t samplesToCollect){
		
		uint8_t j;
		
		uint32_t accumulator[3];
		uint32_t newData[3];
		
		for(j=0; j<newDataArraySize; j++){
			memcpy(&accumulator[j], accumulatorArray+j, 4);
			memcpy(&newData[j], newDataArray+j, 4);
		}

		
		//I've to think about the synthetic data in two points (depends from the calculation type):
		//	every time a new data comes
		//	when a new data has to be sent
		
		for(j=0; j<newDataArraySize; j++){
		
			
#if DEBUG == VERBOSE
printf("AAAAAAAA - newData = %ld \n", newData[j]);
printfflush(); 
#endif
			
			if( (samplesCollected-1) == 0) accumulator[j] = newData[j];
			else{
			
				switch(idFunction){

					case SYNTHETIC_DATA_NO_SYNTHETIC: 
						accumulator[j] = newData[j];
						break;
#if FUNCTION == STANDARD || FUNCTION == STANDARD_ELABORATION_ONLY
					case SYNTHETIC_DATA_AVERAGE:
						accumulator[j] += newData[j];
						if(samplesCollected == samplesToCollect) accumulator[j] = accumulator[j] / samplesToCollect;
						break;
					case SYNTHETIC_DATA_MIN:
						accumulator[j] = Utils_min(accumulator[j], newData[j]);
						break;
					case SYNTHETIC_DATA_MAX:
						accumulator[j] = Utils_max(accumulator[j], newData[j]);
						break;
#endif
					default: // if we are in the default case, it means we are trying to calculate something which we can't calculate!!!
						return FAIL;
				}
			
			}
		}
		
		
		
		
		for(j=0; j<newDataArraySize; j++){
			memcpy(accumulatorArray+j, &accumulator[j], 4);		
		}
		
		//memcpy(samplesCollectedValue, &samplesCollected, 2);
		
		
		
		return SUCCESS;
	}

}


