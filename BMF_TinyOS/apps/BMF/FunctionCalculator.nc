/**/

interface FunctionCalculator{

	command error_t calculate( uint8_t idFunction, uint32_t* accumulatorArray, uint32_t* newDataArray, uint8_t newDataArraySize, 
								uint16_t samplesCollected, uint16_t samplesToCollect);
								
}