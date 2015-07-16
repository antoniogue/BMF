/**/


interface Sensor {
  /**
   * This is called to asks for a reading.
   * 
   * @return SUCCESS if it's everything OK.
   */
  command error_t read();

  /**
   * This is signalled when a read() is completed.
   *
   * @param result says if the reading is successful done (=SUCCESS)
   * @param data the read value
   */
  event void readDone( error_t result, uint16_t data );
}