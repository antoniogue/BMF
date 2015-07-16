/* "Copyright (c) 2008 The Regents of the University  of California.
 * All rights reserved."
 *
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for any purpose, without fee, and without written agreement is
 * hereby granted, provided that the above copyright notice, the following
 * two paragraphs and the author appear in all copies of this software.
 *
 * IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
 * OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
 * CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
 * ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
 * PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS."
 *
 */

/**
 * ACme Energy Monitor
 * @author Fred Jiang <fxjiang@eecs.berkeley.edu>
 * @version $Revision: 1.1 $
 */


#include <Timer.h>
#include "ADE7753.h"

module ACMeterNEWM {
	//provides interface SplitControl; 
	provides interface ACMeterNEW;
	uses interface Boot;
	//uses interface Timer<TMilli> as Timer;
	uses interface Leds;
	uses interface ADE7753;
	uses interface SplitControl as MeterControl;
	uses interface HplMsp430GeneralIO as onoff;
}

implementation {
	uint32_t energy;
	uint8_t stage;
	uint8_t onoff_state;
//	uint16_t interval;
	bool dirty;
	
	enum STAGES {
		INIT,
		SET_MODE,
		SET_GAIN,
		NORMAL
	};

	enum ONOFF_STATE {
		SET,
		CLR
	};
	
	//task void signalStartDone() {
	//	signal SplitControl.startDone(SUCCESS);
	//	return;
	//}

	task void signalSampleDone() {
		uint32_t l_energy;
		atomic l_energy = energy;
		signal ACMeterNEW.readDone(l_energy);
		return;
	}
	
	event void Boot.booted(){
		atomic energy = 0;
		atomic stage = INIT;
		// atomic interval = 1024;
		atomic dirty = TRUE;
		atomic onoff_state = SET;
		call onoff.makeOutput();
		call onoff.set();
		call MeterControl.start();
		//return SUCCESS;
	}

	//command error_t SplitControl.stop() {}
	
	//event void SplitControl.startDone(error_t error) {}

	event void MeterControl.startDone(error_t err) {
		atomic stage = SET_MODE;
		call ADE7753.setReg(ADE7753_MODE, 3, ADE7753_MODE_VAL);
	}

	event void MeterControl.stopDone(error_t err) {}	

	command bool ACMeterNEW.set(bool state) {
		if (state) {
			call onoff.set();
                        onoff_state = SET;
		} else {
			call onoff.clr();
                        onoff_state = CLR;
		}
		return state;
	}

	command bool ACMeterNEW.getState() {
		bool isSet;
		if(onoff_state == SET) {
			isSet = TRUE;
		} else {
			isSet = FALSE;
		}
		return isSet;
	}
	
	command bool ACMeterNEW.toggle() {
		if (onoff_state == SET) {
			onoff_state = CLR;
			call onoff.clr();
			return FALSE;
		} else {
			onoff_state = SET;
			call onoff.set();
			return TRUE;
		}	
	}
	
	//command error_t ACMeter.start(uint16_t interval) {
	//	atomic dirty = TRUE;
	//	if(interval==0) call ADE7753.getReg(ADE7753_RAENERGY, 4);
	//	else call Timer.startOneShot(interval);
	//	return SUCCESS;
	//}

	//command error_t ACMeter.stop() {
    //    atomic dirty = TRUE;
	//	call Timer.stop();
	//	return SUCCESS;
	//}

	// event void ACMeter.sampleDone(uint32_t ener) {}
	
	//event void Timer.fired() {
	//	// at 1Hz, reading RENERGY is equal to power
	//	 call ADE7753.getReg(ADE7753_RAENERGY, 4);
	//	// call ADE7753.getReg(ADE7753_MODE, 3);
	//	
	//}

	async event void ADE7753.getRegDone( error_t error, uint8_t regAddr, uint32_t val, uint16_t len) {
		atomic if (dirty) {
			dirty = FALSE;
			return;
		} else {
			atomic energy = val;
			post signalSampleDone();
		}
	}
	
	task void setReg() {
		call ADE7753.setReg(ADE7753_GAIN, 2, ADE7753_GAIN_VAL);
	}
	
	task void getReg() {
		call ADE7753.getReg(ADE7753_RAENERGY, 4);
	}

	async event void ADE7753.setRegDone( error_t error, uint8_t regAddr, uint32_t val, uint16_t len) {
		switch (stage) {
			case INIT:
				return;
			case SET_MODE:
				atomic stage = SET_GAIN;
				post setReg();
				return;
			case SET_GAIN:
				atomic stage = NORMAL;
				//post signalStartDone();
				//call ADE7753.getReg(ADE7753_RAENERGY, 4);
				post getReg();
				return;
			default:
				return;
		}
	}
	
	command error_t ACMeterNEW.read(){
		atomic if(!dirty){
			call ADE7753.getReg(ADE7753_RAENERGY, 4);
			return SUCCESS;
		}
		else return FAIL;
	}
}
	
