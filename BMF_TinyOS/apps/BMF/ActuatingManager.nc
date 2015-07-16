/**/

/**
 * Interface of the ActuatingManager
 *
 * @author Antonio Guerrieri
 *
 **/
interface ActuatingManager{

	command error_t actuate(uint8_t type, uint16_t param);
	
}