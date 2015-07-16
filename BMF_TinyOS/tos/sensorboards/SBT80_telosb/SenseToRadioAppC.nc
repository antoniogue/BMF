/*****************************************************************************************/
/* Filename : SenseToRadioAppC.n                                                         */
/*****************************************************************************************/
/*                                                                                       */
/* This code is intended for the EasySen SBT80 Sensor board along with TelosB            */
/* family of wireless motes. It samples the 8 sensor channels in the following order:    */
/* Visual Light (VL), Microphone (MIC), Infrared (IR), Temperature (TEMP),               */ 
/* Acceleration over x axis (ACCx), Acceleration over y axis (ACCy),                     */
/* Magnetic field over x axis (MGx), Magnetic field over y axis (MGy).                   */
/* Sampling is performed over each ADC channel individually.                             */
/*                                                                                       */
/* For further details, please refer to README.txt and                                   */ 
/* Guide_to_TinyOS20_code_for_EasySen_SensorBoards.doc                                   */
/*                                                                                       */
/* Disclaimer: Easysen does not take on any liability for the use of this code.          */
/* This code is not designed for use in critical or life support systems                 */
/* where failure to perform affects safety or effectiveness or results in                */
/* any personal injury to the user. This code is available for free downloading          */
/* at www.easysen.com, and not for sale.                                                 */ 
/*                                                                                       */
/*****************************************************************************************/



#include "Timer.h"
#include "SenseToRadio.h"
#include "printf.h"

configuration SenseToRadioAppC 
{ 
} 

implementation { 

 #define CONFIG_AVCC 

  components SenseToRadioC;
  components MainC;
  components LedsC;
  components new TimerMilliC() as WakeupTimer;
  components new TimerMilliC() as SwitchTimer;

  SenseToRadioC.Boot -> MainC;
  SenseToRadioC.Leds -> LedsC;
  SenseToRadioC.WakeupTimer -> WakeupTimer;
  SenseToRadioC.SwitchTimer -> SwitchTimer;

  
  components HplMsp430GeneralIOC;
  SenseToRadioC.SBcontrol -> HplMsp430GeneralIOC.Port23;
  SenseToRadioC.SBswitch  -> HplMsp430GeneralIOC.Port26;

  
  // --------- ADC related ---------
  components new SBT80_ADCconfigC() as VL;
  components new SBT80_ADCconfigC() as MIC;
  components new SBT80_ADCconfigC() as IR;
  components new SBT80_ADCconfigC() as TEMP;
  components new SBT80_ADCconfigC() as ACCMGx;
  components new SBT80_ADCconfigC() as ACCMGy;	

  SenseToRadioC.ReadVL		-> VL.ReadADC0;
  SenseToRadioC.ReadMIC		-> MIC.ReadADC1;
  SenseToRadioC.ReadIR		-> IR.ReadADC2;
  SenseToRadioC.ReadTEMP	-> TEMP.ReadADC3;
  SenseToRadioC.ReadACCMGx 	-> ACCMGx.ReadADC6;
  SenseToRadioC.ReadACCMGy 	-> ACCMGy.ReadADC7;	




  // --------- Message related ---------
  components ActiveMessageC;
  components new AMSenderC(AM_SENSETORADIOMSG);
  //components new AMReceiverC(AM_SENSETORADIOMSG);

  SenseToRadioC.Packet -> AMSenderC;
  SenseToRadioC.AMPacket -> AMSenderC;
  SenseToRadioC.AMSend -> AMSenderC;
  SenseToRadioC.AMControl -> ActiveMessageC;
  //SenseToRadioC.Receive -> AMReceiverC;



}
