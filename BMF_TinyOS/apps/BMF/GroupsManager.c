
#ifndef GROUPS_MANAGER
#define GROUPS_MANAGER


uint8_t myGroups[MAX_GROUPS];
uint8_t myGroupsSize = 0;

/**
*
*/
void GroupsManager_initializeMyGroups(){ 
	uint8_t i;
	myGroupsSize = 0; 
	for(i=0;i<MAX_GROUPS;i++){
		myGroups[i] = GROUP_QUEUE_EMPTY_PLACE;
	}
}

/**
*
*/
uint8_t GroupsManager_getMyGroupsSize() { return myGroupsSize;}

/**
*
*/
bool GroupsManager_isInMyGroups(uint8_t grp){
	uint8_t i = 0;
	for(;i<MAX_GROUPS;i++){
		if(grp == myGroups[i]) return TRUE;
	}
	return FALSE;
}

/**
*
*/
void GroupsManager_processMembership(membership_t *mem){
		
	uint8_t i, j, firstEmptyPosition;
	bool alreadyInMembership = FALSE, emptyPositionFound = FALSE;
	
	
	// ACTIONS ON EXTRACTED DATA
	// the membershipType can be
	//  - MEMBERSHIP_TYPE_UPDATE --> delete old groups and add the groups in the membership
	//  - MEMBERSHIP_TYPE_ADD --> add the groups in the membership to the groups already in memory
	//  - MEMBERSHIP_TYPE_DELETE --> delete from the actual groups the groups in membership
	//  - MEMBERSHIP_TYPE_RESET --> reset the groups 
	
	if(mem->membershipType == MEMBERSHIP_TYPE_UPDATE){ //we must delete old groups (it's enough to initialize the GroupsManager)
		GroupsManager_initializeMyGroups();
		mem->membershipType = MEMBERSHIP_TYPE_ADD; // now UPDATE operation become ADD operation
	}
	else if(mem->membershipType == MEMBERSHIP_TYPE_RESET){
		GroupsManager_initializeMyGroups();
		return;
	}
	
	//add new groups to myGroups
	for(i=0;i<mem->membershipGroups;i++){
		
		if(myGroupsSize == MAX_GROUPS && mem->membershipType == MEMBERSHIP_TYPE_ADD){
			#if DEBUG != NO_DEBUG
			printf("File: %s - Line:  %d - Too many groups added in membership, MAX_GROUPS size = %d. Some groups will be ignored \n", __FILE__ ,__LINE__, MAX_GROUPS);
			printfflush(); 
			#endif
			break;
		}
		else if(myGroupsSize == 0 && mem->membershipType == MEMBERSHIP_TYPE_DELETE){
			#if DEBUG != NO_DEBUG
			printf("File: %s - Line:  %d - No more groups in membership. Some groups to delete will be ignored \n", __FILE__ ,__LINE__);
			printfflush(); 
			#endif
			break;
		}
		else {
			//check if the new group or the group to delete is already in membership
			// and save the first available position and the position where mem->groupIDs[i] is equals to myGroups[j]
			alreadyInMembership = FALSE;
			emptyPositionFound = FALSE;
			firstEmptyPosition=GROUP_QUEUE_EMPTY_PLACE;
			
			for(j=0; j<MAX_GROUPS; j++){
				if(myGroups[j] == mem->groupIDs[i]){
					alreadyInMembership = TRUE;
					
					#if DEBUG == VERBOSE
					printf("Group %d found in membership, ", mem->groupIDs[i]);
					if(mem->membershipType == MEMBERSHIP_TYPE_DELETE) printf("it will be deleted!\n");
					else printf("it will not be added!\n");					
					printfflush();
					#endif
					
					break;
				}
				
				if(mem->membershipType == MEMBERSHIP_TYPE_ADD && myGroups[j] == GROUP_QUEUE_EMPTY_PLACE && !emptyPositionFound){
					emptyPositionFound = TRUE;
					firstEmptyPosition = j;
				}
			}
			
			if(!alreadyInMembership && mem->membershipType == MEMBERSHIP_TYPE_ADD){
				// we have to find the right position where add the new group and then add the group
				
				myGroups[firstEmptyPosition] = mem->groupIDs[i];
				myGroupsSize++;
				
			}
			else if(alreadyInMembership && mem->membershipType == MEMBERSHIP_TYPE_DELETE){
				myGroups[j] = GROUP_QUEUE_EMPTY_PLACE;
				myGroupsSize--;
			}
		}//else
	}//for
	#if DEBUG == VERBOSE
	printf("Now the node belongs to %d groups\n", myGroupsSize);
	printfflush();
	#endif
}


#endif //GROUPS_MANAGER