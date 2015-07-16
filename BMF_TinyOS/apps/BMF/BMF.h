

#ifndef BMF_H
#define BMF_H

#if DEBUG != NO_DEBUG
#include "printf.h"
#endif

#ifndef LOW_POWER_LISTENING
#define LOW_POWER_LISTENING
#endif 

uint16_t lastLocalDutyCycle; // 9000 = 90%; 1000 = 10%;
uint8_t isDynamicLengthSystemActivated;

enum {

	BASE_STATION_ID = 0,

	ADDRESSEE_TYPE_NODE = 0,
	ADDRESSEE_TYPE_GROUP = 1,
	
	ADDRESSEE_BROADCAST = 0xFF,

	IS_NOT_FIELD_TRUE = 1,
	IS_NOT_FIELD_FALSE = 0,

	ASSOCIATIVE_RULE_AND = 0,
	ASSOCIATIVE_RULE_OR = 1,

	TIMESCALE_SEC = 0,
	TIMESCALE_MIN = 1,
	TIMESCALE_HOUR = 2,
	TIMESCALE_DAY = 3,
	 
	TIMESCALE_SEC_MULTIPLIER = 0x400, //1024,
	TIMESCALE_MIN_MULTIPLIER = 0xF000, //61440, // 60*TIMESCALE_SEC_MULTIPLIER,
	TIMESCALE_HOUR_MULTIPLIER = 0x384000, //3686400, // 60*TIMESCALE_MIN_MULTIPLIER,
	TIMESCALE_DAY_MULTIPLIER = 0x5460000, //88473600, // 24*TIMESCALE_HOUR_MULTIPLIER,
	
	SECONDS_PER_SAMPLE_TIMESCALE_SEC = 1, 
	SECONDS_PER_SAMPLE_TIMESCALE_MIN = 10,
	SECONDS_PER_SAMPLE_TIMESCALE_HOUR = 60,
	SECONDS_PER_SAMPLE_TIMESCALE_DAY = 1800,

	ACTION_SENSING = 0,
	ACTION_ACTUATING = 1,

	SENSOR_TYPE_NO_SENSOR 		= 0,
	SENSOR_TYPE_ACC_X 			= 1,
	SENSOR_TYPE_ACC_Y 			= 2,
	SENSOR_TYPE_ACC_Z 			= 3,
	SENSOR_TYPE_HUMIDITY 		= 4,
	SENSOR_TYPE_IR	 			= 5,
	SENSOR_TYPE_LIGHT 			= 6,
	SENSOR_TYPE_MAGNETIC_X 		= 7,
	SENSOR_TYPE_MAGNETIC_Y 		= 8,
	SENSOR_TYPE_SOUND 			= 9,
	SENSOR_TYPE_TEMPERATURE 	= 10,
	SENSOR_TYPE_ELECTRICITY 	= 11,
	SENSOR_TYPE_INTERNAL_VOLTAGE= 17,
	
	// THESE ARE NOT SENSOR TYPES - TO REVIEW
	SENSOR_TYPE_ENERGY 			= 12,
	SENSOR_TYPE_RX_TIME 		= 13,
	SENSOR_TYPE_SEND_TIME 		= 14,
	SENSOR_TYPE_SLEEP_TIME		= 15,
	SENSOR_TYPE_RX_SEND_SLEEP_TIME	= 16,
	// 2012-02 - modifications to send link quality of the nodes and their parent ID
	SENSOR_TYPE_NODE_LINK_QUALITY_PARENT_ID 	= 18,
	// 2012-02 end
	
	
	
	
	// IN THE FOLLOWING THERE ARE THE CODES FOR THE FUNCTIONS ACTIVATED ON THE NODE
	FUNCTION_ELABORATION_AND_THRESHOLD_STANDARD_ENABLED = 1, //  includes elaboration and threshold elaborations
	FUNCTION_ELABORATION_STANDARD_ENABLED = 2, // Includes average, max and min
	FUNCTION_THRESHOLD_STANDARD_ENABLED = 3, // includes lower, bigger and transition threshold
	
	FUNCTION_AVERAGE_ENABLED = 50,
	FUNCTION_MIN_ENABLED = 51,
	FUNCTION_MAX_ENABLED = 52,
	
	FUNCTION_THRESHOLD_TYPE_LOWER_ENABLED = 100,
	FUNCTION_THRESHOLD_TYPE_BIGGER = 101,
	FUNCTION_THRESHOLD_TYPE_TRANSITION = 102,
	
	
	
	
	
	// IS THIS AN ACTUATION?
	ACTUATION_TYPE_LOW_POWER	= 1,
	ACTUATION_TYPE_LED			= 2,
	ACTUATION_TYPE_DYNAMIC_LENGHT_PACKET= 3,
	
	ACTUATION_TYPE_DYNAMIC_LENGHT_PARAM_YES 	= 1,
	ACTUATION_TYPE_DYNAMIC_LENGHT_PARAM_NO 		= 0,
	
	ACTUATION_PARAM_LED_0_TOGGLE		= 0,
	ACTUATION_PARAM_LED_1_TOGGLE		= 1,
	ACTUATION_PARAM_LED_2_TOGGLE		= 2,
	
