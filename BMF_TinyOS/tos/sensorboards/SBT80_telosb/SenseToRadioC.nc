/*****************************************************************************************/
/* Filename : SenseToRadioC.nc                                                           */
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
#include "SBT80ADCmap.h"
#include "printf.h"

module SenseToRadioC
{
  uses {
    interface Boot;
    interface Leds;
    interface Timer<TMilli> as WakeupTimer;
    interface Timer<TMilli> as SwitchTimer;

    interface Read<uint16_t> as ReadVL;
    interface Read<uint16_t> as ReadMIC;
    interface Read<uint16_t> as ReadIR;
    interface Read<uint16_t> as ReadTEMP;
    interface Read<uint16_t> as ReadACCMGx;
    interface Read<uint16_t> as ReadACCMGy;


    interface HplMsp430GeneralIO as SBcontrol;
    interface HplMsp430GeneralIO as SBswitch;

    interface Packet;
    interface AMPacket;
    interface AMSend;
    interface SplitControl as AMControl;
                                         
    //interface Receive;
  }


}


implementation
{

  /* Define variables and constants */  

  #define SAMPLING_FREQUENCY 500  /* should be higher than 100 ms */

  uint16_t ChannelNo = 0; 
  uint16_t counter = 0;
  uint32_t t0,t1 = 0;

  /* global variables to hold sensor readings */ 
  uint16_t VLdata, MICdata, IRdata, TEMPdata, ACCxdata, ACCydata, MGxdata, MGydata;

  uint8_t len;
  bool busy = FALSE;  /* used to keep track if radio is busy */
  bool readACC;
  message_t pkt;
  void task getData();
  void task switchACCMG();

  
  /* Initializations at powerup */  
  event void Boot.booted() {
    call WakeupTimer.startPeriodic(SAMPLING_FREQUENCY);
    call AMControl.start();

    /* Wake up the sensor board */
    call SBcontrol.clr();
    call SBcontrol.makeOutput();
    call SBcontrol.selectIOFunc();
  }


  /* -------- EVENT: WakeupTimer fired --------------- */
  event void WakeupTimer.fired() 
  {
    call Leds.led0On();
    //call Leds.led1On();

    readACC = 1;
    post switchACCMG();
    
    ChannelNo = 0;
    post getData();

    counter++;
    printf("Wakeup Counter: %u \n", counter);
  }




  event void AMControl.startDone(error_t err) {
  }

  event void AMControl.stopDone(error_t err) {
  }

  event void AMSend.sendDone(message_t* msg, error_t error) {
    if (&pkt == msg) {
      busy = FALSE;
      call Leds.led2Off();
      printf("Wakeup Counter after packet Tx: %u \n", counter);
      printf("Sensor readings       :    VL   MIC    IR  TEMP  ACCx  ACCy   MGx   MGy\n");
      printf("range 0-4095 (decimal):  %4u  %4u  %4u  %4u  %4u  %4u  %4u  %4u\n\n", 
			VLdata, MICdata, IRdata, TEMPdata, ACCxdata, ACCydata, MGxdata, MGydata);
    }
  }


  /* -------- TASK : Set the Switch for reading ACcel or MaGnetic sensors --------------- */
  task void switchACCMG() {
    if (readACC) { 
      call SBswitch.clr();  // Low = Acceleration
    }
    else { 
      call SBswitch.set();  // High = Magnetic
    }

    call SBswitch.makeOutput();
    call SBswitch.selectIOFunc();		
  }


  void task getData()
  {
    call ReadVL.read();
  }


  /* -------- EVENT: VL channel sampling done - Read MIC data  --------------- */
  event void ReadVL.readDone(error_t result, uint16_t data)  { 

    SenseToRadioMsg* ptrpkt = (SenseToRadioMsg*)(call Packet.getPayload(&pkt, len));
    ptrpkt->nodeid = TOS_NODE_ID;
    ptrpkt->counter = counter;
    ptrpkt->data[ChannelNo++] = data;

    VLdata = data;
    call ReadMIC.read();
  }


  /* -------- EVENT: MIC channel sampling done - Read IR data  --------------- */
  event void ReadMIC.readDone(error_t result, uint16_t data) { 
    SenseToRadioMsg* ptrpkt = (SenseToRadioMsg*)(call Packet.getPayload(&pkt, len));
    ptrpkt->data[ChannelNo++] = data;

    MICdata = data;
    call ReadIR.read();
  }


  /* -------- EVENT: IR channel sampling done - Read TEMP data  --------------- */
  event void ReadIR.readDone(error_t result, uint16_t data) { 
    SenseToRadioMsg* ptrpkt = (SenseToRadioMsg*)(call Packet.getPayload(&pkt, len));
    ptrpkt->data[ChannelNo++] = data;


    IRdata = data;
    call ReadTEMP.read();
  }


  /* -------- EVENT: TEMP channel sampling done - Read Acceleration channel X --------------- */
  event void ReadTEMP.readDone(error_t result, uint16_t data) { 
    SenseToRadioMsg* ptrpkt = (SenseToRadioMsg*)(call Packet.getPayload(&pkt, len));
    ptrpkt->data[ChannelNo++] = data;

    TEMPdata = data;
    call ReadACCMGx.read();
  }


  /* -------- EVENT: Acceleration or Magnetic chanel X sampling done --------------- */
  event void ReadACCMGx.readDone(error_t result, uint16_t data) { 
    SenseToRadioMsg* ptrpkt = (SenseToRadioMsg*)(call Packet.getPayload(&pkt, len));
    ptrpkt->data[ChannelNo++] = data;

    if (readACC) {
      ACCxdata = data;
    }
    else {
      MGxdata = data;     
    }
    call ReadACCMGy.read();
  }

  /* -------- EVENT: Acceleration or Magnetic chanel Y data ready --------------- */
  event void ReadACCMGy.readDone(error_t result, uint16_t data) { 
    SenseToRadioMsg* ptrpkt = (SenseToRadioMsg*)(call Packet.getPayload(&pkt, len));
    ptrpkt->data[ChannelNo++] = data;
   
    if (readACC) {
      ACCydata = data;
      readACC = 0;
      post switchACCMG();
      call SwitchTimer.startOneShot(1);
    }
    else {
      MGydata = data;
      call Leds.led0Off();

      printf("Time elapsed since Wakeup Timer fired: %lu ms \n", 
          (call WakeupTimer.getNow())-(call WakeupTimer.gett0()));

      if (!busy) {
        if (call AMSend.send(AM_BROADCAST_ADDR, &pkt, sizeof(SenseToRadioMsg)) == SUCCESS) {
          call Leds.led2On();
          busy = TRUE;
        }
      }
    }

  }

  /* -------- EVENT: SwitchTimer fired --------------- */
  event void SwitchTimer.fired() {
      call ReadACCMGx.read();
    }


}


