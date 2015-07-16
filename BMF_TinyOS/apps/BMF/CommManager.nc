/**/

interface CommManager{


event void startDone(error_t err);

command error_t start();

command void sendPacketToBS(packet_to_send_t* pts);
command void sendPeriodicPacketToBS(uint32_t startPeriodTo, uint32_t periodNew, packet_to_send_t* pts);
command void stopPeriodicPacketToBS();

// 2012-02 - modifications to send link quality of the nodes and their parent ID
command void sendPeriodicLinkQuality_ParentIDPacketToBS(uint32_t periodNew, uint8_t requestID);
command uint8_t getNodeLinkQualityParentID_RequestID();
command bool isNodeLinkQualityParentID_Requested();
// 2012-02 end

command void resetCommManager();

command void setLocalDutyCycle(uint16_t newDC);

command uint16_t getLastLocalDutyCycle();

event void messageReceivedFromBS(void* packetPayload);



}