	SENSORBOARD_TYPE_NO_SENSORBOARD		= 0,
	SENSORBOARD_TYPE_SBT80_TELOSB		= 1,
	SENSORBOARD_TYPE_STD_TELOSB			= 2,
	SENSORBOARD_TYPE_TYNSENSORBRD		= 3,
	SENSORBOARD_TYPE_BLACKPLUG			= 4,
	SENSORBOARD_TYPE_ACMEX2				= 5,
	SENSORBOARD_TYPE_SHIMMER2R_KINEMATICS	=6,


	DATA_TYPE_SENSED_DATA = 0,
	DATA_TYPE_THRESHOLD_NOTIFICATION = 1,

	SYNTHETIC_DATA_NO_SYNTHETIC = 0,
	SYNTHETIC_DATA_AVERAGE = 1,
	SYNTHETIC_DATA_MIN = 2,
	SYNTHETIC_DATA_MAX = 3,

	THRESHOLD_TYPE_LOWER = 0,
	THRESHOLD_TYPE_BIGGER = 1,
	THRESHOLD_TYPE_TRANSITION = 2,
	
	THRESHOLD_TRANSITION_DOWN_UP = 1,
	THRESHOLD_TRANSITION_UP_DOWN = 0,
	
	MEMBERSHIP_TYPE_UPDATE = 0, //overwrite
	MEMBERSHIP_TYPE_ADD = 1,
	MEMBERSHIP_TYPE_DELETE = 2,
	MEMBERSHIP_TYPE_RESET = 3,

	// OUTGOING PACKETS
	PKT_TYPE_NEW_NODE = 10,
	PKT_TYPE_DATA = 11,
	PKT_TYPE_ACK = 12,
	PKT_TYPE_INFO = 13,
	// INCOMING PACKETS
	PKT_TYPE_MEMBERSHIP = 1,
	PKT_TYPE_CONFIGURATION_SCHEDULE = 2,
	PKT_TYPE_CONFIGURATION_UNSCHEDULE = 3,
	PKT_TYPE_RESET_NODE = 4,
	
	//PKT_CONFIGURATION_MAX_LENGTH = 19,
	//PKT_MEMBERSHIP_MAX_LENGTH = 16,
	PKT_NEW_NODE_MAX_LENGTH = 3,
	PKT_DATA_MAX_LENGTH = 26,
	PKT_MAX_LENGTH = 26,

	AM_BMF_MSG = 0x1,
	DISSEMINATION_KEY_8 = 0x2,
	DISSEMINATION_KEY_16 = 0x3,
	DISSEMINATION_KEY_MAX = 0x4,
	
	INITIAL_DUTY_CYCLE = 9000,
	
	RESULT_BITS_8  = 2,
	RESULT_BITS_16 = 0,
	RESULT_BITS_32 = 1,
	
	
	//// IN THE FOLLOWING THERE ARE NODE ONLY CONSTANTS ////
	GROUP_QUEUE_EMPTY_PLACE = 0xFF,
	NEW_NODE_PKT_PERIOD = 0x2800, // these are 10 seconds
	DISALIGNMENT_FACTOR = 0x99, // these are 150 ms
	INTERNAL_REQUEST_REQUEST_ID = 0xFF,
	RX_TIME_POSITION 		= 0,
	SEND_TIME_POSITION 		= 1,
	SLEEP_TIME_POSITION		= 2
	
	////////////////////////////////////////////////////////
};


typedef nx_struct eight_b_msg {
  nx_uint8_t array[8];
} eight_b_msg_t;

typedef nx_struct sixteen_b_msg {
  nx_uint8_t array[16];
} sixteen_b_msg_t;

typedef nx_struct max_length_msg {
  nx_uint8_t array[PKT_MAX_LENGTH];
} max_length_msg_t;

typedef struct packet_to_send {
  uint8_t* array;
  uint8_t arrayLength;
} packet_to_send_t;

typedef struct header{
	uint8_t packetType;
	uint8_t senderID;
	uint8_t addresseeType; 
	uint8_t howManyGroups; //group case
	uint8_t isNotGroup[4]; //group case
	uint8_t destGroupIDs[4]; //group case
	uint8_t associativeRules[3]; //group case 
	uint8_t destNodeID; //node case
} header_t;

typedef struct membership{
	uint8_t membershipType;
	uint8_t membershipGroups;
	uint8_t groupIDs[8];
} membership_t;

typedef struct request{
	uint8_t requestID;
	uint8_t periodTimescale; 
	uint8_t periodValue;
	uint8_t lifetimeTimescale;
	uint8_t lifetimeValue;
	uint8_t action; 
	uint8_t sensor_actuatorType;
	uint16_t actuatorParams; // actuator case
	uint8_t dataType; 
	uint8_t syntheticData;
	// if dataType == sensedData from here should be all before accumulators null
	uint8_t thresholdNumber; 
	uint8_t sensorIfThreshold;
	uint8_t thresholdType[2]; 
	uint16_t thresholdValue[2]; 
	uint8_t sensorTypeMoreThreshold[1];
	//uint8_t associatedTimerID;
	///////////////////////////////
	uint32_t accumulator[3];
	//uint32_t accumulator2;
	//uint32_t accumulator3;
	uint16_t samplesToCollect;
	uint16_t samplesCollected;
} request_t;


#endif // BMF_H
