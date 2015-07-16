// $Id: OctopusC.nc,v 2.0.0 2008/30/07 $

/*									tab:4
 * Copyright (c) 2007 University College Dublin.
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for any purpose, without fee, and without written agreement is
 * hereby granted, provided that the above copyright notice and the following
 * two paragraphs appear in all copies of this software.
 *
 * IN NO EVENT SHALL UNIVERSITY COLLEGE DUBLIN BE LIABLE TO ANY
 * PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF 
 * UNIVERSITY COLLEGE DUBLIN HAS BEEN ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * UNIVERSITY COLLEGE DUBLIN SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
 * ON AN "AS IS" BASIS, AND UNIVERSITY COLLEGE DUBLIN HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *
 * Authors:	Antonio Ruzzelli, Raja Jurdak, and Alessio Barbirato
 * Date created: 2008/30/08
 *
 */

/**
 * ruzzelli@ucd.ie, raja.jurdak@ucd.ie, alessiobarbirato@gmail.com
 */

interface Energy {
// it returns the energy spent since the last energy packet transmitted by the node
	command uint32_t getPartialEnergy(uint16_t awakeDutyCycle);
	
//It returns the total energy spent since node initialisation	
	command uint32_t getTotalEnergy(uint16_t awakeDutyCycle);
	
//It returns the energy spent by the radio activity	
	command uint32_t getRadioEnergy(uint16_t awakeDutyCycle);
	
//It returns the energy spent by the MCU	
	command uint32_t getMcuEnergy(uint16_t awakeDutyCycle);
	
//It returns the energy spent by led activity	
	command uint32_t getLedEnergy();

//It is used to timestamp the beginning of radio activity 	
	async command void startSend();

// It is used to timestamp the end of radio activity	
	async command void stopSend();

// It is used to timestamp the beginning of other hardware activity	
	command void startTimer();

// It is used to timestamp the beginning of other hardware activity	
	command void stopTimer();

//This event is signalled everytime the energy module change dynamically the energy scale	
	event void scaleVariation(uint16_t val, uint16_t valMcu, uint16_t valLed, uint16_t valTotal);

//This is for debugging purposes	
	command void startLed(uint8_t l);
	
	command void stopLed(uint8_t l); 
	
	command void toggleLed(uint8_t l);
	
	command uint32_t getRxTimeMicroSec();
	command uint32_t getSendTimeMicroSec();
	command uint32_t getSleepTimeMicroSec();
}
